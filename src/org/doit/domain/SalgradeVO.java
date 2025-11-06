package org.doit.domain;
public class SalgradeVO {
	private int grade;
	private int losal;
	private int hisal;

	private int cnt;  // 해당 등급에 속한 사원수

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getLosal() {
		return losal;
	}

	public void setLosal(int losal) {
		this.losal = losal;
	}

	public int getHisal() {
		return hisal;
	}

	public void setHisal(int hisal) {
		this.hisal = hisal;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	@Override
	public String toString() {
		return "SalgradeVO [grade=" + grade + ", losal=" + losal + ", hisal=" + hisal + ", cnt=" + cnt + "]";
	}

	public SalgradeVO(int grade, int losal, int hisal, int cnt) {
		super();
		this.grade = grade;
		this.losal = losal;
		this.hisal = hisal;
		this.cnt = cnt;
	}

	public SalgradeVO() {
		super();
	}
	
	
}
