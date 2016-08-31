/*
 * [REVIEW 8/30]
 * 
 * CORRECTIONS MADE:
 *   - Changed package (classes -> data.util)
 */
package data.util;

public class Section {

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

}