package main1_19;

import javax.swing.JPanel;
import javax.swing.event.MenuKeyEvent;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import java.util.Arrays;
/*
 * JPanel ��extend����class
 * Main.java��Frame��add���邽�߂�class
 * */

public class Screen extends JPanel{
	double frame = 50;
	double SleepTime = 1000 / frame; // 1s������30�t���[��
	double LastRefresh = 0;
	
	static double[] ViewFrom = new double[] {10.00,10.00,10.00}; 
	static double[] ViewTo   = new double[] { 1, 1, 1};
	static int NumberOfPolygons = 0 ;
	static int NumberOf3DPolygons = 0;
	static Object[] DrawavlePolygons = new Object[1000]; //�ݒu�ł���I�u�W�F�N�g�̐�
	static DPolygon[] DPolygons = new DPolygon[1000]; //�`�悷��|���S���̐�
	
	int[] NewOrder;
	boolean[] Keys = new boolean[12];
	static int groundW = 10;
	static int groundH= 10;
	static String In = "NONE";
	
	/*�`�悵����3D�|���S�����`����class*/
	public Screen() {
		
		/*Object 1 ������ (2*2*2)	*/
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{0,2,2,0} , new double[] {0,0,2,2},new double[] {0,0,0,0}, Color.YELLOW);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{0,2,2,0} , new double[] {0,0,2,2},new double[] {2,2,2,2}, Color.YELLOW);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{0,2,2,0} , new double[] {0,0,0,0},new double[] {0,0,2,2}, Color.CYAN);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{0,2,2,0} , new double[] {2,2,2,2},new double[] {0,0,2,2}, Color.CYAN);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{0,0,0,0} , new double[] {0,2,2,0},new double[] {0,0,2,2}, Color.MAGENTA);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{2,2,2,2} , new double[] {0,2,2,0},new double[] {0,0,2,2}, Color.MAGENTA);
		
		/*Object 2 �O�p��*/
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ 0,-4,-4, 0} , new double[] {0, 0,-4,-4},new double[] { 4, 4, 0, 0}, Color.YELLOW);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ 0,-4,-4, 0} , new double[] {0, 0, 0, 0},new double[] { 0, 0, 4, 4}, Color.CYAN);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ 0, 0, 0, 0} , new double[] {0,-4, 0, 0},new double[] { 0, 0, 4, 4}, Color.MAGENTA);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{-4,-4,-4,-4} , new double[] {0,-4, 0, 0},new double[] { 0, 0, 4, 4}, Color.MAGENTA);
		
		/*Object 3 �傫��������*/
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ 0,-3,-3, 0} , new double[] {3,3,6,6},new double[] {4,4,4,4}, Color.YELLOW);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ 0,-3,-3, 0} , new double[] {3,3,3,3},new double[] {0,0,4,4}, Color.CYAN);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ 0,-3,-3, 0} , new double[] {6,6,6,6},new double[] {0,0,4,4}, Color.CYAN);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ 0, 0, 0, 0} , new double[] {3,6,6,3},new double[] {0,0,4,4}, Color.MAGENTA);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{-3,-3,-3,-3} , new double[] {3,6,6,3},new double[] {0,0,4,4}, Color.MAGENTA);
		
		/*Object 4 ������������*/
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{5,6,6,5} , new double[] {2,2,3,3},new double[] {1,1,1,1}, Color.YELLOW);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{5,6,6,5} , new double[] {2,2,2,2},new double[] {0,0,1,1}, Color.CYAN);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{5,6,6,5} , new double[] {3,3,3,3},new double[] {0,0,1,1}, Color.CYAN);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{5,5,5,5} , new double[] {2,3,3,2},new double[] {0,0,1,1}, Color.MAGENTA);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{6,6,6,6} , new double[] {2,3,3,2},new double[] {0,0,1,1}, Color.MAGENTA);
		
		/*Object 5 �ϐ����g���ė����̂����*/
		int x = 6;
		int y = -4;
		int z = 6;
		int dx = x - 4;//x�̕�
		int dy = y - 4;//y�̕�
		int dz = z - 4;//z�̕�
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{dx, x, x,dx} , new double[] {dy,dy, y, y},new double[] {dz,dz,dz,dz}, Color.YELLOW);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{dx, x, x,dx} , new double[] {dy,dy, y, y},new double[] { z, z, z, z}, Color.YELLOW);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{dx, x, x,dx} , new double[] {dy,dy,dy,dy},new double[] {dz,dz, z, z}, Color.CYAN);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{dx, x, x,dx} , new double[] { y, y, y, y},new double[] {dz,dz, z, z}, Color.CYAN);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{dx,dx,dx,dx} , new double[] {dy, y, y,dy},new double[] {dz,dz, z, z}, Color.MAGENTA);
		DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{ x, x, x, x} , new double[] {dy, y ,y,dy},new double[] {dz,dz, z, z}, Color.MAGENTA);
		
		/*�n�ʂ̕\��*/
		for(int i = -groundW ; i < groundW ; i ++) {
			for(int j = -groundH  ; j < groundH ; j ++) {
				DPolygons[NumberOf3DPolygons] = new DPolygon(new double[]{i, i, i+1, i+1} , new double[] {j, j+1, j+1, j},new double[] {0,0,0,0}, Color.GREEN);
			}
		}
		
		
		setFocusable(true);
		addKeyListener(new KeyInput()); //KeyInput class����̃L�[����
		
	}
	public void paintComponent(Graphics g) {
		
		Control();
		g.clearRect(0 , 0, 2000 , 1200);
		g.drawString(frame + " FPS" , 20 , 10);
		g.drawString("ELAPSED TIME : " +  (System.currentTimeMillis() - Main.StartUpTime ) + "ms" , 20 ,20);
		g.drawString("OBJECT : " + Arrays.toString(ViewTo)   , 20 ,30);
		g.drawString("CAMERA : " + Arrays.toString(ViewFrom) , 20 ,40);
		g.drawString("CONDITION ->" + In , 20 , 60);
		g.setColor(Color.BLACK);
		
		
		for(int i = 0 ; i < NumberOf3DPolygons ; i ++) {
			DPolygons[i].updatePolygon(); //�|���S���̃A�b�v�f�[�g
		}
		
		setOrder();//�|���S���̋������\�[�g
		
		for(int  i = 0 ; i < NumberOfPolygons ; i ++) {
			DrawavlePolygons[NewOrder[i]].drawPolygon(g);
		}
		
		SleepAndRefresh();
	}
	
	/*Sorting*/
	void setOrder() {
		/*���ׂĂ̋�����ێ�����z��*/
		double[] k = new double[NumberOfPolygons];
		
		NewOrder = new int[NumberOfPolygons];
		
		for(int i = 0; i < NumberOfPolygons ; i++) {
			k[i] = DrawavlePolygons[i].AverageDistance;
			NewOrder[i] = i;
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
	
	/*�`��X�V�̂��߂̃��\�b�h*/
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
	/*�L�[���͂��琧�䂷�郁�\�b�h*/
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
		//���Ɉړ�
		if(Keys[5]){
			ViewFrom[0] += SideViewVector.x;
			ViewFrom[1] += SideViewVector.y;
			ViewFrom[2] += SideViewVector.z;
			ViewTo[0] += SideViewVector.x;
			ViewTo[1] += SideViewVector.y;
			ViewTo[2] += SideViewVector.z;
		}
		//�E�Ɉړ�
		if(Keys[7]){
			ViewFrom[0] -= SideViewVector.x;
			ViewFrom[1] -= SideViewVector.y;
			ViewFrom[2] -= SideViewVector.z;
			ViewTo[0] -= SideViewVector.x;
			ViewTo[1] -= SideViewVector.y;
			ViewTo[2] -= SideViewVector.z;
		}
		
		//���̎��_�ɂ��ǂ�
		if(Keys[8]) {
			ViewFrom[0] = 10;
			ViewFrom[1] = 10;
			ViewFrom[2] = 10;
			ViewTo[0] = 1;
			ViewTo[1] = 1;
			ViewTo[2] = 1;
			
		}
	}
	
	/*�L�[���͗pclass*/
	class KeyInput extends KeyAdapter{
		
		//�L�[������������true
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
			
			}
		}
		
		//�L�[�𗣂�������false
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
