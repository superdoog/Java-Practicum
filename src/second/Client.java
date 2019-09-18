package second;

import javax.swing.*;
import java.awt.*;

public class Client extends JFrame {

	private JButton send;
	private JTextArea massage;//文本区，用来显示聊天记录
	private JTextArea text;//文本框，用来写留言
	private Choice clist;//用来罗列在线用户

	public Client(String title){
		super(title);
		this.setSize(400,400);
		Utils.setFrameCenter(this);
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());//设置窗体布局为BorderLayout


		JPanel pp = new JPanel();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();


		massage = new JTextArea();


		massage.setEnabled(false);//设置成只读
		massage.setFont(new Font("标楷体", Font.BOLD, 16));
		massage.setLineWrap(true);// 激活自动换行功能
		massage.setWrapStyleWord(true);// 激活断行不断字功能
		pp.setLayout(new GridLayout(2,1));//把pp设成（2，1）网格布局
		pp.add(p1);
		pp.add(p2);

		con.add(pp, BorderLayout.SOUTH);//Container把pp放在窗体北边
		con.add(massage, BorderLayout.CENTER);//Container把文本区放在中间

		JLabel l1 = new JLabel("留言");
		JLabel l2 = new JLabel("to");

		text = new JTextArea(3,15);
		text.setLineWrap(true);// 激活自动换行功能
		text.setWrapStyleWord(true);// 激活断行不断字功能
		clist = new Choice();
		clist.add("服务器");
		send = new JButton("发送");
		p1.add(l1);
		p1.add(text);
		p2.add(l2);
		p2.add(send);
		p2.add(clist);
		clist.addItem("所有联系人");//下拉列表框添加一项内容



		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	public static void main(String[] args) {
		Client c = new Client("客户端");
		c.setVisible(true);
	}
}
