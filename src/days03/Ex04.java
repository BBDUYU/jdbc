package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
public class Ex04 {

	public static void main(String[] args) {
		// [1] 모든 부서  정보를  조회
		String sql = "SELECT * "
				+ " FROM tbl_dept";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<DeptVO> list = null;
		DeptVO vo = null;

		int deptno;
		String dname, loc;

		try {
			conn = DBConn.getConnection();
			pstmt = conn.prepareStatement(sql);
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

} // 
