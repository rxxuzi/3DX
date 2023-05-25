package main2_9;

public class Plane {
	Vector V1, V2, NewVector;
	double[] P = new double[3];
	
	public Plane(DPolygon DP){
		P[0] = DP.x[0]; 
		P[1] = DP.y[0]; 
		P[2] = DP.z[0]; 


		V1 = new Vector(DP.x[1] - DP.x[0], 
		        		DP.y[1] - DP.y[0], 
		        		DP.z[1] - DP.z[0]);

		V2 = new Vector(DP.x[2] - DP.x[0], 
		        		DP.y[2] - DP.y[0], 
		        		DP.z[2] - DP.z[0]);
		
		//V1‚ÆV2‚ð’¼Œð‚³‚¹‚é
		NewVector = V1.CrossProduct(V2);
	}

	public Plane(Vector VE1, Vector VE2){
        V1 = VE1;
        V2 = VE2;
		NewVector = V1.CrossProduct(V2);
		System.out.println(NewVector.Angle(V1));
		System.out.println(NewVector.Angle(V2));
		NewVector = V1.VectorProduct(VE1,VE2);
	}
	
	public Plane(Vector VE1, Vector VE2, double[] Z){
		//double[] Z = P
		P = Z;
		
		V1 = VE1;
		
		V2 = VE2;
		
		NewVector = V1.CrossProduct(V2);
	}
}