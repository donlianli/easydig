package com.donlian.bean;


public class CommItem extends Base {
	private static final long serialVersionUID = 1798072689201874726L;
	private String name ;
	private Student student;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
}
