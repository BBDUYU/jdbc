package days02;

import java.sql.Connection;

import com.util.DBConn;

public class Ex02 {

   public static void main(String[] args) {
      // 모든 프로젝트들은 DB 연동해서 데이터 처리
      // 라이브러리 선언 : com.util.DBConn.java
      
      Connection conn = null;
            for (int i = 0; i < 10; i++) {
               conn = DBConn.getConnection();
               System.out.println(conn);
            }
      
      System.out.println(conn);
      
      DBConn.close();
      
      System.out.println("end");
      
      
   } // main

} // class
