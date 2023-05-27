package main2_5;

import java.awt.Color;


public class Pyramid {
	//座標
	double x, y, z, dx, dy, dz;
	double rotation = Math.PI*0.75;
	double[] RotAdd = new double[4];
	//色情報
	Color color;
	//回転後の座標
	double x1, x2, x3, x4, x5, y1, y2, y3, y4, y5;
	
	//ポリゴン格納クラス
	DPolygon[] Polys = new DPolygon[5];
	//角度を収納する配列
	double[] angle;
	
	final static double e = 0.0001;

	//x,y,z座標
	double a = x + dx;
	double b = y + dy;
	double c = z + dz;

	//座標と色情報からポリゴンを生成
	public Pyramid(double x, double y, double z, double dx, double dy, double dz, Color color){
		
//		double h = Math.sqrt(3) * c / 2 ;
		
		Polys[0] = new DPolygon(new double[]{x, x+dx, x+dx, x}, new double[]{y, y, y+dy, y+dy},  new double[]{z, z, z, z}, color, false);
		Screen.DPolygons.add(Polys[0]);
		Polys[1] = new DPolygon(new double[]{x, x, x+dx}, new double[]{y, y, y, y},  new double[]{z, z+dz, z+dz}, color, false);
		Screen.DPolygons.add(Polys[1]);
		Polys[2] = new DPolygon(new double[]{x+dx, x+dx, x+dx}, new double[]{y, y, y+dy},  new double[]{z, z+dz, z+dz}, color, false);
		Screen.DPolygons.add(Polys[2]);
		Polys[3] = new DPolygon(new double[]{x, x, x+dx}, new double[]{y+dy, y+dy, y+dy},  new double[]{z, z+dz, z+dz}, color, false);
		Screen.DPolygons.add(Polys[3]);
		Polys[4] = new DPolygon(new double[]{x, x, x}, new double[]{y, y, y+dy},  new double[]{z, z+dz, z+dz}, color, false);
		Screen.DPolygons.add(Polys[4]);
		
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

		for(int i = 0; i < Polys.length; i++){
			Screen.DPolygons.add(Polys[i]);
			Screen.DPolygons.remove(Polys[i]);
		}

		double radius = Math.sqrt(dx*dx + dy*dy);

		 x1 = x+dx*0.5+radius*0.5*Math.cos(rotation + RotAdd[0]);
		 x2 = x+dx*0.5+radius*0.5*Math.cos(rotation + RotAdd[1]);
		 x3 = x+dx*0.5+radius*0.5*Math.cos(rotation + RotAdd[2]);
		 x4 = x+dx*0.5+radius*0.5*Math.cos(rotation + RotAdd[3]);
		 x5 = x+dx*0.5;

		 y1 = y+dy*0.5+radius*0.5*Math.sin(rotation + RotAdd[0]);
		 y2 = y+dy*0.5+radius*0.5*Math.sin(rotation + RotAdd[1]);
		 y3 = y+dy*0.5+radius*0.5*Math.sin(rotation + RotAdd[2]);
		 y4 = y+dy*0.5+radius*0.5*Math.sin(rotation + RotAdd[3]);
		 y5 = y+dy*0.5;

		Polys[0].x = new double[]{x1, x2, x3, x4};
		Polys[0].y = new double[]{y1, y2, y3, y4};
		Polys[0].z = new double[]{z, z, z, z};
			   
		Polys[1].x = new double[]{x1, x5, x2};
		Polys[1].y = new double[]{y1, y5, y2};
		Polys[1].z = new double[]{z, z+dz, z};
	
		Polys[2].x = new double[]{x3, x2, x5};
		Polys[2].y = new double[]{y3, y2, y5};
		Polys[2].z = new double[]{z, z, z+dz};
	
		Polys[3].x = new double[]{x3, x5, x4};
		Polys[3].y = new double[]{y3, y5, y4};
		Polys[3].z = new double[]{z, z+dz, z};
	
		Polys[4].x = new double[]{x1, x4, x5};
		Polys[4].y = new double[]{y1, y4, y5};
		Polys[4].z = new double[]{z, z, z+dz};

	}

	void removePyramid(){

		for(int i = 0; i < 6; i ++) {
			Screen.DPolygons.remove(Polys[i]);			
		}
		Screen.Pyramid.remove(this);
	}
}
