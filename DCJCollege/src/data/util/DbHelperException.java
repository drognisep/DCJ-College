package data.util;

public class DbHelperException extends Exception {
	private static final long serialVersionUID = 1905675394696544019L;

	public DbHelperException() {
		this("General exception");
	}
	
	public DbHelperException(String message) {
		super(message);
	}
}
