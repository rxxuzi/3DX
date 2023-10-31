package main2_5;
import java.awt.Color;


public class DPolygon {
	Color c;
	double[] x, y, z;
	boolean draw = true, seeThrough = false;
	double[] CalculatorPosition, nx, ny;
	Object DrawablePolygon;
	double AverageDistance;
	int Poly = 0;
	
	public DPolygon(double[] x, double[] y,  double[] z, Color c, boolean seeThrough){
		this.x = x;
		this.y = y;
		this.z = z;		
		this.c = c;
		this.seeThrough = seeThrough; 
		createPolygon();
	}
	
	//ポリゴンを作成するメソッド
	void createPolygon(){
		DrawablePolygon = new Object(new double[x.length], new double[x.length], c, Screen.DPolygons.size(), seeThrough);
	}
	
	//ポリゴンを更新するメソッド
	void updatePolygon(){		
		nx = new double[x.length];
		ny = new double[x.length];
		draw = true;
		
		
		for(int  i = 0 ; i < x.length ; i ++ ) {
			CalculatorPosition = Calculator.CalculatePositionP(Screen.ViewFrom, Screen.ViewTo, x[i], y[i], z[i]);
			nx[i] = (Main.ScreenSize.getWidth()  / 2 - Calculator.CalculateFocusPosition[0]) + CalculatorPosition[0] * Screen.zoom; 
			ny[i] = (Main.ScreenSize.getHeight() / 2 - Calculator.CalculateFocusPosition[1]) + CalculatorPosition[1] * Screen.zoom; 
			
			//視点の後ろにオブジェクトがある場合、描画しない
			if(Calculator.t < 0 ) {
				draw = false;
			}
			
		}
		
		DrawablePolygon.draw = draw;
		DrawablePolygon.updatePolygon(nx, ny);
		AverageDistance = GetDistance();
	}
	
	//距離を取得
	double GetDistance(){
		double total = 0;
		for(int i=0; i<x.length; i++)
			total += GetDistanceToP(i);
		return total / x.length;
	}
	
	double GetDistanceToP(int i){
		double Fx = Screen.ViewFrom[0] - x[i];
		double Fy = Screen.ViewFrom[1] - y[i];
		double Fz = Screen.ViewFrom[2] - z[i];
		return Math.sqrt( Fx * Fx + Fy * Fy + Fz * Fz );
	}
}