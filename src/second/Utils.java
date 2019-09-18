package second;

import javax.swing.*;
import java.awt.*;
import java.io.Closeable;
import java.io.IOException;

/**
 * 工具类
 */
public class Utils {
	/**
	 * 释放资源
	 */
	public static void close(Closeable ... targets){
		for (Closeable target:targets) {
			try {
				if (null != target){
					target.close();
				}
			}catch (IOException e){
				System.out.println("---释放资源失败---");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 设置窗体居中
	 * 获取屏幕宽高
	 * 获取窗体宽高
	 * （屏幕宽高-窗体宽高）/2 作为新坐标
	 */
	public static void setFrameCenter(JFrame jf) {
		//获取工具对象
		Toolkit tk = Toolkit.getDefaultToolkit();
		//获取屏幕宽高
		Dimension d = tk.getScreenSize();
		double screenWidth = d.getWidth();
		double screenHeight = d.getHeight();

		//获取窗体宽高
		int frameWidth = jf.getWidth();
		int frameHeight = jf.getHeight();

		//获取新的宽高
		int width = (int) (screenWidth - frameWidth) / 2;
		int height = (int) (screenHeight - frameHeight) / 2;

		//设置窗体位置
		jf.setLocation(width, height);
	}
}
