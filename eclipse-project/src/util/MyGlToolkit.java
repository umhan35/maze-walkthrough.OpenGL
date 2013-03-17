package util;

import javax.media.opengl.GL2;

import shape.face.Line;
import shape.face.util.Color;
import shape.face.util.Vertex3D;

/**
 * MyGlToolkit
 *
 * encapsulate some method like: enable line smooth and draw x,y,z axes.
 * 
 * @instructor John Braico
 * @assignment A5
 * @author Zhao Han, 7633813
 * @date Dec 7, 2011
 * @platform Ubuntu, 32 bit
 * 
 */
public class MyGlToolkit {

	public static void lineSmooth(final GL2 gl) {
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc (GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL2.GL_LINE_SMOOTH);
	}

	/**
	 * Color:
	 *  x axe: red;
	 *  y axe: green;
	 *  z axe: blue;
	 * 
	 * @param gl
	 */
	public static void drawPositiveAxes(final GL2 gl) {
		Line x = new Line(Color.RED,   Vertex3D.ORIGIN, new Vertex3D(100, 0, 0));
		Line y = new Line(Color.GREEN, Vertex3D.ORIGIN, new Vertex3D(0, 100, 0));
		Line z = new Line(Color.BLUE,  Vertex3D.ORIGIN, new Vertex3D(0, 0, 100));
		
		gl.glLineWidth(3f);
		x.draw(gl);
		y.draw(gl);
		z.draw(gl);
		GlReset.resetLineWidth(gl);
	}
}
