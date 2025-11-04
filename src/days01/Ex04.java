package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import org.doit.domain.DeptVO;

public class Ex04 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		org.doit.domain 패키지 + DeptVO.java
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		DeptVO vo=null;
		
		String className="oracle.jdbc.driver.OracleDriver";
		Class.forName(className);
		
		String url="jdbc:oracle:thin:@localhost:1521:xe";
		String user="scott";
		String password="tiger";
		conn=DriverManager.getConnection(url,user,password);
		
		String sql="SELECT * "
				+ "FROM dept";
		
		stmt=conn.createStatement();
		rs=stmt.executeQuery(sql);
		
		// 처리
		int deptno;
		String dname,loc;
	    ArrayList<DeptVO> list = new ArrayList<DeptVO>();
		while(rs.next()) {
			deptno=rs.getInt("deptno");
			dname=rs.getString("dname");
			loc=rs.getString("loc");
			
			vo=new DeptVO(deptno,dname,loc);
			list.add(vo);
		}
		
		rs.close();
		stmt.close();
		conn.close();
		
		//부서정보를 가지고있는 ArrayList를 매개변수로
		//부서정보를 출력하는 메서드
		dispDeptInfo(list);
		System.out.println("end");
	}

	private static void dispDeptInfo(ArrayList<DeptVO> list) {
		 if(  list.isEmpty() ) {
	         System.out.println("조회한 부서가 없다. ");
	         return; 
	      }
	      Iterator<DeptVO> ir = list.iterator();
	      while(ir.hasNext()) {
			DeptVO vo=(DeptVO)ir.next();
			System.out.println(vo);
		}
	}

}
