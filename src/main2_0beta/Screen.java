package main2_0beta;

import javax.swing.JPanel;

import org.w3c.dom.events.MouseEvent;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


/*
 * JPanel ��extend����class
 * Main.java��Frame��add���邽�߂�class
 * */

public class Screen extends JPanel{
//	double SleepTime = 1000 / frame; // 1s������30�t���[��
	double LastRefresh = 0;
	
	//�J�����̈ʒu
	static double[] ViewFrom = new double[] { -5 , -5 , 20 }; 
	
	//�I�u�W�F�N�g�̈ʒu
	static double[] ViewTo   = new double[] {  10 ,  2 ,  5 };
	
	//���̈ʒu
	static double[] LightDir = new double[] {  1 ,  1 ,  1 };
	
	//���݃}�E�X������Ă���|���S���̏��
	static Object PolygonOver = null ;
	
	//ArrayList of all the 3D polygons - each 3D polygon has a 2D 'PolygonObject' inside called 'DrawablePolygon'
	static ArrayList<DPolygon> DPolygons = new ArrayList<DPolygon>();
	static ArrayList<Cube> Cube = new ArrayList<Cube>();
	
	//�}�E�X�𒆐S�ɒu���Ă������߂Ɏg�p����T�u�N���X
	Robot r ;
	
	//�Y�[����
	static double zoom = 1000, MinZoom = 50, MaxZoom = 5000;
	static double MouseX = 0 , MouseY = 0;
	static double MovementSpeed = 0.5;
	
	double drawFPS = 0 , MaxFPS = 2000 , SleepTime = 1000.0 / MaxFPS , StartTime = System.currentTimeMillis();
	double LastFPSCheck = 0 , Checks = 0;
	
	//�z��DPolygon�̕`�悷�鏇�Ԃ�ێ�����z��
	int[] NewOrder;
	
	double VerticalLook = -0.9; //0.99 ~ -0.99�܂ŁA���̒l�̎��͏�����B���̒l�̎��͉�����
	double HorizontalLook = 0; // �C�ӂ̐��l���Ƃ�A���W�A���P�ʂň������
	double aimSight = 4;	// �Z���^�[�N���X�̑傫��
	double VerticalRotaionSpeed   = 5000;
	double HorizontalRotaionSpeed = 1000;
	double SunPos = 0 ;
	double VerticalViewRange = 0;
	
	static boolean OutLine = true;
	static String In = "NONE";
	boolean reset = false;
	boolean[] Keys = new boolean[12];
	
	final static int FontSize = 15;
	static String OverPolygon = "NONE";
	
	static int colorR = 0 , colorG = 0 , colorB = 0;

	/*�`�悵����3D�|���S�����`����class*/
	public Screen() {

		this.setFocusable(true);
		addKeyListener(new KeyTyped()); //KeyInput class����̃L�[����
		this.addMouseListener(new AboutMouse());
		this.addMouseMotionListener(new AboutMouse());
		this.addMouseWheelListener(new AboutMouse());

		//invisibleMouse()
		
//		new GenerateTerrain();
		
		/*
		 *  �P�D�ԁ@R255
			�Q�D��@R255�{G150			
			�R�D���@R255�{G240			
			�S�D�΁@G135			
			�T�D�@G145�{B255			
			�U�D���@G100�{B190			
			�V�D���@R145�{B130    
		 */
		Cube.add(new Cube(0,  6, 0, 2, 2, 2, new Color(255,0,0)));
		Cube.add(new Cube(0,  4, 0, 2, 2, 2, new Color(255 ,150, 0)));
		Cube.add(new Cube(0,  2, 0, 2, 2, 2, new Color(255,240,0)));		
		Cube.add(new Cube(0, -0, 0, 2, 2, 2, new Color(0,135,0)));
		Cube.add(new Cube(0, -2, 0, 2, 2, 2, new Color(0,145,255)));
		Cube.add(new Cube(0, -4, 0, 2, 2, 2, new Color(0,100,190)));
		Cube.add(new Cube(0, -6, 0, 2, 2, 2, new Color(145,0,130)));
		
		Color originalC = new Color(0,0,0);
		// RGB�l�̑�����
        int step = 5;
        // �F�̔z��
        Color[] colors = new Color[255/step];
        // ���̐F��z��ɒǉ�
        colors[0] = originalC;

        // for����RGB�l�𐧌䂵�ĐF���쐬
        for (int i = 1; i < colors.length; i++) {
        	int r = 0 , g = 0 , b = 0;
            r = Math.min(originalC.getRed()   + i * step / 3, 255);
            g = Math.min(originalC.getGreen() + i * step, 255);
//            b = Math.min(originalC.getBlue()  + i * step, 255);
            colors[i] = new Color(r, g, b);
            Cube.add(new Cube(-i*2, 0,  0, 2, 2, 2, colors[i]));
        }
        
	}
	
	public void paintComponent(Graphics g) {
		
		
		Font font = new Font(Font.DIALOG, Font.ITALIC,FontSize);
		g.setFont(font);
		
		g.clearRect(0 , 0, 2000 , 1200);
		//FPS display
		g.drawString("FPS: " + (int)drawFPS , 20, 15);
		g.drawString("ELAPSED TIME : " +  (System.currentTimeMillis() - Main.StartUpTime ) + "ms" , 20 , 45);
		g.drawString("OBJECT : " + Arrays.toString(ViewTo)   , 20 ,60);
		g.drawString("CAMERA : " + Arrays.toString(ViewFrom) , 20 ,75);
		g.drawString("ZOOM : " + zoom , 20 , 90);
		g.drawString("MOUSEX : " + MouseX + " MOUSEY" + MouseY , 20 , 115);
		g.drawString("Vertical View Range (arctan)"   + VerticalViewRange , 20 ,130);
		g.drawString("Horizontal View Range (arctan)" + HorizontalLook, 20 ,145);
		g.drawString("R :" + colorR + " G :" + colorG + " B :" + colorB , 20 ,170);
//		font = new Font(Font.DIALOG, Font.BOLD,FontSize + 10);
//		g.setFont(font);
//		g.drawString("CONDITION : " + In , 20 , 100);
		g.setColor(Color.BLACK);
		
		CameraMove();//�J�����𓮂���
		
		////���̃J�����ʒu�ň�ʓI�Ȃ��̂����ׂČv�Z���܂��B
		Calculator.Info();
		
		// �|���S�������e���A�b�v�f�[�g
		for(int i = 0 ; i < DPolygons.size() ; i ++) {
			DPolygons.get(i).updatePolygon();
		}
//		
//		Cube.get(0).rotation += .01;
//		Cube.get(0).updatePoly();
		
		setOrder();//�|���S���̋������\�[�g
		
		setPolygonOver(); //�}�E�X������Ă���|���S������肷��
		
		//setOrder�֐��Őݒ肳�ꂽ�����Ń|���S����`��
		for(int i = 0 ; i < NewOrder.length ; i ++) {
			DPolygons.get(NewOrder[i]).DrawablePolygon.drawPolygon(g);
		}
		
		SleepAndRefresh();
	}
	
	/*�`��X�V�̂��߂̃��\�b�h*/
	void SleepAndRefresh() {
		long timeSLU = (long)(System.currentTimeMillis() - LastRefresh);
		double hz = (System.currentTimeMillis() - LastFPSCheck)/1000.0;
		Checks ++;
		
		if(Checks >= 15) {
			drawFPS = Checks/ hz;
			LastFPSCheck = System.currentTimeMillis();
			Checks = 0 ;
		}
		if(timeSLU < 1000.0/MaxFPS) {
			try {
				Thread.sleep((long)(1000.0 / MaxFPS - timeSLU));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
//		colorR += 0.1;
//		if(colorR > 255) {
//			colorR = 0;
//		}
		LastRefresh = System.currentTimeMillis();
		repaint();
	}
	
	/*�}�E�X�J�[�\���Əd�Ȃ��Ă���|���S���̓���*/
	private void setPolygonOver() {
		PolygonOver = null;
		for(int i = NewOrder.length -1 ; i >= 0 ; i -- ) {
			if(DPolygons.get(NewOrder[i]).DrawablePolygon.MouseOver() && DPolygons.get(NewOrder[i]).draw && DPolygons.get(NewOrder[i]).DrawablePolygon.visible) {
				PolygonOver = DPolygons.get(NewOrder[i]).DrawablePolygon;
				break;
			}
		}
	}

	/*Sorting*/
	void setOrder() {
		//���������i�[����z��
		double[] k = new double[DPolygons.size()];
		//�\�[�g����������
		NewOrder = new int[DPolygons.size()];
		
		for(int i = 0 ; i < DPolygons.size() ; i++) {
			k[i] = DPolygons.get(i).AverageDistance;
			NewOrder[i] = i ;
		}
		
		/*���ϋ����̒Z�����Ƀ\�[�g����*/
		double dtmp ;
		int itmp;
		for(int i = 0 ; i < k.length - 1 ; i++) {
			for(int j = 0 ; j < k.length - 1 ; j++) {
				if(k[j] < k[j + 1]) {
					dtmp = k[j];
					itmp = NewOrder[j];
					NewOrder[j] = NewOrder[j + 1];
					k[j] = k[j + 1];
					NewOrder[j + 1] = itmp;
					k[j + 1] = dtmp;
				}
			}
		}
		
		
		
	}
	
	
	
	
	public void MouseMovement(double NX , double NY) {
		//�}�E�X��y��(�X�N���[���̒���)����ǂꂾ���͂Ȃꂽ���v��
		double difX = (NX - Main.ScreenSize.getWidth() / 2); 
		//�}�E�X��x��(�X�N���[���̒���)����ǂꂾ���͂Ȃꂽ���v��
		double difY = (NY - Main.ScreenSize.getHeight() / 2);
		
		difY *= 6 - Math.abs(VerticalLook) * 5;
		
		VerticalLook	+= difY / VerticalRotaionSpeed;
		HorizontalLook  -= difX / HorizontalRotaionSpeed;
		
		if(VerticalLook >  0.999) VerticalLook = 0.999;
		if(VerticalLook < -0.999) VerticalLook = -0.999;
		
		VerticalViewRange = VerticalLook;
		
		updateView();
	}
	
	private void MoveTo(double x, double y , double z) {
		ViewFrom[0] = x;
		ViewFrom[1] = y;
		ViewFrom[2] = z;
		updateView();
	}
	
	private void updateView() {
		double r = Math.sqrt(1 - (VerticalLook * VerticalLook));
		ViewTo[0] = ViewFrom[0] + r * Math.cos(HorizontalLook); // x���ړ�
		ViewTo[1] = ViewFrom[1] + r * Math.sin(HorizontalLook); // y���ړ�
		ViewTo[2] = ViewFrom[2] + VerticalLook ;				// z���ړ�	
	}
	
	/*�L�[���͂��琧�䂷�郁�\�b�h*/
	private void CameraMove() {
		Vector ViewVector = new Vector(ViewTo[0] - ViewFrom[0] , ViewTo[1] - ViewFrom[1] , ViewTo[2] - ViewFrom[2] );
		double xMove = 0 , yMove = 0 , zMove = 0;
		Vector VerticalVector = new Vector(0,0,1);
		Vector SideViewVector = ViewVector.CrossProduct(VerticalVector);
		
		
		if(Keys[0]){
			xMove += ViewVector.x;
			yMove += ViewVector.y;
			zMove += ViewVector.z;
		}
		
		if(Keys[2]){
			xMove -= ViewVector.x;
			yMove -= ViewVector.y;
			zMove -= ViewVector.z;
		}
		
		//���Ɉړ�
		if(Keys[1]){
			xMove += SideViewVector.x;
			yMove += SideViewVector.y;
			zMove += SideViewVector.z;
		}
		
		//�E�Ɉړ�
		if(Keys[3]){
			xMove -= SideViewVector.x;
			yMove -= SideViewVector.y;
			zMove -= SideViewVector.z;
		}
		
		//���̎��_�ɂ��ǂ�
		if(Keys[4]) {
			ViewFrom[0] = 15 ; ViewFrom[1] =  5 ; ViewFrom[2] = 10 ; 
			ViewTo[0] = 0;     ViewTo[1] = 0;ViewTo[2] = 0; 
		}
		
		
		Vector MoveVector = new Vector(xMove , yMove , zMove);
		double fX = MoveVector.x * MovementSpeed;
		double fY = MoveVector.y * MovementSpeed;
		double fz = MoveVector.z * MovementSpeed;
		
		//�J�����̍X�V
		MoveTo(ViewFrom[0] + fX , ViewFrom[1] + fY , ViewFrom[2] + fz);
		
	}
	
	/*�}�E�X�̏��������N���X*/
	class AboutMouse implements MouseListener , MouseMotionListener, MouseWheelListener{
		
		@Override
		public void mouseDragged(java.awt.event.MouseEvent e) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			MouseMovement(e.getX(), e.getY());
			MouseX = e.getX();
			MouseY = e.getY();
			CenterMouse();
		}
		
		//�}�E�X�𒆉��Ɉړ������郁�\�b�h
		private void CenterMouse() {
			try {
				r = new Robot();
				r.mouseMove((int)Main.ScreenSize.getWidth()/2 , (int)Main.ScreenSize.getHeight()/2 );
			} catch (AWTException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}	
		}
		
		
		@Override
		public void mouseMoved(java.awt.event.MouseEvent e) {	
			MouseMovement(e.getX(), e.getY());
			MouseX = e.getX();
			MouseY = e.getY();
			CenterMouse();
		}
		@Override
		public void mouseClicked(java.awt.event.MouseEvent e) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
		
		public void mousePressed(MouseEvent arg0) {
			//�E�N���b�N�E���N���b�N�̓����ǉ�
//			if(arg0.getButton() == MouseEvent.BUTTON1) {
//				
//			}
			
		}
		
		@Override
		public void mousePressed(java.awt.event.MouseEvent e) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
		
		@Override
		public void mouseReleased(java.awt.event.MouseEvent e) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
		@Override
		public void mouseEntered(java.awt.event.MouseEvent e) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
		@Override
		public void mouseExited(java.awt.event.MouseEvent e) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			if(e.getUnitsToScroll() > 0) {
				if(zoom > MinZoom) {
					zoom -= 25 * e.getUnitsToScroll();
				}
			}else {
				if(zoom < MaxZoom) {
					zoom -= 25 * e.getUnitsToScroll();
				}
			}
			
		}

		

	}
	/*�L�[���͗pclass*/
	class KeyTyped extends KeyAdapter{
		
		//�L�[������������true
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_W : 	 Keys[0] = true ; break;
			case KeyEvent.VK_A : 	 Keys[1] = true ; break;
			case KeyEvent.VK_S : 	 Keys[2] = true ; break;
			case KeyEvent.VK_D :	 Keys[3] = true ; break;
			case KeyEvent.VK_X :	 Keys[4] = true ; break;
			case KeyEvent.VK_SHIFT:  Keys[5] = true ; break;
			case KeyEvent.VK_UP : 	 Keys[6] = true ; break;
			case KeyEvent.VK_DOWN :	 Keys[7] = true ; break;
			case KeyEvent.VK_LEFT :	 Keys[8] = true ; break;
			case KeyEvent.VK_RIGHT : Keys[9] = true ; break;
			case KeyEvent.VK_ESCAPE : System.exit(0); //Esc�L�[�������ƏI��
			
			}
		}
		
		//�L�[�𗣂�������false
		public void keyReleased(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_W : 	 Keys[0] = false ; break;
			case KeyEvent.VK_A : 	 Keys[1] = false ; break;
			case KeyEvent.VK_S : 	 Keys[2] = false ; break;
			case KeyEvent.VK_D :	 Keys[3] = false ; break;
			case KeyEvent.VK_X :     Keys[4] = false ; break;
			case KeyEvent.VK_SHIFT:  Keys[5] = false ; break;
			case KeyEvent.VK_UP : 	 Keys[6] = false ; break;
			case KeyEvent.VK_DOWN :	 Keys[7] = false ; break;
			case KeyEvent.VK_LEFT :	 Keys[8] = false ; break;
			case KeyEvent.VK_RIGHT : Keys[9] = false ; break;

			}
		}
	}	
	
}