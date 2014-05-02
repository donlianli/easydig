package com.donlian.jdk.seri;

public class Person implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Person(String fn, String ln, int a) {
		this.firstName = fn;
		this.lastName = ln;
		this.age = a;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public Person getSpouse() {
		return spouse;
	}

	public void setFirstName(String value) {
		firstName = value;
	}

	public void setLastName(String value) {
		lastName = value;
	}

	public void setAge(int value) {
		age = value;
	}

	public void setSpouse(Person value) {
		spouse = value;
	}

	public String toString() {
		return "[Person: firstName=" + firstName + " lastName=" + lastName
				+ " age=" + age + " spouse=" + spouse.getFirstName() + "]";
	}

	private String firstName;
	private String lastName;
	private int age;
	private Person spouse;

}
