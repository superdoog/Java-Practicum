package third_jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import third_jdbc.dao.UserDao;
import third_jdbc.pojo.User;
import third_jdbc.uti.DBUtils;

public class UserDaoImpl implements UserDao {


	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	@Override
	public boolean login(String userID, String password) {
		boolean flag = false;


		try {
			conn = DBUtils.getConnection();
			String sql = "select userID, password from user where userID='"+userID+"'"
					+" and password='"+password+"'";



			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (!rs.next()){
				// rs is null
				flag = false;
			}else{
				// rs is not null
				flag = true;
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if (rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return flag;

	}

	@Override
	public void register(User user) {
		try {
			conn = DBUtils.getConnection();

			String sql = "insert into user ( userID, password, name, sex, education, hobby)"
					+" values(?,?,?,?,?,?)";

			ps = conn.prepareStatement(sql);

			String edu = ""+user.getEducation();

			ps.setObject(1, user.getUserID());
			ps.setObject(2, user.getPassword());
			ps.setObject(3, user.getName());
			ps.setObject(4, user.getSex());
			ps.setObject(5, edu);
			ps.setObject(6, user.getHobby());


			ps.execute();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if (ps != null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}


	}

}
