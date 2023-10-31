package rage;
/*DPolygon.class�̌v�Z�pclass*/
public class Calculator {
	static double t = 0;
	static Vector RVector1, RVector2, ViewVector, RotationVector, DirectionVector, PlaneVector1, PlaneVector2;
	static Plane P;
	static double[] CalculateFocusPosition = new double[2];
	/*X , Y ���W�̕`��̌v�Z*/
	static double[] CalculatePositionP(double[] ViewFrom, double[] ViewTo, double x, double y, double z){		
		double[] projP = getProject(ViewFrom, ViewTo, x, y, z, P);
		double[] drawP = getDrawP(projP[0], projP[1], projP[2]);
		return drawP;
	}

	static double[] getProject(double[] ViewFrom, double[] ViewTo, double x, double y, double z, Plane P){
		
		//���_����Ƃ���|�C���g�܂ł̃x�N�g��
		Vector ViewToPoint = new Vector(x - ViewFrom[0], y - ViewFrom[1], z - ViewFrom[2]);
		
		double VNP = P.NV.x*P.P[0]        + P.NV.y*P.P[1] 		 +  P.NV.z*P.P[2];
		double VCP = P.NV.x*ViewFrom[0]   + P.NV.y*ViewFrom[1]   +  P.NV.z*ViewFrom[2] ;
		double VTP = P.NV.x*ViewToPoint.x + P.NV.y*ViewToPoint.y +  P.NV.z*ViewToPoint.z;
		
		/*�x�N�g����ŕ��ʂƏՓ˂��鋗��*/
		t = ( VNP - VCP ) / VTP ;
		
		//���ʓ��̓_
		x = ViewFrom[0] + ViewToPoint.x * t;
		y = ViewFrom[1] + ViewToPoint.y * t;
		z = ViewFrom[2] + ViewToPoint.z * t;
		
		return new double[] { x, y, z };
	}
	
	//2������Ƀ|���S����`�悷�邽�߂̃��\�b�h
	private static double[] getDrawP(double x, double y, double z)	{
		double DrawX = RVector2.x * x + RVector2.y * y + RVector2.z * z;
		double DrawY = RVector1.x * x + RVector1.y * y + RVector1.z * z;		
		return new double[]{DrawX, DrawY};
	}
	
	/*��]�x�N�g���̃��\�b�h*/
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
		/*�x�N�g���v�Z�@(x�̎n�_ - x�̏I�_) , (y�̎n�_ - y�̏I�_) , (z�̎n�_ - z�̏I�_)*/
		ViewVector = new Vector(Screen.ViewTo[0] - Screen.ViewFrom[0], Screen.ViewTo[1] - Screen.ViewFrom[1], Screen.ViewTo[2] - Screen.ViewFrom[2]);			
		//�����x�N�g�� (�P�ʃx�N�g��)
		DirectionVector = new Vector(1, 1, 1);	
		
		/*
		 * 3�����x�N�g����2�����x�N�g���ŕ`�悷��ׂɂ�2�̃x�N�g����p�ӂ���K�v������
		 * �O�����E�V���~�b�g�̐��K�������@��p����
		 */
		
		PlaneVector1 = ViewVector.CrossProduct(DirectionVector);
		PlaneVector2 = ViewVector.CrossProduct(PlaneVector1);
		
		P = new Plane(PlaneVector1, PlaneVector2, Screen.ViewTo);
		
		//��]�x�N�g��
		RotationVector = Calculator.getRotationVector(Screen.ViewFrom, Screen.ViewTo);
		//��]�x�N�g��1
		RVector1 = ViewVector.CrossProduct(RotationVector);
		//��]�x�N�g��2
		RVector2 = ViewVector.CrossProduct(RVector1);
		
		
		CalculateFocusPosition = Calculator.CalculatePositionP(Screen.ViewFrom, Screen.ViewTo, Screen.ViewTo[0], Screen.ViewTo[1], Screen.ViewTo[2]);
		
		CalculateFocusPosition[0] = Screen.zoom * CalculateFocusPosition[0];
		CalculateFocusPosition[1] = Screen.zoom * CalculateFocusPosition[1];
	}
}