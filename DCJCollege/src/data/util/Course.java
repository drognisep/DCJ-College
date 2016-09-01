/*
 * [REVIEW 8/30]
 * 
 * CORRECTIONS MADE:
 *   - Changed package (classes -> data.util)
 *
 *
 *
 * [REVIEW 8/31]
 *
 * CORRECTIONS MADE:
 * 	 - Removed fees property: duplicate functionality
 *   - Added toString() and compareTo()
 */

package data.util;

import java.io.Serializable;
import java.util.List;

public class Course implements Comparable<Course>, Serializable {
	private static final long serialVersionUID = 4603886173429370941L;
	private String course_id;
	private String course_name;
	private int hours;
	private int dept_id;
	private List<Section> sections;

	Course() {
	}

	public Course(String course_id, String course_name, int hours, int dept_id) {
		this.course_id = course_id;
		this.course_name = course_name;
		this.hours = hours;
		this.dept_id = dept_id;
	}

	public Course(String course_id, String course_name, int hours, int dept_id,
			List<Section> sections) {
		this.course_id = course_id;
		this.course_name = course_name;
		this.hours = hours;
		this.dept_id = dept_id;
		this.sections = sections;
	}
	
	@Override
	public String toString() {
		return course_id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
            return true;
        }
        if (!(obj instanceof Course)) {
            return false;
        }
        Course other = (Course) obj;
        return this.getCourse_id().equals(other.getCourse_id());
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
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

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	@Override
	public int compareTo(Course c) {
		return course_id.compareTo(c.getCourse_id());
	}

}
