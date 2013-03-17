package shape.face;

import javax.media.opengl.GL2;

import shape.face.util.Color;
import shape.face.util.Vertex3D;

/**
 * Polygon
 *
 * is a special case of face where # of vertices is >= 3
 * 
 * @instructor John Braico
 * @assignment A5
 * @author Zhao Han, 7633813
 * @date Dec 7, 2011
 * @platform Ubuntu, 32 bit
 * 
 */
public class Polygon extends Face {

	public Polygon(Color color, Vertex3D...vertices) {
		super(color, vertices);
	}

	public Polygon() {
		super();
	}
	
	@Override
	public void draw(GL2 gl) {
		gl.glEnable(GL2.GL_POLYGON_OFFSET_FILL);
		gl.glPolygonOffset(1.0f, 1.0f);
		setColor3f(gl);
		glBeginEndBlockWithTexture(gl, GL2.GL_POLYGON);
		gl.glDisable(GL2.GL_POLYGON_OFFSET_FILL);
	}

	public void drawOutline(GL2 gl, Color color) {
		gl.glDisable(GL2.GL_TEXTURE_2D);
		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
		
		color.setGlColor3f(gl);
		glBeginEndBlock(gl, GL2.GL_POLYGON);
		
		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
		gl.glEnable(GL2.GL_TEXTURE_2D);
	}
}
