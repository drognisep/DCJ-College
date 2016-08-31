package data.account;


public class DummyAccountBeanHelper extends AccountBeanHelper {
	public DummyAccountBeanHelper(
			AbstractStudentFunctionHelper sHelper,
			AbstractInstructorFunctionHelper iHelper,
			AbstractReportingFunctionHelper rHelper) {
		super(sHelper, iHelper, rHelper);
	}
	
	@Override
	public boolean checkCredentials(String name, String password) {
		return true;
	}
}
