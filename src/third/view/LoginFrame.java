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
	private JFrame jf = new JFrame("登陆界面");
		

	private JTextField jt1 = new JTextField("用户ID：", 5);
	private JTextField jt2 = new JTextField("密   码 ：", 5);
	private JTextField jtfuserID = new JTextField("", 15);
	private JPasswordField jpfpassword = new JPasswordField("", 15);
	private Button login = new Button("登陆");
	private Button chongzhi = new Button("重置");
	private Button regist = new Button("注册");

	public LoginFrame(String[] args) {
		jf.setSize(300, 150);
		jf.setLayout(new FlowLayout());
		jt1.setEditable(false);
		jt2.setEditable(false);

		jf.add(jt1);
		jf.add(jtfuserID);
		jf.add(jt2);
		jf.add(jpfpassword);
		jf.add(login);
		jf.add(chongzhi);
		jf.add(regist);

		//监听重置按钮,清除文本
		chongzhi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jtfuserID.setText("");
				jpfpassword.setText("");
				jtfuserID.requestFocus();
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

				String username = jtfuserID.getText().trim();
				String password =  jpfpassword.getText().trim();

				String usernameRegex = "[a-zA-Z]{5}";
				String passwordRegex = "\\w{6,12}";


				if (!(username.matches(usernameRegex))) {
					JOptionPane.showMessageDialog(jf, "用户名不满足条件(5个英文字母组成)");
					jtfuserID.setText("");
					jtfuserID.requestFocus();
					return;
				}
				if (!(password.matches(passwordRegex))) {
					JOptionPane.showMessageDialog(jf, "密码不满足条件(6~12个任意字符)");
					System.out.println(password);
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
					jtfuserID.setText("");
					jpfpassword.setText("");
					jtfuserID.requestFocus();
				}

			}
		});

		//注册按钮 转跳到注册界面
		regist.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RegisterFrame.main(args);
				jf.dispose();
			}
		});

		//关闭窗口
		jf.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		UiUtil.setFrameCenter(jf);
		jf.setResizable(false);
		jf.setVisible(true);
		jtfuserID.requestFocus();
	}

	public static void main(String[] args) {
		new LoginFrame(args);
	}
}
