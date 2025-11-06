package days03;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.doit.domain.DeptVO;

import com.util.DBConn;

/**
 * @author kenik  
 * @date 2025. 11. 5. 오후 12:17:09
 * @subject   SELECT 
 * @content   INSERT, UPDATE, DELETE
 *            
 *             tbl_dept 테이블 사용
 */ 
public class Ex03 {
	
	public static void main(String[] args) {
		// [1] SELECT 조회
		String sql = "SELECT * "
				+ " FROM tbl_dept";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<DeptVO> list = null;
		DeptVO vo = null;
		
		int deptno;
		String dname, loc;
		
		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
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
				stmt.close();
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

}








