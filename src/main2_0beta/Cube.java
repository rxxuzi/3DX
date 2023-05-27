package main2_0beta;

import java.awt.*;

public class Cube {
	double x, y, z, dx, dy, dz;
	double w , l , h;
	double rotation = Math.PI * 0.75;
	double[] RotAdd = new double[4];
	Color c;
	double x1,x2,x3,x4,y1,y2,y3,y4;
	DPolygon[] Polys = new DPolygon[6];
	double[] angle;
	final double e = 0.00001;
	
	public Cube(double x, double y, double z, double dx, double dy, double dz, Color c){
		this.w = x + dx; 
		this.l = y + dy; 
		this.h = z + dz; 
		
		Polys[0] = new DPolygon(new double[] { x, w, w, x} , new double[] { y, y, l, l} , new double[] { z, z, z, z} , c, false);
		Polys[1] = new DPolygon(new double[] { x, w, w, x} , new double[] { y, y, l, l} , new double[] { h, h, h, h} , c, false);
		Polys[2] = new DPolygon(new double[] { x, x, w, w} , new double[] { y, y, y, y} , new double[] { z, h, h, z} , c, false);
		Polys[3] = new DPolygon(new double[] { w, w, w, w} , new double[] { y, y, l, l} , new double[] { z, h, h, z} , c, false);
		Polys[4] = new DPolygon(new double[] { x, x, w, w} , new double[] { l, l, l, l} , new double[] { z, h, h, z} , c, false);
		Polys[5] = new DPolygon(new double[] { x, x, x, x} , new double[] { y, y, l, l} , new double[] { z, h, h, z} , c, false);
		Screen.DPolygons.add(Polys[0]);
		Screen.DPolygons.add(Polys[1]);
		Screen.DPolygons.add(Polys[2]);
		Screen.DPolygons.add(Polys[3]);
		Screen.DPolygons.add(Polys[4]);
		Screen.DPolygons.add(Polys[5]);
		
		this.c = c;
		this.x = x;
		this.y = y;
		this.z = z;
		this.dx = dx ;
		this.dy = dy ;
		this.dz = dz ;
		
		setRotAdd();
		updatePoly();
	}

	private void setRotAdd() {
		
		angle = new double[4];
		

		double xdif = - dx / 2 + e;
		double ydif = - dy / 2 + e;
		
		for(int i = 0 ; i < 4 ; i++) {
			
			angle[i] = Math.atan(ydif / xdif);
			
			if(xdif < 0) {
				angle[i] += Math.PI;
			}
			
			switch(i) {
			case 0: xdif =   dx / 2 + e; ydif = - dy / 2 + e; break;
			case 1: xdif =   dx / 2 + e; ydif =   dy / 2 + e; break;
			case 2: xdif = - dx / 2 + e; ydif =   dy / 2 + e; break;	
			}
			
		}
		
		for(int i = 0 ; i < 4 ; i ++) {
			RotAdd[i] = angle[i] + 0.25 * Math.PI;
		}
		
	}
	
	public void UpdateDirection(double toX , double toY) {
		double xdif = toX - (x - dx / 2) + e;
		double ydif = toX - (y - dy / 2) + e;
		double anglet = Math.atan(ydif / xdif) + 0.75 * Math.PI;
		
		if(xdif < 0) {
			anglet += Math.PI;
		}
		rotation = anglet;
		updatePoly();
		
	}
	
	public void updatePoly() {
		
		for(int i = 0 ; i < 6 ; i++) {
			Screen.DPolygons.add(Polys[i]);
			Screen.DPolygons.remove(Polys[i]);
		}
		
		//Šp“x
		double rad = Math.sqrt(dx * dx + dy * dy);
		 x1 = x+dx*0.5+rad*0.5*Math.cos(rotation + RotAdd[0]);
		 x2 = x+dx*0.5+rad*0.5*Math.cos(rotation + RotAdd[1]);
		 x3 = x+dx*0.5+rad*0.5*Math.cos(rotation + RotAdd[2]);
		 x4 = x+dx*0.5+rad*0.5*Math.cos(rotation + RotAdd[3]);

		 y1 = y+dy*0.5+rad*0.5*Math.sin(rotation + RotAdd[0]);
		 y2 = y+dy*0.5+rad*0.5*Math.sin(rotation + RotAdd[1]);
		 y3 = y+dy*0.5+rad*0.5*Math.sin(rotation + RotAdd[2]);
		 y4 = y+dy*0.5+rad*0.5*Math.sin(rotation + RotAdd[3]);
		 
		
		 Polys[0].x = new double[]{x1, x2, x3, x4};
		 Polys[0].y = new double[]{y1, y2, y3, y4};
		 Polys[0].z = new double[]{z, z, z, z};

		 Polys[1].x = new double[]{x4, x3, x2, x1};
		 Polys[1].y = new double[]{y4, y3, y2, y1};
		 Polys[1].z = new double[]{z+dz, z+dz, z+dz, z+dz};
		 
		 Polys[2].x = new double[]{x1, x1, x2, x2};
		 Polys[2].y = new double[]{y1, y1, y2, y2};
		 Polys[2].z = new double[]{z, z+dz, z+dz, z};
		 
		 Polys[3].x = new double[]{x2, x2, x3, x3};
		 Polys[3].y = new double[]{y2, y2, y3, y3};
		 Polys[3].z = new double[]{z, z+dz, z+dz, z};
		 
		 Polys[4].x = new double[]{x3, x3, x4, x4};
		 Polys[4].y = new double[]{y3, y3, y4, y4};
		 Polys[4].z = new double[]{z, z+dz, z+dz, z};
		 
		 Polys[5].x = new double[]{x4, x4, x1, x1};
		 Polys[5].y = new double[]{y4, y4, y1, y1};
		 Polys[5].z = new double[]{z, z+dz, z+dz, z};
	}
	
	void removeCube() {
		for(int  i = 0 ; i < 6; i++) {
			Screen.DPolygons.remove(Polys[i]);
		}
		Screen.Cube.remove(this);
	}
	
}
