package days04;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Scanner;

import org.doit.domain.DeptVO;

import com.util.DBConn;



/**
 * @author kenik  
 * @date 2025. 11. 5. 오후 3:33:02
 * @subject   Statement
 * @content   [ PreparedStatement  *** ]
 *            CallableStatement  저장프로시저,저장함수 
 *            
 *            Ex03.java -> Ex04.java 수정
 */ 
public class Ex04_02 {

	public static void main(String[] args) {
		String pdname = "QC", ploc = "SEOUL";
		 
		Scanner scanner = new Scanner(System.in);		
		System.out.print("> 부서명 입력 ? ");
		pdname = scanner.nextLine();		
		System.out.print("> 지역명 입력 ? ");		
		ploc = scanner.nextLine();
		
		// ? 바인딩변수 사용해서 쿼리 작성
		String sql = "{call up_insertdept(?,?)}";
		//System.out.println( sql );
		
		Connection conn = null;
		CallableStatement cstmt = null;
				
		try {
			conn = DBConn.getConnection();
			cstmt =  conn.prepareCall(sql);
			// ? ? IN 매개변수가 누락되었다.
			cstmt.setString(1, pdname);
			cstmt.setString(2, ploc);
			int rowCount = cstmt.executeUpdate();
			
			if ( rowCount == 1 ) {
				System.out.println("부서 추가 성공!!!");
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

	private static void dispDeptInfo(ArrayList<DeptVO> list) {		
		list.forEach(System.out::println);
	}

} // 
/*
CREATE OR REPLACE PROCEDURE up_insertdept
(
   pdname tbl_dept.dname%TYPE := NULL
   , ploc tbl_dept.loc%TYPE := NULL
)
IS
   vdeptno tbl_dept.deptno%TYPE;
BEGIN 
  SELECT MAX(NVL(deptno,0)) + 10 INTO vdeptno
  FROM tbl_dept;
  
  INSERT INTO tbl_dept  
  VALUES ( vdeptno, pdname , ploc );
  
  COMMIT;
EXCEPTION
   WHEN OTHERS THEN
     ROLLBACK;
     raise_application_error(-20114,'> INSERT Others Error....');
END;   
 */
