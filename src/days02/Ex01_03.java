package days02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import org.doit.domain.EmpVO;
/**
 *  1. org.doit.domain EmpVO.java 추가
 *  2. ArrayList<EmpVO> list = null
 *  3. dispEmpInfo(list);
 */


public class Ex01_03 {

	public static void main(String[] args)  {
		//1. jdbc 드라이버
		String className="oracle.jdbc.driver.OracleDriver";
		
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		EmpVO vo=null;
		
		try {
			Class.forName(className);
			//2. db연결
			String url="jdbc:oracle:thin:@localhost:1521:xe";
			String user="scott";
			String password="tiger";
			conn=DriverManager.getConnection(url,user,password);
			//3. crud
			String sql="SELECT * "
					+ "FROM emp";
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			
			int empno,mgr,deptno;
			double sal,comm;
			String ename,job;
			LocalDate hiredate;
			
		    ArrayList<EmpVO> list = new ArrayList<EmpVO>();
		    
			while(rs.next()) {
				empno=rs.getInt("empno");
				mgr=rs.getInt("mgr");
				sal=rs.getDouble("sal");
				comm=rs.getDouble("comm");
				deptno=rs.getInt("deptno");
				
				ename=rs.getString("ename");
				job=rs.getString("job");
				hiredate=rs.getDate("hiredate").toLocalDate();
				
				vo=new EmpVO(empno,mgr,comm,sal, deptno, ename, job, hiredate);
				list.add(vo);			
			}
			dispEmpInfo(list);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//4. db닫기
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(" end ");
		
	}

	private static void dispEmpInfo(ArrayList<EmpVO> list) {
		if(  list.isEmpty() ) {
	         System.out.println("조회한 사원이 없다. ");
	         return; 
	      }
	      Iterator<EmpVO> ir = list.iterator();
	      while(ir.hasNext()) {
	    	  EmpVO vo=(EmpVO)ir.next();
			System.out.println(vo);
		}		
	}
}


