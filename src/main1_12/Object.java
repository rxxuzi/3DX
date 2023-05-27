<<<<<<< HEAD
package main1_12;
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
		g.drawPolygon(P);
		 
	}
}
=======
package main1_12;
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
		g.drawPolygon(P);
		 
	}
}
>>>>>>> fd9d39fe20262db2ac5de077ce0a3492479eaba7
