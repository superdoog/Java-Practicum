package third.view;

import third.dao.UserDao;
import third.dao.impl.UserDaoImpl;
import third.uti.UiUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 登录界面
 */
public class LoginFrame {
	public static void main(String[] args) {

		JFrame jf = new JFrame("登陆界面");
		jf.setSize(300, 150);
		jf.setLayout(new FlowLayout());

		JTextField jt1 = new JTextField("用户ID：", 5);
		JTextField jt2 = new JTextField("密   码 ：", 5);
		JTextField jtfusername = new JTextField("", 15);
		JPasswordField jpfpassword = new JPasswordField("", 15);
		Button login = new Button("登陆");
		Button chongzhi = new Button("重置");
		Button regist = new Button("注册");

		jt1.setEditable(false);
		jt2.setEditable(false);

		jf.add(jt1);
		jf.add(jtfusername);
		jf.add(jt2);
		jf.add(jpfpassword);
		jf.add(login);
		jf.add(chongzhi);
		jf.add(regist);

		chongzhi.addActionListener(new ActionListener() {//监听重置按钮,清除文本

			@Override
			public void actionPerformed(ActionEvent e) {
				jtfusername.setText("");
				jpfpassword.setText("");
				jtfusername.requestFocus();

			}
		});

		login.addActionListener(new ActionListener() {
			/**
			 * 获得用户名和密码
			 * 用正则表达式做校验
			 * 创建对象调用功能，返回一个boolean值
			 * 根据boolean值给出提示
			 */
			@Override
			public void actionPerformed(ActionEvent e) {

				String username = jtfusername.getText().trim();
				String password = ("" + jpfpassword.getPassword()).trim();

				String usernameRegex = "[a-zA-Z]{5}";
				String passwordRegex = "^.{6,12}$";

				if (!(username.matches(usernameRegex))) {
					JOptionPane.showMessageDialog(jf, "用户名不满足条件(5个英文字母组成)");
					jtfusername.setText("");
					jtfusername.requestFocus();
					return;
				}
				if (!(password.matches(passwordRegex))) {
					JOptionPane.showMessageDialog(jf, "密码不满足条件(6~12个任意字符)");
					jpfpassword.setText("");
					jpfpassword.requestFocus();
					return;
				}

				UserDao uDao = new UserDaoImpl();
				boolean flag = uDao.login(username, password);
				if (flag) {
					JOptionPane.showMessageDialog(jf, "登陆成功！");
				} else {
					JOptionPane.showMessageDialog(jf, "用户名或密码不正确");
					jtfusername.setText("");
					jpfpassword.setText("");
					jtfusername.requestFocus();
				}

			}
		});

		regist.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				RegisterFrame.main(args);
				jf.dispose();

			}
		});

		jf.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		UiUtil.setFrameCenter(jf);
		jf.setResizable(false);
		jf.setVisible(true);

		jtfusername.requestFocus();


	}
}
