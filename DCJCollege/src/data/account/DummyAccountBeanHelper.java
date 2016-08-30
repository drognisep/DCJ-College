package data.account;


public class DummyAccountBeanHelper extends AccountBeanHelper {
	public DummyAccountBeanHelper(AbstractStudentFunctionHelper sHelper) {
		super(sHelper);
	}
	
	@Override
	public boolean checkCredentials(String name, String password) {
		return true;
	}
}
