package main1_20;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import java.util.Arrays;
/*
 * JPanel をextendしたclass
 * Main.javaのFrameにaddするためのclass
 * */

public class Screen extends JPanel{
	double frame = 50;
	double SleepTime = 1000 / frame; // 1sあたり30フレーム
	double LastRefresh = 0;
	
	static double[] ViewFrom = new double[] {10.00,10.00,10.00}; 
	static double[] ViewTo   = new double[] { 1, 1, 1};
	static int NumberOfPolygons = 0 ;
	static int NumberOf3DPolygons = 0;
	static Object[] DrawavlePolygons = new Object[5000]; //設置できるオブジェクトの数
	static DPolygon[] DPolygons = new DPolygon[5000]; //描画するポリゴンの数
	
	int[] NewOrder;
	boolean reset = false;
	boolean[] Keys = new boolean[12];
	static int groundW = 13;//マップの立幅
	static int groundH = 13; //マップの横幅
	static String In = "NONE";
	
	double x,y,z,dx,dy,dz,ax,ay,az;
	final static int FontSize = 15;

	/*描画したい3Dポリゴンを定義するclass*/
	public Screen() {
		
		/*Object 1 立方体 (2*2*2)	*/
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{0,2,2,0} , new double[] {0,0,2,2},new double[] {0,0,0,0}, Color.YELLOW);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{0,2,2,0} , new double[] {0,0,2,2},new double[] {2,2,2,2}, Color.YELLOW);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{0,2,2,0} , new double[] {0,0,0,0},new double[] {0,0,2,2}, Color.CYAN);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{0,2,2,0} , new double[] {2,2,2,2},new double[] {0,0,2,2}, Color.CYAN);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{0,0,0,0} , new double[] {0,2,2,0},new double[] {0,0,2,2}, Color.MAGENTA);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{2,2,2,2} , new double[] {0,2,2,0},new double[] {0,0,2,2}, Color.MAGENTA);
		
		/*Object 2 三角柱*/
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ 0,-4,-4, 0} , new double[] {0, 0,-4,-4},new double[] { 4, 4, 0, 0}, Color.YELLOW);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ 0,-4,-4, 0} , new double[] {0, 0, 0, 0},new double[] { 0, 0, 4, 4}, Color.CYAN);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ 0, 0, 0, 0} , new double[] {0,-4, 0, 0},new double[] { 0, 0, 4, 4}, Color.MAGENTA);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{-4,-4,-4,-4} , new double[] {0,-4, 0, 0},new double[] { 0, 0, 4, 4}, Color.MAGENTA);
		
		/*Object 3 大きい直方体*/
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ 0,-3,-3, 0} , new double[] {3,3,6,6},new double[] {4,4,4,4}, Color.YELLOW);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ 0,-3,-3, 0} , new double[] {3,3,3,3},new double[] {0,0,4,4}, Color.CYAN);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ 0,-3,-3, 0} , new double[] {6,6,6,6},new double[] {0,0,4,4}, Color.CYAN);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ 0, 0, 0, 0} , new double[] {3,6,6,3},new double[] {0,0,4,4}, Color.MAGENTA);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{-3,-3,-3,-3} , new double[] {3,6,6,3},new double[] {0,0,4,4}, Color.MAGENTA);
		
		/*Object 4 小さい立方体*/
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{5,6,6,5} , new double[] {2,2,3,3},new double[] {1,1,1,1}, Color.YELLOW);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{5,6,6,5} , new double[] {2,2,2,2},new double[] {0,0,1,1}, Color.CYAN);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{5,6,6,5} , new double[] {3,3,3,3},new double[] {0,0,1,1}, Color.CYAN);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{5,5,5,5} , new double[] {2,3,3,2},new double[] {0,0,1,1}, Color.MAGENTA);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{6,6,6,6} , new double[] {2,3,3,2},new double[] {0,0,1,1}, Color.MAGENTA);
		
		/*Object 5 変数を使って立方体を作る*/
		x = 6;
		y = -4;
		z = 6;
		dx = x - 2;//xの幅
		dy = y - 2;//yの幅
		dz = z - 2;//zの幅
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{dx, x, x,dx} , new double[] {dy,dy, y, y},new double[] {dz,dz,dz,dz}, Color.YELLOW);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{dx, x, x,dx} , new double[] {dy,dy, y, y},new double[] { z, z, z, z}, Color.YELLOW);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{dx, x, x,dx} , new double[] {dy,dy,dy,dy},new double[] {dz,dz, z, z}, Color.CYAN);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{dx, x, x,dx} , new double[] { y, y, y, y},new double[] {dz,dz, z, z}, Color.CYAN);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{dx,dx,dx,dx} , new double[] {dy, y, y,dy},new double[] {dz,dz, z, z}, Color.MAGENTA);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ x, x, x, x} , new double[] {dy, y ,y,dy},new double[] {dz,dz, z, z}, Color.MAGENTA);
		
		/*Object 6 正八面体*/
		x = -4; dx = x - 2; ax = (x + dx) / 2;
		y = 6; dy = y - 2; ay = (y + dy) / 2;
		z = 5; dz = z - 1;
		double wz = z + 1;
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ x,dx,ax,ax} , new double[] { y, y,ay,ay},new double[] { z, z,dz,dz}, Color.CYAN);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ x,dx,ax,ax} , new double[] {dy,dy,ay,ay},new double[] { z, z,dz,dz}, Color.CYAN);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ax,ax, x,dx} , new double[] {ay,ay, y, y},new double[] {wz,wz, z, z}, Color.CYAN);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ax,ax, x,dx} , new double[] {ay,ay,dy,dy},new double[] {wz,wz, z, z}, Color.CYAN);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ x, x,ax,ax} , new double[] { y,dy,ay,ay},new double[] { z, z,dz,dz}, Color.MAGENTA);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{dx,dx,ax,ax} , new double[] { y,dy,ay,ay},new double[] { z, z,dz,dz}, Color.MAGENTA);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ax,ax, x, x} , new double[] {ay,ay, y,dy},new double[] {wz,wz, z, z}, Color.MAGENTA);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ax,ax,dx,dx} , new double[] {ay,ay, y,dy},new double[] {wz,wz, z, z}, Color.MAGENTA);
		
//		/*地面の表示*/
//		for(int i = -groundW ; i < groundW ; i ++) {
//			for(int j = -groundH  ; j < groundH ; j ++) {
//				DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{i, i, i+1, i+1} , new double[] {j, j+1, j+1, j},new double[] {0,0,0,0}, Color.LIGHT_GRAY);
//			}
//		}
		
		
		setFocusable(true);
		addKeyListener(new KeyInput()); //KeyInput classからのキー入力
		
	}
	public void paintComponent(Graphics g) {
		
		Control();
		Font font = new Font(Font.DIALOG, Font.ITALIC,FontSize);
		g.setFont(font);
		
		g.clearRect(0 , 0, 2000 , 1200);
		g.drawString(frame + " FPS" , 20 , 15);
		g.drawString("number of polygons : " + NumberOf3DPolygons , 20, 30);
		g.drawString("ELAPSED TIME : " +  (System.currentTimeMillis() - Main.StartUpTime ) + "ms" , 20 , 45);
		g.drawString("OBJECT : " + Arrays.toString(ViewTo)   , 20 ,60);
		g.drawString("CAMERA : " + Arrays.toString(ViewFrom) , 20 ,75);
		font = new Font(Font.DIALOG, Font.BOLD,FontSize + 10);
		g.setFont(font);
		g.drawString("CONDITION : " + In , 20 , 100);
		
		g.setColor(Color.BLACK);
		
		
		for(int i = 0 ; i < NumberOf3DPolygons ; i ++) {
			DPolygons[i].updatePolygon(); //ポリゴンのアップデート
		}
		
		setOrder();//ポリゴンの距離をソート
		
		for(int  i = 0 ; i < NumberOfPolygons ; i ++) {
			DrawavlePolygons[NewOrder[i]].drawPolygon(g);
		}
		
		SleepAndRefresh();
	}
	
	/*Sorting*/
	void setOrder() {
		/*すべての距離を保持する配列*/
		double[] k = new double[NumberOfPolygons];
		
		NewOrder = new int[NumberOfPolygons];
		
		for(int i = 0; i < NumberOfPolygons ; i++) {
			k[i] = DrawavlePolygons[i].AverageDistance;
			NewOrder[i] = i;
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
	
	/*描画更新のためのメソッド*/
	void SleepAndRefresh() {
		while(true) {
			if( (System.currentTimeMillis() - LastRefresh ) > SleepTime ) {
				LastRefresh = System.currentTimeMillis();
				repaint();
				break;
			}else {
				try {
					long ThreadSleepTime = (long) Math.abs( SleepTime - (System.currentTimeMillis() - LastRefresh ) );
					Thread.sleep(ThreadSleepTime);
					
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	/*キー入力から制御するメソッド*/
	private void Control() {
		Vector ViewVector = new Vector(ViewTo[0] - ViewFrom[0] , ViewTo[1] - ViewFrom[1] , ViewTo[2] - ViewFrom[2] );
//		Vector BasicVector = new Vector(ViewTo[0] - ViewFrom[0] , ViewTo[1] - ViewFrom[1] , ViewTo[2] - ViewFrom[2] );
		if(Keys[0]) {
			ViewFrom[0] -= 0.2;
		}
		if(Keys[1]) {
			ViewFrom[0] += 0.2;
		}
		if(Keys[2]) {
			ViewFrom[1] -= 0.2;
		}
		if(Keys[3]) {
			ViewFrom[1] += 0.2;
		}
		if(Keys[9]) {
			ViewFrom[2] += 0.1;
		}
		if(Keys[10]) {
			ViewFrom[2] -= 0.1;
		}
		
		/*Zoom in*/
		if(Keys[4]){
			ViewFrom[0] += ViewVector.x;
			ViewFrom[1] += ViewVector.y;
			ViewFrom[2] += ViewVector.z;
			ViewTo[0] += ViewVector.x;
			ViewTo[1] += ViewVector.y;
			ViewTo[2] += ViewVector.z;
		}
		
		//Zoom out
		if(Keys[6]){
			ViewFrom[0] -= ViewVector.x;
			ViewFrom[1] -= ViewVector.y;
			ViewFrom[2] -= ViewVector.z;
			ViewTo[0] -= ViewVector.x;
			ViewTo[1] -= ViewVector.y;
			ViewTo[2] -= ViewVector.z;
		}
		
		
		Vector VerticalVector = new Vector(0,0,1);
		Vector SideViewVector = ViewVector.CrossProduct(VerticalVector);
		//左に移動
		if(Keys[5]){
			ViewFrom[0] += SideViewVector.x;
			ViewFrom[1] += SideViewVector.y;
			ViewFrom[2] += SideViewVector.z;
			ViewTo[0] += SideViewVector.x;
			ViewTo[1] += SideViewVector.y;
			ViewTo[2] += SideViewVector.z;
		}
		//右に移動
		if(Keys[7]){
			ViewFrom[0] -= SideViewVector.x;
			ViewFrom[1] -= SideViewVector.y;
			ViewFrom[2] -= SideViewVector.z;
			ViewTo[0] -= SideViewVector.x;
			ViewTo[1] -= SideViewVector.y;
			ViewTo[2] -= SideViewVector.z;
		}
		
		//元の視点にもどす
		if(Keys[8]) {
			ViewFrom[0] = 10;
			ViewFrom[1] = 10;
			ViewFrom[2] = 10;
			ViewTo[0] = 1;
			ViewTo[1] = 1;
			ViewTo[2] = 1;
			
		}
	}
	
	/*キー入力用class*/
	class KeyInput extends KeyAdapter{
		
		//キーを押した時にtrue
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:   Keys[0] = true ; In = "Negative X" ; break;
			case KeyEvent.VK_RIGHT:  Keys[1] = true ; In = "Positive X" ; break;				 
			case KeyEvent.VK_UP:	 Keys[2] = true ; In = "Negative Y" ; break;				 
			case KeyEvent.VK_DOWN:   Keys[3] = true ; In = "Positive Y" ; break;
			case KeyEvent.VK_W : 	 Keys[4] = true ; In = "Zoom in"   	; break;
			case KeyEvent.VK_A : 	 Keys[5] = true ; In = "Move left"	; break;
			case KeyEvent.VK_S : 	 Keys[6] = true ; In = "Zoom out"   ; break;
			case KeyEvent.VK_D :	 Keys[7] = true ; In = "Move right" ; break;
			case KeyEvent.VK_SPACE:  Keys[8] = true ; In = "RESET"   	; break;
			case KeyEvent.VK_SHIFT:  Keys[9] = true ; In = "Positive Z" ; break;
			case KeyEvent.VK_CONTROL:Keys[10]= true ; In = "Negative Z" ; break;
			case KeyEvent.VK_ESCAPE : System.exit(0);
			
			}
		}
		
		//キーを離した時にfalse
		public void keyReleased(KeyEvent e) {
			
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:   Keys[0] = false ;  break;
			case KeyEvent.VK_RIGHT:  Keys[1] = false ;  break;				 
			case KeyEvent.VK_UP:	 Keys[2] = false ;  break;				 
			case KeyEvent.VK_DOWN:   Keys[3] = false ;  break;
			case KeyEvent.VK_W :	 Keys[4] = false ;  break;
			case KeyEvent.VK_A :	 Keys[5] = false ;  break;
			case KeyEvent.VK_S :	 Keys[6] = false ;  break;
			case KeyEvent.VK_D :	 Keys[7] = false ;  break;
			case KeyEvent.VK_SPACE:  Keys[8] = false ;  break;
			case KeyEvent.VK_SHIFT:  Keys[9] = false ;  break;
			case KeyEvent.VK_CONTROL:Keys[10]= false ;  break;
			}
		}
	}	
	
}
