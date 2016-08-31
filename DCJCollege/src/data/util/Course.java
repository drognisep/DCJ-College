/*
 * [REVIEW 8/30]
 * 
 * CORRECTIONS MADE:
 *   - Changed package (classes -> data.util)
 */
package data.util;

public class Course {
	private String course_id;
	private String course_name;
	private int hours;
	private int dept_id;
	private int fees;
	
	Course(){}
	
	public Course(String course_id, String course_name, int hours, int dept_id, int fees){
		this.course_id = course_id;
		this.course_name = course_name;
		this.hours = hours;
		this.dept_id = dept_id;
		this.fees = fees;
	}

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getDept_id() {
		return dept_id;
	}

	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}

	public int getFees() {
		return fees;
	}

	public void setFees(int fees) {
		this.fees = fees;
	}

}
