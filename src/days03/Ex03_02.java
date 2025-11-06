package days03;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.util.DBConn;

/**
 * @author kenik  
 * @date 2025. 11. 5. 오후 12:35:08
 * @subject   부서명과 지역명을 입력받아서
 * @content   tbl_dept INSERT 작업...
 */ 
public class Ex03_02 {

	public static void main(String[] args) {
		
		String pdname = "QC", ploc = "SEOUL";
		 
		Scanner scanner = new Scanner(System.in);		
		System.out.print("> 부서명 입력 ? ");
		pdname = scanner.nextLine();		
		System.out.print("> 지역명 입력 ? ");		
		ploc = scanner.nextLine();
		
		String sql = String.format(
				"INSERT INTO tbl_dept "
				+ " VALUES ( (SELECT MAX(NVL(deptno,0)) + 10 FROM tbl_dept), '%s', '%s' )", pdname, ploc);
		System.out.println( sql );
		
		Connection conn = null;
		Statement stmt = null;
				
		try {
			conn = DBConn.getConnection();
			stmt =  conn.createStatement();
			// rs = stmt.executeQuery(sql);    SELECT
			int rowCount = stmt.executeUpdate(sql);  // INSERT
			// COMMIT X - 기본은 자동 커밋(기억)
			if ( rowCount == 1 ) {
				System.out.println("부서 추가 성공!!!");
			}
			
		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				DBConn.close();	
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		} 
		System.out.println(" end ");

	} // main

} // class






