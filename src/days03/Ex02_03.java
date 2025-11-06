package days03;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.doit.domain.EmpDeptSalgradeVO;
import org.doit.domain.SalgradeVO;

import com.util.DBConn;

/**
 * @author kenik  
 * @date 2025. 11. 5. 오전 7:12:37
 * @subject   
 * @content 
 */ 
public class Ex02_03 {  // Ex01_02

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
		
		LinkedHashMap<SalgradeVO, ArrayList<EmpDeptSalgradeVO>> map = new LinkedHashMap<>(); 
		
		int grade, losal, hisal, cnt;
		
		int deptno , empno;
		String dname , ename;
		double sal;
				
		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if ( rs.next() ) {				
				do {
					grade = rs.getInt("grade");
					losal = rs.getInt("losal");
					hisal = rs.getInt("hisal");
					cnt = rs.getInt("cnt");
					// key
					vo = new SalgradeVO();
					vo.setGrade(grade);
					vo.setLosal(losal);
					vo.setHisal(hisal);
					vo.setCnt(cnt);

					stmt2 = conn.createStatement();
					rs2 = stmt2.executeQuery(sql2 + grade);
					ArrayList<EmpDeptSalgradeVO> elist = null;
					if( rs2.next() ) {
					    elist = new ArrayList<>();						
					    do {
					    	// deptno, dname , empno, ename, sal
							deptno = rs2.getInt("deptno");
							dname = rs2.getString("dname");
							empno = rs2.getInt("empno");
							ename = rs2.getString("ename");
							sal = rs2.getDouble("sal");
							
							EmpDeptSalgradeVO evo = new EmpDeptSalgradeVO();
							evo.setDname(dname);
							evo.setEmpno(empno);
							evo.setEname(ename);
	
	                    elist.add(evo);
							 
						} while (rs2.next());
					    
					    map.put(vo, elist);
					}else {
						System.out.println("\t 사원 존재 X");
					}
					
					rs2.close();
					stmt2.close();
				} while (rs.next());
				
				dispGradeEmpInfo( map );
			} // if
			
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
	
	
	// 람다식을 사용해서 수정.
	private static void dispGradeEmpInfo(LinkedHashMap<SalgradeVO, 		ArrayList<EmpDeptSalgradeVO>> map) {
		 map.forEach( (vo,list)->{
			 System.out.printf("%d등급	( %d ~ %d ) - %d명\n", 
						vo.getGrade(), vo.getLosal(), vo.getHisal()
						, vo.getCnt() );
			 
			 list.forEach(evo->System.out.printf("\t%d\t%s\t%d\t%s\t%.2f \n", 
					   0
					 , evo.getDname()
					 , evo.getEmpno()
					 , evo.getEname()
					 , 0.0));
		 });
    }
	
	/*
	private static void dispGradeEmpInfo(LinkedHashMap<SalgradeVO, ArrayList<EmpDeptSalgradeVO>> map) {
		
		Set<Entry<SalgradeVO, ArrayList<EmpDeptSalgradeVO>>>  s =	map.entrySet();
		Iterator<Entry<SalgradeVO, ArrayList<EmpDeptSalgradeVO>>>  ir = s.iterator();
		
		while (ir.hasNext()) {
			Entry<SalgradeVO, ArrayList<EmpDeptSalgradeVO>> entry =  ir.next();
			
			SalgradeVO vo = entry.getKey();
			ArrayList<EmpDeptSalgradeVO> list = entry.getValue();
			
			System.out.printf("%d등급	( %d ~ %d ) - %d명\n", 
					vo.getGrade(), vo.getLosal(), vo.getHisal()
					, vo.getCnt() );
			
			Iterator<EmpDeptSalgradeVO> ir2 = list.iterator();
			while (ir2.hasNext()) {
				EmpDeptSalgradeVO evo =  ir2.next();
				System.out.printf("\t%d\t%s\t%d\t%s\t%.2f \n", 
						   0
						 , evo.getDname()
						 , evo.getEmpno()
						 , evo.getEname()
						 , 0.0);
			} 
			
		} // while
    }
    */

	 
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







// ArrayList<SalgradeVO> list
// ArrayList< ArrayList<EmpVO> > gEmpsList





