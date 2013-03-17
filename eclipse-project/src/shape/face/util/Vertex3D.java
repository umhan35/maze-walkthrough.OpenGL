package shape.face.util;

import javax.media.opengl.GL2;

/**
 * Vertex3D
 *
 * encapsulates 1. vertex(in 3D) data read from the .obj file and related operations
 * 
 * @instructor John Braico
 * @assignment A5
 * @author Zhao Han, 7633813
 * @date Dec 7, 2011
 * @platform Ubuntu, 32 bit
 * 
 */
public class Vertex3D {

	public static final Vertex3D ORIGIN = new Vertex3D(0, 0, 0);
	
	private float _x, _y, _z;

	public Vertex3D(float x, float y, float z) {
		_x = x;
		_y = y;
		_z = z;
	}

	public Vertex3D(Vertex3D v) {
		_x = v.x();
		_y = v.y();
		_z = v.z();
	}

	@Override
	public String toString() {
		return String.format("(%f, %f, %f)", _x, _y, _z);
	}

	public String toIntString() {
		return String.format("(%.0f, %.0f, %.0f)", _x, _y, _z);
	}
	
	public float x() { return _x; }
	public float y() { return _y; }
	public float z() { return _z; }
	
	public void setGlVertex3f(GL2 gl) {
		gl.glVertex3f(_x, _y, _z);
	}

	public Vertex3D moveX(float value) {
		_x += value;
		return this;
	}

	public Vertex3D moveY(float value) {
		_y += value;
		return this;
	}

	public Vertex3D moveZ(float value) {
		_z += value;
		return this;
	}

	public Vertex3D deepCopy() {
		return new Vertex3D(_x, _y, _z);
	}
}
