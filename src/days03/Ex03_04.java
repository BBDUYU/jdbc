package days03;

import java.sql.Connection;
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
public class Ex03_04 { // Ex04_04.java

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
		
   //+ "   SET dname='"+uDname+"', loc='"+uLoc+"' "
   //+ String.format("SET dname='%s', loc='%s' ", uDname, uLoc)
        if(uDname.length() != 0 && uLoc.length() != 0 ) {
        	sql += String.format(" dname='%s', loc='%s' ", uDname, uLoc);
        }else if ( uDname.length() != 0 ) {
			sql += String.format(" dname='%s' ", uDname);
		}else if ( uLoc.length() != 0 ) {
			sql += String.format(" loc='%s' ", uLoc);
		}else {
			System.out.println(" 수정하지 않겠다. ");
			return ;
		}
   
		sql	+= "   WHERE deptno= "+uDeptno;
		
		System.out.println( sql );

		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DBConn.getConnection();
			stmt =  conn.createStatement();
			// executeUpdate() : INSERT, UPDATE, DELETE 사용
			int rowCount = stmt.executeUpdate(sql);  // UPDATE
			// COMMIT X - 기본은 자동 커밋(기억)
			if ( rowCount == 1 ) {
				System.out.println("부서 수정 성공!!!");
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


	}

}
