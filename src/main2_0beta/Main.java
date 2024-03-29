package main2_0beta;

import javax.swing.JFrame;
import java.awt.*;
/**
 * @author Rxxuzi
 * @version 2.0beta
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
		setLocation(10,10);
		setTitle("3D Graphics Ver 2.0pre ");
		add(Panel); // Screen.javaの内容をFrameに追加する。
	}
	public static void main(String[] args) {
		JFrame f = new Main();
		f.setVisible(true);
		f.getJMenuBar();
	}

}
