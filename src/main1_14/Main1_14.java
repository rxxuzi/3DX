package main1_14;

import javax.swing.JFrame;
import java.awt.*;
/**
 * @author 1futureX
 * @version 1.14
 */
public class Main1_14 extends JFrame{
	static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	static JFrame f = new Main1_14();
	Screen Object = new Screen();
	
	public Main1_14() {
		setUndecorated(false);//trueにするとバーが消える
		setSize(ScreenSize);//全画面表示
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(0,0);
		setTitle("3D Graphics Ver:1.14");
		add(Object); // Screen.javaの内容をFrameに追加する。
	}
	public static void main(String[] args) {
	}

}
