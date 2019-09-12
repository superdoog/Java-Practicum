package first;

import java.io.*;
import java.net.Socket;

/**
 * 发送端
 * 1、发送消息
 * 2、释放资源
 * 3、从控制台获取消息
 * 4、重写run
 */
public class Send implements Runnable {
	private BufferedReader console;
	private DataOutputStream dos;
	private Socket client;
	private boolean isRunning;
	private String name;
	public Send(Socket client,String name){
		this.client = client;
		this.name = name;
		console = new BufferedReader(new InputStreamReader(System.in));
		try {
			dos = new DataOutputStream(client.getOutputStream());
			send(name);
			isRunning = true;
		} catch (IOException e) {
			System.out.println("---发送端异常---");
			this.release();
		}

	}

	private void send(String msg){
		try {
			dos.writeUTF(msg);
			dos.flush();

		} catch (IOException e) {
			System.out.println("---发送端：发送异常---");
			release();
		}
	}


	// 从控制台获取消息
	private String getStrFromConsole(){
		try {
			return console.readLine();
		} catch (IOException e) {
			System.out.println("---发送端：控制台获取异常---");
			e.printStackTrace();
		}
		return "";
	}


	//释放资源
	private void release(){
		this.isRunning = false;
		Utils.close(dos,client);
	}

	@Override
	public void run() {
		while (isRunning){
			String msg = getStrFromConsole();
			if (!msg.equals("")){
				send(msg);
			}

		}
	}
}
