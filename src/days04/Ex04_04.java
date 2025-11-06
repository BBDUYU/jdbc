package days04;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.util.DBConn;

/**
 * @author kenik  
 * @date 2025. 11. 5. 오후 2:31:44
 * @subject   부서 정보 수정... 
 * @content up_updatedept 저장프로시저
 */ 
public class Ex04_04 { // Ex04_04.java

	public static void main(String[] args) {
		// DeptVO [deptno=30, dname=SALES, loc=CHICAGO]
		// 수정할 부서번호 입력
		// 수정할 부서명 입력
		// 수정할 지역명 입력
		int uDeptno;
		String uDname, uLoc;

		// 50	QC	SEOUL
		Scanner scanner = new Scanner(System.in);		
		System.out.print("> 수정할 부서번호 입력 ? ");  // 필수
		uDeptno = Integer.parseInt( scanner.nextLine() );		
		System.out.print("> 수정할 부서명 입력 ? ");   // 입력X, 수정X		
		uDname = scanner.nextLine();
		System.out.print("> 수정할 지역명 입력 ? ");		
		uLoc = scanner.nextLine();

		String sql = "{call up_updatedept(?, ?, ?)}";
		Connection conn = null;
		CallableStatement cstmt = null;

		try {
			conn = DBConn.getConnection();
			cstmt =  conn.prepareCall(sql);
			
			cstmt.setInt(1, uDeptno);
			cstmt.setString(2, uDname);
			cstmt.setString(3, uLoc);
					
			int rowCount = cstmt.executeUpdate();  
			 
			if ( rowCount == 1 ) {
				System.out.println("부서 수정 성공!!!");
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


	}

}
