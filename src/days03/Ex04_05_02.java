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
 */ 
public class Ex04_05_02 {     

	public static void main(String[] args) {
		String pDeptno;
		Scanner scanner = new Scanner(System.in);		
		System.out.print("> 삭제할 부서번호 입력 ? ");
		pDeptno = scanner.nextLine(); //  "10, 20, 30"	 
		
		String [] pDeptnoArr = pDeptno.split("\\s*,\\s*");

		String sql = "DELETE FROM tbl_dept "
			      + "  WHERE deptno IN ( ";
		// ?,?,?
		for (int i = 0; i < pDeptnoArr.length; i++) {
			sql += "?, ";
		}
		
		sql = sql.substring(0, sql.length()-2);
		
	    sql += " )";
		
		 System.out.println( sql );

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConn.getConnection();
			pstmt =  conn.prepareStatement(sql);
			// ? ? ?
			for (int i = 0; i < pDeptnoArr.length; i++) {
				pstmt.setInt(i+1, Integer.parseInt(pDeptnoArr[i]) );
			}
			
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


/* [2]
String placeholders = IntStream.range(0, deptnoArr.length)
        .mapToObj(i -> "?")
        .collect(Collectors.joining(", "));

String sql = "DELETE FROM dept WHERE deptno IN (" + placeholders + ")";


// [3]
String placeholders = String.join(", ", 
		java.util.Collections.nCopies(deptnoArr.length, "?")
		);
String sql = "DELETE FROM dept WHERE deptno IN (" + placeholders + ")";

System.out.println( sql );
 */