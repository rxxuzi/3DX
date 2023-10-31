package rage;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JPanel;


public class Screen extends JPanel {
	/**
	 * serialVersionUID �́A���񉻂��ꂽ�I�u�W�F�N�g�̃o�[�W�����Ǘ��Ɏg�p����܂��B
	 * serialVersionUID �𖾎��I�ɐ錾���Ȃ��ꍇ�AJVM �� Serializable �N���X�̂��܂��܂ȑ��ʂɊ�Â��Ď����I�Ɍv�Z���܂��B
	 * �������A�قȂ�o�[�W�����̃N���X��ǂݍ���ł��܂����ƂŔ���������s���ȓ����h�����߂ɁAserialVersionUID ���N���X�ɐ錾���邱�Ƃ���������Ă��܂��B
	 */
	private static final long serialVersionUID = 1L;
	
	//class���i�[����ArrayList
	static ArrayList<DPolygon> DPolygons = new ArrayList<DPolygon>();
	static ArrayList<Cube> Cube = new ArrayList<Cube>();
	
	
	//�J�E���g�p�z��
	static int[] colorBox = new int[256 * 256 * 256];
	static int counter1 = 0;
	
	//���݃}�E�X������Ă���|���S���̏��
	static Object PolygonOver = null ;
	//�t�H�[�J�X���Ă���|���S�����
	static Object FocusPolygon = null;
	//�I�u�W�F�N�g���폜����C���^�[�o��(Mill Second)
	static final long deleteinterval = 100;
	//�Ō�ɃL���[�u���폜��������
	static long LastCubeDeleteTime = 0;
	int NumberOfDeleteCube = 0 ;
	static String dCube = "NONE";
	
	//�}�E�X�𒆐S�ɒu���Ă������߂Ɏg�p����T�u�N���X
	Robot r ;
	//��������
	static Random random = new Random();
	
	//�����̃J�����ƃI�u�W�F�N�g�̈ʒu
	static final double[] FViewFrom = { 15 , 5 , 10 };
	static final double[] FViewTo = {  0 , 0 ,  0 };
	
	//�J�����̈ʒu
	static double[] ViewFrom = FViewFrom.clone();
	
	//�I�u�W�F�N�g�̈ʒu
	static double[] ViewTo   = FViewTo.clone();
	

	//�Y�[����
	static double zoom = 1000, MinZoom = 100, MaxZoom = 5000;
	//�}�E�X�̍��W
	static double MouseX = 0 , MouseY = 0;
	
	//���_�̓����X�s�[�h�𐧌�B�傫���قǒx������
	static double MovementSpeed = 0.5;
	//FPS�̑���
	double drawFPS = 0 , MaxFPS = 2000 , SleepTime = 1000.0 / MaxFPS , StartTime = System.currentTimeMillis();
	double LastFPSCheck = 0 , Checks = 0 , LastRefresh = 0;
		
	double VerticalLook = -0.9; //0.99 ~ -0.99�܂ŁA���̒l�̎��͏�����B���̒l�̎��͉�����
	double HorizontalLook = 0; // �C�ӂ̐��l���Ƃ�A���W�A���P�ʂň������
	double VerticalRotaionSpeed   = 1000; //������]�̑���
	double HorizontalRotaionSpeed = 500; //������]�̑���
	
	static final double aimSight = 4;	// �Z���^�[�N���X�̑傫��

	//�z��DPolygon�̕`�悷�鏇�Ԃ�ێ�����z��
	int[] NewOrder;
	
	static boolean OutLines = true;
	
	//�L�[���͂̏����i�[����z��
	boolean[] Control = new boolean[10];
	
	//�t�H���g�T�C�Y
	final static int FontSize = 15;
	//�F���(RGB�l)�̐錾
	static int colorR = 0 , colorG = 0 , colorB = 0;
	
	static int[][] txt2object;
	
	static String condition = "NONE";
	
	/*�`�悵����3D�|���S�����`����class*/
	public Screen(){		
		this.addKeyListener(new KeyTyped());
		setFocusable(true);		
		
		this.addMouseListener(new AboutMouse());
		this.addMouseMotionListener(new AboutMouse());
		this.addMouseWheelListener(new AboutMouse());
		
		invisibleMouse();
		
		Cube.add(new Cube(0, -6, 0, 2, 2, 2, Color.CYAN));

		//�e�L�X�g����I�u�W�F�N�g�𐻑�
		for(int i = 0 ; i < 3 ; i ++ ) {
			new TextToObject("./rsc/world/world" + i + ".txt" , 1);			
		}
		
	}	
	
	/*�`��Ɋւ��郁�\�b�h*/
	public void paintComponent(Graphics g){
		//�`�惊�Z�b�g
		g.clearRect(0, 0, (int)Main.ScreenSize.getWidth(), (int)Main.ScreenSize.getHeight());
		
		//�J�����𓮂���
		KeyControl();
		
		//�t�H�[�J�X���ꂽ�|���S�����폜����
		deleteCube();
		
		//���̃J�����ʒu�ň�ʓI�Ȃ��̂����ׂČv�Z���܂��B
		Calculator.VectorInfo();

		// �|���S�������e���A�b�v�f�[�g
		for(int i = 0; i < DPolygons.size(); i++) {			
			DPolygons.get(i).updatePolygon();
		}
		
//		//�|���S������]����
//		Cube.get(0).rotation+=0.01;
//		Cube.get(0).updatePoly();

		//�S�Ẵ|���S���̋������\�[�g
		setOrder();
		
		//�}�E�X������Ă���|���S������肷��
		setPolygonOver();
		
		//setOrder�֐��Őݒ肳�ꂽ�����Ń|���S����`��
		for(int i = 0; i < NewOrder.length; i++) {			
			DPolygons.get(NewOrder[i]).DrawablePolygon.drawPolygon(g);
		}
			
		//��ʂ̒����ɃG�C��������
		drawMouseAim(g);			

		//�t�H���g�̐ݒ�
		Font font = new Font(Font.DIALOG, Font.ITALIC,FontSize);
		g.setFont(font);
		
		g.drawString("FPS : " + (int)drawFPS , 10, 15);
		g.drawString("ELAPSED TIME : " + (System.currentTimeMillis() - Main.StartUpTime ) + "ms" , 10 , 30);
		g.drawString("OBJECT : " + Arrays.toString(ViewTo)   , 10 ,45);
		g.drawString("CAMERA : " + Arrays.toString(ViewFrom) , 10 ,60);
		g.drawString("ZOOM   : " + zoom , 10 , 75);
		g.drawString("Vertical   Look : " + VerticalLook , 10 , 90);
		g.drawString("Horizontal Look(rad) : " + HorizontalLook , 10 , 105);
		double VAngle =  Math.toDegrees(Math.tan(VerticalLook));
		g.drawString("Vertical angle   	 : " + (int)VAngle + "��" , 10 ,120);
		g.drawString("Number Of Polygons : " + DPolygons.size() , 10 ,135);
		g.drawString("Number Of Cubes    : " + Cube.size() , 10 ,150);
		g.setFont(new Font(Font.SANS_SERIF , Font.BOLD , 20));
		g.drawString("CONDITION: " + condition , 10 ,190);
		
		//�`��X�V�̃C���^�[�o��
		SleepAndRefresh();
	}
	
	/*
	 * Control7�������ꂽ���A�t�H�[�J�X���Ă���L���[�u����肵�č폜����
	 *�@�A���ŏ�����̂�h���ׁAdelete interval��݂��Ă���
	 */
	private void deleteCube() {
		if(Control[7]) {
			if(System.currentTimeMillis() - LastCubeDeleteTime >= deleteinterval) {
				for(int i = 0 ; i < Cube.size() ; i ++) {
					for(int j = 0 ; j < Cube.get(i).Polys.length ; j ++) {
						if( Cube.get(i).Polys[j].DrawablePolygon.equals(FocusPolygon) ) {
							dCube = Cube.get(i).toString();
							Cube.get(i).removeCube();
							LastCubeDeleteTime = System.currentTimeMillis();
							NumberOfDeleteCube ++ ;								
							condition = "CUBE DELETED : " + dCube;
							break;
						}
					}	
				}
			}
		}
		
		//�S�폜
		if(Control[9]) {
			for(int i = 0 ; i < Cube.size() ; i ++ ) {
				Cube.get(i).removeCube();
				condition = "ALL DELETE";
			}
		}
	}

	//���������\�[�g
	private void setOrder(){
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
	
	//�}�E�X���\��
	private void invisibleMouse(){
		 Toolkit toolkit = Toolkit.getDefaultToolkit();
		 BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT); 
		 Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, new Point(0,0), "InvisibleCursor");        
		 setCursor(invisibleCursor);
	}
	
	//�}�E�X�G�C����`��
	private void drawMouseAim(Graphics g){
		g.setColor(Color.black);
		g.drawLine((int)(Main.ScreenSize.getWidth()/2 - aimSight), (int)(Main.ScreenSize.getHeight()/2), (int)(Main.ScreenSize.getWidth()/2 + aimSight), (int)(Main.ScreenSize.getHeight()/2));
		g.drawLine((int)(Main.ScreenSize.getWidth()/2), (int)(Main.ScreenSize.getHeight()/2 - aimSight), (int)(Main.ScreenSize.getWidth()/2), (int)(Main.ScreenSize.getHeight()/2 + aimSight));			
	}
	
	/*�`��X�V�̂��߂̃��\�b�h*/
	private void SleepAndRefresh(){
		long timeSLU = (long) (System.currentTimeMillis() - LastRefresh); 

		Checks ++;			
		
		if(Checks >= 15){
			drawFPS = Checks/((System.currentTimeMillis() - LastFPSCheck)/1000.0);
			LastFPSCheck = System.currentTimeMillis();
			Checks = 0;
		}
		
		if(timeSLU < 1000.0/MaxFPS){
			try {
				Thread.sleep((long) (1000.0/MaxFPS - timeSLU));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
				
		LastRefresh = System.currentTimeMillis();
		
		repaint();
	}
	
	//�L�[���͂𐧌�
	private void KeyControl(){
		Vector ViewVector = new Vector(ViewTo[0] - ViewFrom[0], ViewTo[1] - ViewFrom[1], ViewTo[2] - ViewFrom[2]);
		double xMove = 0, yMove = 0, zMove = 0;
		//(����)�P�ʃx�N�g�����擾
		Vector VerticalVector = new Vector (0, 0, 1);
		//�����ɓ����x�N�g��
		Vector SideViewVector = ViewVector.CrossProduct(VerticalVector);
		
		//�O�Ɉړ�
		if(Control[0]){
			xMove += ViewVector.x * 0.25;
			yMove += ViewVector.y * 0.25;
			zMove += ViewVector.z * 0.25;
		}
		
		//���Ɉړ�
		if(Control[2]){
			xMove -= ViewVector.x * 0.25;
			yMove -= ViewVector.y * 0.25;
			zMove -= ViewVector.z * 0.25;
		}
		//���Ɉړ�
		if(Control[1]){
			xMove += SideViewVector.x * 0.25;
			yMove += SideViewVector.y * 0.25;
			zMove += SideViewVector.z * 0.25;
		}
		//�E�Ɉړ�
		if(Control[3]){
			xMove -= SideViewVector.x * 0.25;
			yMove -= SideViewVector.y * 0.25;
			zMove -= SideViewVector.z * 0.25;
		}
		
		//�ړ�����x�N�g��
		Vector MoveVector = new Vector(xMove, yMove, zMove);
		double fx = MoveVector.x * MovementSpeed;
		double fy = MoveVector.y * MovementSpeed;
		double fz = MoveVector.z * MovementSpeed;
		
		MoveTo(ViewFrom[0] + fx, ViewFrom[1] + fy, ViewFrom[2] + fz);
		
		//�J�������W�����Z�b�g
		if(Control[4]) {
			for(int i = 0 ; i < FViewFrom.length ; i ++) {
				ViewFrom[i] = FViewFrom[i];
				ViewTo[i] = FViewTo[i];
			}
			zoom = 1000;
			condition = "View Reset";
		}
		
		//���̕�����z�ړ�
		if(Control[5]) {
			ViewFrom[2] += 0.4;
			ViewTo[2] += 0.4;			
		}
		//���̕�����z�ړ�
		if(Control[6]) {
			//z < 0�̏ꍇz = 0�Ŏ~�߂�
			if(ViewFrom[2] > 2.0) {
				ViewFrom[2] -= 0.4;
				ViewTo[2] -= 0.4;
			}else {
				ViewFrom[2] -= 0.1;
				ViewTo[2] -= 0.1;
			}
		}
		
		//�����_���ȃL���[�u�𐶐�
		if(Control[8]) {
			int rx = random.nextInt(255);
			int ry = random.nextInt(255);
			int rz = random.nextInt(255);
			int xyz = rx * 1000000 + ry * 1000 + rz;
			colorBox[counter1] = xyz;
			if(CoordinateCheck(xyz)) {
				Cube.add(new Cube(-rx/25 , ry/25 , rz/25 + 2, 1, 1, 1, new Color(rx , ry , rz) ));
				condition = "CUBE GENERATED : (x,y,z) = " + rx /25 +"," + ry /25 + "," + rz /25;
			}
			counter1 ++ ;
		}
		
	}
	
	private boolean CoordinateCheck(Integer xyz) {
		for(int i = 0 ; i < counter1 ; i ++) {
			if(colorBox[i] == xyz) {
				return false;
			}
		}
		return true;
		
	}

	//�J�����̍��W�����߂郁�\�b�h
	private void MoveTo(double x, double y, double z){
		ViewFrom[0] = x;
		ViewFrom[1] = y;
		ViewFrom[2] = z;
		
		if(ViewFrom[2] < 0) ViewFrom[2] = 0;
		
		//�`��X�V
		updateView();
	}
	
	//�}�E�X������Ă���|���S�������
	private void setPolygonOver(){
		//��xnull�ƒ�`
		PolygonOver = null;
		//�T��
		for(int i = NewOrder.length-1; i >= 0; i--) {
			if(DPolygons.get(NewOrder[i]).DrawablePolygon.MouseOver() && DPolygons.get(NewOrder[i]).draw && DPolygons.get(NewOrder[i]).DrawablePolygon.visible){
				FocusPolygon = DPolygons.get(NewOrder[i]).DrawablePolygon;
				PolygonOver = DPolygons.get(NewOrder[i]).DrawablePolygon;
				
				break;
			}
		}	
	}

	private void MouseMovement(double NewX, double NewY){		
		//�}�E�X��y��(�X�N���[���̒���)����ǂꂾ���͂Ȃꂽ���v��
		double difX = (NewX - Main.ScreenSize.getWidth()/2);
		//�}�E�X��x��(�X�N���[���̒���)����ǂꂾ���͂Ȃꂽ���v��
		double difY = (NewY - Main.ScreenSize.getHeight()/2);
		difY *= 6 - Math.abs(VerticalLook) * 5;
		
		VerticalLook   -= difY  / VerticalRotaionSpeed;
		HorizontalLook += difX / HorizontalRotaionSpeed;
		
		//VerticalLook�̐�Βl��1.0�ȏ�ɂȂ�Ȃ��悤�ɂ���
		if(VerticalLook>0.999) VerticalLook = 0.999;
		if(VerticalLook<-0.999) VerticalLook = -0.999;
		
		updateView();
	}
	
	//���_���A�b�v�f�[�g
	private void updateView(){
		double r = Math.sqrt(1 - (VerticalLook * VerticalLook));
		ViewTo[0] = ViewFrom[0] + r * Math.cos(HorizontalLook); // x���ړ�
		ViewTo[1] = ViewFrom[1] + r * Math.sin(HorizontalLook);	// y���ړ�
		ViewTo[2] = ViewFrom[2] + VerticalLook;					// z���ړ�	
	}
	
	/*�L�[���͗pclass*/
	class KeyTyped extends KeyAdapter{
		
		//�L�[������������true
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_W : 	 Control[0] = true ; break; //�O�i
			case KeyEvent.VK_A : 	 Control[1] = true ; break; //���
			case KeyEvent.VK_S : 	 Control[2] = true ; break; //����
			case KeyEvent.VK_D :	 Control[3] = true ; break; //�E��
			case KeyEvent.VK_X : 	 Control[4] = true ; break; //���_���Z�b�g
			case KeyEvent.VK_SPACE:	 Control[5] = true ; break; //���
			case KeyEvent.VK_SHIFT:	 Control[6] = true ; break; //����
			case KeyEvent.VK_BACK_SPACE: Control[7] = true ; break; //����
			case KeyEvent.VK_R : 	 Control[8] = true ; break;
			case KeyEvent.VK_DELETE :Control[9] = true ; break;
			case KeyEvent.VK_O 	:	 Control[10] = true ; break;
			case KeyEvent.VK_ESCAPE : System.exit(0); //Escape�L�[�������ƏI��
			}
		}
		
		//�L�[�𗣂�������false
		public void keyReleased(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_W : 	 Control[0] = false ; break;
			case KeyEvent.VK_A : 	 Control[1] = false ; break;
			case KeyEvent.VK_S : 	 Control[2] = false ; break;
			case KeyEvent.VK_D :	 Control[3] = false ; break;
			case KeyEvent.VK_X : 	 Control[4] = false ; break;
			case KeyEvent.VK_SPACE:	 Control[5] = false ; break;
			case KeyEvent.VK_SHIFT:	 Control[6] = false ; break;
			case KeyEvent.VK_BACK_SPACE: Control[7] = false ; break;
			case KeyEvent.VK_R : 	 	 Control[8] = false ; break;
			case KeyEvent.VK_DELETE :Control[9] = false ; break;
			case KeyEvent.VK_O 	:	 Control[10] = false ; break;

			}
		}
	}
	
	/*�}�E�X�̏��������N���X*/
	class AboutMouse implements MouseListener , MouseMotionListener, MouseWheelListener{
		
		//�}�E�X�𒆉��Ɉړ������郁�\�b�h
		void CenterMouse(){
				try {
					r = new Robot();
					r.mouseMove((int)Main.ScreenSize.getWidth()/2, (int)Main.ScreenSize.getHeight()/2);
				} catch (AWTException e) {
					e.printStackTrace();
				}
		}
		
		//�}�E�X���h���b�O�����Ƃ��̐���		
		public void mouseDragged(MouseEvent e) {
			MouseMovement(e.getX(), e.getY());
			MouseX = e.getX();
			MouseY = e.getY();
			CenterMouse();
		}

		//�}�E�X�̓����𐧌�
		public void mouseMoved(MouseEvent e) {
			MouseMovement(e.getX(), e.getY());
			MouseX = e.getX();
			MouseY = e.getY();
			CenterMouse();
		}
		
		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}
		
		//�}�E�X�̃N���b�N�������Ƃ��̐���
		public void mousePressed(MouseEvent e) {
			//�E�N���b�N
			if(e.getButton() == MouseEvent.BUTTON1) {
				if(PolygonOver != null) PolygonOver.seeThrough = false;				
			}
			//���N���b�N
			if(e.getButton() == MouseEvent.BUTTON3) {				
				if(PolygonOver != null) PolygonOver.seeThrough = true;
			}
		}

		public void mouseReleased(MouseEvent e) {
		}
		
		//�}�E�X�z�C�[���������ǂ郁�\�b�h
		public void mouseWheelMoved(MouseWheelEvent e) {
			if(e.getUnitsToScroll()>0)			{
				if(zoom > MinZoom) zoom -= 25 * e.getUnitsToScroll();
				condition = "Zoom out";
			}
			else			{
				if(zoom < MaxZoom) zoom -= 25 * e.getUnitsToScroll();
				condition = "Zoom in";
			}	
		}
	}
}