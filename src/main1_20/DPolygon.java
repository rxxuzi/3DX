package main1_20;

import java.awt.Color;
import java.awt.Polygon;

public class DPolygon {
	Color c ;
	double[] x , y , z;
	int poly = 0;
 	public DPolygon(double[] x , double[] y , double[] z,  Color c) {
 		Screen.NumberOf3DPolygons ++;
		this.x = x;
		this.y = y;
		this.z = z;
		this.c = c;
		createPolygon();
		
	}
 	//ポリゴンを作成するメソッド
	private void createPolygon() {
		poly = Screen.NumberOfPolygons;
		Screen.DrawavlePolygons[Screen.NumberOfPolygons] = new Object(new double[] {}, new double[] {} , c);
		updatePolygon();
		
	}
	//ポリゴンを更新するメソッド
	void updatePolygon() {
		double dx = - 50 *  Calculator.CalculatorPositionX(Screen.ViewFrom, Screen.ViewTo, Screen.ViewTo[0], Screen.ViewTo[1], Screen.ViewTo[2]) ;
		double dy = - 50 *  Calculator.CalculatorPositionY(Screen.ViewFrom, Screen.ViewTo, Screen.ViewTo[0], Screen.ViewTo[1], Screen.ViewTo[2]) ;
		double[] newX = new double[x.length];
		double[] newY = new double[x.length];
		
		for(int i = 0 ; i < x.length; i ++) {
			newX[i] = dx + Main.ScreenSize.getWidth()  /2 + 50 * Calculator.CalculatorPositionX(Screen.ViewFrom, Screen.ViewTo, x[i], y[i], z[i]);
			newY[i] = dy + Main.ScreenSize.getHeight() /2 + 50 * Calculator.CalculatorPositionY(Screen.ViewFrom, Screen.ViewTo, x[i], y[i], z[i]);
		}
		Screen.DrawavlePolygons[poly] = new Object(newX, newY , c);
		Screen.DrawavlePolygons[poly].AverageDistance = GetDistance();
		Screen.NumberOfPolygons -- ; 
	}
 	
	double GetDistance() {
		double total = 0 ; 
		for(int  i = 0 ; i < x.length ; i++) {
			total += GetDistanceTop(i);
		}
		return total / x.length;
		
	}
	//ある点から別の点までの距離を取得
	private double GetDistanceTop(int i) {
		double Fx = Screen.ViewFrom[0] - x[i];
		double Fy = Screen.ViewFrom[1] - y[i];
		double Fz = Screen.ViewFrom[2] - z[i];
		
		return Math.sqrt( Fx * Fx + Fy * Fy + Fz * Fz );
	}
}
