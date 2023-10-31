package main1_19;

import javax.swing.JFrame;
import java.awt.*;
/**
 * @author 1futureX
 * @version 1.16
 */
public class Main extends JFrame{
	static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static long StartUpTime = System.currentTimeMillis();
	static JFrame f = new Main();
	Screen Object = new Screen();
	
	public Main() {
		setUndecorated(false);//trueにするとバーが消える
		setSize(ScreenSize);//全画面表示
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(0,0);
		setTitle("3D Graphics");
		add(Object); // Screen.javaの内容をFrameに追加する。
	}
	public static void main(String[] args) {
	}

}
