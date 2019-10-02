package third_jdbc.pojo;

/**
 * 学生类
 *
 * 用户ID
 * 密码
 * 姓名
 * 性别
 * 学历
 * 爱好
 */
public class User {
	private String userID;
	private String password;
	private String name;
	private boolean sex;
	private String education;
	private String hobby;


	public User() {}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
}

