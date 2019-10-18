package cn.lv.servlet;

import cn.lv.jdbc.DBUtils;
import cn.lv.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User u = new User();
		req.setCharacterEncoding("UTF-8");//解决中文乱码
		String loginName = req.getParameter("username");
		String loginPassword = req.getParameter("password");

		String sql = "select * from vuser where username = ?";
		u = DBUtils.getOneUser(sql, loginName);
		if (null != u){
			if (u.getPassword().equals(loginPassword)){
				resp.getWriter().println("登陆成功！");
			}else {
				resp.getWriter().println("账号或密码错误！");
			}
		}

	}
}
