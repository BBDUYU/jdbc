package org.doit.domain;


//부서 정보를 저장할 클래스
public class DeptVO {	
	private int deptno;
	private String dname;
	private String loc;
	
	private int cnt;

	public int getDeptno() {
		return deptno;
	}

	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	@Override
	public String toString() {
		return "DeptVO [deptno=" + deptno + ", dname=" + dname + ", loc=" + loc + ", cnt=" + cnt + "]";
	}

	public DeptVO(int deptno, String dname, String loc, int cnt) {
		super();
		this.deptno = deptno;
		this.dname = dname;
		this.loc = loc;
		this.cnt = cnt;
	}
	
	public DeptVO(int deptno, String dname, String loc) {
		super();
		this.deptno = deptno;
		this.dname = dname;
		this.loc = loc;
	}
	
	public DeptVO() {
		super();
	}
	
	
	
	
}
