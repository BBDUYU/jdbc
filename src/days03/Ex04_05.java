package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
public class Ex04_05 {    // Ex04_05_02.java

	public static void main(String[] args) {
		int pDeptno;
		Scanner scanner = new Scanner(System.in);		
		System.out.print("> 삭제할 부서번호 입력 ? ");
		pDeptno = scanner.nextInt();	 

		String sql = "DELETE FROM tbl_dept "
			      + "  WHERE deptno =  ? ";
		
		// System.out.println( sql );

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConn.getConnection();
			pstmt =  conn.prepareStatement(sql);
			// ?
			pstmt.setInt(1, pDeptno);
			
			int rowCount = pstmt.executeUpdate();
			 
			if ( rowCount != 0 ) {
				System.out.println("부서 삭제 성공!!!");
			}

		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				DBConn.close();	
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		} 
		System.out.println(" end ");

	} // main

} // class


