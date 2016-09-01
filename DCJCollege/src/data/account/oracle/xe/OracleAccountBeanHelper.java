/*
 * [REVIEW 8/31]
 * 
 * CORRECTIONS MADE:
 *   - Capital 'O' in driver class is invalid, corrected
 *   - Invalid table names (student -> students, instructor -> instructors)
 */
package data.account.oracle.xe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import data.account.AbstractInstructorFunctionHelper;
import data.account.AbstractReportingFunctionHelper;
import data.account.AbstractStudentFunctionHelper;
import data.account.AccountBeanHelper;
import data.util.DbHelperException;

public class OracleAccountBeanHelper extends AccountBeanHelper {

	public OracleAccountBeanHelper(AbstractStudentFunctionHelper sHelper,
			AbstractInstructorFunctionHelper iHelper,
			AbstractReportingFunctionHelper rHelper) {
		super(sHelper, iHelper, rHelper);
	}

	private Connection connection = null;

	@Override
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
			Class.forName("oracle.jdbc.driver.OracleDriver");
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
	public void closeConnection() {
		if(connection != null) {
			try {
				connection.close();
			} catch(Exception any) { /* ignore */ }
		}
	}
	
	@Override
	public boolean checkCredentials(String name, String password) {
		if (name.charAt(0) == 's') {
			try {
				connection = getConnection();
				PreparedStatement pstmt = connection
						.prepareStatement("Select password from students where student_id = ? and password = ?");
				pstmt.setString(1, name);
				pstmt.setString(2, password);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					try {
						rs.close();
						pstmt.close();
					} catch(Exception any) { /* ignore */ }
					return true;
				} else {
					try {
						rs.close();
						pstmt.close();
					} catch(Exception any) { /* ignore */ }
					return false;
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				try {
					throw new DbHelperException(
							"Error obtaining or matching password");
				} catch (DbHelperException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
				try {
					throw new DbHelperException();
				} catch (DbHelperException e1) {
					e1.printStackTrace();
				}
			}

		} else if (name.charAt(0) == 'i') {
			try {
				connection = getConnection();
				PreparedStatement pstmt = connection
						.prepareStatement("Select password from instructors where instr_id = ? and password = ?");
				pstmt.setString(1, name);
				pstmt.setString(2, password);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					rs.close();
					return true;
				} else
					rs.close();
					return false;
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				try {
					throw new DbHelperException(
							"Error obtaining or matching password");
				} catch (DbHelperException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
				try {
					throw new DbHelperException();
				} catch (DbHelperException e1) {
					e1.printStackTrace();
				}
			}
		}
		return false;

	}
}
