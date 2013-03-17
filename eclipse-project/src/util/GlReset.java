package util;

import javax.media.opengl.GL2;

/**
 * GlReset
 *
 * reset gl settings
 * 
 * @instructor John Braico
 * @assignment A5
 * @author Zhao Han, 7633813
 * @date Dec 7, 2011
 * @platform Ubuntu, 32 bit
 * 
 */
public class GlReset {

	public static void resetPointSize(GL2 gl) {
		gl.glPointSize(1);
	}

	public static void resetLineWidth(GL2 gl) {
		gl.glLineWidth(1);
	}
}
