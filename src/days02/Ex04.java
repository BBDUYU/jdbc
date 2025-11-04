package days02;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import com.util.DBConn;

public class Ex04 {

	public static void main(String[] args) {
		//emp + dept + salgrade 조인
		//empno, ename,hiredate,pay,dname,grade

        String sql = "SELECT empno, ename, hiredate, sal + NVL(comm, 0) pay "
        		+ "      , dname "
        		+ "      , grade "
        		+ "FROM emp e LEFT JOIN dept d ON e.deptno = d.deptno "
        		+ "                JOIN salgrade s ON sal BETWEEN losal AND hisal";
        
        int empno;
        String ename;
        LocalDateTime hiredate;
        double pay;
        String dname;
        int grade;
        
		Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        //1 + 2
        conn=DBConn.getConnection();
        
        //3
        try {
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			
			if(rs.next()) {
				//최소한 1명
				do {
					empno=rs.getInt("empno");
					ename=rs.getString("ename");
					hiredate=rs.getTimestamp("hiredate").toLocalDateTime();
					pay=rs.getDouble("pay");
					dname=rs.getString("dname");
					grade=rs.getInt("grade");
					
					System.out.printf("%d\t%s\t%s\t%.2f\t%s\t%d\n",empno,ename,hiredate,pay,dname,grade);
					
				}while(rs.next());
			} else {
				//1명도 없음
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
		        //4
		        DBConn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

        System.out.println(" end ");
        
	}

}
