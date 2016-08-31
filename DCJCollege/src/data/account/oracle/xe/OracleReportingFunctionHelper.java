package data.account.oracle.xe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.account.AccountBean;
import data.account.AbstractReportingFunctionHelper;
import data.util.Course;
import data.util.DbHelperException;
import data.util.Section;

public class OracleReportingFunctionHelper extends
		AbstractReportingFunctionHelper {

	private Connection connection = null;

	public Connection getConnection() throws DbHelperException {

		try {
			if (connection != null && !connection.isClosed()) {
				return connection;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new DbHelperException(
					"Unable to get determined state of connection");
		}

		try {

			Class.forName("Oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", "school", "school");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DbHelperException(
					"SQL Exception; AbstractStudentFunctionHelper; connectDB()");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			throw new DbHelperException(
					"ClassNotFound; AbstractStudentFunctionHelper; connectDB");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbHelperException();
		}
		return connection;

	}

	@Override
	public List<Section> getInstructorSections(AccountBean act, String instr_id)
			throws DbHelperException {
		String myinstr_id = act.getId();
		ArrayList<Section> sections = new ArrayList<Section>();
		if (myinstr_id.charAt(0) == 'i') {
			try {
				connection = getConnection();
				PreparedStatement pstmt = connection
						.prepareStatement("Select term, section_id, course_id, room, schedule_id from sections where instr_id = ?");
				pstmt.setString(1, "instr_id");
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					int term = rs.getInt("term");
					String section_id = rs.getString("section_id");
					String course_id = rs.getString("course_id");
					int room = rs.getInt("room");
					int schedule_id = rs.getInt("schedule_id");
					Section section = new Section(term, section_id, course_id,
							room, schedule_id, instr_id);
					sections.add(section);
				}
				
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				throw new DbHelperException(
						"Error getting sections for instructor");
			} catch (Exception e) {
				e.printStackTrace();
				throw new DbHelperException();
			}
		}
		return sections;
	}

	@Override
	public List<Section> getStudentSections(String student_id)
			throws DbHelperException {
		ArrayList<Section> sections = new ArrayList<Section>();
			try {
				connection = getConnection();
				PreparedStatement pstmt = connection
						.prepareStatement("Select term, section_id, course_id, room, schedule_id, instr_id from sections where student_id = ?");
				pstmt.setString(1, "student_id");
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
					sections.add(section);
				}
				
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				throw new DbHelperException(
						"Error getting sections for student");
			} catch (Exception e) {
				e.printStackTrace();
				throw new DbHelperException();
		}
		return sections;
	}

	@Override
	public List<Course> getAllCourses() throws DbHelperException {

		ArrayList<Course> courses = new ArrayList<Course>();
			try {
				connection = getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from courses");
				while (rs.next()) {
					String course_id = rs.getString("course_id");
					String course_name = rs.getString("course_name");
					int hours = rs.getInt("hours");
					int dept_id = rs.getInt("dept_id");
//					int fees = rs.getInt("fees");
					Course course = new Course(course_id, course_name, hours, dept_id);
					courses.add(course);
				}
				
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				throw new DbHelperException(
						"Error getting all courses");
			} catch (Exception e) {
				e.printStackTrace();
				throw new DbHelperException();
			}
		
		return courses;
	}

	@Override
	public List<Section> getAllSections() throws DbHelperException {
		ArrayList<Section> sections = new ArrayList<Section>();
			try {
				connection = getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("Select term, section_id, course_id, room, schedule_id, instr_id from sections");
				while (rs.next()) {
					int term = rs.getInt("term");
					String section_id = rs.getString("section_id");
					String course_id = rs.getString("course_id");
					int room = rs.getInt("room");
					int schedule_id = rs.getInt("schedule_id");
					String instr_id = rs.getString("instr_id");
					Section section = new Section(term, section_id, course_id,
							room, schedule_id, instr_id);
					sections.add(section);
				}
				
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				throw new DbHelperException(
						"Error getting all sections");
			} catch (Exception e) {
				e.printStackTrace();
				throw new DbHelperException();
		}
		
		return sections;
	}

	@Override
	public List<String> getHonorsList(int dept_id, int term) throws DbHelperException {
		ArrayList<String> honors = new ArrayList<String>();
			try {
				connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement("Select first_name, last_name from student_section_gpa where (dept_id = ?) and (term = ?) and sec_gpa >= 3.0");
				pstmt.setInt(1, dept_id);
				pstmt.setInt(2, term);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					String name = rs.getString(1) + " " + rs.getString(2);
					honors.add(name);
				}
				
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				throw new DbHelperException(
						"Error getting honors lists for specified department");
			} catch (Exception e) {
				e.printStackTrace();
				throw new DbHelperException();
		}
		
		return honors;
	}

}
