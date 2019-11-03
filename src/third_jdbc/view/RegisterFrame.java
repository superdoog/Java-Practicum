package third_jdbc.view;

import third_jdbc.dao.UserDao;
import third_jdbc.dao.impl.UserDaoImpl;
import third_jdbc.pojo.User;
import third_jdbc.uti.UiUtil;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * 注册界面
 */
public class RegisterFrame {

	private String[] args;
	private boolean sex;
	private String education = "小 学";
	private JFrame jf = new JFrame("注册界面");
	private JTextField jt1 = new JTextField("用户ID ：", 5);
	private JTextField jt2 = new JTextField("密   码 ：", 5);
	private JTextField jt3 = new JTextField("姓   名 ：", 5);
	private JTextField jt4 = new JTextField("性   别 ：", 8);
	private JTextField jt5 = new JTextField("学   历 ：", 8);
	private JTextField jt6 = new JTextField("爱   好 ：", 7);
	private JTextField jtfUserID = new JTextField("", 15);
	private JPasswordField jpfPassword = new JPasswordField("", 15);
	private JTextField jtfUserName = new JTextField("", 15);
	private ButtonGroup group = new ButtonGroup();
	private JRadioButton male = new JRadioButton("男");
	private JRadioButton female = new JRadioButton("女");
	private JComboBox<String> jcbUserEducation = new JComboBox<>();
	private JTextField jtfUserHobby = new JTextField("", 15);
	private Button cancel = new Button("取消");
	private Button register = new Button("注册");


	public RegisterFrame(String[] args) {
		this.args = args;

		jcbUserEducation.addItem("小 学");
		jcbUserEducation.addItem("初 中");
		jcbUserEducation.addItem("高 中");
		jcbUserEducation.addItem("本 科");
		jt1.setEditable(false);
		jt2.setEditable(false);
		jt3.setEditable(false);
		jt4.setEditable(false);
		jt5.setEditable(false);
		jt6.setEditable(false);

		jf.add(jt1);
		jf.add(jtfUserID);
		jf.add(jt2);
		jf.add(jpfPassword);
		jf.add(jt3);
		jf.add(jtfUserName);
		jf.add(jt4);
		group.add(male);
		group.add(female);
		jf.add(male);
		jf.add(female);
		jf.add(jt5);
		jf.add(jcbUserEducation);
		jf.add(jt6);
		jf.add(jtfUserHobby);
		jf.add(register);
		jf.add(cancel);

		jf.setSize(300, 300);
		jf.setLayout(new FlowLayout());


		//获取性别
		male.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sex = true;
			}
		});
		female.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sex = false;
			}
		});

		//获取学历
		jcbUserEducation.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (ItemEvent.SELECTED == e.getStateChange()) {
					education = e.getItem().toString();
				}
				}
		});



		register.addActionListener(new ActionListener() {
			/**
			 * 获得用户名和密码
			 * 用正则表达式做校验
			 * 封装成用户对象
			 * 调用用户操作的功能进行注册
			 * 回到登陆界面
			 */
			@Override
			public void actionPerformed(ActionEvent e) {

				String userID = jtfUserID.getText().trim();
				String password = jpfPassword.getText().trim();
				String userName = jtfUserName.getText().trim();
				String userHobby = jtfUserHobby.getText().trim();


				String usernameRegex = "[a-zA-z]{5}";
				String passwordRegex = "\\w{6,12}";


				if (!(userID.matches(usernameRegex))) {
					JOptionPane.showMessageDialog(jf, "用户ID不满足条件(5个英文字母组成)");
					jtfUserID.setText("");
					jtfUserID.requestFocus();
					return;
				}
				if (!(password.matches(passwordRegex))) {
					JOptionPane.showMessageDialog(jf, "密码不满足条件(6~12个任意字符)");
					jpfPassword.setText("");
					jpfPassword.requestFocus();
					return;
				}

				User user = new User();
				user.setUserID(userID);
				user.setPassword(password);
				user.setName(userName);
				user.setSex(sex);
				user.setEducation(education);
				user.setHobby(userHobby);

				UserDao ud = new UserDaoImpl();
				ud.register(user);

				JOptionPane.showMessageDialog(jf, "注册成功！");

				third.view.LoginFrame.main(args);
				jf.dispose();

			}
		});

		//取消按钮 转跳登陆界面
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginFrame.main(args);
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

		jtfUserID.requestFocus();


	}

	public static void main(String[] args) {

		new RegisterFrame(args);

	}
}
