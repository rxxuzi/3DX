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
	 * serialVersionUID は、直列化されたオブジェクトのバージョン管理に使用されます。
	 * serialVersionUID を明示的に宣言しない場合、JVM は Serializable クラスのさまざまな側面に基づいて自動的に計算します。
	 * ただし、異なるバージョンのクラスを読み込んでしまうことで発生しうる不正な動作を防ぐために、serialVersionUID をクラスに宣言することが推奨されています。
	 */
	private static final long serialVersionUID = 1L;
	
	//classを格納するArrayList
	static ArrayList<DPolygon> DPolygons = new ArrayList<DPolygon>();
	static ArrayList<Cube> Cube = new ArrayList<Cube>();
	
	
	//カウント用配列
	static int[] colorBox = new int[256 * 256 * 256];
	static int counter1 = 0;
	
	//現在マウスが乗っているポリゴンの情報
	static Object PolygonOver = null ;
	//フォーカスしているポリゴン情報
	static Object FocusPolygon = null;
	//オブジェクトを削除するインターバル(Mill Second)
	static final long deleteinterval = 100;
	//最後にキューブを削除した時間
	static long LastCubeDeleteTime = 0;
	int NumberOfDeleteCube = 0 ;
	static String dCube = "NONE";
	
	//マウスを中心に置いておくために使用するサブクラス
	Robot r ;
	//乱数生成
	static Random random = new Random();
	
	//初期のカメラとオブジェクトの位置
	static final double[] FViewFrom = { 15 , 5 , 10 };
	static final double[] FViewTo = {  0 , 0 ,  0 };
	
	//カメラの位置
	static double[] ViewFrom = FViewFrom.clone();
	
	//オブジェクトの位置
	static double[] ViewTo   = FViewTo.clone();
	

	//ズーム率
	static double zoom = 1000, MinZoom = 100, MaxZoom = 5000;
	//マウスの座標
	static double MouseX = 0 , MouseY = 0;
	
	//視点の動くスピードを制御。大きいほど遅く動く
	static double MovementSpeed = 0.5;
	//FPSの測定
	double drawFPS = 0 , MaxFPS = 2000 , SleepTime = 1000.0 / MaxFPS , StartTime = System.currentTimeMillis();
	double LastFPSCheck = 0 , Checks = 0 , LastRefresh = 0;
		
	double VerticalLook = -0.9; //0.99 ~ -0.99まで、正の値の時は上向き。負の値の時は下向き
	double HorizontalLook = 0; // 任意の数値をとり、ラジアン単位で一周する
	double VerticalRotaionSpeed   = 1000; //垂直回転の速さ
	double HorizontalRotaionSpeed = 500; //水平回転の速さ
	
	static final double aimSight = 4;	// センタークロスの大きさ

	//配列DPolygonの描画する順番を保持する配列
	int[] NewOrder;
	
	static boolean OutLines = true;
	
	//キー入力の情報を格納する配列
	boolean[] Control = new boolean[10];
	
	//フォントサイズ
	final static int FontSize = 15;
	//色情報(RGB値)の宣言
	static int colorR = 0 , colorG = 0 , colorB = 0;
	
	static int[][] txt2object;
	
	static String condition = "NONE";
	
	/*描画したい3Dポリゴンを定義するclass*/
	public Screen(){		
		this.addKeyListener(new KeyTyped());
		setFocusable(true);		
		
		this.addMouseListener(new AboutMouse());
		this.addMouseMotionListener(new AboutMouse());
		this.addMouseWheelListener(new AboutMouse());
		
		invisibleMouse();
		
		Cube.add(new Cube(0, -6, 0, 2, 2, 2, Color.CYAN));

		//テキストからオブジェクトを製造
		for(int i = 0 ; i < 3 ; i ++ ) {
			new TextToObject("./rsc/world/world" + i + ".txt" , 1);			
		}
		
	}	
	
	/*描画に関するメソッド*/
	public void paintComponent(Graphics g){
		//描画リセット
		g.clearRect(0, 0, (int)Main.ScreenSize.getWidth(), (int)Main.ScreenSize.getHeight());
		
		//カメラを動かす
		KeyControl();
		
		//フォーカスされたポリゴンを削除する
		deleteCube();
		
		//このカメラ位置で一般的なものをすべて計算します。
		Calculator.VectorInfo();

		// ポリゴン情報を各自アップデート
		for(int i = 0; i < DPolygons.size(); i++) {			
			DPolygons.get(i).updatePolygon();
		}
		
//		//ポリゴンを回転する
//		Cube.get(0).rotation+=0.01;
//		Cube.get(0).updatePoly();

		//全てのポリゴンの距離をソート
		setOrder();
		
		//マウスが乗っているポリゴンを特定する
		setPolygonOver();
		
		//setOrder関数で設定された順序でポリゴンを描画
		for(int i = 0; i < NewOrder.length; i++) {			
			DPolygons.get(NewOrder[i]).DrawablePolygon.drawPolygon(g);
		}
			
		//画面の中央にエイムをつける
		drawMouseAim(g);			

		//フォントの設定
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
		g.drawString("Vertical angle   	 : " + (int)VAngle + "°" , 10 ,120);
		g.drawString("Number Of Polygons : " + DPolygons.size() , 10 ,135);
		g.drawString("Number Of Cubes    : " + Cube.size() , 10 ,150);
		g.setFont(new Font(Font.SANS_SERIF , Font.BOLD , 20));
		g.drawString("CONDITION: " + condition , 10 ,190);
		
		//描画更新のインターバル
		SleepAndRefresh();
	}
	
	/*
	 * Control7が押された時、フォーカスしているキューブを特定して削除する
	 *　連続で消えるのを防ぐ為、delete intervalを設けている
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
		
		//全削除
		if(Control[9]) {
			for(int i = 0 ; i < Cube.size() ; i ++ ) {
				Cube.get(i).removeCube();
				condition = "ALL DELETE";
			}
		}
	}

	//距離情報をソート
	private void setOrder(){
		//距離情報を格納する配列
		double[] k = new double[DPolygons.size()];
		//ソートした情報を代入
		NewOrder = new int[DPolygons.size()];
		
		for(int i = 0 ; i < DPolygons.size() ; i++) {
			k[i] = DPolygons.get(i).AverageDistance;
			NewOrder[i] = i ;
		}
		
		/*平均距離の短い順にソートする*/
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
	
	//マウスを非表示
	private void invisibleMouse(){
		 Toolkit toolkit = Toolkit.getDefaultToolkit();
		 BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT); 
		 Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, new Point(0,0), "InvisibleCursor");        
		 setCursor(invisibleCursor);
	}
	
	//マウスエイムを描画
	private void drawMouseAim(Graphics g){
		g.setColor(Color.black);
		g.drawLine((int)(Main.ScreenSize.getWidth()/2 - aimSight), (int)(Main.ScreenSize.getHeight()/2), (int)(Main.ScreenSize.getWidth()/2 + aimSight), (int)(Main.ScreenSize.getHeight()/2));
		g.drawLine((int)(Main.ScreenSize.getWidth()/2), (int)(Main.ScreenSize.getHeight()/2 - aimSight), (int)(Main.ScreenSize.getWidth()/2), (int)(Main.ScreenSize.getHeight()/2 + aimSight));			
	}
	
	/*描画更新のためのメソッド*/
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
	
	//キー入力を制御
	private void KeyControl(){
		Vector ViewVector = new Vector(ViewTo[0] - ViewFrom[0], ViewTo[1] - ViewFrom[1], ViewTo[2] - ViewFrom[2]);
		double xMove = 0, yMove = 0, zMove = 0;
		//(垂直)単位ベクトルを取得
		Vector VerticalVector = new Vector (0, 0, 1);
		//水平に動くベクトル
		Vector SideViewVector = ViewVector.CrossProduct(VerticalVector);
		
		//前に移動
		if(Control[0]){
			xMove += ViewVector.x * 0.25;
			yMove += ViewVector.y * 0.25;
			zMove += ViewVector.z * 0.25;
		}
		
		//後ろに移動
		if(Control[2]){
			xMove -= ViewVector.x * 0.25;
			yMove -= ViewVector.y * 0.25;
			zMove -= ViewVector.z * 0.25;
		}
		//左に移動
		if(Control[1]){
			xMove += SideViewVector.x * 0.25;
			yMove += SideViewVector.y * 0.25;
			zMove += SideViewVector.z * 0.25;
		}
		//右に移動
		if(Control[3]){
			xMove -= SideViewVector.x * 0.25;
			yMove -= SideViewVector.y * 0.25;
			zMove -= SideViewVector.z * 0.25;
		}
		
		//移動するベクトル
		Vector MoveVector = new Vector(xMove, yMove, zMove);
		double fx = MoveVector.x * MovementSpeed;
		double fy = MoveVector.y * MovementSpeed;
		double fz = MoveVector.z * MovementSpeed;
		
		MoveTo(ViewFrom[0] + fx, ViewFrom[1] + fy, ViewFrom[2] + fz);
		
		//カメラ座標をリセット
		if(Control[4]) {
			for(int i = 0 ; i < FViewFrom.length ; i ++) {
				ViewFrom[i] = FViewFrom[i];
				ViewTo[i] = FViewTo[i];
			}
			zoom = 1000;
			condition = "View Reset";
		}
		
		//正の方向にz移動
		if(Control[5]) {
			ViewFrom[2] += 0.4;
			ViewTo[2] += 0.4;			
		}
		//負の方向にz移動
		if(Control[6]) {
			//z < 0の場合z = 0で止める
			if(ViewFrom[2] > 2.0) {
				ViewFrom[2] -= 0.4;
				ViewTo[2] -= 0.4;
			}else {
				ViewFrom[2] -= 0.1;
				ViewTo[2] -= 0.1;
			}
		}
		
		//ランダムなキューブを生成
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

	//カメラの座標を決めるメソッド
	private void MoveTo(double x, double y, double z){
		ViewFrom[0] = x;
		ViewFrom[1] = y;
		ViewFrom[2] = z;
		
		if(ViewFrom[2] < 0) ViewFrom[2] = 0;
		
		//描画更新
		updateView();
	}
	
	//マウスが乗っているポリゴンを特定
	private void setPolygonOver(){
		//一度nullと定義
		PolygonOver = null;
		//探索
		for(int i = NewOrder.length-1; i >= 0; i--) {
			if(DPolygons.get(NewOrder[i]).DrawablePolygon.MouseOver() && DPolygons.get(NewOrder[i]).draw && DPolygons.get(NewOrder[i]).DrawablePolygon.visible){
				FocusPolygon = DPolygons.get(NewOrder[i]).DrawablePolygon;
				PolygonOver = DPolygons.get(NewOrder[i]).DrawablePolygon;
				
				break;
			}
		}	
	}

	private void MouseMovement(double NewX, double NewY){		
		//マウスがy軸(スクリーンの中央)からどれだけはなれたか計測
		double difX = (NewX - Main.ScreenSize.getWidth()/2);
		//マウスがx軸(スクリーンの中央)からどれだけはなれたか計測
		double difY = (NewY - Main.ScreenSize.getHeight()/2);
		difY *= 6 - Math.abs(VerticalLook) * 5;
		
		VerticalLook   -= difY  / VerticalRotaionSpeed;
		HorizontalLook += difX / HorizontalRotaionSpeed;
		
		//VerticalLookの絶対値が1.0以上にならないようにする
		if(VerticalLook>0.999) VerticalLook = 0.999;
		if(VerticalLook<-0.999) VerticalLook = -0.999;
		
		updateView();
	}
	
	//視点をアップデート
	private void updateView(){
		double r = Math.sqrt(1 - (VerticalLook * VerticalLook));
		ViewTo[0] = ViewFrom[0] + r * Math.cos(HorizontalLook); // x軸移動
		ViewTo[1] = ViewFrom[1] + r * Math.sin(HorizontalLook);	// y軸移動
		ViewTo[2] = ViewFrom[2] + VerticalLook;					// z軸移動	
	}
	
	/*キー入力用class*/
	class KeyTyped extends KeyAdapter{
		
		//キーを押した時にtrue
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_W : 	 Control[0] = true ; break; //前進
			case KeyEvent.VK_A : 	 Control[1] = true ; break; //後退
			case KeyEvent.VK_S : 	 Control[2] = true ; break; //左へ
			case KeyEvent.VK_D :	 Control[3] = true ; break; //右へ
			case KeyEvent.VK_X : 	 Control[4] = true ; break; //視点リセット
			case KeyEvent.VK_SPACE:	 Control[5] = true ; break; //上へ
			case KeyEvent.VK_SHIFT:	 Control[6] = true ; break; //下へ
			case KeyEvent.VK_BACK_SPACE: Control[7] = true ; break; //下へ
			case KeyEvent.VK_R : 	 Control[8] = true ; break;
			case KeyEvent.VK_DELETE :Control[9] = true ; break;
			case KeyEvent.VK_O 	:	 Control[10] = true ; break;
			case KeyEvent.VK_ESCAPE : System.exit(0); //Escapeキーを押すと終了
			}
		}
		
		//キーを離した時にfalse
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
	
	/*マウスの情報を扱うクラス*/
	class AboutMouse implements MouseListener , MouseMotionListener, MouseWheelListener{
		
		//マウスを中央に移動させるメソッド
		void CenterMouse(){
				try {
					r = new Robot();
					r.mouseMove((int)Main.ScreenSize.getWidth()/2, (int)Main.ScreenSize.getHeight()/2);
				} catch (AWTException e) {
					e.printStackTrace();
				}
		}
		
		//マウスをドラッグしたときの制御		
		public void mouseDragged(MouseEvent e) {
			MouseMovement(e.getX(), e.getY());
			MouseX = e.getX();
			MouseY = e.getY();
			CenterMouse();
		}

		//マウスの動きを制御
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
		
		//マウスのクリックをしたときの制御
		public void mousePressed(MouseEvent e) {
			//右クリック
			if(e.getButton() == MouseEvent.BUTTON1) {
				if(PolygonOver != null) PolygonOver.seeThrough = false;				
			}
			//左クリック
			if(e.getButton() == MouseEvent.BUTTON3) {				
				if(PolygonOver != null) PolygonOver.seeThrough = true;
			}
		}

		public void mouseReleased(MouseEvent e) {
		}
		
		//マウスホイールをつかさどるメソッド
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