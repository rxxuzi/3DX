package main2_3;

import java.awt.Color;

public class Cube {
	//座標
	double x, y, z, dx, dy, dz;
	double rotation = Math.PI*0.75;
	double[] RotAdd = new double[4];
	//色情報
	Color color;
	//回転後の座標
	double x1, x2, x3, x4, y1, y2, y3, y4;
	DPolygon[] Polys = new DPolygon[6];
	//角度を収納する配列
	double[] angle;
	final static double e = 0.0001;
	
	//x,y,z座標
	double a = x + dx;
	double b = y + dy;
	double c = z + dz;
	
	//座標と色情報からポリゴンを生成
	public Cube(double x, double y, double z, double dx, double dy, double dz, Color color){
		
		Polys[0] = new DPolygon(new double[]{x, a, a, x}, new double[]{y, y, b, b},  new double[]{z, z, z, z}, color, false);
		Polys[1] = new DPolygon(new double[]{x, a, a, x}, new double[]{y, y, b, b},  new double[]{c, c, c, c}, color, false);
		Polys[2] = new DPolygon(new double[]{x, x, x, x}, new double[]{y, y, b, b},  new double[]{z, c, c, z}, color, false);
		Polys[3] = new DPolygon(new double[]{a, a, a, a}, new double[]{y, y, b, b},  new double[]{z, c, c, z}, color, false);
		Polys[4] = new DPolygon(new double[]{x, x, a, a}, new double[]{y, y, y, y},  new double[]{z, c, c, z}, color, false);
		Polys[5] = new DPolygon(new double[]{x, x, a, a}, new double[]{b, b, b, b},  new double[]{z, c, c, z}, color, false);
		
		//Screen.javaのDPolygons<List>に転送
		Screen.DPolygons.add(Polys[0]);
		Screen.DPolygons.add(Polys[1]);
		Screen.DPolygons.add(Polys[2]);
		Screen.DPolygons.add(Polys[3]);
		Screen.DPolygons.add(Polys[4]);
		Screen.DPolygons.add(Polys[5]);
		
		//インスタンス変数に代入
		this.color = color;
		this.x = x;
		this.y = y;
		this.z = z;
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		
		//角度情報を取得
		setRotAdd();
		
		updatePoly();
	}
	
	public void setRotAdd(){
		
		angle = new double[4];
		
		double xdif = - dx/2 + e;
		double ydif = - dy/2 + e;
		
		for(int i = 0 ; i < angle.length ; i++) {
			angle[i] = Math.atan(ydif/xdif);
			if(xdif < 0) {
				angle[i] += Math.PI;				
			}
			switch(i) {
			case 0 : xdif =    dx/2 + e; ydif = - dy/2 + e; break;
			case 1 : xdif =    dx/2 + e; ydif =   dy/2 + e; break;
			case 2 : xdif =  - dx/2 + e; ydif =   dy/2 + e; break;
			}
			
			RotAdd[i] = angle[i] + 0.25 * Math.PI;
			
		}
	}
	
	@SuppressWarnings("unused")
	private void UpdateDirection(double toX, double toY){
		double xdif = toX - (x + dx/2) + e;
		double ydif = toY - (y + dy/2) + e;
		
		double anglet = Math.atan(ydif/xdif) + 0.75 * Math.PI;

		if(xdif<0)
			anglet += Math.PI;

		rotation = anglet;
		updatePoly();		
	}

	void updatePoly(){
		
		for(int i = 0; i < 6; i++){
			Screen.DPolygons.add(Polys[i]);
			Screen.DPolygons.remove(Polys[i]);
		}
		
		double radius = Math.sqrt(dx*dx + dy*dy);
		
		x1 = x + dx*0.5 + radius*0.5*Math.cos(rotation + RotAdd[0]);
		x2 = x + dx*0.5 + radius*0.5*Math.cos(rotation + RotAdd[1]);
		x3 = x + dx*0.5 + radius*0.5*Math.cos(rotation + RotAdd[2]);
		x4 = x + dx*0.5 + radius*0.5*Math.cos(rotation + RotAdd[3]);

		y1 = y + dy*0.5 + radius*0.5*Math.sin(rotation + RotAdd[0]);
		y2 = y + dy*0.5 + radius*0.5*Math.sin(rotation + RotAdd[1]);
		y3 = y + dy*0.5 + radius*0.5*Math.sin(rotation + RotAdd[2]);
		y4 = y + dy*0.5 + radius*0.5*Math.sin(rotation + RotAdd[3]);
   
		Polys[0].x = new double[]{x1, x2, x3, x4};
		Polys[0].y = new double[]{y1, y2, y3, y4};
		Polys[0].z = new double[]{ z,  z,  z,  z};

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

	void removeCube(){
		
		for(int i = 0; i < 6; i ++) {
			Screen.DPolygons.remove(Polys[i]);			
		}
		Screen.Cube.remove(this);
	}
}