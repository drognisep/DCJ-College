package util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RandomBean {
	private Logger log = Logger.getLogger("RandomBean");
	private static final SecureRandom RANDOM = new SecureRandom();
	
	public String getToken() {
		byte[] bytes = new byte[20];
		RANDOM.nextBytes(bytes);
		
		try {
			return toHexString(new String(bytes, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.log(Level.SEVERE, "THIS SHOULD NEVER HAPPEN: " + e.getMessage());
		}
		
		return null;
	}
	
	private String toHexString(String s) throws UnsupportedEncodingException {
		return String.format("%040x", new BigInteger(1, s.getBytes("UTF-8")));
	}
}
