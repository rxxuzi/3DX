package main2_0alpha;

import javax.swing.JFrame;
import java.awt.*;
/**
 * @author Rxxuzi
 * @version 2.0 alpha
 */
@SuppressWarnings("serial")
public class Main extends JFrame{
	static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static long StartUpTime = System.currentTimeMillis();
	Screen Panel = new Screen();
	public Main() {
		setUndecorated(false);//true�ɂ���ƃo�[��������
		setSize(ScreenSize);//�S��ʕ\��
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(0,0);
		setTitle("3D Graphics Ver 1.20");
		add(Panel); // Screen.java�̓��e��Frame�ɒǉ�����B
	}
	public static void main(String[] args) {
		JFrame f = new Main();
		f.setVisible(true);
		f.getJMenuBar();
	}

}
