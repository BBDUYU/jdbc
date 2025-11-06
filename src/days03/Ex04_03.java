package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import org.doit.domain.DeptVO;

import com.util.DBConn;

/**
 * @author kenik  
 * @date 2025. 11. 5. 오후 12:35:08
 * @subject   tbl_dept 테이블에    수정/삭제 
 * @content                 <검색>
 *            1) 부서번호 검색 d
 *            2) 부서명 검색   n
 *            3) 지역명 검색   l
 *            4) 부서명 + 지역명 검색   nl
 */ 
public class Ex04_03 {  
	
	public static void main(String[] args) {
		// [1] SELECT 조회
		String sql = "SELECT * "
				+ " FROM tbl_dept "
				+ " WHERE ";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<DeptVO> list = null;
		DeptVO vo = null;

		int deptno;
		String dname, loc;
		
		// 검색조건과 검색어 입력
		String searchCondition, searchWord;		
		Scanner scanner = new Scanner(System.in);		
		System.out.print("> 검색조건 입력 ? ");
		searchCondition = scanner.nextLine();		
		System.out.print("> 검색어 입력 ? ");		
		searchWord = scanner.nextLine();
		
		if ( searchCondition.equalsIgnoreCase("d") ) {  // 부서번호
			sql += " deptno = ? " ;
		}else if (searchCondition.equalsIgnoreCase("n")) { // 부서명
			sql += " REGEXP_LIKE(dname, ? , 'i')";
		}else if (searchCondition.equalsIgnoreCase("l")) { // 지역명
			sql += " REGEXP_LIKE(loc, ?, 'i')";
		}else if (searchCondition.equalsIgnoreCase("nl")) { // 부서명 + 지역명
			sql += " REGEXP_LIKE(dname, ?, 'i') OR REGEXP_LIKE(loc, ?, 'i')";
		}
		
		sql += " ORDER BY deptno ASC ";
		
		System.out.println( sql );
		

		try {
			conn = DBConn.getConnection();
			pstmt = conn.prepareStatement(sql);
			// ?
			pstmt.setString(1, searchWord);
			if(searchCondition.equalsIgnoreCase("nl")) {
				// ?
				pstmt.setString(2, searchWord);
			}
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				list = new ArrayList<DeptVO>();
				do {
					deptno = rs.getInt("deptno");
					dname = rs.getString("dname");
					loc = rs.getString("loc");

					vo = new DeptVO(deptno, dname, loc);

					list.add(vo);

				} while (rs.next());

				dispDeptInfo(list);
			}

		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				DBConn.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		} 

		System.out.println( " end ");

	}

	private static void dispDeptInfo(ArrayList<DeptVO> list) {		
		list.forEach(System.out::println);
	}


} // class






