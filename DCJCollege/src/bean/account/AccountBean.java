package bean.account;

import java.io.Serializable;

public class AccountBean implements Serializable {
	private static final long serialVersionUID = 6349567425326309885L;
	private String id, fname, lname, clazz, street, city, state, degree;

	private int zip;
	private long phone;
	private double feesPaid, feesDue;
	
	public AccountBean() {}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getFname() {
		return fname == null ? "" : fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname == null ? "" : lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getClazz() {
		return clazz == null ? "" : clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getStreet() {
		return street == null ? "" : street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city == null ? "" : city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state == null ? "" : state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDegree() {
		return degree == null ? "" : degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public double getFeesPaid() {
		return feesPaid;
	}

	public void setFeesPaid(double feesPaid) {
		this.feesPaid = feesPaid;
	}

	public double getFeesDue() {
		return feesDue;
	}

	public void setFeesDue(double feesDue) {
		this.feesDue = feesDue;
	}
}
