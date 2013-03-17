package shape.face;

import java.util.ArrayList;

import javax.media.opengl.GL2;

import shape.face.util.Color;
import shape.face.util.Vertex3D;

/**
 * Face
 * 
 * tries to wrap primitives of OpenGl
 * 
 * @instructor John Braico
 * @assignment A5
 * @author Zhao Han, 7633813
 * @date Dec 7, 2011
 * @platform Ubuntu, 32 bit
 * 
 */
public abstract class Face {

	private ArrayList<Vertex3D> _vertices;
	
	private Color _color;

	public Face() {
		_vertices = new ArrayList<Vertex3D>();
	}

	protected Face(Color color, Vertex3D...vertices) {
		this();
		
		setColor(color);
		addVertex(vertices);
	}
	
	public void setColor(Color color) { _color = color; }

	protected void setColor3f(GL2 gl) {
		_color.setGlColor3f(gl);
	}

	protected void setGlColor4f(GL2 gl, float alpha) {
		_color.setGlColor4f(gl, alpha);
	}

	public void addVertex(Vertex3D... vertices) {
		for (Vertex3D v : vertices) {
			_vertices.add(v);
		}
	}
	
	public void addVertex(float x, float y, float z) {
		_vertices.add(new Vertex3D(x, y, z));
	}

	public abstract void draw(GL2 gl);
	
	protected void glBeginEndBlock(GL2 gl, int primitiveType) {
		gl.glBegin(primitiveType);
		
		for (Vertex3D v : _vertices)
			v.setGlVertex3f(gl);
		
		gl.glEnd();
	}

	protected void glBeginEndBlockWithTexture(GL2 gl, int primitiveType) {
		gl.glBegin(primitiveType);
		
		final int REPEAT_TIMES = 3;
		float s = 1, t = 1;
		int caseNum = 0;
		
		for (Vertex3D v : _vertices) {
			if (caseNum == 0) {
				s = 0;
				t = 0;
				caseNum++;
			}
			else if (caseNum == 1) {
				s = REPEAT_TIMES;
				t = 0;
				caseNum++;
			}
			else if (caseNum == 2) {
				s = REPEAT_TIMES;
				t = REPEAT_TIMES;
				caseNum++;
			}
			else if (caseNum == 3) {
				s = 0;
				t = REPEAT_TIMES;
				caseNum = 0;
			}
			
			gl.glTexCoord2f(s, t);
			v.setGlVertex3f(gl);
		}
		
		gl.glEnd();
	}
	
	@Override
	public String toString() {
		String result = "face: ";
		
		for (Vertex3D v : _vertices) {
			result += v.toIntString() + "; ";
		}
		
		return result;
	}
}
