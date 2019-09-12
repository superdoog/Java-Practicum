package first;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 结合多线程的程序设计方法
 * 使得服务器端可以同时与多个客户端聊天
 */

public class Server {
	private static CopyOnWriteArrayList<Channel> all = new CopyOnWriteArrayList<Channel>();
	public static void main(String[] args) throws Exception {
		System.out.println("-----Server启动-----");
		//1、指定端口 使用ServerSocket创建服务器
		ServerSocket server = new ServerSocket(8888);
		//2、阻塞式等待连接 accept
		while (true){
			Socket client = server.accept();
			System.out.println("一个客户端建立类连接");
			Channel c = new Channel(client);
			all.add(c);//管理所有的成员
			new Thread(c).start();

		}

	}

	static class Channel implements Runnable{
		private DataInputStream dis;
		private DataOutputStream dos;
		private BufferedReader console;
		private Socket client;
		private boolean isRunning;
		private String name;

		public Channel(Socket client) {
			this.client = client;
			try {
				dis = new DataInputStream(client.getInputStream());
				dos = new DataOutputStream(client.getOutputStream());
				console = new BufferedReader(new InputStreamReader(System.in));
				isRunning = true;
				//获取名字
				this.name = receive();
				//print();

			}catch (IOException e){
				System.out.println("---1-客户端IOException---");
			}
		}

		//接收消息
		private String receive(){
			String msg ="";
			try {
				msg = dis.readUTF();
			} catch (IOException e) {
				System.out.println("-----2-----");
				release();
			}
			return msg;
		}

		//打印消息
		private void print(){
			String msg = "";
			msg = receive();
			if (!msg.equals(""))
				System.out.println(this.name+":"+msg);
		}

		//从控制台获取消息
		private String getStrFromConsole(){
			try {
				return console.readLine();
			}catch (IOException e){
				System.out.println("---从控制台获取信息失败---");
			}
			return "";
		}


		//发送消息
		private void send(String msg){
			try {
				dos.writeUTF(msg);
				dos.flush();
			} catch (IOException e) {
				System.out.println("---发送错误---");
				release();
			}
		}
		private void sendToClient(String msg){
			int idx = msg.indexOf(":");
			String targetName = msg.substring(1,idx);
			msg = msg.substring(idx+1);
			for (Channel other:all){
				if (other.name.equals(targetName)){//目标
					other.send( "服务端：" + msg);//私聊消息
				}
			}
		}

		//释放资源
		private void release(){
			this.isRunning = false;
			Utils.close(dis,dos,client);
			//退出
			all.remove(this);
		}

		@Override
		public void run() {
			while (isRunning){
				print();
				String msg = getStrFromConsole();
				sendToClient(msg);
			}


		}
	}
}
