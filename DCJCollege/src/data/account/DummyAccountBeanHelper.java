package data.account;


public class DummyAccountBeanHelper extends AccountBeanHelper {
	@Override
	public boolean checkCredentials(String name, String password) {
		return true;
	}
	
	private static class InstanceHolder {
		private static final AccountBeanHelper INSTANCE = new DummyAccountBeanHelper();
	}

	public static AccountBeanHelper getInstance() {
		return InstanceHolder.INSTANCE;
	}
}
