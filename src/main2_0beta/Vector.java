package main2_0beta;

public class Vector {
	double x = 0 ,y = 0 ,z = 0;
	public Vector(double x , double y , double z) {
		/*ベクトルの大きさを計算*/
		double length = Math.sqrt(x*x + y*y + z*z);
		/*線形独立の時に実行。線形従属の時にはx,y,zの値は0に*/
		if(length > 0 ) {
			this.x = x/length;
			this.y = y/length;
			this.z = z/length;
		}
		
	}
	/*ベクトルの直角に交差するベクトルの作成*/
	Vector CrossProduct(Vector V) {
		Vector CrossVector = new Vector(
		y * V.z - z * V.y,
		z * V.x - x * V.z,
		x * V.y - y * V.x);
		return CrossVector;
	}
}
