package days03;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import org.doit.domain.SalgradeVO;

import com.util.DBConn;

/**
 * @author kenik  
 * @date 2025. 11. 5. 오전 7:12:37
 * @subject   
 * @content 
 */ 
public class Ex01 {  // Ex01_02

	public static void main(String[] args) {	
		String sql = "SELECT grade, losal, hisal "
				+ "     , ( "
				+ "         SELECT COUNT(*) "
				+ "         FROM emp "
				+ "         WHERE sal BETWEEN losal  AND hisal "
				+ "       ) cnt "
				+ "FROM salgrade";
		
		// SalgradeVO.java
		SalgradeVO vo = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<SalgradeVO> list = null;
		
		int grade, losal, hisal, cnt;
		
		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
			    list = new ArrayList<SalgradeVO>();
			    do {
			        grade = rs.getInt("grade");
			        losal = rs.getInt("losal");
			        hisal = rs.getInt("hisal");
			        cnt = rs.getInt("cnt");
			        
			        vo = new SalgradeVO();
			        vo.setGrade(grade);
			        vo.setLosal(losal);
			        vo.setHisal(hisal);
			        vo.setCnt(cnt);
			        
			        list.add(vo);
			    } while (rs.next());
			    
			    dispGradeInfo(list);
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
		
		System.out.println(" end ");
	} // main
	
	private static void dispGradeInfo(ArrayList<SalgradeVO> list) {
		// 1등급	(    700~1200 ) - 2명
		Iterator<SalgradeVO> ir = list.iterator();
		while (ir.hasNext()) {
			SalgradeVO vo = ir.next();
			System.out.printf("%d등급	( %d ~ %d ) - %d명\n", 
					vo.getGrade(), vo.getLosal(), vo.getHisal()
					, vo.getCnt() );
			
		} // while
		
	}

} // class


/*
SELECT grade, losal, hisal
, (
    SELECT COUNT(*)
    FROM emp
    WHERE sal BETWEEN losal  AND hisal
  ) cnt
FROM salgrade;
--
SELECT grade, losal, hisal, COUNT(*) cnt
FROM salgrade s JOIN emp e ON sal BETWEEN losal AND hisal
GROUP BY grade, losal, hisal
ORDER BY grade ASC;
*/ 

