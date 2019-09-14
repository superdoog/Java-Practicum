package third.dao;


import third.pojo.User;

public interface UserDao {
	/**
	 * 用户登陆功能
	 * @param userID 用户名
	 * @param password 密码
	 * @return 登陆是否成功
	 */
	public abstract boolean login(String userID,String password);

	/**
	 * 用户注册功能
	 * @param user 被注册的用户信息
	 */
	public abstract void register(User user);
}
