package days04;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.util.DBConn;

/**
 * @author kenik  
 * @date 2025. 11. 5. 오후 3:11:14
 * @subject   tbl_dept [삭제]
 * @content   삭제할 부서번호을 입력받아서 삭제... 
 * 				up_deletedept 저장 프로시저
 * 				삭제할 pdeptno 매개변수
 *            
 */ 
public class Ex04_05 {    // Ex04_05_02.java

	public static void main(String[] args) {
		int pDeptno;
		Scanner scanner = new Scanner(System.in);		
		System.out.print("> 삭제할 부서번호 입력 ? ");
		pDeptno = scanner.nextInt();	 

		String sql = "{call up_deletedept(?)}";

		Connection conn = null;
		CallableStatement cstmt = null;

		try {
			conn = DBConn.getConnection();
			cstmt =  conn.prepareCall(sql);
			// ?
			cstmt.setInt(1, pDeptno);
			
			int rowCount = cstmt.executeUpdate();
			 
			if ( rowCount != 0 ) {
				System.out.println("부서 삭제 성공!!!");
			}

		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			try {
				cstmt.close();
				DBConn.close();	
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		} 
		System.out.println(" end ");

	} // main

} // class
/*
 * CREATE OR REPLACE PROCEDURE up_deletedept
(
   pdeptno tbl_dept.deptno%TYPE
)
IS
BEGIN 
  DELETE FROM tbl_dept
  WHERE deptno=deptno;
  COMMIT;
EXCEPTION
   WHEN OTHERS THEN
     ROLLBACK;
     raise_application_error(-20115,'> DELETE Others Error....');
END;    
 * */

