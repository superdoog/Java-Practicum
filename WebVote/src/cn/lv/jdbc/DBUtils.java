package cn.lv.jdbc;

import cn.lv.model.User;
import com.mysql.jdbc.Connection;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class DBUtils {

	/**
	 * 获取数据库连接的方法
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception {
		Properties prop = new Properties();//创建Properties类的对象，vote.properties
		//取到配置文件的内容
		InputStream in = DBUtils.class.getClassLoader().getResourceAsStream("vote.properties");
		prop.load(in);//把拿到的配置文件的内容，放到Properties类的对象prop

		String driverClass = prop.getProperty("driverClass");//通过prop对象的getProperties方法拿到具体的值
		String url = prop.getProperty("url");
		String user = prop.getProperty("user");
		String password = prop.getProperty("password");

		//加载数据库驱动，给DriverManager注册数据库驱动
		Class.forName(driverClass);//通过反射，加载数据库的驱动到DriverManager
		//通过DriverManager的getConnection方法获取数据库的连接
		Connection conn = (Connection) DriverManager.getConnection(url, user, password);
		return conn;
	}

	public static void close(java.sql.Connection conn, Statement statement, ResultSet rs){
        if (null != rs){
	        try {
		        rs.close();
	        } catch (Exception e) {
		        e.printStackTrace();
	        }

        }
		if (null != statement){
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (null != conn){
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * 查询一条信息
	 * @param sql
	 * @param args
	 * @return
	 */
	public static User getOneUser(String sql, Object... args){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;

		try {
			//连接数据库
			conn = DBUtils.getConnection();
			//写sql语句
			//String sql = "select id,username,`password`,phone_no,address,reg_date from users where id="+userid;
			//执行sql,获取statement对象
			ps = conn.prepareStatement(sql);
			for (int i=0;i<args.length;i++){
				ps.setObject(i+1, args[i]);
			}
			rs = ps.executeQuery();
			//从rs中拿出数据库取出的具体值
			if (rs.next()){
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUserName(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setQualification(rs.getBoolean("qualification"));
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			DBUtils.close(conn, ps, rs);
		}
		return user;
	}
}
