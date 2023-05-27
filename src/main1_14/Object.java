package main1_14;
import java.awt.Color;
import java.awt.*;
public class Object {
	Polygon P;
	Color c ;
	double AverageDistance = 0;
	public Object(double[] x , double[] y , Color c) {
		Screen.NumberOfPolygons ++;
		P = new Polygon();
		for(int i = 0 ; i < x.length ; i ++) {
			P.addPoint( (int)x[i] , (int)y[i] );
		}	
		this.c = c;
	}
	void drawPolygon(Graphics g) {
		g.setColor(c);
		
		g.setColor(Color.LIGHT_GRAY); 
		g.fillPolygon(P);
		g.setColor(Color.BLACK);
		g.drawPolygon(P);
		 
	}
}
