import javax.swing.JFrame;
import javax.swing.JTextField;


/*
 * @author Rxxuzi
 * @version 2.8
 */
public class Main{	
	static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	static JTextField TF;

	static final long StartUpTime = System.currentTimeMillis();

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
