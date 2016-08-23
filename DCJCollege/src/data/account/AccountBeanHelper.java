package data.account;

/**
 * Singleton class template for DAO actions pertaining to AccountBean. Update this class to add required
 * features to all DAO's.
 * @author U50773
 */
public abstract class AccountBeanHelper {
	protected AccountBeanHelper() {}
	
	public abstract boolean checkCredentials(String name, String password);
}