package second;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *  接收端
 *  1、接收消息
 *  2、释放资源
 *  3、重写run
 */
public class Receive implements Runnable {
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
				System.out.println(msg);
			}
		}
	}
}
