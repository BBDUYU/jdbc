package org.doit.domain;

import java.time.LocalDateTime;

public class EmpDeptSalgradeVO {

	private int empno;
	private String ename;
	private LocalDateTime hiredate;
	private double pay;
	
	private String dname;
	
	private int grade;

	
	
	public int getEmpno() {
		return empno;
	}

	public void setEmpno(int empno) {
		this.empno = empno;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public LocalDateTime getHiredate() {
		return hiredate;
	}

	public void setHiredate(LocalDateTime hiredate) {
		this.hiredate = hiredate;
	}

	public double getPay() {
		return pay;
	}

	public void setPay(double pay) {
		this.pay = pay;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "EmpDeptSalgradeVO [empno=" + empno + ", ename=" + ename + ", hiredate=" + hiredate + ", pay=" + pay
				+ ", dname=" + dname + ", grade=" + grade + "]";
	}

	public EmpDeptSalgradeVO(int empno, String ename, LocalDateTime hiredate, double pay, String dname, int grade) {
		super();
		this.empno = empno;
		this.ename = ename;
		this.hiredate = hiredate;
		this.pay = pay;
		this.dname = dname;
		this.grade = grade;
	}

	public EmpDeptSalgradeVO() {
		super();
	}

	
	
}
