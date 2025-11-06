package days03;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.util.DBConn;

/**
 * @author kenik  
 * @date 2025. 11. 5. 오후 3:11:14
 * @subject   tbl_dept [삭제]
 * @content   삭제할 부서번호을 입력받아서 삭제... 
 *            rs = stmt.executeQuery()
 *            INSERT,UPDATE,DELETE
 *            int rowCount = stmt.executeUpdate()
 */ 
public class Ex03_05 {

	public static void main(String[] args) {
		String pDeptno;
		Scanner scanner = new Scanner(System.in);		
		System.out.print("> 삭제할 부서번호 입력 ? ");
		pDeptno = scanner.nextLine();	 

		String sql = String.format(
				"DELETE FROM tbl_dept "
			+ "  WHERE deptno =  %s ",  pDeptno );
		
		System.out.println( sql );

		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DBConn.getConnection();
			stmt =  conn.createStatement(); 
			
			int rowCount = stmt.executeUpdate(sql);  // INSERT
			 
			if ( rowCount != 0 ) {
				System.out.println("부서 삭제 성공!!!");
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


