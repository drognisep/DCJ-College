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
 * 
 * CORRECTIONS RECOMMENDED:
 *   - TODO: Implement getTransaction method as outlined in the abstract class
 */
package data.account.oracle.xe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import bean.account.AccountBean;
import data.account.AbstractStudentFunctionHelper;
import data.util.DbHelperException;
import data.util.TranscriptEntry;

public class OracleStudentFunctionHelper extends AbstractStudentFunctionHelper {

	private Connection connection = null;

	public Connection getConnection() throws DbHelperException {

		try {
			if (connection != null && !connection.isClosed()) {
				return connection;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new DbHelperException(
					"Unable to determine state of connection");
		}

		try {
			// TODO: [FUTURE] Make this pull connection information from AppConfig, and push updates
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", "school", "school");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DbHelperException(
					"Unable to establish connection to database");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			throw new DbHelperException(
					"Unable to locate driver class");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbHelperException();
		}
		return connection;

	}

	@Override
	public List<String> getAvailableCourses(AccountBean act)
			throws DbHelperException {
		ArrayList<String> courseArray = new ArrayList<String>();
		Connection connection = getConnection();
		String id = act.getId();
		if (id.charAt(0) == 's') {
			try {
				PreparedStatement pstmt = connection
						.prepareStatement("Select course_id, course_name from courses where student_id != ?");
				pstmt.setString(1, id);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					String course_id = rs.getString("course_id");
					String course_name = rs.getString("course_name");
					courseArray.add(course_id + " - " + course_name);
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
		return courseArray;
	}

	@Override
	public List<String> getCourseSections(String courseID) throws DbHelperException {
		ArrayList<String> arrayList = new ArrayList<String>();
		Connection connection = getConnection();
		try {
			PreparedStatement pstmt = connection
					.prepareStatement("Select section_id from sections where course_id = ?");
			pstmt.setString(1, courseID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String course_no = rs.getString("course_no");
				String course_name = rs.getString("course_name");
				arrayList.add(course_no + " - " + course_name);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DbHelperException("Error getting available courses");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbHelperException();
		}
		return arrayList;
	}

	@Override
	public List<String> getMyCourses(AccountBean act, int term) throws DbHelperException {
		ArrayList<String> courseArray = new ArrayList<String>();
		String student_id = act.getId();
		Connection connection = getConnection();
		if (student_id.charAt(0) == 's') {
			try {
				PreparedStatement pstmt = connection
						.prepareStatement("Select course_id, course_name from courses where student_id = ?");
				pstmt.setString(1, student_id);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					String course_id = rs.getString("course_id");
					String course_name = rs.getString("course_name");
					courseArray.add(course_id + " - " + course_name);
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				throw new DbHelperException(
						"Error querying current student courses");
			} catch (Exception e) {
				e.printStackTrace();
				throw new DbHelperException();
			}
			return courseArray;
		}
		return courseArray;
	}

	@Override
	public boolean addSection(AccountBean act, String courseID, String sectionID, int term) {
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
						.prepareStatement("Delete from enrollment where (student_id = ?) and (section_id = ?) ");
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
		/*
		 * TODO: Should't this be total fees, and not fees due? Fees due can be obtained with simple
		 * subtraction once both of these values are obtained. This could lead to less queries for 
		 * information that can be easily calculated.
		 */		
		String student_id = act.getId();
		Connection connection = getConnection();
		double fees_due = 0;
		if (student_id.charAt(0) == 's') {
			try {
				PreparedStatement pstmt = connection
						.prepareStatement("Select fees_due from student_fees where student_id = ?");
				pstmt.setString(1, student_id);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					fees_due = rs.getDouble("fees_due");
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				throw new DbHelperException(
						"Error querying for student fees due");
			} catch (Exception e) {
				e.printStackTrace();
				throw new DbHelperException();
			}
			return fees_due;
		}
		fees_due = 0; // should we return another amount?
		return fees_due;
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
					fees_paid = rs.getDouble("fees_due");
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				throw new DbHelperException(
						"Error querying for student fees due");
			} catch (Exception e) {
				e.printStackTrace();
				throw new DbHelperException();
			}
			return fees_paid;
		}
		fees_paid = 0; // should we return another amount?
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
					new_balance = 0; // what value should we return?
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
		new_balance = 0; // should we return another amount?
		return new_balance;
	}

	@Override
	public List<TranscriptEntry> getTranscript(AccountBean act)
			throws DbHelperException {
		// TODO Auto-generated method stub
		return null;
	}
}
