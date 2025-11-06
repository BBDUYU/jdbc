package days03;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import org.doit.domain.SalgradeVO;

import com.util.DBConn;

/**
 * @author kenik  
 * @date 2025. 11. 5. 오전 10:06:57
 * @subject   
 * @content 
 */ 
public class Ex02 {

	public static void main(String[] args) {
		/*
		String sql = "SELECT \r\n"
				+ "    s.grade,\r\n"
				+ "    s.losal,\r\n"
				+ "    s.hisal,\r\n"
				+ "    COUNT(*) OVER (PARTITION BY s.grade) AS cnt, \r\n"
				+ "    e.deptno,\r\n"
				+ "    d.dname,\r\n"
				+ "    e.empno,\r\n"
				+ "    e.ename,\r\n"
				+ "    e.sal\r\n"
				+ "FROM \r\n"
				+ "    emp e\r\n"
				+ "JOIN \r\n"
				+ "    dept d ON e.deptno = d.deptno\r\n"
				+ "JOIN \r\n"
				+ "    salgrade s ON e.sal BETWEEN s.losal AND s.hisal \r\n"
				+ "ORDER BY \r\n"
				+ "    s.grade, e.sal";
		*/		

	 


	} // main

	 

} // class


/*
[실행결과]
1등급	(     700~1200 ) - 2명                         
   	20	RESEARCH	7369	SMITH	800                
   	30	SALES			7900	JAMES	950
2등급	(	1201~1400 ) - 2명
	30	SALES	7654	MARTIN	2650
	30	SALES	7521	WARD		1750	
3등급	(	1401~2000 ) - 2명
	30	SALES	7499	ALLEN		1900
	30	SALES	7844	TURNER	1500
4등급	(	2001~3000 ) - 4명
    10	ACCOUNTING	7782	CLARK	2450
	20	RESEARCH	7902	FORD	3000
	20	RESEARCH	7566	JONES	2975
	30	SALES	7698	BLAKE	2850
5등급	(	3001~9999 ) - 1명   
	10	ACCOUNTING	7839	KING	5000
 */	
