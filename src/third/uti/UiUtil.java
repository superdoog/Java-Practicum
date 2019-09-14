package third.uti;

import javax.swing.*;
import java.awt.*;

public class UiUtil {
	private UiUtil() {
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
