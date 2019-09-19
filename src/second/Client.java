package second;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class Client extends JFrame {

	private static JButton sendButton;
	private static JTextArea massage;//文本区，用来显示聊天记录
	private static JTextArea text;//文本框，用来写留言
	private Choice clist;//用来罗列在线用户

	public Client(String title){
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
		clist = new Choice();
		clist.add("服务器");
		sendButton = new JButton("发送");
		p1.add(l1);
		p1.add(text);
		p2.add(sendButton);
		p2.add(l2);
		p2.add(clist);


		massage.append("---客户端启动---");

		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	}


	static class Receive implements Runnable {
		private DataInputStream dis;
		private Socket socket;
		private boolean isRunning;

		public Receive(Socket socket) {
			this.socket = socket;
			try {
				dis = new DataInputStream(socket.getInputStream());
				isRunning = true;
			}catch (IOException e){
				System.out.println("---接收端异常---");
				release();
			}
		}

		//接收消息
		private String receive(){
			String msg = "";
			try {
				msg = dis.readUTF();

			}catch (IOException e){
				System.out.println("---接收端：接收错误---");
				release();
			}
			return msg;
		}

		//释放资源
		private void release(){
			this.isRunning = false;
			Utils.close(dis,socket);
		}

		@Override
		public void run() {
			while (isRunning){
				String msg = receive();
				if (!msg.equals("")){
					massage.append("\r\n");
					massage.append(msg);
				}
			}
		}
	}


	static class Send implements Runnable {

		private DataOutputStream dos;
		private Socket client;
		private boolean isRunning;

		public Send(Socket client){
			this.client = client;


			try {
				dos = new DataOutputStream(client.getOutputStream());
				isRunning = true;
			} catch (IOException e) {
				System.out.println("---发送端异常---");
				this.release();
			}

		}

		 public void send(String msg){
			try {
				dos.writeUTF(msg);
				dos.flush();

			} catch (IOException e) {
				System.out.println("---发送端：发送异常---");
				release();
			}
		}

		//释放资源
		private void release(){
			this.isRunning = false;
			Utils.close(dos,client);
		}

		@Override
		public void run() {
			while (isRunning){

				sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String msg = text.getText().trim();
						if (!msg.equals("")){
							send(msg);
							text.setText("");
						}
					}
				});

			}
		}
	}

	public static void main(String[] args) throws Exception{
		Client c = new Client("客户端");
		c.setVisible(true);

		System.out.println("-----Client启动-----");
		//1、建立连接：Socket创建客户端+服务器的地址和端口
		Socket client = new Socket("localhost",8888);
		//2、客户端发送消息
		new Thread(new Send(client)).start();
		new Thread(new Receive(client)).start();
	}
}
