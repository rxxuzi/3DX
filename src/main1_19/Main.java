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
		setUndecorated(false);//true�ɂ���ƃo�[��������
		setSize(ScreenSize);//�S��ʕ\��
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(0,0);
		setTitle("3D Graphics");
		add(Object); // Screen.java�̓��e��Frame�ɒǉ�����B
	}
	public static void main(String[] args) {
	}

}
