package days04;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.doit.domain.DeptVO;

import com.util.DBConn;

import oracle.jdbc.OracleTypes; 
  


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
		String sql = "{call up_selectdept(?)}";

		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		ArrayList<DeptVO> list = null;
		DeptVO vo = null;

		int deptno;
		String dname, loc;

		try {
			conn = DBConn.getConnection();
			cstmt = conn.prepareCall(sql);
			// ? OUT
//			cstmt.registerOutParameter(1, Types.REF_CURSOR);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();
			rs=(ResultSet) cstmt.getObject(1);
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
				cstmt.close();
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
/*
 * CREATE OR REPLACE PROCEDURE up_selectdept
(
    pdeptcursor OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN pdeptcursor FOR
        SELECT *
        FROM tbl_dept
        WHERE deptno > 0;
--EXCEPTION
END;
 */
