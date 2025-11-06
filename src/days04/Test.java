package days04;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.doit.domain.EmpVO;

import com.util.DBConn;

public class Test {

	public static void main(String[] args) {
		String sqlDept = "SELECT deptno, dname, "
                + "(SELECT COUNT(*) FROM emp WHERE deptno = d.deptno) cnt "
                + "FROM dept d ORDER BY deptno";

        // 각 부서에 속한 사원 목록 (급여+수당 포함 pay)
        String sqlEmp = "SELECT empno, ename, hiredate, sal + NVL(comm,0) AS pay "
                + "FROM emp WHERE deptno = ";

        Connection conn = null;
        Statement stmt = null, stmt2 = null;
        ResultSet rs = null, rs2 = null;

        // key : 부서정보 / value : 해당 부서 사원 리스트
        LinkedHashMap<String, List<EmpVO>> map = new LinkedHashMap<>();

        try {
            conn = DBConn.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlDept);

            while (rs.next()) {
                int deptno = rs.getInt("deptno");
                String dname = rs.getString("dname");
                int cnt = rs.getInt("cnt");

                stmt2 = conn.createStatement();
                rs2 = stmt2.executeQuery(sqlEmp + deptno);

                List<EmpVO> emps = new ArrayList<>();

                while (rs2.next()) {
                    EmpVO emp = new EmpVO();
                    emp.setEmpno(rs2.getInt("empno"));
                    emp.setEname(rs2.getString("ename"));
                    emp.setHiredate(rs2.getDate("hiredate").toLocalDate());
                    emp.setSal(rs2.getDouble("pay")); // pay를 sal 필드에 임시 저장
                    emps.add(emp);
                }


                rs2.close();
                stmt2.close();
            }

            // 출력
            printDeptEmpList(map);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                DBConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.println("end");
    }

    // 출력 메서드
    private static void printDeptEmpList(LinkedHashMap<String, List<EmpVO>> map) {

        map.forEach((deptInfo, empList) -> {
            System.out.printf("[%s]%n", deptInfo);
            System.out.println("-------------------------------------------------------");
            System.out.println("seq     empno   ename   hiredate        pay");
            System.out.println("-------------------------------------------------------");

            int seq = 1;
            for (EmpVO e : empList) {
                System.out.printf("%d.\t%d\t%s\t%s\t%.2f%n",
                        seq++, e.getEmpno(), e.getEname(), e.getHiredate(), e.getSal());
            }
            System.out.println();
        });
	}

}
/*
 * 
 * /* 실행 결과

[ACCOUNTING(10)-2명]
-------------------------------------------------------
seq  empno   ename   hiredate      pay
-------------------------------------------------------
  1. 7782   CLARK   1981-06-09T00:00   2450.00
  2. 7934   MILLER   1982-01-23T00:00   1300.00

[RESEARCH(20)-3명]
-------------------------------------------------------
seq  empno   ename   hiredate      pay
-------------------------------------------------------
  1. 7902   FORD   1981-12-03T00:00   3000.00
  2. 7369   SMITH   1980-12-17T00:00   800.00
  3. 7566   JONES   1981-04-02T00:00   2850.00

[SALES(30)-6명]
-------------------------------------------------------
seq  empno   ename   hiredate      pay
-------------------------------------------------------
  1. 7900   JAMES   1981-12-03T00:00   950.00
  2. 7844   TURNER   1981-09-08T00:00   1500.00
  3. 7654   Martin   1981-09-28T00:00   2650.00
  4. 7521   WARD   1981-02-22T00:00   1750.00
  5. 7499   ALLEN   1981-02-20T00:00   1900.00
  6. 7698   BLAKE   1981-05-01T00:00   2850.00

[OPERATIONS(40)-0명]
-------------------------------------------------------
seq  empno   ename   hiredate      pay
-------------------------------------------------------
  
 
 * */
