/*
 * [REVIEW 8/30]
 * 
 * CORRECTIONS MADE:
 *   - Changed package (classes -> data.util)
 *   - Added toString() and compareTo()
 */
package data.util;

import java.io.Serializable;

public class Section implements Comparable<Section>, Serializable {
	private static final long serialVersionUID = -360349305981284528L;
	private int term;
	private String section_id;
	private String course_id;
	private int room;
	private int schedule_id;
	private String instr_id;
	private int capacity;
	private int no_enrolled;

	Section(){}
	
	public Section(int term, String section_id, String course_id, int room, int schedule_id, String instr_id){
		this.term = term;
		this.section_id = section_id;
		this.course_id = course_id;
		this.room = room;
		this.schedule_id = schedule_id;
		this.instr_id = instr_id;
	}
	
	@Override
	public String toString() {
		return section_id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
            return true;
        }
        if (!(obj instanceof Section)) {
            return false;
        }
        Section other = (Section) obj;
        return this.getSection_id().equals(other.getSection_id());
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
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

	public int getRoom() {
		return room;
	}

	public void setRoom(int room) {
		this.room = room;
	}

	public int getSchedule_id() {
		return schedule_id;
	}

	public void setSchedule_id(int schedule_id) {
		this.schedule_id = schedule_id;
	}

	public String getInstr_id() {
		return instr_id;
	}

	public void setInstr_id(String instr_id) {
		this.instr_id = instr_id;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getNo_enrolled() {
		return no_enrolled;
	}

	public void setNo_enrolled(int no_enrolled) {
		this.no_enrolled = no_enrolled;
	}

	@Override
	public int compareTo(Section s) {
		return section_id.compareTo(s.getSection_id());
	}

}