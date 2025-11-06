package days04;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.doit.domain.DeptVO;
import org.doit.domain.EmpVO;

import com.util.DBConn;

/**
 * 2025-11-06
 * PreparedStatement
 * 1) 바로출력
 * 2) Map 저장해서 출력하는 함수만들어서 출력
 */

public class Ex01 {

   public static void main(String[] args) {

      String sql = "SELECT dname, d.deptno "
            + "       , COUNT(ename) OVER(PARTITION BY d.deptno) cnt "
            + "       , empno, ename, hiredate, sal + NVL(comm, 0) pay "
            + "FROM dept d LEFT JOIN emp e ON d.deptno = e.deptno";
      
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      
      int deptno = -1, cnt, empno;
      String dname, ename;
      LocalDateTime hiredate = null;
      double pay;
      int seq = 1;
      
      conn = DBConn.getConnection();
      
      try {
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();
         
         if (rs.next()) {
            
            do {
               
               if (deptno != rs.getInt("deptno")) {                  
                  deptno = rs.getInt("deptno");
                  dname = rs.getString("dname");
                  cnt = rs.getInt("cnt");
                  System.out.printf("[%s(%d)-%d명]\n", dname, deptno, cnt);
                  seq = 1;
                  
                  System.out.println("-".repeat(55));
                     System.out.printf("%s  %s\t%s\t%s\t\t%s\n","seq", "empno", "ename", "hiredate", "pay");
                     System.out.println("-".repeat(55));
                  
                  
                  if (cnt == 0) {
                     System.out.println("사원이 존재하지 않습니다");
                     continue;
                  }
               }
               
               // [ACCOUNTING(10)-2명]
               empno = rs.getInt("empno");
               ename = rs.getString("ename");
               
               try {
                  hiredate = rs.getTimestamp("hiredate").toLocalDateTime();
               } catch (Exception e) {               }
               
               pay = rs.getDouble("pay");
               System.out.printf("%d. %d   %s   %s   %.2f\n",seq++, empno, ename, hiredate, pay);
               
               
            } while (rs.next());
            
         }// if
         
         
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            rs.close();
            pstmt.close();
            DBConn.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      
      
      System.out.println(" end ");
      
   }// main

}// class

/* 실행 결과

[ACCOUNTING(10)-2명]
-------------------------------------------------------
seq  empno   ename   hiredate      pay
-------------------------------------------------------
  1. 7782   CLARK   1981-06-09T00:00   2450.00
  2. 7934   MILLER   1982-01-23T00:00   1300.00

[RESEARCH(20)-3명]
-------------------------------------------------------
seq  empno   ename   hiredate      pay
-------------------------------------------------------
  1. 7902   FORD   1981-12-03T00:00   3000.00
  2. 7369   SMITH   1980-12-17T00:00   800.00
  3. 7566   JONES   1981-04-02T00:00   2850.00

[SALES(30)-6명]
-------------------------------------------------------
seq  empno   ename   hiredate      pay
-------------------------------------------------------
  1. 7900   JAMES   1981-12-03T00:00   950.00
  2. 7844   TURNER   1981-09-08T00:00   1500.00
  3. 7654   Martin   1981-09-28T00:00   2650.00
  4. 7521   WARD   1981-02-22T00:00   1750.00
  5. 7499   ALLEN   1981-02-20T00:00   1900.00
  6. 7698   BLAKE   1981-05-01T00:00   2850.00

[OPERATIONS(40)-0명]
-------------------------------------------------------
seq  empno   ename   hiredate      pay
-------------------------------------------------------
  LinkedHashMap<DeptVO,ArrayList<EmpVO>> lhMap=null;
      DeptVO dvo=null;
      ArrayList<EmpVO> list=null;
      EmpVO evo=null;
 
 * */
