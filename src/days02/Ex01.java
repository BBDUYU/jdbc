package days02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Ex01 {

	public static void main(String[] args)  {
		//1. jdbc 드라이버
		String className="oracle.jdbc.driver.OracleDriver";
		Connection conn=null;
		try {
			Class.forName(className);
			//2. db연결
			String url="jdbc:oracle:thin:@localhost:1521:xe";
			String user="scott";
			String password="tiger";
			conn=DriverManager.getConnection(url,user,password);
			//3. crud
			System.out.println(conn.isClosed());
			System.out.println(conn);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//4. db닫기
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(" end ");
		
	}
}
