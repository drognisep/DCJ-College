package bean.account;

import java.io.Serializable;

public class AccountBean implements Serializable {
	private static final long serialVersionUID = 6349567425326309885L;
	private String name;
	
	public AccountBean() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
