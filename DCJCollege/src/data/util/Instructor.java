/*
 * [REVIEW 8/30 - Generated from usage hinting]
 * 
 * 
 */
package data.util;

import java.util.List;

public class Instructor {
	String instr_id, first_name, last_name;
	int dept_id;
	List<Course> courses;
	
	public Instructor(String instr_id, String first_name, String last_name,
			int dept_id) {
		super();
		this.instr_id = instr_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.dept_id = dept_id;
	}
	
	public String getInstr_id() {
		return instr_id;
	}
	public void setInstr_id(String instr_id) {
		this.instr_id = instr_id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public int getDept_id() {
		return dept_id;
	}
	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

}
