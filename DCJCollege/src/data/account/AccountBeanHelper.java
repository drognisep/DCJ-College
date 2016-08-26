package data.account;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import util.AppConfig;
import bean.account.AccountBean;
import data.util.*;

/**
 * Singleton class template for DAO actions pertaining to AccountBean. Update this class to add required
 * features to all DAO's.
 * @author U50773
 */
public abstract class AccountBeanHelper {
	protected AbstractStudentFunctionHelper sHelper;
	
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
				Constructor<?> sfCons = sfhClass.getConstructor(new Class<?>[0]);
				Object sfInstance = (AbstractStudentFunctionHelper)sfCons.newInstance(new Object[] {});
				Constructor<?> abCons = abhClass.getConstructor(new Class<?>[] {sfhClass});
				Object abInstance = (AccountBeanHelper)abCons.newInstance(sfInstance);
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
					INSTANCE = new DummyAccountBeanHelper(new DummyStudentFunctionHelper());
					System.out.println("Failed to initialize INSTANCE");
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
	
	/*
	 ******************************
	 * Student Function Delegates *
	 ******************************
	 */

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

	public boolean registerSection(AccountBean act, String courseID,
			String sectionID) {
		return sHelper.registerSection(act, courseID, sectionID);
	}

	public boolean dropSection(AccountBean act, String courseID,
			String sectionID) {
		return sHelper.dropSection(act, courseID, sectionID);
	}

	public double getFees(AccountBean act) throws DbHelperException {
		return sHelper.getFees(act);
	}

	public double payFees(AccountBean act, double amount)
			throws DbHelperException {
		return sHelper.payFees(act, amount);
	}
}