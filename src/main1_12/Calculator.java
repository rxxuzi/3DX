package main1_12;
/*DPolygon.classの計算用class*/
public class Calculator {
	static double DrawX = 0 , DrawY = 0;
	/*X座標の描画の計算*/
	static double CalculatorPositionX(double[] ViewFrom , double[] ViewTo , double x , double y, double z) {
		setStuff(ViewFrom , ViewTo , x, y, z);
		return DrawX ; 
	}
	/*Y座標の描画の計算*/
	static double CalculatorPositionY(double[] ViewFrom , double[] ViewTo , double x , double y, double z) {
		setStuff(ViewFrom , ViewTo , x, y, z);
		return DrawY ; 
	}
	/*ベクトル計算*/
	static void setStuff(double[] ViewFrom , double[] ViewTo , double x , double y , double z) {
		
		/*ベクトル計算　(xの始点 - xの終点) , (yの始点 - yの終点) , (zの始点 - zの終点)*/
		Vector ViewVector = new Vector(ViewTo[0] - ViewFrom[0] , ViewTo[1] - ViewFrom[1] , ViewTo[2] - ViewFrom[2] );
		/*方向ベクトル (単位ベクトル) */
		Vector DirectionVector = new Vector(1,1,1);
		/*
		 * 3次元ベクトルを2次元ベクトルで描画する為には2つのベクトルを用意する必要がある
		 * グラム・シュミットの正規直交化法を用いる
		 */
		Vector PlaneVector1 = ViewVector.CrossProduct(DirectionVector);
		Vector PlaneVector2 = ViewVector.CrossProduct(PlaneVector1);
		
		Vector ViewToPoint = new Vector( x - ViewFrom[0] , y - ViewFrom[1] , z - ViewFrom[2] );
		
		double v_vt = ViewVector.x * ViewTo[0]  	 + ViewVector.y * ViewTo[1]	  	+ ViewVector.z * ViewTo[2];
		double v_vf = ViewVector.x * ViewFrom[0] 	 + ViewVector.y * ViewFrom[1]	+ ViewVector.z * ViewFrom[2];
		double v_tp = ViewVector.x * ViewToPoint.x   + ViewVector.y * ViewToPoint.y + ViewVector.z * ViewToPoint.z;
		/*ベクトル上で平面と衝突する距離*/
		double t = ( v_vt - v_vf ) / v_tp ;
		//平面内の点
		x = ViewFrom[0] + ViewToPoint.x * t ;
		y = ViewFrom[1] + ViewToPoint.y * t ;
		z = ViewFrom[2] + ViewToPoint.z * t ;
		
		/*ベクトルの始点より前にある時のみ、3次元から2次元に変換できる*/
		if(t > 0) {
			DrawX = PlaneVector2.x * x + PlaneVector2.y * y + PlaneVector2.z * z;
			DrawY = PlaneVector1.x * x + PlaneVector1.y * y + PlaneVector1.z * z;
			
		}
		
	}
}
