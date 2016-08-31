package util;

import java.util.Properties;

public class AppConfig {
	private Properties appProps;
	
	private AppConfig() {
		appProps = new Properties();
		
		// Default properties relating to the config file
		appProps.setProperty("app.config.location", "./appProps");
		appProps.setProperty("app.config.type", "xml");
		
		// Set application properties here
		appProps.setProperty("db.helper.class", "data.account.DummyAccountBeanHelper");
		appProps.setProperty("db.helper.student.class", "data.account.oracle.xe.OracleStudentFunctionHelper");
		appProps.setProperty("db.helper.instructor.class", "data.account.oracle.xe.OracleInstructorFunctionHelper");
		appProps.setProperty("db.helper.reporting.class", "data.account.oracle.xe.OracleReportingFunctionHelper");
	}
	
	public void setProperty(String key, String value) {
		appProps.setProperty(key, value);
	}
	
	public String getProperty(String key) {
		return appProps.getProperty(key);
	}
	
	private static class InstanceHolder {
		private static final AppConfig INSTANCE = new AppConfig();
	}
	
	public static AppConfig getInstance() { return InstanceHolder.INSTANCE; }
}
