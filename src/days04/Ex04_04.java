package days04;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.util.DBConn;

/**
 * @author kenik  
 * @date 2025. 11. 5. 오후 2:31:44
 * @subject   부서 정보 수정... 
 * @content 
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

		String sql = "UPDATE tbl_dept "
				+ "   SET ";
	 
        if(uDname.length() != 0 && uLoc.length() != 0 ) {
        	sql += " dname=?, loc=? " ;
        }else if ( uDname.length() != 0 ) {
			sql +=  " dname=? ";
		}else if ( uLoc.length() != 0 ) {
			sql += " loc=? ";
		}else {
			System.out.println(" 수정하지 않겠다. ");
			return ;
		}
   
		sql	+= "   WHERE deptno= ?";
		
		System.out.println( sql );

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConn.getConnection();
			pstmt =  conn.prepareStatement(sql);
			
			if(uDname.length() != 0 && uLoc.length() != 0 ) {
	        	// sql += " dname=?, loc=? " ;
				pstmt.setString(1, uDname);
				pstmt.setString(2, uLoc);
				pstmt.setInt(3, uDeptno);
	        }else if ( uDname.length() != 0 ) {
				// sql +=  " dname=? ";
	        	pstmt.setString(1, uDname);
	        	pstmt.setInt(2, uDeptno);
			}else if ( uLoc.length() != 0 ) {
				// sql += " loc=? ";
				pstmt.setString(1, uLoc);
	        	pstmt.setInt(2, uDeptno);
			}
			//   WHERE deptno= ?
			
			int rowCount = pstmt.executeUpdate();  
			 
			if ( rowCount == 1 ) {
				System.out.println("부서 수정 성공!!!");
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


	}

}
