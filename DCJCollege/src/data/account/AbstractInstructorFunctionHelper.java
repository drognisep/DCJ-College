/*
 * [REVIEWED 8/30]
 * 
 * CORRECTIONS MADE:
 *   - Removed superfluous parameter 'term' from dropSection method
 */
package data.account;
import java.util.List;

import bean.account.AccountBean;
import data.util.Course;
import data.util.DbHelperException;
import data.util.ScheduleEntry;
import data.util.Section;
public abstract class AbstractInstructorFunctionHelper {
	
//TODO allow instructor to change password
	
		/**
		 * Allow instructor to create a new course
		 * @param Account Bean with all account information
		 * @param Course object with all information to pass to db.
		 * @return True if the addition was successful, false otherwise. 
		 */
		public abstract boolean addCourse(AccountBean act, Course course);
		
		/**
		 * Allow instructor to drop an existing course
		 * @param AccountBean with all account information
		 * @param courseID The course that the sections should belong to.
		 * @return True if the deletion was successful, false otherwise.
		 */
		public abstract boolean dropCourse(AccountBean act, String courseID);
		
		/**
		 * Allow instructor to add a new section.
		 * @param act Initialized {@code AccountBean} with all account information
		 * @param Section object with information to add section to db.
		 * @return True if the addition was successful, false otherwise.
		 */
		public abstract boolean addSection(AccountBean act, Section section);
		
		/**
		 * Allows instructor to drop an existing course
		 * @param act Initialized {@code AccountBean} with all account information. 
		 * @param sectionID This should be the section
		 * @return True if the deletion was successful, false otherwise.
		 */
		public abstract boolean dropSection(AccountBean act, String sectionID);
		
		/**
		 * Allows an instructor to update current section information
		 * @param act Initialized {@code AccountBean} with all account information.
		 * @param section information
		 * @throws DbHelperException indicating what error occurred.
		 * @return number of rows updated;
		 */
		public abstract boolean updateSection(AccountBean act, Section section);
		
//		/**
//		 * Allow instructor to add a student to an existing class
//		 * @param act Initialized {@code AccountBean} with all account information.
//		 * @param student_id, course_id, and section_id to enroll student in course
//		 * @throws DbHelperException indicating what error occurred.
//		 * @return A double with the total amount of fees due if successful
//		 */
//		public abstract boolean addStudent(AccountBean act, Student student);
//		
//		/**
//		 * Allows instructor to drop a student from a section
//		 * @param act Initialized {@code AccountBean} with all account information.
//		 * @param student_id and the section_id from which he is being dropped
//		 * @return True if the deletion was successful, false otherwise.
//		 */
//		public abstract boolean dropStudent(AccountBean act,String student_id);
//		
		
		/**
		 * Allow instructor to add a student to an existing class
		 * @param act Initialized {@code AccountBean} with all account information.
		 * @param student_id, course_id, and section_id to enroll student in course
		 * @throws DbHelperException indicating what error occurred.
		 * @return A double with the total amount of fees due if successful
		 */
		public abstract boolean addStudentEnrollment(AccountBean act, String student_id, String course_id, String section_id, int term);
		
		/**
		 * Allows instructor to drop a student from a section
		 * @param act Initialized {@code AccountBean} with all account information.
		 * @param student_id and the section_id from which he is being dropped
		 * @return True if the deletion was successful, false otherwise.
		 */
		public abstract boolean dropStudentEnrollment(AccountBean act,String student_id, String section_id, int term);
		
		/**
		 * Allows admin to add instructor to section
		 * @param act Initialized {@code AccountBean} with all account information.
		 * @param student_id, course_id, and section_id to enroll student in course
		 * @throws DbHelperException indicating what error occurred.
		 * @return A double with the total amount of fees due if successful
		 */
		public abstract boolean addInstructor(AccountBean act, String instr_id, String section_id, int term);
		
		/**
		 * Allows admin to delete instructor from section
		 * @param act Initialized {@code AccountBean} with all account information.
		 * @param student_id and the section_id from which he is being dropped
		 * @return True if the deletion was successful, false otherwise.
		 */
		public abstract boolean dropInstructor(AccountBean act, String section_id, String instr_id, int term);
		
		/**
		 * Allows admin to delete instructor from section
		 * @param act Initialized {@code AccountBean} with all account information.
		 * @param student_id and the section_id from which he is being dropped
		 * @return True if the deletion was successful, false otherwise.
		 */
		public abstract List<Instructor> getInstructors(AccountBean act) throws DbHelperException;
		
		/**
		 * Allows instructor to update a grade for a student in a section
		 * @param act Initialized {@code AccountBean} with all account information.
		 * @param student_id and section_id to update.
		 * @param grade to update in the system.
		 * @return True if the update was successful, false otherwise
		 */
		public abstract boolean updateGrade(AccountBean act, String student_id, String section_id, int grade, int term);
		
		/**
		 * Return a schedule object array of instructor classes
		 * @param AccountBean with all account information
		 * @throws DbHelperException indicating what error occurred.
		 * @return An array containing the scheduleIDs of the instructors courses
		 */
		public abstract List<ScheduleEntry> getSchedule(AccountBean act) throws DbHelperException;
	
}


