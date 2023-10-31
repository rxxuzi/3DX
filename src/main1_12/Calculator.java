package main1_12;
/*DPolygon.class�̌v�Z�pclass*/
public class Calculator {
	static double DrawX = 0 , DrawY = 0;
	/*X���W�̕`��̌v�Z*/
	static double CalculatorPositionX(double[] ViewFrom , double[] ViewTo , double x , double y, double z) {
		setStuff(ViewFrom , ViewTo , x, y, z);
		return DrawX ; 
	}
	/*Y���W�̕`��̌v�Z*/
	static double CalculatorPositionY(double[] ViewFrom , double[] ViewTo , double x , double y, double z) {
		setStuff(ViewFrom , ViewTo , x, y, z);
		return DrawY ; 
	}
	/*�x�N�g���v�Z*/
	static void setStuff(double[] ViewFrom , double[] ViewTo , double x , double y , double z) {
		
		/*�x�N�g���v�Z�@(x�̎n�_ - x�̏I�_) , (y�̎n�_ - y�̏I�_) , (z�̎n�_ - z�̏I�_)*/
		Vector ViewVector = new Vector(ViewTo[0] - ViewFrom[0] , ViewTo[1] - ViewFrom[1] , ViewTo[2] - ViewFrom[2] );
		/*�����x�N�g�� (�P�ʃx�N�g��) */
		Vector DirectionVector = new Vector(1,1,1);
		/*
		 * 3�����x�N�g����2�����x�N�g���ŕ`�悷��ׂɂ�2�̃x�N�g����p�ӂ���K�v������
		 * �O�����E�V���~�b�g�̐��K�������@��p����
		 */
		Vector PlaneVector1 = ViewVector.CrossProduct(DirectionVector);
		Vector PlaneVector2 = ViewVector.CrossProduct(PlaneVector1);
		
		Vector ViewToPoint = new Vector( x - ViewFrom[0] , y - ViewFrom[1] , z - ViewFrom[2] );
		
		double v_vt = ViewVector.x * ViewTo[0]  	 + ViewVector.y * ViewTo[1]	  	+ ViewVector.z * ViewTo[2];
		double v_vf = ViewVector.x * ViewFrom[0] 	 + ViewVector.y * ViewFrom[1]	+ ViewVector.z * ViewFrom[2];
		double v_tp = ViewVector.x * ViewToPoint.x   + ViewVector.y * ViewToPoint.y + ViewVector.z * ViewToPoint.z;
		/*�x�N�g����ŕ��ʂƏՓ˂��鋗��*/
		double t = ( v_vt - v_vf ) / v_tp ;
		//���ʓ��̓_
		x = ViewFrom[0] + ViewToPoint.x * t ;
		y = ViewFrom[1] + ViewToPoint.y * t ;
		z = ViewFrom[2] + ViewToPoint.z * t ;
		
		/*�x�N�g���̎n�_���O�ɂ��鎞�̂݁A3��������2�����ɕϊ��ł���*/
		if(t > 0) {
			DrawX = PlaneVector2.x * x + PlaneVector2.y * y + PlaneVector2.z * z;
			DrawY = PlaneVector1.x * x + PlaneVector1.y * y + PlaneVector1.z * z;
			
		}
		
	}
}
