/*
 * [REVIEWED 8/29]
 * 
 * CORRECTIONS MADE:
 *   - Changed AccountBean method to match master version (getID -> getId)
 *   - Integrated abstract class changes as needed (getFees -> getTotalFees, +getPaidFees)
 *   - Reworded exception messages to more clearly indicate error context
 *   - getConnection() should be used in all methods that utilize a Connection object
 *     * Talk about why
 *   - Corrected minor typos
 * 
 * CORRECTIONS RECOMMENDED:
 *   - Change getTotalFees method to return fees for _total_ fees for all courses for specified student_id.
 * 
 * [REVIEWED 8/30]
 * 
 * CORRECTIONS MADE:
 *   - Changed return types of some methods to match new abstract class
 *   - Removed array return variables and conversion to int[]
 *   - Changed package to aid organization (data.account -> data.account.oracle.xe)
 *   - Implement getTranscript method as outlined in the abstract class
 *   
 * [REVIEWED 8/31]
 * 
 * CORRECTIONS MADE:
 *   - changed return types of some methods from List<String> to List<Section> or List<Course>
 *   - Changed a few queries to accomodate missed changes in db structure and naming
 */
package data.account.oracle.xe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import bean.account.AccountBean;
import data.account.AbstractStudentFunctionHelper;
import data.account.AccountBeanHelper;
import data.util.Course;
import data.util.DbHelperException;
import data.util.Section;
import data.util.TranscriptEntry;

public class OracleStudentFunctionHelper extends AbstractStudentFunctionHelper {

	private Connection connection = null;

	public Connection getConnection() throws DbHelperException {
		return AccountBeanHelper.getInstance().getConnection();
	}

	@Override
	public List<Course> getAvailableCourses(AccountBean act)
			throws DbHelperException {
		ArrayList<Course> courseArray = new ArrayList<Course>(
				new LinkedHashSet<Course>());
		Connection connection = getConnection();
		String id = act.getId();
		if (id.charAt(0) == 's') {
			try {
				PreparedStatement pstmt = connection
						.prepareStatement("select * from courses c, enrollment e where c.course_id = e.course_id and student_id != ?");
				pstmt.setString(1, id);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					String course_id = rs.getString("course_id");
					String course_name = rs.getString("course_name");
					int hours = rs.getInt("hours");
					int dept_id = rs.getInt("dept_id");
					Course course = new Course(course_id, course_name, hours,
							dept_id);
					courseArray.add(course);
				}

			} catch (SQLException sqle) {
				sqle.printStackTrace();
				throw new DbHelperException("Error getting available courses");
			} catch (Exception e) {
				e.printStackTrace();
				throw new DbHelperException();
			}
			return courseArray;
		}
		Collections.sort(courseArray);
		return courseArray;
	}

	@Override
	public List<Section> getCourseSections(String courseID)
			throws DbHelperException {
		ArrayList<Section> arraySections = new ArrayList<Section>(
				new LinkedHashSet<Section>());
		Connection connection = getConnection();
		try {
			PreparedStatement pstmt = connection
					.prepareStatement("Select * from sections where course_id = ?");
			pstmt.setString(1, courseID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int term = rs.getInt("term");
				String section_id = rs.getString("section_id");
				String course_id = rs.getString("course_id");
				int room = rs.getInt("room");
				int schedule_id = rs.getInt("schedule_id");
				String instr_id = rs.getString("instr_id");
				Section section = new Section(term, section_id, course_id,
						room, schedule_id, instr_id);
				arraySections.add(section);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DbHelperException("Error getting available courses");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbHelperException();
		}
		Collections.sort(arraySections);
		return arraySections;
	}

	@Override
	public List<Course> getMyCourses(AccountBean act, int term)
			throws DbHelperException {
		ArrayList<Course> myCourseArray = new ArrayList<Course>(
				new LinkedHashSet<Course>());
		String id = act.getId();
		String query;
		Connection connection = getConnection();
		if (id.charAt(0) == 's') {
			query = "Select * from student_section where (student_id = ?) and (term = ?)";
		} else if (id.charAt(0) == 'i') {
			query = "Select * from instr_section where (instr_id = ?) and (term = ?)";
		} else {
			throw new DbHelperException("Not a student or instructor id");
		}
		try {
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setInt(2, term);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String course_id = rs.getString("course_id");
				String course_name = rs.getString("course_name");
				int hours = rs.getInt("hours");
				int dept_id = rs.getInt("dept_id");
				List<Section> sections = getCourseSections(course_id);
				Course course = new Course(course_id, course_name, hours,
						dept_id, sections);
				myCourseArray.add(course);
			}
			Collections.sort(myCourseArray);
			return myCourseArray;

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DbHelperException(
					"Error querying current student courses");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbHelperException();
		}

	}

	public List<Course> getMyCourses(AccountBean act) throws DbHelperException {
		ArrayList<Course> myCourseArray = new ArrayList<Course>(
				new LinkedHashSet<Course>());
		String id = act.getId();
		String query;
		Connection connection = getConnection();
		if (id.charAt(0) == 's') {
			query = "Select * from student_section where (student_id = ?)";
		} else if (id.charAt(0) == 'i') {
			query = "Select * from instr_section where (instr_id = ?)";
		} else {
			throw new DbHelperException("Not a student or instructor id");
		}
		try {
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String course_id = rs.getString("course_id");
				String course_name = rs.getString("course_name");
				int hours = rs.getInt("hours");
				int dept_id = rs.getInt("dept_id");
				List<Section> sections = getCourseSections(course_id);
				Course course = new Course(course_id, course_name, hours,
						dept_id, sections);
				myCourseArray.add(course);
			}
			Collections.sort(myCourseArray);
			return myCourseArray;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DbHelperException(
					"Error querying current student courses");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbHelperException();
		}
	}

	@Override
	public boolean enrollSection(AccountBean act, String courseID,
			String sectionID) {
		String student_id = act.getId();
		boolean updated = false;

		if (student_id.charAt(0) == 's') {
			try {
				connection = getConnection();
				PreparedStatement pstmt = connection
						.prepareStatement("Insert into enrollment values(?,?,?,0,3)");
				pstmt.setString(1, student_id);
				pstmt.setString(2, courseID);
				pstmt.setString(3, sectionID);
				int rs = pstmt.executeUpdate();
				if (rs == 1) {
					updated = true;
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return updated;
	}

	@Override
	public boolean dropSection(AccountBean act, String courseID,
			String sectionID) {
		String student_id = act.getId();
		Connection connection;
		try {
			connection = getConnection();
		} catch (DbHelperException dbhx) {
			dbhx.printStackTrace();
			return false;
		}
		boolean updated = false;
		if (student_id.charAt(0) == 's') {
			try {
				PreparedStatement pstmt = connection
						.prepareStatement("Delete from enrollment where (student_id = ?) and (section_id = ?)");
				pstmt.setString(1, student_id);
				pstmt.setString(2, sectionID);
				int rs = pstmt.executeUpdate();
				if (rs == 1) {
					updated = true;
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return updated;
	}

	@Override
	public double getTotalFees(AccountBean act) throws DbHelperException {

		String student_id = act.getId();
		Connection connection = getConnection();
		double total_fees_due = 0;
		if (student_id.charAt(0) == 's') {
			try {
				PreparedStatement pstmt = connection
						.prepareStatement("Select total_fees_due from student_fees where student_id = ?");
				pstmt.setString(1, student_id);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					total_fees_due = rs.getDouble("total_fees_due");
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				throw new DbHelperException(
						"Error querying for student fees due");
			} catch (Exception e) {
				e.printStackTrace();
				throw new DbHelperException();
			}
			return total_fees_due;
		}
		total_fees_due = 0;
		return total_fees_due;
	}

	@Override
	public double getPaidFees(AccountBean act) throws DbHelperException {
		String student_id = act.getId();
		Connection connection = getConnection();
		double fees_paid = 0;
		if (student_id.charAt(0) == 's') {
			try {
				PreparedStatement pstmt = connection
						.prepareStatement("Select fees_paid from students where student_id = ?");
				pstmt.setString(1, student_id);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					fees_paid = rs.getDouble("fees_paid");
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				throw new DbHelperException(
						"Error querying for student fees paid");
			} catch (Exception e) {
				e.printStackTrace();
				throw new DbHelperException();
			}
			return fees_paid;
		}
		fees_paid = 0;
		return fees_paid;
	}

	@Override
	public double payFees(AccountBean act, double amount)
			throws DbHelperException {
		String student_id = act.getId();
		Connection connection = getConnection();
		double total_fees_paid = getPaidFees(act) + amount;
		double new_balance = 0;

		if (student_id.charAt(0) == 's') {
			try {
				PreparedStatement pstmt = connection
						.prepareStatement("Update students set fees_paid = ? where student_id = ?");
				pstmt.setDouble(1, total_fees_paid);
				pstmt.setString(2, student_id);
				int rs = pstmt.executeUpdate();
				if (rs == 1) {
					double total_fees = getTotalFees(act);
					new_balance = total_fees - total_fees_paid;
				} else {
					new_balance = 0;
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				throw new DbHelperException("Error paying student fees");
			} catch (Exception e) {
				e.printStackTrace();
				throw new DbHelperException();
			}
			return new_balance;
		}
		new_balance = 0;
		return new_balance;
	}

	@Override
	public List<TranscriptEntry> getTranscript(AccountBean act)
			throws DbHelperException {
		ArrayList<TranscriptEntry> transcript = new ArrayList<TranscriptEntry>(
				new LinkedHashSet<TranscriptEntry>());
		String student_id = act.getId();
		Connection connection = getConnection();
		if (student_id.charAt(0) == 's') {
			try {
				PreparedStatement pstmt = connection
						.prepareStatement("Select * from transcript where student_id = ?");
				pstmt.setString(1, student_id);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					String first_name = rs.getString("first_name");
					String last_name = rs.getString("last_name");
					String section_id = rs.getString("section_id");
					String course_id = rs.getString("course_id");
					String course_name = rs.getString("course_name");
					int dept_id = rs.getInt("dept_id");
					int hours = rs.getInt("hours");
					int term = rs.getInt("term");
					double section_gpa = rs.getDouble("section_gpa");
					double overall_gpa = rs.getDouble("overall_gpa");
					TranscriptEntry entry = new TranscriptEntry(first_name,
							last_name, student_id, section_id, course_id,
							course_name, dept_id, hours, term, section_gpa,
							overall_gpa);
					transcript.add(entry);
				}
				// return transcript;
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				throw new DbHelperException(
						"Error querying current student courses");
			} catch (Exception e) {
				e.printStackTrace();
				throw new DbHelperException();
			}
		}
		Collections.sort(transcript);
		return transcript;
	}
}