/*
 * [REVIEWED 8/31]
 * 
 * CORRECTIONS MADE:
 *   - Added comparable interface
 */
package data.util;

import java.io.Serializable;

public class TranscriptEntry implements Comparable<TranscriptEntry>, Serializable {
	private static final long serialVersionUID = 5578449613655131977L;
	private String first_name;
	private String last_name;
	private String student_id;
	private String section_id;
	private String course_id;
	private String course_name;
	private int dept_id;
	private int hours;
	private int term;
	private double section_gpa;
	private double overall_gpa;

	TranscriptEntry() {
	}

	public TranscriptEntry(String first_name, String last_name, String student_id,
			String section_id, String course_id, String course_name,
			int dept_id, int hours, int term, double section_gpa,
			double overall_gpa) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.student_id = student_id;
		this.section_id = section_id;
		this.course_id = course_id;
		this.course_name = course_name;
		this.dept_id = dept_id;
		this.hours = hours;
		this.term = term;
		this.section_gpa = section_gpa;
		this.overall_gpa = overall_gpa;
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

	public String getStudent_id() {
		return student_id;
	}

	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}

	public String getSection_id() {
		return section_id;
	}

	public void setSection_id(String section_id) {
		this.section_id = section_id;
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

	public int getDept_id() {
		return dept_id;
	}

	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public double getSection_gpa() {
		return section_gpa;
	}

	public void setSection_gpa(double section_gpa) {
		this.section_gpa = section_gpa;
	}

	public double getOverall_gpa() {
		return overall_gpa;
	}

	public void setOverall_gpa(double overall_gpa) {
		this.overall_gpa = overall_gpa;
	}

	@Override
	public int compareTo(TranscriptEntry e) {
		return Integer.compare(term, e.getTerm());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<tr>");
		sb.append("<td>").append(this.course_id).append("</td>");
		sb.append("<td>").append(this.course_name).append("</td>");
		sb.append("<td>").append(this.term).append("</td>");
		sb.append("<td>").append(this.section_gpa).append("</td>");
		sb.append("<td>").append(this.overall_gpa).append("</td>");
		sb.append("</tr>\n");
		return sb.toString();
	}
	
	public static String getHeaderRow() {
		return "<tr><th>Course ID</th><th>Name</th><th>Term</th><th>Section GPA</th><th>Course GPA</th></tr>\n";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
            return true;
        }
        if (!(obj instanceof TranscriptEntry)) {
            return false;
        }
        TranscriptEntry other = (TranscriptEntry) obj;
        return this.getCourse_id().equals(other.getCourse_id()) && 
        		this.getSection_id().equals(other.getSection_id()) &&
        		this.getStudent_id().equals(other.getStudent_id());
	}
}
