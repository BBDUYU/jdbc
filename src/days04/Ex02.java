package days04;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

import com.util.DBConn;

/*
 * 	Statement stmt
 * 	*** [PreparedStatement] pstmt
 * 	CallableStatement cstmt
 * 
 * 	예)회원가입
 * */
public class Ex02 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("> 중복체크할 id 입력");
		int id = scanner.nextInt();

		// String sql="{call UP_IDCHECK(?,?)}";
		String sql = "{call UP_IDCHECK(pid=>?,pcheck=>?)}";

		Connection conn = null;
		CallableStatement cstmt = null;

		conn = DBConn.getConnection();
		try {
			cstmt = conn.prepareCall(sql);
			// ? IN , ? OUT
			cstmt.setInt(1, id);
			// 출력용 파라미터 설정 ?
			cstmt.registerOutParameter(2, Types.INTEGER);
			cstmt.executeQuery();
			// boolean타입 cstmt.execute();
			int check = cstmt.getInt(2);
			if (check == 0) {
				System.out.println("사용가능한 id 입니다");
			} else {
				System.out.println("이미 사용중인 id 입니다");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		DBConn.close();
		System.out.println("end");
	}

}
/*
 * 
 * CREATE OR REPLACE PROCEDURE up_idcheck ( pid IN emp.empno%TYPE , pcheck OUT
 * NUMBER ) IS BEGIN SELECT COUNT(*) INTO pcheck FROM emp WHERE empno=pid; END;
 * 
 * DECLARE vcheck NUMBER(1); BEGIN UP_IDCHECK(9999,vcheck);
 * DBMS_OUTPUT.PUT_LINE(vcheck); END;
 */
