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
		setUndecorated(false);//true�ɂ���ƃo�[��������
		setSize(ScreenSize);//�S��ʕ\��
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(0,0);
		setTitle("3D Graphics Ver:1.14");
		add(Object); // Screen.java�̓��e��Frame�ɒǉ�����B
	}
	public static void main(String[] args) {
	}

}
