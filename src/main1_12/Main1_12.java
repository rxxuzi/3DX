package main1_12;

import javax.swing.JFrame;
import java.awt.*;
/**
 * @author 1futureX
 * @version 1.12
 * 
 */
@SuppressWarnings("serial")
public class Main1_12 extends JFrame{
	static JFrame f = new Main1_12();
	Screen Object = new Screen();
	public Main1_12() {
		setUndecorated(false);//trueにするとバーが消える
		setSize(Toolkit.getDefaultToolkit().getScreenSize());//全画面表示
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(0,0);
		setTitle("3D Graphics Ver:1.12");
		add(Object); // Screen.javaの内容をFrameに追加する。
	}
	public static void main(String[] args) {
	}

}
