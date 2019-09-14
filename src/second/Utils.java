package second;

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
}
