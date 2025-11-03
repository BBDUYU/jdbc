package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ex03 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
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
		stmt.executeQuery(sql);
		
		// 처리
		
		rs.close();
		stmt.close();
		conn.close();
		System.out.println("end");
	}

}
