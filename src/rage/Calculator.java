package rage;
/*DPolygon.classの計算用class*/
public class Calculator {
	static double t = 0;
	static Vector RVector1, RVector2, ViewVector, RotationVector, DirectionVector, PlaneVector1, PlaneVector2;
	static Plane P;
	static double[] CalculateFocusPosition = new double[2];
	/*X , Y 座標の描画の計算*/
	static double[] CalculatePositionP(double[] ViewFrom, double[] ViewTo, double x, double y, double z){		
		double[] projP = getProject(ViewFrom, ViewTo, x, y, z, P);
		double[] drawP = getDrawP(projP[0], projP[1], projP[2]);
		return drawP;
	}

	static double[] getProject(double[] ViewFrom, double[] ViewTo, double x, double y, double z, Plane P){
		
		//視点からとあるポイントまでのベクトル
		Vector ViewToPoint = new Vector(x - ViewFrom[0], y - ViewFrom[1], z - ViewFrom[2]);
		
		double VNP = P.NV.x*P.P[0]        + P.NV.y*P.P[1] 		 +  P.NV.z*P.P[2];
		double VCP = P.NV.x*ViewFrom[0]   + P.NV.y*ViewFrom[1]   +  P.NV.z*ViewFrom[2] ;
		double VTP = P.NV.x*ViewToPoint.x + P.NV.y*ViewToPoint.y +  P.NV.z*ViewToPoint.z;
		
		/*ベクトル上で平面と衝突する距離*/
		t = ( VNP - VCP ) / VTP ;
		
		//平面内の点
		x = ViewFrom[0] + ViewToPoint.x * t;
		y = ViewFrom[1] + ViewToPoint.y * t;
		z = ViewFrom[2] + ViewToPoint.z * t;
		
		return new double[] { x, y, z };
	}
	
	//2次元上にポリゴンを描画するためのメソッド
	private static double[] getDrawP(double x, double y, double z)	{
		double DrawX = RVector2.x * x + RVector2.y * y + RVector2.z * z;
		double DrawY = RVector1.x * x + RVector1.y * y + RVector1.z * z;		
		return new double[]{DrawX, DrawY};
	}
	
	/*回転ベクトルのメソッド*/
	private static Vector getRotationVector(double[] ViewFrom, double[] ViewTo){
		double dx = Math.abs(ViewFrom[0]-ViewTo[0]);
		double dy = Math.abs(ViewFrom[1]-ViewTo[1]);
		double xRot, yRot;
		xRot=dy/(dx+dy);		
		yRot=dx/(dx+dy);

		if(ViewFrom[1]>ViewTo[1])
			xRot = -xRot;
		if(ViewFrom[0]<ViewTo[0])
			yRot = -yRot;

			Vector V = new Vector(xRot, yRot, 0);
		return V;
	}
	
	static void VectorInfo(){
		/*ベクトル計算　(xの始点 - xの終点) , (yの始点 - yの終点) , (zの始点 - zの終点)*/
		ViewVector = new Vector(Screen.ViewTo[0] - Screen.ViewFrom[0], Screen.ViewTo[1] - Screen.ViewFrom[1], Screen.ViewTo[2] - Screen.ViewFrom[2]);			
		//方向ベクトル (単位ベクトル)
		DirectionVector = new Vector(1, 1, 1);	
		
		/*
		 * 3次元ベクトルを2次元ベクトルで描画する為には2つのベクトルを用意する必要がある
		 * グラム・シュミットの正規直交化法を用いる
		 */
		
		PlaneVector1 = ViewVector.CrossProduct(DirectionVector);
		PlaneVector2 = ViewVector.CrossProduct(PlaneVector1);
		
		P = new Plane(PlaneVector1, PlaneVector2, Screen.ViewTo);
		
		//回転ベクトル
		RotationVector = Calculator.getRotationVector(Screen.ViewFrom, Screen.ViewTo);
		//回転ベクトル1
		RVector1 = ViewVector.CrossProduct(RotationVector);
		//回転ベクトル2
		RVector2 = ViewVector.CrossProduct(RVector1);
		
		
		CalculateFocusPosition = Calculator.CalculatePositionP(Screen.ViewFrom, Screen.ViewTo, Screen.ViewTo[0], Screen.ViewTo[1], Screen.ViewTo[2]);
		
		CalculateFocusPosition[0] = Screen.zoom * CalculateFocusPosition[0];
		CalculateFocusPosition[1] = Screen.zoom * CalculateFocusPosition[1];
	}
}