package main2_0;


public class Vector {
	double x, y, z;
	public Vector(double x, double y, double z){
		/*�x�N�g���̑傫�����v�Z*/
		double length = Math.sqrt(x*x + y*y + z*z);
		/*���`�Ɨ��̎��Ɏ��s�B���`�]���̎��ɂ�x,y,z�̒l��0��*/
		if(length>0){
			this.x = x/length;
			this.y = y/length;
			this.z = z/length;
		}

	}
	
	/*�x�N�g���̒��p�Ɍ�������x�N�g���̍쐬*/
	Vector CrossProduct(Vector V){
		Vector CrossVector = new Vector(
				y * V.z - z * V.y,
				z * V.x - x * V.z,
				x * V.y - y * V.x);
		return CrossVector;		
	}
}