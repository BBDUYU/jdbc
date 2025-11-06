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
public class Ex02_02 {  // Ex01_02

	public static void main(String[] args) {	
		String sql = "SELECT grade, losal, hisal "
				+ "     , ( "
				+ "         SELECT COUNT(*) "
				+ "         FROM emp "
				+ "         WHERE sal BETWEEN losal  AND hisal "
				+ "       ) cnt "
				+ "FROM salgrade";
		String sql2 = "SELECT d.deptno, dname , empno, ename, sal   "				
				+ "FROM emp e LEFT JOIN dept d ON d.deptno = e.deptno "
				+ "           JOIN salgrade s ON sal BETWEEN losal AND hisal "
				+ "WHERE grade = ";
		
		// SalgradeVO.java
		SalgradeVO vo = null;
		Connection conn = null;
		Statement stmt = null, stmt2 = null;
		ResultSet rs = null, rs2 = null;
		ArrayList<SalgradeVO> list = null;
		
		int grade, losal, hisal, cnt;
		
		int deptno , empno;
		String dname , ename;
		double sal;
				
		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if ( rs.next() ) {
			    list = new ArrayList<SalgradeVO>();
			    do {
			        grade = rs.getInt("grade");
			        losal = rs.getInt("losal");
			        hisal = rs.getInt("hisal");
			        cnt = rs.getInt("cnt");
			        
			        // ✅ builder() → setter 방식으로 수정
			        vo = new SalgradeVO();
			        vo.setGrade(grade);
			        vo.setLosal(losal);
			        vo.setHisal(hisal);
			        vo.setCnt(cnt);
			        list.add(vo);

			        System.out.printf("%d등급\t( %d ~ %d ) - %d명\n", 
			                vo.getGrade(), vo.getLosal(), vo.getHisal(), vo.getCnt());
			        
			        // grade => sql2 쿼리 실행 결과 처리
			        stmt2 = conn.createStatement();
			        rs2 = stmt2.executeQuery(sql2 + grade);
			        
			        if( rs2.next() ) {
			            do {
			                deptno = rs2.getInt("deptno");
			                dname = rs2.getString("dname");
			                empno = rs2.getInt("empno");
			                ename = rs2.getString("ename");
			                sal = rs2.getDouble("sal");
			                
			                System.out.printf("\t%d\t%s\t%d\t%s\t%.2f\n", 
			                            deptno, dname, empno, ename, sal);
			            } while (rs2.next());
			        } else {
			            System.out.println("\t사원 존재 X");
			        }
			        
			        rs2.close();
			        stmt2.close();
			    } while (rs.next());
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

