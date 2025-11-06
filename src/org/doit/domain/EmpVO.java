package org.doit.domain;

import java.time.LocalDate;

public class EmpVO {

	private int empno;
	private int mgr;
	private double sal;
	private double comm;
	private int deptno;
	
	private String ename;
	private String job;
	private LocalDate hiredate;
	
	private double pay;
	
	
	
	public double getPay() {
		return pay;
	}
	public void setPay(double pay) {
		this.pay = pay;
	}
	public int getEmpno() {
		return empno;
	}
	public void setEmpno(int empno) {
		this.empno = empno;
	}
	public int getMgr() {
		return mgr;
	}
	public void setMgr(int mgr) {
		this.mgr = mgr;
	}
	public double getSal() {
		return sal;
	}
	public void setSal(double sal) {
		this.sal = sal;
	}
	public double getComm() {
		return comm;
	}
	public void setComm(double comm) {
		this.comm = comm;
	}
	public int getDeptno() {
		return deptno;
	}
	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public LocalDate getHiredate() {
		return hiredate;
	}
	public void setHiredate(LocalDate hiredate) {
		this.hiredate = hiredate;
	}
	public EmpVO(int empno, int mgr, double sal, double comm, int deptno, String ename, String job,
			LocalDate hiredate) {
		super();
		this.empno = empno;
		this.mgr = mgr;
		this.sal = sal;
		this.comm = comm;
		this.deptno = deptno;
		this.ename = ename;
		this.job = job;
		this.hiredate = hiredate;
	}
	public EmpVO() {
		super();
	}
	@Override
	public String toString() {
		return "EmpVO [empno=" + empno + ", mgr=" + mgr + ", sal=" + sal + ", comm=" + comm + ", deptno=" + deptno
				+ ", ename=" + ename + ", job=" + job + ", hiredate=" + hiredate + ", pay=" + pay + "]";
	}
	public EmpVO(int empno, int mgr, double sal, double comm, int deptno, String ename, String job, LocalDate hiredate,
			double pay) {
		super();
		this.empno = empno;
		this.mgr = mgr;
		this.sal = sal;
		this.comm = comm;
		this.deptno = deptno;
		this.ename = ename;
		this.job = job;
		this.hiredate = hiredate;
		this.pay = pay;
	}
	
	
	
	
	
	
}
