package main1_14;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import java.util.Arrays;
/*
 * JPanel をextendしたclass
 * Main.javaのFrameにaddするためのclass
 * */

public class Screen extends JPanel{
	double SleepTime = 1000/30; // 1sあたり30フレーム
	double LastRefresh = 0;
	
	static double[] ViewFrom = new double[] {10,10,10}; 
	static double[] ViewTo   = new double[] { 1, 1,1.5};
	static int NumberOfPolygons = 0 ;
	static int NumberOf3DPolygons = 0;
	static Object[] DrawavlePolygons = new Object[100];
	static DPolygon[] DPolygons = new DPolygon[100];
	
	int[] NewOrder;
	
	public Screen() {
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{0,2,2,0} , new double[] {0,0,2,2},new double[] {0,0,0,0}, Color.BLACK);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{0,2,2,0} , new double[] {0,0,2,2},new double[] {2,2,2,2}, Color.BLACK);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{0,2,2,0} , new double[] {0,0,0,0},new double[] {0,0,2,2}, Color.BLACK);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{0,2,2,0} , new double[] {2,2,2,2},new double[] {0,0,2,2}, Color.BLACK);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{0,0,0,0} , new double[] {0,2,2,0},new double[] {0,0,2,2}, Color.BLACK);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{2,2,2,2} , new double[] {0,2,2,0},new double[] {0,0,2,2}, Color.BLACK);

		setFocusable(true);
		addKeyListener(new KeyInput()); 
		
	}
	public void paintComponent(Graphics g) {
		
		
		g.clearRect(0 , 0, 2000 , 1200);
		g.drawString(System.currentTimeMillis() + "ms" , 20 ,20);
		
		g.drawString("Vector : " + Arrays.toString(ViewFrom) , 20 ,40);
		g.setColor(Color.BLACK);
		
		
		for(int i = 0 ; i < NumberOf3DPolygons ; i ++) {
			DPolygons[i].updatePolygon(); //ポリゴンのアップデート
		}
		
		setOrder();
		
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
		double tmp ;
		int tmpr;
		for(int i = 0 ; i < k.length - 1 ; i++) {
			for(int j = 0 ; j < k.length - 1 ; j++) {
				if(k[j] < k[j + 1]) {
					tmp = k[j];
					tmpr = NewOrder[j];
					NewOrder[j] = NewOrder[j + 1];
					k[j] = k[j + 1];
					
					NewOrder[j + 1] = tmpr;
					k[j + 1] = tmp;
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
	
	/*キー入力用class*/
	class KeyInput extends KeyAdapter{
		
		public void keyPressed(KeyEvent e) {
			
			switch(e.getKeyCode()) {
			
			case KeyEvent.VK_LEFT:
				 ViewFrom[0] --; 
				 System.out.print("L"); 
				 
				 break;
				 
			case KeyEvent.VK_RIGHT:
				 System.out.print("R"); 
				 ViewFrom[0] ++;
				 break;
				 
			case KeyEvent.VK_UP:
				 ViewFrom[1] --; 
				 System.out.print("U"); 
				 break;
				 
			case KeyEvent.VK_DOWN:
				 ViewFrom[1] ++;
				 System.out.print("D"); 
				 break;
				 
			case KeyEvent.VK_SPACE :
				ViewFrom[2] -- ;
				break;
				
			case KeyEvent.VK_ENTER :
				ViewFrom[2] ++ ;
				break;
			}
		}
	}	
	
}
