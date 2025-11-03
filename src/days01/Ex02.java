package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Ex02 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Connection conn=null;
		//1. Class.forName() 메서드 - JDBC 드라이버 로딩
		String className="oracle.jdbc.driver.OracleDriver";
		Class.forName(className);
		//2. DriverManager.getConnection() 메서드 - Connection 객체
		String url="jdbc:oracle:thin:@localhost:1521:xe";
		String user="scott";
		String password="tiger";
		conn=DriverManager.getConnection(url,user,password);
		//3. CRUD 작업
		System.out.println(conn.isClosed());
		System.out.println(conn);
		//4. Connection 객체 닫기 (Close)
		conn.close();
		System.out.println("end");
	}

}
