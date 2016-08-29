package data.account;

import bean.account.AccountBean;

public class DummyAccountBeanHelper extends AccountBeanHelper {
	public DummyAccountBeanHelper(AbstractStudentFunctionHelper sHelper) {
		super(sHelper);
	}
	
	@Override
	public boolean checkCredentials(String name, String password) {
		return true;
	}
	
	/*private static class InstanceHolder {
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
		
	}*/

	/*public static AccountBeanHelper getInstance() {
		return InstanceHolder.INSTANCE;
	}*/
	
	/**
	 * Simulate getting course_no and course_name from database
	 * @param act AccountBean instance that represents a valid account.
	 * @return If successful, returns an array of courses. Otherwise, returns zero length array.
	 *//*
	public String[] getAvailableCourses(AccountBean act) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("A10 - Chemistry 101|");
		sb.append("B20 - Technology 101|");
		sb.append("C30 - Elementary Education 101|");
		sb.append("D40 - Public Speaking|");
		
		String s = sb.toString();
		
		return s.substring(0, s.length() - 1).split("|");
	}*/
	
	
}
