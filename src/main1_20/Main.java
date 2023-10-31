package main1_20;

import javax.swing.JFrame;
import java.awt.*;
/**
 * @author 1futureX
 * @version 1.20
 */
@SuppressWarnings("serial")
public class Main extends JFrame{
	static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static long StartUpTime = System.currentTimeMillis();
	Screen Panel = new Screen();
	public Main() {
		setUndecorated(false);//trueにするとバーが消える
		setSize(ScreenSize);//全画面表示
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(0,0);
		setTitle("3D Graphics Ver 1.20");
		add(Panel); // Screen.javaの内容をFrameに追加する。
	}
	public static void main(String[] args) {
		JFrame f = new Main();
		f.setVisible(true);
		f.getJMenuBar();
	}

}
