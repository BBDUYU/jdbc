package days02;

import org.doit.domain.EmpVO;

public class Ex03 {

	public static void main(String[] args) {
		EmpVO emp1=new EmpVO();
//		EmpVO emp2=new EmpVO(8888,"홍길동");
//		EmpVO emp3=new EmpVO(8888,"홍길동","job");
		//emp2,emp3 등의 객체생성을 하려면
		// -> 생성자 함수를 오버로딩할 수 밖에없다
		/*
		EmpVO emp3=new EmpVO().builder()
							.empno(8888)
							.ename("홍길동")
							.job("잡");
							*/
		/*
		EmpVO emp3=new EmpVO();
		emp3.setEmpno(8888);
		emp3.setEname("홍길동");
		emp3.setJob("잡");
		emp3.setSal(1000);
		*/
		/*
		EmpVO emp3=new EmpVO(8888,0,0,null
					,0,"홍길동","잡",0);
		*/
		
	}
	
}
