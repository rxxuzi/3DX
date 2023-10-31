package main2_1;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JTextField;

/*
 * @author rxxuzi
 * @version 2.1
 */
public class Main{	
	static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	static JTextField TF;
	
	static final long StartUpTime = System.currentTimeMillis();
	
	Screen Panel = new Screen();
	
	public static void main(String[] args){
		JFrame jf = new JFrame();
		Screen Panel = new Screen();
		jf.add(Panel);
		jf.setUndecorated(true);
	    jf.setSize(ScreenSize);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.getJMenuBar();
		System.out.println("It is working properly.");
	}
}
