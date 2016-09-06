/*
 * [REVIEWED 8/30]
 * 
 * CORRECTIONS MADE:
 *   - Changed package to aid organization (data.account -> data.account.oracle.xe)
 *   - Removed superfluous parameter 'term' from dropSection method
 */
package data.account.oracle.xe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.account.AccountBean;
import data.account.AbstractInstructorFunctionHelper;
import data.account.AccountBeanHelper;
import data.util.Course;
import data.util.DbHelperException;
import data.util.Instructor;
import data.util.ScheduleEntry;
import data.util.Section;

//change password feature?

public class OracleInstructorFunctionHelper extends
		AbstractInstructorFunctionHelper {

	private Connection connection = null;

	public Connection getConnection() throws DbHelperException {
		return AccountBeanHelper.getInstance().getConnection();
		// try {
		// if (connection != null && !connection.isClosed()) {
		// return connection;
		// }
		// } catch (SQLException e1) {
		// e1.printStackTrace();
		// throw new DbHelperException(
		// "Unable to get determined state of connection");
		// }
		// try {
		//
		// Class.forName("Oracle.jdbc.driver.OracleDriver");
		// connection = DriverManager.getConnection(
		// "jdbc:oracle:thin:@localhost:1521:XE", "school", "school");
		// } catch (SQLException sqle) {
		// sqle.printStackTrace();
		// throw new DbHelperException(
		// "SQL Exception; AbstractStudentFunctionHelper; connectDB()");
		// } catch (ClassNotFoundException cnfe) {
		// cnfe.printStackTrace();
		// throw new DbHelperException(
		// "ClassNotFound; AbstractStudentFunctionHelper; connectDB");
		// } catch (Exception e) {
		// e.printStackTrace();
		// throw new DbHelperException();
		// }
		// return connection;
	}

	@Override
	public boolean addCourse(AccountBean act, Course course) {
		String instr_id = act.getId();
		String course_id = course.getCourse_id();
		String course_name = course.getCourse_name();
		int dept_id = course.getDept_id();
		boolean updated = false;
		if (instr_id.charAt(0) == 'i') {
			try {
				connection = getConnection();
				PreparedStatement pstmt = connection
						.prepareStatement("Insert into courses(course_id, course_name, dept_id) values(?,?,?)");
				pstmt.setString(1, course_id);
				pstmt.setString(2, course_name);
				pstmt.setInt(3, dept_id);
				int rs = pstmt.executeUpdate();
				if (rs == 1) {
					updated = true;
				}
				try {
					pstmt.close();
				} catch (Exception any) { /* ignore */
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
	public boolean removeCourse(AccountBean act, String courseID) {
		boolean autoCom = true;
		String instr_id = act.getId();
		int updated = 0;
		if (instr_id.charAt(0) == 'i') {
			try {
				connection = getConnection();
				autoCom = connection.getAutoCommit();
				connection.setAutoCommit(false);
				PreparedStatement pstmt1 = connection
						.prepareStatement("Delete from enrollment where course_id = ?");
				pstmt1.setString(1, courseID);
				updated = pstmt1.executeUpdate();
				if (updated < 1) {
					try {
						pstmt1.close();
					} catch (Exception any) { /* ignore */
					}
					throw new SQLException();
				} else {
					PreparedStatement pstmt2 = connection
							.prepareStatement("Delete from sections where course_id = ?");
					pstmt2.setString(1, courseID);
					updated = pstmt2.executeUpdate();
					if (updated < 1) {
						try {
							pstmt1.close();
						} catch (Exception any) { /* ignore */
						}
						try {
							pstmt2.close();
						} catch (Exception any) { /* ignore */
						}
						throw new SQLException();
					} else {
						PreparedStatement pstmt3 = connection
								.prepareStatement("Delete from courses where course_id = ?");
						pstmt3.setString(1, courseID);
						updated = pstmt3.executeUpdate();
						if (updated < 1) {
							try {
								pstmt1.close();
							} catch (Exception any) { /* ignore */
							}
							try {
								pstmt2.close();
							} catch (Exception any) { /* ignore */
							}
							try {
								pstmt3.close();
							} catch (Exception any) { /* ignore */
							}
							throw new SQLException();
						} else {
							connection.commit();
							connection.setAutoCommit(autoCom);
							try {
								pstmt1.close();
							} catch (Exception any) { /* ignore */
							}
							try {
								pstmt2.close();
							} catch (Exception any) { /* ignore */
							}
							try {
								pstmt3.close();
							} catch (Exception any) { /* ignore */
							}
							return true;
						}
					}
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				try {
					connection.rollback();
					connection.setAutoCommit(autoCom);
				} catch (SQLException e) {
					// ignoring this
				}

			} catch (Exception e) {
				e.printStackTrace();
				try {
					connection.rollback();
					connection.setAutoCommit(autoCom);
				} catch (SQLException e1) {
					// ignore this
				}

			}
		}
		return false;
	}

	@Override
	public boolean addSection(AccountBean act, Section section) {
		String instr_id = act.getId();
		int term = section.getTerm();
		String section_id = section.getSection_id();
		String course_id = section.getCourse_id();
		int room = section.getRoom();
		int schedule_id = section.getSchedule_id();

		int updated = 0;
		if (instr_id.charAt(0) == 'i') {
			try {
				connection = getConnection();
				PreparedStatement pstmt = connection
						.prepareStatement("Insert into sections(term,section_id,course_id,room,schedule_id,instr_id) values(?, ?, ?, ?, ?, ?)");
				pstmt.setInt(1, term);
				pstmt.setString(2, section_id);
				pstmt.setString(3, course_id);
				pstmt.setInt(4, room);
				pstmt.setInt(5, schedule_id);
				pstmt.setString(6, instr_id);
				updated = pstmt.executeUpdate();
				if (updated == 1) {
					return true;
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return false;
	}

	@Override
	public boolean removeSection(AccountBean act, String section_id) {
		boolean autoCom = true;
		String instr_id = act.getId();
		int updated = 0;
		if (instr_id.charAt(0) == 'i') {
			try {
				connection = getConnection();
				autoCom = connection.getAutoCommit();
				connection.setAutoCommit(false);
				PreparedStatement pstmt1 = connection
						.prepareStatement("Delete from enrollment where section_id = ?"/* "Delete from enrollment where (section_id = ?) and (term = ?)" */);
				pstmt1.setString(1, section_id);
				// pstmt1.setInt(2, term);
				updated = pstmt1.executeUpdate();
				if (updated < 1) {
					throw new SQLException();
				} else {
					PreparedStatement pstmt2 = connection
							.prepareStatement("Delete from sections where section_id = ?"/* "Delete from sections where (section_id = ?) and (term = ?)" */);
					pstmt2.setString(1, section_id);
					// pstmt2.setInt(2, term);
					updated = pstmt2.executeUpdate();
					if (updated < 1) {
						throw new SQLException();
					} else {
						connection.commit();
						connection.setAutoCommit(autoCom);
						return true;
					}
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				try {
					connection.rollback();
					connection.setAutoCommit(autoCom);
				} catch (SQLException e) {
					// ignoring this
				}

			} catch (Exception e) {
				e.printStackTrace();
				try {
					connection.rollback();
					connection.setAutoCommit(autoCom);
				} catch (SQLException e1) {
					// ignore this
				}

			}
		}
		return false;
	}

	@Override
	public boolean updateSection(AccountBean act, Section section) {
		String instr_id = act.getId();
		int term = section.getTerm();
		String section_id = section.getSection_id();
		String course_id = section.getCourse_id();
		int room = section.getRoom();
		int schedule_id = section.getSchedule_id();

		int updated = 0;
		if (instr_id.charAt(0) == 'i') {
			try {
				connection = getConnection();
				PreparedStatement pstmt = connection
						.prepareStatement("Update sections set term = ?, room = ?, schedule_id = ?, instr_id = ? where (course_id = ?) and (section_id = ?)");
				pstmt.setInt(1, term);
				pstmt.setInt(2, room);
				pstmt.setInt(3, schedule_id);
				pstmt.setString(4, instr_id);
				pstmt.setString(5, course_id);
				pstmt.setString(6, section_id);
				updated = pstmt.executeUpdate();
				if (updated == 1) {
					return true;
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	// @Override
	// public boolean addStudent(AccountBean act, Student student) {
	// String instr_id = act.getId();
	// String student_id = student.getStudent_id();
	// String last_name = student.getLast_name();
	// String first_name = student.getFirst_name();
	// String classYear = student.getClassYear();
	// int phone = student.getPhone();
	// String street = student.getStreet();
	// String city = student.getCity();
	// String state = student.getState();
	// int zip = student.getZip();
	// String degree = student.getDegree();
	// int fees_paid = student.getFees_paid();
	//
	// int updated = 0;
	// if (instr_id.charAt(0) == 'i') {
	// try {
	// connection = getConnection();
	// PreparedStatement pstmt = connection
	// .prepareStatement("Insert into students(student_id, last_name, first_name, class, phone,street,city,state,zip,degree, fees_paid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	// pstmt.setString(1, student_id);
	// pstmt.setString(2, last_name);
	// pstmt.setString(3, first_name);
	// pstmt.setString(4, classYear);
	// pstmt.setInt(5, phone);
	// pstmt.setString(6, street);
	// pstmt.setString(7, city);
	// pstmt.setString(8, state);
	// pstmt.setInt(9, zip);
	// pstmt.setString(10, degree);
	// pstmt.setInt(11, fees_paid);
	//
	// updated = pstmt.executeUpdate();
	// if (updated == 1) {
	// return true;
	// }
	// } catch (SQLException sqle) {
	// sqle.printStackTrace();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// return false;
	// }
	//
	// @Override
	// public boolean dropStudent(AccountBean act, String student_id) {
	// boolean autoCom = true;
	// String instr_id = act.getId();
	// int updated = 0;
	// if (instr_id.charAt(0) == 'i') {
	// try {
	// connection = getConnection();
	// autoCom = connection.getAutoCommit();
	// connection.setAutoCommit(false);
	// PreparedStatement pstmt1 = connection
	// .prepareStatement("Delete from enrollment where student_id = ?");
	// pstmt1.setString(1, student_id);
	// updated = pstmt1.executeUpdate();
	// if (updated < 1) {
	// throw new SQLException();
	// } else {
	// PreparedStatement pstmt2 = connection
	// .prepareStatement("Delete from students where student_id = ?");
	// pstmt2.setString(1, student_id);
	// updated = pstmt2.executeUpdate();
	// if (updated < 1) {
	// throw new SQLException();
	// } else {
	// connection.commit();
	// connection.setAutoCommit(autoCom);
	// return true;
	// }
	// }
	// } catch (SQLException sqle) {
	// sqle.printStackTrace();
	// try {
	// connection.rollback();
	// connection.setAutoCommit(autoCom);
	// } catch (SQLException e) {
	// // ignoring this
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// try {
	// connection.rollback();
	// connection.setAutoCommit(autoCom);
	// } catch (SQLException e1) {
	// // ignore this
	// }
	//
	// }
	// }
	// return false;
	// }

	@Override
	public boolean addStudentEnrollment(AccountBean act, String student_id,
			String course_id, String section_id, int term) {
		String instr_id = act.getId();

		int updated = 0;
		if (instr_id.charAt(0) == 'i') {
			try {
				connection = getConnection();
				PreparedStatement pstmt = connection
						.prepareStatement("Insert into enrollment (student_id, course_id, section_id, term) values (?, ?, ?, ?)");
				pstmt.setString(1, student_id);
				pstmt.setString(2, course_id);
				pstmt.setString(3, section_id);
				pstmt.setInt(4, term);
				updated = pstmt.executeUpdate();
				if (updated == 1) {
					return true;
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return false;
	}

	@Override
	public boolean dropStudentEnrollment(AccountBean act, String student_id,
			String section_id, int term) {
		String instr_id = act.getId();

		int updated = 0;
		if (instr_id.charAt(0) == 'i') {
			try {
				connection = getConnection();
				PreparedStatement pstmt = connection
						.prepareStatement("Delete from enrollment where (student_id = ?) and (section_id = ?) and (term = ?");
				pstmt.setString(1, student_id);
				pstmt.setString(2, section_id);
				pstmt.setInt(3, term);
				updated = pstmt.executeUpdate();
				if (updated == 1) {
					return true;
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return false;
	}

	@Override
	public boolean addInstructor(AccountBean act, String instr_id,
			String section_id) {
		String myinstr_id = act.getId();

		int updated = 0;
		if (myinstr_id.charAt(0) == 'i') {
			try {
				connection = getConnection();
				PreparedStatement pstmt = connection
						.prepareStatement("Update sections set instr_id = ? where (section_id = ?)");
				pstmt.setString(1, instr_id);
				pstmt.setString(2, section_id);
				updated = pstmt.executeUpdate();
				if (updated == 1) {
					return true;
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean dropInstructor(AccountBean act, String section_id,
			String instr_id) {
		boolean autoCom = true;
		String myinstr_id = act.getId();
		int updated = 0;
		if (myinstr_id.charAt(0) == 'i') {
			try {
				connection = getConnection();
				autoCom = connection.getAutoCommit();
				connection.setAutoCommit(false);
				PreparedStatement pstmt1 = connection
						.prepareStatement("Update sections set instr_id = null where (section_id = ?)");
				pstmt1.setString(1, section_id);
				updated = pstmt1.executeUpdate();
				if (updated < 1) {
					throw new SQLException();
				} else {
					return true;

				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				try {
					connection.rollback();
					connection.setAutoCommit(autoCom);
				} catch (SQLException e) {
					// ignoring this
				}

			} catch (Exception e) {
				e.printStackTrace();
				try {
					connection.rollback();
					connection.setAutoCommit(autoCom);
				} catch (SQLException e1) {
					// ignore this
				}

			}
		}
		return false;
	}

	@Override
	public List<Instructor> getInstructors(AccountBean act)
			throws DbHelperException {
		String myinstr_id = act.getId();
		ArrayList<Instructor> arrayInstr = new ArrayList<Instructor>();
		if (myinstr_id.charAt(0) == 'i') {
			try {
				connection = getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt
						.executeQuery("Select instr_id, last_name, first_name, dept_id from instructors");
				while (rs.next()) {
					String instr_id = rs.getString("instr_id");
					String last_name = rs.getString("last_name");
					String first_name = rs.getString("first_name");
					int dept_id = rs.getInt("dept_id");
					Instructor instr = new Instructor(instr_id, last_name,
							first_name, dept_id);
					arrayInstr.add(instr);
				}
				return arrayInstr;

			} catch (SQLException sqle) {
				sqle.printStackTrace();
				throw new DbHelperException("Error getting available courses");
			} catch (Exception e) {
				e.printStackTrace();
				throw new DbHelperException();
			}
		}
		return arrayInstr;
	}

	@Override
	public boolean updateGrade(AccountBean act, String student_id,
			String section_id, String grade) {
		String instr_id = act.getId();

		int updated = 0;
		if (instr_id.charAt(0) == 'i') {
			try {
				connection = getConnection();
				PreparedStatement pstmt = connection
						.prepareStatement("Update enrollment set grade = ? where (student_id = ?) and (section_id = ?)");
				pstmt.setString(1, grade);
				pstmt.setString(2, student_id);
				pstmt.setString(3, section_id);
				updated = pstmt.executeUpdate();
				if (updated == 1) {
					return true;
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public List<ScheduleEntry> getSchedule(AccountBean act, int term)
			throws DbHelperException {
		ArrayList<ScheduleEntry> scheduleEntries = new ArrayList<ScheduleEntry>();
		String id = act.getId();
		String query;
		if (id.charAt(0) == 'i') {
			query = "select distinct * from schedule s, sections sec, enrollment e, courses c, instructors i where(s.schedule_id = sec.schedule_id) and (sec.section_id = e.section_id) and (sec.course_id = c.course_id) and (sec.instr_id = i.instr_id) and (i.instr_id = ?) and (sec.term = ?)";
		} else if (id.charAt(0) == 's') {
			query = "select distinct * from schedule s, sections sec, enrollment e, courses c, instructors i where(s.schedule_id = sec.schedule_id) and (sec.section_id = e.section_id) and (sec.course_id = c.course_id) and (sec.instr_id = i.instr_id) and (e.student_id = ?) and (sec.term = ?)";
		} else {
			List<ScheduleEntry> nullEntry = new ArrayList<ScheduleEntry>();
			return nullEntry;
		}
		try {
			connection = getConnection();
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setInt(2, term);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int schedule_id = rs.getInt("schedule_id");
				double mon_start = rs.getDouble("mon_start");
				double mon_end = rs.getDouble("mon_end");
				double tues_start = rs.getDouble("tues_start");
				double tues_end = rs.getDouble("tues_end");
				double wed_start = rs.getDouble("wed_start");
				double wed_end = rs.getDouble("wed_end");
				double thur_start = rs.getDouble("thur_start");
				double thur_end = rs.getDouble("thur_end");
				double fri_start = rs.getDouble("fri_start");
				double fri_end = rs.getDouble("fri_end");
				String section_id = rs.getString("section_id");
				String course_id = rs.getString("course_id");
				int room = rs.getInt("room");
				String instr_last_name = rs.getString("last_name");
				ScheduleEntry sched = new ScheduleEntry(schedule_id, mon_start,
						mon_end, tues_start, tues_end, wed_start, wed_end,
						thur_start, thur_end, fri_start, fri_end, section_id,
						course_id, room, instr_last_name);
				scheduleEntries.add(sched);
			}
			rs.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DbHelperException(
					"Error querying current student courses");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbHelperException();
		}
		return scheduleEntries;

	}
}