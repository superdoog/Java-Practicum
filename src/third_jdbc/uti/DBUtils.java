package third_jdbc.uti;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtils {
	/**
	 * 获取数据库连接方法
	 */
	public static Connection getConnection() throws Exception{
		Properties prop = new Properties();//创建Propertie类到对象
		//获取配置文件到内容
		InputStream in = DBUtils.class.getClassLoader().getResourceAsStream("third_jdbc/jdbc.properties");
		prop.load(in);

		String driverClass = prop.getProperty("driverClass");
		String url = prop.getProperty("url");
		String user = prop.getProperty("user");
		String password = prop.getProperty("password");

		Class.forName(driverClass);

		Connection conn = (Connection) DriverManager.getConnection(url, user, password);
		return conn;
	}
}
