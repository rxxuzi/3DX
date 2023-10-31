package main2_0beta;

import java.awt.Color;
import java.awt.Polygon;

public class DPolygon {
	Color c ;
	double[] x , y , z;
	double[] nx , ny , CaluculatorPosition;
	boolean draw = true, seeThrough = false;
	Object DrawablePolygon;
	double AverageDistance;
	int poly = 0;
 	public DPolygon(double[] x , double[] y , double[] z,  Color c, boolean seeThrough) {
 
		this.x = x;
		this.y = y;
		this.z = z;
		this.c = c;
		this.seeThrough = seeThrough;
		
		createPolygon();
		
	}
 	//�|���S�����쐬���郁�\�b�h
	private void createPolygon() {
		DrawablePolygon = new Object(new double[x.length] , new double[x.length] , c , Screen.DPolygons.size() , seeThrough);
		
		
	}
	//�|���S�����X�V���郁�\�b�h
	void updatePolygon() {
		nx = new double[x.length];
		ny = new double[x.length];
		draw = true;
		for(int  i = 0 ; i < x.length ; i++) {
			CaluculatorPosition = Calculator.CalculatorPositionP(Screen.ViewFrom , Screen.ViewTo , x[i] , y[i] , z[i]);
			nx[i] = (Main.ScreenSize.getWidth()  / 2 - Calculator.CalculateFocusPosition[0]) + CaluculatorPosition[0] * Screen.zoom; 
			ny[i] = (Main.ScreenSize.getHeight() / 2 - Calculator.CalculateFocusPosition[1]) + CaluculatorPosition[1] * Screen.zoom; 
			
			//���_�̌��ɃI�u�W�F�N�g������ꍇ�Adraw���Ȃ�
			if(Calculator.t < 0 ) {
				draw = false;
			}
			
		}
		
		DrawablePolygon.draw = draw;
		DrawablePolygon.updatePolygon(nx, ny);
		AverageDistance = GetDistance();
		
	}
	
 	//�������擾
	double GetDistance() {
		double total = 0 ; 
		for(int  i = 0 ; i < x.length ; i++) {
			total += GetDistanceTop(i);
		}
		return total / x.length;
		
	}
	
	//����_����ʂ̓_�܂ł̋������擾
	private double GetDistanceTop(int i) {
		double Fx = Screen.ViewFrom[0] - x[i];
		double Fy = Screen.ViewFrom[1] - y[i];
		double Fz = Screen.ViewFrom[2] - z[i];
		return Math.sqrt( Fx * Fx + Fy * Fy + Fz * Fz );
	}
}
