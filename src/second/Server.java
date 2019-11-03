package second;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server extends JFrame {
	private static JButton sendButton;
	private static JTextArea massage;//文本区，用来显示聊天记录
	private static JTextArea text;//文本框，用来写留言
	private static JComboBox<String> clist = new JComboBox<>();//用来罗列在线用户
	private static CopyOnWriteArrayList<Channel> all = new CopyOnWriteArrayList<Channel>();

	public Server(String title){
		super(title);
		this.setSize(500,500);
		Utils.setFrameCenter(this);
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());//设置窗体布局为BorderLayout


		JPanel pp = new JPanel();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();


		massage = new JTextArea(25,30);
		massage.setEnabled(false);//设置成只读
		massage.setFont(new Font("标楷体", Font.BOLD, 12));
		massage.setLineWrap(true);// 激活自动换行功能
		massage.setWrapStyleWord(true);// 激活断行不断字功能

		pp.setLayout(new GridLayout(2,1));//把pp设成（2，1）网格布局
		pp.add(p1);
		pp.add(p2);

		con.add(pp, BorderLayout.SOUTH);//Container把pp放在窗体北边
		con.add(massage, BorderLayout.CENTER);//Container把文本区放在中间

		JLabel l1 = new JLabel("留言");
		JLabel l2 = new JLabel("to");

		text = new JTextArea(3,30);
		text.setLineWrap(true);// 激活自动换行功能
		text.setWrapStyleWord(true);// 激活断行不断字功能
		clist = new JComboBox<String>();
		sendButton = new JButton("发送");
		p1.add(l1);
		p1.add(text);
		p2.add(sendButton);
		p2.add(l2);
		p2.add(clist);


		massage.append("---服务端启动---");

		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	}

	static int i=0;
	static class Channel implements Runnable {
		private DataInputStream dis;
		private DataOutputStream dos;
		private Socket client;
		private boolean isRunning;
		private String name;


		public Channel(Socket client) {
			this.client = client;
			System.out.println(i);

			try {
				dis = new DataInputStream(client.getInputStream());
				dos = new DataOutputStream(client.getOutputStream());
				isRunning = true;
				this.name = receive() + i++;


			} catch (IOException e) {
				System.out.println("---1-客户端IOException---");
			}
		}

		//接收消息
		private String receive() {
			String msg = "";
			try {
				msg = dis.readUTF();
			} catch (IOException e) {
				System.out.println("-----2-----");
				release();
			}
			return msg;
		}

		//打印消息
		private void print() {
			String msg = "";
			msg = receive();
			if (!msg.equals("")){
				massage.append("\r\n" + this.name + ":" + msg);
			}

		}


		//发送消息
		private void send(String msg) {
			try {
				dos.writeUTF(msg);
				dos.flush();
			} catch (IOException e) {
				System.out.println("---发送错误---");
				release();
			}
		}

		static String clientName ="";
		private void sendToClient(String msg) {

			clist.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					/**
					 * 选择客户端
					 */
					clientName = "客户端" + clist.getSelectedIndex();
				}
			});
			msg = clientName + ":" + msg;
			int idx = msg.indexOf(":");
			msg = msg.substring(idx + 1);
			for (Channel other : all) {
				if (other.name.equals(clientName)) {//目标
					other.send(msg);
				}
			}
		}

		//释放资源
		private void release() {
			this.isRunning = false;
			first.Utils.close(dis, dos, client);
			//退出
			all.remove(this);
		}

		@Override
		public void run() {
			while (isRunning) {
				print();
				sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String msg = text.getText().trim();
						if (!msg.equals("")){
							sendToClient(msg);
							massage.append("\r\n"+msg);
							text.setText("");
						}

					}
				});

			}
		}
	}

	public static void main(String[] args) throws Exception {

		Server s = new Server("服务端");
		s.setVisible(true);
		System.out.println("-----Server启动-----");
		//1、指定端口 使用ServerSocket创建服务器
		ServerSocket server = new ServerSocket(8888);
		//2、阻塞式等待连接 accept
		while (true) {
			Socket client = server.accept();
			System.out.println("一个客户端建立类连接");
			Channel c = new Channel(client);
			all.add(c);//管理所有的成员
			clist.addItem(c.name);
			new Thread(c).start();
		}
	}
}
