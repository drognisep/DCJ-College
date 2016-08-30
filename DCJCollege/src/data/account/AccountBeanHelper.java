package data.account;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import util.AppConfig;
import bean.account.AccountBean;
import data.util.DbHelperException;

/**
 * Singleton class template for DAO actions pertaining to AccountBean. Update this class to add required
 * features to all DAO's.
 * @author U50773
 */
public abstract class AccountBeanHelper {
	protected AbstractStudentFunctionHelper sHelper;
	private static Logger log = Logger.getLogger("AccountBeanHelper");
	
	protected AccountBeanHelper(AbstractStudentFunctionHelper sHelper) {
		this.sHelper = sHelper;
	}
	
	private static class InstanceHolder {
		private static AccountBeanHelper INSTANCE = null; // = new DummyAccountBeanHelper(new DummyStudentFunctionHelper());
		static {
			AppConfig conf = AppConfig.getInstance();
			String abName = conf.getProperty("db.helper.class");
			String sfName = conf.getProperty("db.helper.student.class");
			
			try {
				Class<?> abhClass = Class.forName(abName);
				Class<?> sfhClass = Class.forName(sfName);
				Constructor<?> sfhCons = sfhClass.getConstructor(new Class<?>[0]);
				Object sfInstance = (AbstractStudentFunctionHelper)sfhCons.newInstance(new Object[] {});
				Constructor<?> abhCons = abhClass.getConstructor(new Class<?>[] {AbstractStudentFunctionHelper.class});
				Object abInstance = (AccountBeanHelper)abhCons.newInstance(sfInstance);
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
	
	// Generic functionality generically

	public String[] getAvailableCourses(AccountBean act)
			throws DbHelperException {
		return sHelper.getAvailableCourses(act);
	}

	public String[] getCourseSections(String courseID) throws DbHelperException {
		return sHelper.getCourseSections(courseID);
	}

	public String[] getMyCourses(AccountBean act) throws DbHelperException {
		return sHelper.getMyCourses(act);
	}

	public boolean addSection(AccountBean act, String courseID, String sectionID) {
		return sHelper.addSection(act, courseID, sectionID);
	}

	public boolean dropSection(AccountBean act, String courseID,
			String sectionID) {
		return sHelper.dropSection(act, courseID, sectionID);
	}

	public double getTotalFees(AccountBean act) throws DbHelperException {
		return sHelper.getTotalFees(act);
	}

	public double payFees(AccountBean act, double amount)
			throws DbHelperException {
		return sHelper.payFees(act, amount);
	}

	public double getPaidFees(AccountBean act) throws DbHelperException {
		return sHelper.getPaidFees(act);
	}
}