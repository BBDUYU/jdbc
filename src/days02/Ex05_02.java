package days02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.doit.domain.EmpVO;

import com.util.DBConn;

/**
 * @author kenik  
 * @date 2025. 11. 4. 오전 11:15:59
 * @subject   Ex01_02.java -> Ex05_02.java
 * @content   DBConn 클래스 사용. 
 */ 
public class Ex05_02 {

	public static void main(String[] args) {
		
		// 1. JDBC 드라이버(ojdbc6.jar) 로딩 
		String className = "oracle.jdbc.driver.OracleDriver";
		
		Connection conn = null;
		Statement stmt = null; 
		ResultSet rs = null;
		ArrayList<EmpVO> list = null;
		EmpVO vo = null;
		
		try (Scanner scanner = new Scanner(System.in);){ 
			conn = DBConn.getConnection();					
			// 3. CRUD 작업
			list = new ArrayList<EmpVO>();
			
			String sql = "SELECT * "
					+ " FROM emp"
					+ " WHERE ";
			String searchCondition = null;  // 검색조건
			String searchWord = null;   // 검색어
			
			System.out.print("> 검색조건, 검색어 입력하세요 ? ");
			searchCondition = scanner.nextLine();
			searchWord = scanner.nextLine();
			
			if (searchCondition.equals("n")) {
				sql += " REGEXP_LIKE( ename, '"+ searchWord+"', 'i') ";
			}else if (searchCondition.equals("j")) {
				sql += " REGEXP_LIKE( job, '"+ searchWord+"', 'i') ";
			}else if (searchCondition.equals("nj")) {
				sql += " REGEXP_LIKE( ename, '"+ searchWord+"', 'i') OR REGEXP_LIKE( job, '"+ searchWord+"', 'i') ";
			}else if (searchCondition.equals("d")) {
				sql += " deptno = " + searchWord;
			}
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while ( rs.next() ) {
				int empno = rs.getInt("empno");
				String ename = rs.getString("ename"); 
				String job = rs.getString("job");   
				int mgr = rs.getInt("mgr");  
				// String hiredate = rs.getString("hiredate");
				// Date hiredate = rs.getDate("hiredate");
				LocalDate hiredate = rs.getDate("hiredate").toLocalDate();				
				double sal = rs.getDouble("sal");
				double comm = rs.getDouble("comm");
				int deptno = rs.getInt("deptno");
				
				vo = new EmpVO(empno, mgr, sal, comm, deptno, ename, job, hiredate);
				
				list.add(vo);
				
			} // while
			
			dispEmpInfo( list );
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 4. DB 닫기 == Close
			try {
				rs.close();
				stmt.close();
				DBConn.close(); // 
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		} 
		
		System.out.println(" end ");

	} // main

	private static void dispEmpInfo(ArrayList<EmpVO> list) {
		
		if ( list.isEmpty() ) {
			System.out.println("사원이 존재하지 않습니다.");
			return;
		}
		
		Iterator<EmpVO> ir = list.iterator();
		while (ir.hasNext()) {
			EmpVO vo = (EmpVO) ir.next();
			System.out.println( vo );
		} // while
		
	}

} // class

/*
10
20
30
40
부서 번호를 선택하세요 ? 10 엔터

		[10번 부서원(2명)]
		1
		2
*/		
		
		
		

