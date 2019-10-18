package cn.lv.model;

public class User {
	//用户编号
	private int id;
	//用户名
	private String userName;
	//用户密码
	private String password;
	//用户投票资格
	private boolean qualification;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String UserName) {
		this.userName = UserName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isQualification() {
		return qualification;
	}

	public void setQualification(boolean qualification) {
		this.qualification = qualification;
	}

	public User() {
	}

	public User(int id, String UserName, String password, boolean qualification) {
		this.id = id;
		this.userName = UserName;
		this.password = password;
		this.qualification = qualification;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", UserName='" + userName + '\'' +
				", password='" + password + '\'' +
				", qualification=" + qualification +
				'}';
	}
}
