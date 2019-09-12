package first;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 结合多线程的程序设计方法
 * 使得服务器端可以同时与多个客户端聊天
 */
public class Client {
	public static void main(String[] args) throws Exception{
		System.out.println("---客户端启动---");
		System.out.print("请输入名字:");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String name = br.readLine();
		//1、建立连接：Socket创建客户端+服务器的地址和端口
		Socket client = new Socket("localhost",8888);
		//2、客户端发送消息
		new Thread(new Send(client,name)).start();
		new Thread(new Receive(client)).start();
	}
}
