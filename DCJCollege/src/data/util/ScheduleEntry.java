/*
 * [REVIEW 8/30]
 * 
 * CORRECTIONS MADE:
 *   - Changed package (classes -> data.util)
 */
package data.util;

public class ScheduleEntry {

	private int schedule_id;
	private double mon_start;
	private double mon_end;
	private double tues_start;
	private double tues_end;
	private double wed_start;
	private double wed_end;
	private double thur_start;
	private double thur_end;
	private double fri_start;
	private double fri_end;
	private String section_id;
	private String course_id;
	private int room;
	private String instr_last_name;
	
	ScheduleEntry(){}
	
	public ScheduleEntry(int schedule_id, double mon_start, double mon_end, double tues_start, double tues_end, double wed_start, double wed_end,
	double thur_start, double thur_end, double fri_start, double fri_end, String section_id, String course_id, int room, String instr_last_name){
		this.schedule_id = schedule_id;
		this.mon_start = mon_start;
		this.mon_end = mon_end;
		this.tues_start = tues_start;
		this.tues_end = tues_end;
		this.wed_start = wed_start;
		this.wed_end = wed_end;
		this.thur_start = thur_start;
		this.fri_start = fri_start;
		this.fri_end = fri_end;
		this.section_id = section_id;
		this.course_id = course_id;
		this.room = room;
		this.instr_last_name = instr_last_name;
		
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

	public String getInstr_last_name() {
		return instr_last_name;
	}

	public void setInstr_last_name(String instr_last_name) {
		this.instr_last_name = instr_last_name;
	}

	public int getSchedule_id() {
		return schedule_id;
	}

	public void setSchedule_id(int schedule_id) {
		this.schedule_id = schedule_id;
	}

	public double getMon_start() {
		return mon_start;
	}

	public void setMon_start(double mon_start) {
		this.mon_start = mon_start;
	}

	public double getMon_end() {
		return mon_end;
	}

	public void setMon_end(double mon_end) {
		this.mon_end = mon_end;
	}

	public double getTues_start() {
		return tues_start;
	}

	public void setTues_start(double tues_start) {
		this.tues_start = tues_start;
	}

	public double getTues_end() {
		return tues_end;
	}

	public void setTues_end(double tues_end) {
		this.tues_end = tues_end;
	}

	public double getWed_start() {
		return wed_start;
	}

	public void setWed_start(double wed_start) {
		this.wed_start = wed_start;
	}

	public double getWed_end() {
		return wed_end;
	}

	public void setWed_end(double wed_end) {
		this.wed_end = wed_end;
	}

	public double getThur_start() {
		return thur_start;
	}

	public void setThur_start(double thur_start) {
		this.thur_start = thur_start;
	}

	public double getThur_end() {
		return thur_end;
	}

	public void setThur_end(double thur_end) {
		this.thur_end = thur_end;
	}

	public double getFri_start() {
		return fri_start;
	}

	public void setFri_start(double fri_start) {
		this.fri_start = fri_start;
	}

	public double getFri_end() {
		return fri_end;
	}

	public void setFri_end(double fri_end) {
		this.fri_end = fri_end;

	}
    public static String toTimeString(double d) {
        double fractional = d % 1;
        long integral = (long) (d - fractional);
        String ap = "am";
        
        if(integral == 12) ap = "pm";
        else if(integral > 12) {
               integral -= 12;
               ap = "pm";
        } else if(integral == 0) integral = 12;
        
        return String.format("%02d:%s%s", integral, (fractional > 0 ? "30" : "00"), ap);
 }

}
