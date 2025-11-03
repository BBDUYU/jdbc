package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ex04 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		org.doit.domain 패키지 + DeptVO.java
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
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
		while(rs.next()) {
			deptno=rs.getInt("deptno");
			dname=rs.getString("dname");
			loc=rs.getString("loc");
			System.out.printf("%d\t%s\t%s\n",deptno,dname,loc);
		}
		
		rs.close();
		stmt.close();
		conn.close();
		System.out.println("end");
	}

}
