package main2_10;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;


/**
 * Project 3DX
 * @author Rxxuzi
 * @version 2.8
 *
 */
public class Main{	
	static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static final long StartUpTime = System.currentTimeMillis();
	public static Saves saves = new Saves("./rsc/log/data.log");
	private static final boolean Debug = false;
	public static void main(String[] args){
		JFrame jf = new JFrame();
		Screen Panel = new Screen();
		if(Debug){
			jf.setSize(1000,1000);
			jf.add(new Menu());
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}else {
			jf.setUndecorated(true);
			jf.setSize(ScreenSize);
			jf.getJMenuBar();
			jf.add(Panel);
		}
		jf.setVisible(true);
		System.out.println("It is working properly.");
	}
}
