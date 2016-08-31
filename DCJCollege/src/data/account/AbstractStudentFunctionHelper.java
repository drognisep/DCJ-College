/*
 * [REVIEWED 8/30]
 * 
 * CORRECTIONS MADE:
 *   - Changed TranscriptEntry package (classes -> data.util)
 *   - int parameter 'term' not needed to drop section, sectionID will suffice
 */

package data.account;
import java.util.List;

import data.util.TranscriptEntry;
import bean.account.AccountBean;
import data.util.DbHelperException;

//TODO :  ALLOW USER TO CHANGE PASSWORD 

public abstract class AbstractStudentFunctionHelper {
	/**
	 * Get all courses available to the given account. If the {@code AccountBean} belongs to an 
	 * instructor, then simply return a zero length array: {@code String[] ary = new String[0]; }.
	 * Will not return courses that the student is already taking.
	 * @param act Initialized {@code AccountBean} with all account information.
	 * @throws DbHelperException indicating what error occurred. 
	 * @return Returns an array of {@code String} with each course as an element with the form:
	 *   "course_no - course_name". If an error occurs, returns a zero length array.
	 */
	public abstract List<String> getAvailableCourses(AccountBean act) throws DbHelperException;
	
	/**
	 * Get all sections for the given course
	 * @param courseID The course that the sections should belong to.
	 * @throws DbHelperException indicating what error occurred.
	 * @return An array containing the sectionIDs of the given course, a zero length array if no sections
	 *   have been added to the course, and an exception otherwise.
	 */
	public abstract List<String> getCourseSections(String courseID) throws DbHelperException;
	
	/**
	 * Get all courses the given account is enrolled in. If the {@code AccountBean} belongs to an 
	 * instructor, then simply return a zero length array: {@code String[] ary = new String[0]; }.
	 * @param act Initialized {@code AccountBean} with all account information.
	 * @throws DbHelperException indicating what error occurred.
	 * @return Returns an array of {@code String} with each course enrolled in as an element with
	 * the form: "course_no - course_name".
	 */
	public abstract List<String> getMyCourses(AccountBean act, int term) throws DbHelperException;
	
	/**
	 * Adds a section as an entry in the enrollment table.
	 * @param act Initialized {@code AccountBean} with all account information. 
	 * @param courseID Course ID in the form: "course_no". This parameter will be used directly in
	 *   the backing {@code PreparedStatement}.
	 * @param sectionID This should be the section
	 * @return True if the addition was successful, false otherwise.
	 */
	public abstract boolean addSection(AccountBean act, String courseID, String sectionID, int term);
	
	/**
	 * Removes a course for the given {@code AccountBean} based on the {@code courseID} value passed.
	 * @param act Initialized {@code AccountBean} with all account information.
	 * @param courseID Course ID in the form: "course_no". This parameter will be used directly in
	 *   the backing {@code PreparedStatement}.
	 * @return True if the deletion was successful, false otherwise.
	 */
	public abstract boolean dropSection(AccountBean act, String courseID, String sectionID);
	
	/**
	 * Get the fee total for the given {@code AccountBean}.
	 * @param act Initialized {@code AccountBean} with all account information.
	 * @throws DbHelperException indicating what error occurred.
	 * @return A double with the total amount of fees due if successful
	 */
	public abstract double getTotalFees(AccountBean act) throws DbHelperException;
	
	/**
	 * Get the previously paid fees for the given {@code AccountBean}.
	 * @param act Initialized {@code AccountBean} with all account information.
	 * @throws DbHelperException indicating what error occurred.
	 * @return A double with the total amount of fees previously paid if successful.
	 */
	public abstract double getPaidFees(AccountBean act) throws DbHelperException;
	
	/**
	 * Update the fees owed by the student by the given amount.
	 * @param act Initialized {@code AccountBean} with all account information.
	 * @param amount Positive amount to reduce the amount of fees owed.
	 * @return The new balance amount
	 * @throws DbHelperException indicating what error occurred.
	 */
	public abstract double payFees(AccountBean act, double amount) throws DbHelperException;
	
	/**
	 * request a full transcript
	 * @param Accountbean will verify student id
	 * @return transcript object
	 * @throws dbhelperexception indicating what error occurred
	 */
	public abstract List<TranscriptEntry> getTranscript(AccountBean act) throws DbHelperException;
}