package main3_0;


public class Vector {
	double x, y, z;
	public Vector(double x, double y, double z){
		/*ベクトルの大きさを計算*/
		double length = Length(x,y,z);
		/*線形独立の時に実行。線形従属の時にはx,y,zの値は0に*/
		if(length>0){
			this.x = x/length;
			this.y = y/length;
			this.z = z/length;
		}
	}
	/*ベクトルの大きさ*/
	double Length(double x,  double y, double z){
		return Math.sqrt(x*x + y*y + z*z);
	}
	/*ベクトルの直角に交差するベクトルの作成*/
	Vector CrossProduct(Vector V){
		return new Vector(
				y * V.z - z * V.y,
				z * V.x - x * V.z,
				x * V.y - y * V.x);
	}
	/*内積*/
	double DotProduct(Vector V){
		return x * V.x + y * V.y + z * V.z;
	}
	/*外積*/
	@SuppressWarnings("unused")
	Vector VectorProduct(Vector V1 , Vector V2){
        return new Vector(
				V1.y * V2.z - V1.z * V2.y,
				V1.z * V2.x - V1.x * V2.z,
				V1.x * V2.y - V1.y * V2.x);
    }
	@SuppressWarnings("unused")
	double Angle(Vector V){
		return Math.acos(DotProduct(V));
	}
	/*ベイアルの長さを求める*/
	@SuppressWarnings("unused")
	double Length(Vector V){
		return Math.sqrt(x*x + y*y + z*z);
	}
}