package days04;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import org.doit.domain.DeptVO;

import com.util.DBConn;

import oracle.jdbc.OracleTypes;

/**
 * @author kenik  
 * @date 2025. 11. 5. 오후 12:35:08
 * @subject   tbl_dept 테이블에    수정/삭제 
 * @content                 <검색>
 *            1) 부서번호 검색 d
 *            2) 부서명 검색   n
 *            3) 지역명 검색   l
 *            4) 부서명 + 지역명 검색   nl
 */ 
public class Ex04_03 {  
   
   public static void main(String[] args) {
      // [1] SELECT 조회
      String sql = "{ call up_searchdept(?,?,?) }";

      Connection conn = null;
      CallableStatement cstmt = null;
      ResultSet rs = null;
      ArrayList<DeptVO> list = null;
      DeptVO vo = null;

      int deptno;
      String dname, loc;
      
      // 검색조건과 검색어 입력
      String searchCondition, searchWord;      
      Scanner scanner = new Scanner(System.in);      
      System.out.print("> 검색조건 입력 ? ");
      searchCondition = scanner.nextLine();      
      System.out.print("> 검색어 입력 ? ");      
      searchWord = scanner.nextLine();


      try {
         conn = DBConn.getConnection();
         cstmt = conn.prepareCall(sql);
         
         cstmt.setString(1, searchCondition);
         cstmt.setString(2, searchWord);
         cstmt.registerOutParameter(3, OracleTypes.CURSOR);
          
         cstmt.executeQuery();
         
         rs =  (ResultSet) cstmt.getObject(3);

         if (rs.next()) {
            list = new ArrayList<DeptVO>();
            do {
               deptno = rs.getInt("deptno");
               dname = rs.getString("dname");
               loc = rs.getString("loc");

               vo = new DeptVO(deptno, dname, loc);

               list.add(vo);

            } while (rs.next());

            dispDeptInfo(list);
         }

      } catch (SQLException e) { 
         e.printStackTrace();
      } finally {
         try {
            rs.close();
            cstmt.close();
            DBConn.close();
         } catch (SQLException e) { 
            e.printStackTrace();
         }
      } 

      System.out.println( " end ");

   }

   private static void dispDeptInfo(ArrayList<DeptVO> list) {      
      list.forEach(System.out::println);
   }


} // class


/*
---------------------------------------------------------------
CREATE OR REPLACE PROCEDURE up_searchdept
(
    -- d 부서번호, n 부서명, l 지역명, nl 부서명+지역명
     psearchCondition VARCHAR2
   , psearchWord VARCHAR2
   , pcur OUT SYS_REFCURSOR
)
IS
   vsql VARCHAR2(2000);
BEGIN
  vsql := 'SELECT * ';
  vsql := vsql || ' FROM tbl_dept ';
  vsql := vsql || ' WHERE ';
 
  IF psearchCondition = 'd' THEN
    vsql := vsql || ' deptno = :psearchWord '  ;
  ELSIF psearchCondition = 'n' THEN
    vsql := vsql || ' REGEXP_LIKE( dname , :psearchWord, ''i'' ) ';
  ELSIF psearchCondition = 'l' THEN
    vsql := vsql || ' REGEXP_LIKE( loc , :psearchWord, ''i'' ) ';
  ELSIF psearchCondition = 'nl' THEN
    vsql := vsql || ' REGEXP_LIKE( dname , :psearchWord, ''i'' ) '  || ' OR REGEXP_LIKE( loc , :psearchWord, ''i'' ) ';
  END IF;
  
  vsql := vsql || ' ORDER BY deptno ASC ';
  
  -- vsql 동적 쿼리의 실행결과  1개 행,   여러 개의 행 
  -- X EXECUTE IMMEDIATE 동적쿼리  구문 X
  IF psearchCondition = 'nl' THEN
     OPEN pcur FOR vsql USING psearchWord , psearchWord;
  ELSE
     OPEN pcur FOR vsql USING psearchWord ;
  END IF;
  
EXCEPTION
  WHEN NO_DATA_FOUND THEN
      RAISE_APPLICATION_ERROR(-20001, '> DEPT DATA NOT FOUND...');
  WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20004, '> SEARCH OTHER ERROR...');
END;
-- Procedure UP_SEARCHDEPT이(가) 컴파일되었습니다.
 * */




