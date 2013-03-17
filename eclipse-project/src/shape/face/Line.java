package shape.face;

import javax.media.opengl.GL2;

import shape.face.util.Color;
import shape.face.util.Vertex3D;

/**
 * Line
 *
 * is a special case of face where # of vertices is 2
 * 
 * @instructor John Braico
 * @assignment A5
 * @author Zhao Han, 7633813
 * @date Dec 7, 2011
 * @platform Ubuntu, 32 bit
 * 
 */
public class Line extends Face {

	public Line(Color color, Vertex3D start, Vertex3D end) {
		setColor(color);
		addVertex(start, end);
	}
	
	@Override
	public void draw(GL2 gl) {
		setColor3f(gl);
		glBeginEndBlock(gl, GL2.GL_LINES);
	}
}
