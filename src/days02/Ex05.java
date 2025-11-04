package days02;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.util.DBConn;

public class Ex05 {

	public static void main(String[] args) {
		//emp 테이블에서
		//검색
		//검색조건을 선택 ? 
		//n -> 사원명, d -> 부서번호, j -> job, h -> hiredate
		//nh -> 사원명, job 처럼 여러개 입력가능
		//검색어를 입력
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("검색 조건을 선택하세요 (n:사원명, d:부서번호, j:직업, h:입사일자) : ");
		String search = scanner.nextLine().toLowerCase();
		
		System.out.print("검색어를 입력하세요 : ");
		String keyword=scanner.nextLine().toUpperCase();
		
				
		String sql="SELECT * FROM EMP WHERE ";
		boolean where=false;

		if (search.contains("n")) {
			sql += "ename LIKE '%"+keyword+"%'";
			where=true;
		}
		if (search.contains("d")) {
			if(where) sql +=" OR ";
			sql += "deptno = "+keyword;
			where=true;
		}
		if (search.contains("j")) {
			if(where) sql +=" OR ";
			sql += "job LIKE '%"+keyword+"%'";
			where=true;
		}
		if (search.contains("h")) {
			if(where) sql +=" OR ";
			sql += "TO_CHAR(hiredate,'YYYY-MM-DD') LIKE '%"+keyword+"%'";
			where=true;
		}
		
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
	        	do {
	        		System.out.printf("%d\t%s\t%s\t%d\t%s\t%d\t%d\t%d\n",
	                        rs.getInt("EMPNO"),
	                        rs.getString("ENAME"),
	                        rs.getString("JOB"),
	                        rs.getInt("MGR"),
	                        rs.getDate("HIREDATE"),
	                        rs.getInt("SAL"),
	                        rs.getInt("COMM"),
	                        rs.getInt("DEPTNO")
	                       
	                );
	        	}while(rs.next());
	        }else {
	        	
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //4
        DBConn.close();
        
	}

}
