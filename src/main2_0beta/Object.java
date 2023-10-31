package main2_0beta;
import java.awt.Color;
import java.awt.*;
public class Object {
	Polygon P;
	Color c ;
	boolean draw = true, visible = true, seeThrough;
	double lighting = 1;
	double AverageDistance = 0;
	
	//ƒ|ƒŠƒSƒ“‚Ìî•ñ‚ğæ“¾
	public Object(double[] x , double[] y , Color c , int n , boolean seeThrough) {
		
		P = new Polygon();
		for(int i = 0 ; i < x.length ; i ++) {
			P.addPoint( (int)x[i] , (int)y[i] );
		}	
		this.c = c;
		this.seeThrough = seeThrough;
	}
	
	//ƒ|ƒŠƒSƒ“‚Ìî•ñ‚ğXV
	void updatePolygon(double[] x , double[] y) {
		P.reset();
		
		for(int  i = 0 ; i < x.length ; i ++ ) {
			P.xpoints[i] = (int) x[i];
			P.ypoints[i] = (int) y[i];
			P.npoints = x.length;
		}
		
	}
	
	//ƒ|ƒŠƒSƒ“‚ğ•`‰æ
	void drawPolygon(Graphics g) {
		g.setColor(c);
		g.fillPolygon(P);
		g.setColor(Color.BLACK);
		g.drawPolygon(P);
		
		
		 
	}
	
	boolean MouseOver() {
		return P.contains(Main.ScreenSize.getWidth() / 2 , Main.ScreenSize.getHeight()/2);
	}
}
