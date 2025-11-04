package days02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.doit.domain.DeptVO;
import org.doit.domain.EmpVO;
/**
 *  1. org.doit.domain EmpVO.java 추가
 *  2. ArrayList<EmpVO> list = null
 *  3. dispEmpInfo(list);
 *  10
	20
	30
	40
부서 번호를 선택하세요 ? 10 엔터

      [10번 부서원(2명)]
      1
      2
 */


public class Ex01_03 {

	public static void main(String[] args)  {
		//1. jdbc 드라이버
		String className="oracle.jdbc.driver.OracleDriver";
		Connection conn=null;
		Statement stmt=null;
		ResultSet ers=null;
		ResultSet drs=null;
		EmpVO evo=null;
		DeptVO dvo=null;
		
		try {
			Class.forName(className);
			//2. db연결
			String url="jdbc:oracle:thin:@localhost:1521:xe";
			String user="scott";
			String password="tiger";
			conn=DriverManager.getConnection(url,user,password);
			// 3-1. 부서정보작업
			String sql="SELECT * FROM DEPT";
		    ArrayList<DeptVO> dlist = new ArrayList<DeptVO>();
		    
		    stmt=conn.createStatement();
			drs=stmt.executeQuery(sql);
			int deptno;
			String dname,loc;
			while(drs.next()) {
				deptno=drs.getInt("deptno");
				dname=drs.getString("dname");
				loc=drs.getString("loc");
				
				dvo=new DeptVO(deptno,dname,loc);
				dlist.add(dvo);
			}
			
			drs.close();
			dispDeptInfo(dlist);
			
			// 부서입력작업
			Scanner scanner= new Scanner(System.in);
			System.out.println("> 부서 번호 입력 : ");
			int pDeptno=scanner.nextInt();

			// 3-2. 사원정보작업
			sql="SELECT * "
					+ "FROM emp "
					+ "WHERE deptno = "+ pDeptno;
			stmt=conn.createStatement();
			ers=stmt.executeQuery(sql);
			
			int empno,mgr;
			double sal,comm;
			String ename,job;
			LocalDate hiredate;
			
		    ArrayList<EmpVO> elist = new ArrayList<EmpVO>();
			while(ers.next()) {
				empno=ers.getInt("empno");
				mgr=ers.getInt("mgr");
				sal=ers.getDouble("sal");
				comm=ers.getDouble("comm");
				deptno=ers.getInt("deptno");
				
				ename=ers.getString("ename");
				job=ers.getString("job");
				hiredate=ers.getDate("hiredate").toLocalDate();
				
				evo=new EmpVO(empno,mgr,comm,sal, deptno, ename, job, hiredate);
				elist.add(evo);			
			}
			dispEmpInfo(elist);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//4. db닫기
			try {
				ers.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(" end ");
		
	}

	private static void dispEmpInfo(ArrayList<EmpVO> list) {
		if(  list.isEmpty() ) {
	         System.out.println("조회한 사원이 없다. ");
	         return; 
	      }
		 System.out.printf("%d 부서원수(%d명)\n",list.get(0).getDeptno(),list.size());

	      Iterator<EmpVO> ir = list.iterator();
	      while(ir.hasNext()) {
	    	  EmpVO evo=(EmpVO)ir.next();
			System.out.println(evo);
		}		
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


