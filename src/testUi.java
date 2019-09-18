import java.awt.*;
import javax.swing.*;

public class testUi extends JFrame {

	JTextField tsend;//在外部声明文本框，用来写留言
	Choice clist;//声明下拉框，用来罗列在线用户
	JTextArea ta;//声明大型文本区，用来显示聊天记录

	testUi(String title){
		super(title);
		this.setSize(400,300);
		this.setLocation(150,250);
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());//设置窗体布局为BorderLayout

		JPanel pp = new JPanel();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();

		ta = new JTextArea();//实例化大型文本区
		ta.disable();//设置成只读属性

		pp.setLayout(new GridLayout(2,1));//把pp设成（2，1）网格布局
		pp.add(p1);
		pp.add(p2);

		con.add(pp, BorderLayout.SOUTH);//Container把pp放在窗体北边
		con.add(ta, BorderLayout.CENTER);//Container把文本区放在中间

		JLabel l1 = new JLabel("留言");
		JLabel l2 = new JLabel("to");

		tsend = new JTextField(30);//实例化文本框
		clist = new Choice();//实例化下拉列表框
		clist.add("王");
		clist.add("盖");
		clist.add("邱");
		clist.add("杜");
		JButton bSend = new JButton("发送");

		p1.add(l1);
		p1.add(tsend);
		p2.add(l2);
		p2.add(bSend);
		p2.add(clist);
		clist.addItem("所有联系人");//下拉列表框添加一项内容
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String args[]){
		testUi wm = new testUi("Java聊天窗口");
		wm.show();
	}
}