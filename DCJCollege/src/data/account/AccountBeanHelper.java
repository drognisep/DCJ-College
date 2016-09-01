package data.account;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import util.AppConfig;
import bean.account.AccountBean;
import data.util.Course;
import data.util.DbHelperException;
import data.util.Instructor;
import data.util.ScheduleEntry;
import data.util.Section;
import data.util.Student;
import data.util.TranscriptEntry;

/**
 * Singleton class template for DAO actions pertaining to AccountBean. Update this class to add required
 * features to all DAO's.
 * @author U50773
 */
public abstract class AccountBeanHelper {
	protected AbstractStudentFunctionHelper sHelper;
	protected AbstractInstructorFunctionHelper iHelper;
	protected AbstractReportingFunctionHelper rHelper;
	private static Logger log = Logger.getLogger("AccountBeanHelper");
	private Student student;
	
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	protected AccountBeanHelper(
			AbstractStudentFunctionHelper sHelper,
			AbstractInstructorFunctionHelper iHelper,
			AbstractReportingFunctionHelper rHelper) {
		this.sHelper = sHelper;
		this.iHelper = iHelper;
		this.rHelper = rHelper;
	}
	
	private static class InstanceHolder {
		private static AccountBeanHelper INSTANCE = null; // = new DummyAccountBeanHelper(new DummyStudentFunctionHelper());
		static {
			AppConfig conf = AppConfig.getInstance();
			String abName = conf.getProperty("db.helper.class");
			String sfName = conf.getProperty("db.helper.student.class");
			String ifName = conf.getProperty("db.helper.instructor.class");
			String rfName = conf.getProperty("db.helper.reporting.class");
			
			try {
				Class<?> abhClass = Class.forName(abName);
				Class<?> sfhClass = Class.forName(sfName);
				Class<?> ifhClass = Class.forName(ifName);
				Class<?> rfhClass = Class.forName(rfName);
				Constructor<?> sfhCons = sfhClass.getConstructor(new Class<?>[0]);
				Object sfInstance = (AbstractStudentFunctionHelper)sfhCons.newInstance(new Object[] {});
				Constructor<?> ifhCons = ifhClass.getConstructor(new Class<?>[0]);
				Object ifInstance = (AbstractInstructorFunctionHelper)ifhCons.newInstance(new Object[] {});
				Constructor<?> rfhCons = rfhClass.getConstructor(new Class<?>[0]);
				Object rfInstance = (AbstractReportingFunctionHelper)rfhCons.newInstance(new Object[] {});
				Constructor<?> abhCons = abhClass.getConstructor(new Class<?>[] {
						AbstractStudentFunctionHelper.class,
						AbstractInstructorFunctionHelper.class,
						AbstractReportingFunctionHelper.class
				});
				Object abInstance = (AccountBeanHelper)abhCons.newInstance(sfInstance, ifInstance, rfInstance);
				INSTANCE = (AccountBeanHelper) abInstance;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if(INSTANCE == null) {
					// INSTANCE = new DummyAccountBeanHelper(new DummyStudentFunctionHelper());
					System.out.println("Failed to initialize INSTANCE");
				} else {
					log.info("AccountBeanHelper successfully injected with AppConfig provided dependency");
				}
			}
		}
	}
	
	public static final AccountBeanHelper getInstance() {
		return InstanceHolder.INSTANCE;
	}
	
	/**
	 * Check the parameters to see if there is a match in the database.
	 * @param name Username, should match student/instructor ID.
	 * @param password Password in respective table.
	 * @return True if the credentials match, false otherwise.
	 */
	public abstract boolean checkCredentials(String name, String password);

	public List<Course> getAvailableCourses(AccountBean act)
			throws DbHelperException {
		return sHelper.getAvailableCourses(act);
	}

	public List<Section> getCourseSections(String courseID)
			throws DbHelperException {
		return sHelper.getCourseSections(courseID);
	}

	public List<Course> getMyCourses(AccountBean act, int term)
			throws DbHelperException {
		return sHelper.getMyCourses(act, term);
	}

	public List<Course> getMyCourses(AccountBean act) throws DbHelperException {
		return sHelper.getMyCourses(act);
	}

	public boolean enrollSection(AccountBean act, String courseID,
			String sectionID) {
		return sHelper.enrollSection(act, courseID, sectionID);
	}

	public boolean dropSection(AccountBean act, String courseID,
			String sectionID) {
		return sHelper.dropSection(act, courseID, sectionID);
	}

	public double getTotalFees(AccountBean act) throws DbHelperException {
		return sHelper.getTotalFees(act);
	}

	public double getPaidFees(AccountBean act) throws DbHelperException {
		return sHelper.getPaidFees(act);
	}

	public double payFees(AccountBean act, double amount)
			throws DbHelperException {
		return sHelper.payFees(act, amount);
	}

	public List<TranscriptEntry> getTranscript(AccountBean act)
			throws DbHelperException {
		return sHelper.getTranscript(act);
	}

	public boolean addCourse(AccountBean act, Course course) {
		return iHelper.addCourse(act, course);
	}

	public boolean removeCourse(AccountBean act, String courseID) {
		return iHelper.removeCourse(act, courseID);
	}

	public boolean addSection(AccountBean act, Section section) {
		return iHelper.addSection(act, section);
	}

	public boolean removeSection(AccountBean act, String sectionID) {
		return iHelper.removeSection(act, sectionID);
	}

	public boolean updateSection(AccountBean act, Section section) {
		return iHelper.updateSection(act, section);
	}

	public boolean addStudentEnrollment(AccountBean act, String student_id,
			String course_id, String section_id, int term) {
		return iHelper.addStudentEnrollment(act, student_id, course_id,
				section_id, term);
	}

	public boolean dropStudentEnrollment(AccountBean act, String student_id,
			String section_id, int term) {
		return iHelper.dropStudentEnrollment(act, student_id, section_id, term);
	}

	public boolean addInstructor(AccountBean act, String instr_id,
			String section_id) {
		return iHelper.addInstructor(act, instr_id, section_id);
	}

	public boolean dropInstructor(AccountBean act, String section_id,
			String instr_id) {
		return iHelper.dropInstructor(act, section_id, instr_id);
	}

	public List<Instructor> getInstructors(AccountBean act)
			throws DbHelperException {
		return iHelper.getInstructors(act);
	}

	public boolean updateGrade(AccountBean act, String student_id,
			String section_id, int grade, int term) {
		return iHelper.updateGrade(act, student_id, section_id, grade, term);
	}

	public List<ScheduleEntry> getSchedule(AccountBean act)
			throws DbHelperException {
		return iHelper.getSchedule(act);
	}

	public List<Section> getInstructorSections(AccountBean act, String instr_id)
			throws DbHelperException {
		return rHelper.getInstructorSections(act, instr_id);
	}

	public List<Section> getStudentSections(String student_id)
			throws DbHelperException {
		return rHelper.getStudentSections(student_id);
	}

	public List<Course> getAllCourses() throws DbHelperException {
		return rHelper.getAllCourses();
	}

	public List<Section> getAllSections() throws DbHelperException {
		return rHelper.getAllSections();
	}

	public List<String> getHonorsList(int dept_id, int term)
			throws DbHelperException {
		return rHelper.getHonorsList(dept_id, term);
	}

	public abstract Connection getConnection() throws DbHelperException;
	
	public abstract void closeConnection();
}