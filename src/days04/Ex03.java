package days04;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

import com.util.DBConn;

/*
 * 아이디 : empno
 * 비밀번호 : ename
 * 
 * up_login 저장프로시저
 * */
public class Ex03 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("> 로그인할 ID, PWD 입력");
		int id = scanner.nextInt();//7369
		String pwd=scanner.next();//SMITH

		// String sql="{call UP_IDCHECK(?,?)}";
		String sql = "{call UP_LOGIN(?,?,?)}";

		Connection conn = null;
		CallableStatement cstmt = null;

		conn = DBConn.getConnection();
		try {
			cstmt = conn.prepareCall(sql);
			// ? IN , ? OUT
			cstmt.setInt(1, id);
			cstmt.setString(2, pwd);
			// 출력용 파라미터 설정 ?
			cstmt.registerOutParameter(3, Types.INTEGER);
			cstmt.executeQuery();
			// boolean타입 cstmt.execute();
			int check = cstmt.getInt(3);
			if (check == 0) {
				System.out.println("로그인 성공하였습니다");
			} else if(check==1) {
				System.out.println("비밀번호가 틀렸습니다");
			} else {
				System.out.println("ID가 존재하지 않습니다");
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
 * ---------------------- CREATE OR REPLACE PROCEDURE up_login ( pid IN
 * emp.empno%TYPE , ppwd IN emp.ename%TYPE , pcheck OUT NUMBER -- 0 인증성공, 1
 * (ID존재 O, pwd X), -1(ID 존재 X) ) IS vpwd emp.ename%TYPE; BEGIN -- 9999
 * ID(empno) X pcheck=0, -- O pcheck=1 SELECT COUNT(*) INTO pcheck FROM emp
 * WHERE empno = pid;
 * 
 * IF pcheck = 1 THEN SELECT ename INTO vpwd FROM emp WHERE empno = pid;
 * 
 * IF vpwd = ppwd THEN -- 로그인 성공 pcheck := 0; ELSE -- 아이디 존재 O, 비밀번호 X pcheck :=
 * 1; END IF; ELSE -- 아이디 존재 X pcheck := -1; END IF; --EXCEPTION END; --
 * Procedure UP_LOGIN이(가) 컴파일되었습니다.
 * 
 * DECLARE vcheck NUMBER(1); BEGIN UP_LOGIN( 9999, 'SMITHHH', vcheck );
 * DBMS_OUTPUT.PUT_LINE( vcheck ); END;
 * 
 */
