package data.account;
import java.util. *;

import data.util.Course;
import data.util.Section;
import bean.account.AccountBean;
import data.util.DbHelperException;

public abstract class AbstractReportingFunctionHelper {
	
	/**
	 * Allow instructor to get his sections
	 * @param AccountBean with all account information.
	 * @throws DbHelperException indicating what error occurred.
	 * @return An array containing the sectionIDs of the given instructor, a zero length array if no courses
	 *   scheduled for instructor
	 */
	public abstract List<Section> getInstructorSections(AccountBean act, String instr_id) throws DbHelperException;
	
	/**
	 * Allow student to get his sections
	 * @param AccountBean with all account information.
	 * @throws DbHelperException indicating what error occurred.
	 * @return An array containing the sectionIDs of the given student, a zero length array if no sections 
	 * scheduled for instructor
	 */
	public abstract List<Section> getStudentSections(String student_id) throws DbHelperException;
	
	/**
	 * Allow searcher to get all courses in catalog
	 * @throws DbHelperException indicating what error occurred.
	 * @return An array containing every courseID offered.
	 */
	public abstract List<Course> getAllCourses() throws DbHelperException;
	
	/**
	 * Allow searcher to get all sections in catalog
	 * @throws DbHelperException indicating what error occurred.
	 * @return An array containing every sectionID offered.
	 */
	public abstract List<Section> getAllSections() throws DbHelperException;
	
	/**
	 * Get honors list for a department
	 * @param department id of department looking for
	 * @throws DbHelperException indicating what error occurred.
	 * @return An array containing the student_names on honor role for a given dept
	 */
	public abstract List<String> getHonorsList(int dept_id, int term) throws DbHelperException;

}
