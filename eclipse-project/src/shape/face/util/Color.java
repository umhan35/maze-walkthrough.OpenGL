package shape.face.util;

import javax.media.opengl.GL2;

/**
 * Color
 *
 * encapsulates 1. color RGB data 2. wrap up glColor*() functions from GL
 * 
 * @instructor John Braico
 * @assignment A5
 * @author Zhao Han, 7633813
 * @date Dec 7, 2011
 * @platform Ubuntu, 32 bit
 * 
 */
public class Color {

	public static Color BLACK = new Color(0f, 0f, 0f);
	public static Color RED = new Color(1f, 0f, 0f);
	public static Color GREEN = new Color(0f, 1f, 0f);
	public static Color BLUE  = new Color(0f, 0f, 1f);
	public static Color WHITE = new Color(1f, 1f, 1f);
	
	public static Color DARK_VIOLET = new Color(148f, 0f, 211f);
	
	private Float _r, _g, _b;
	
	public Color(Float r, Float g, Float b) {
		_r = r;
		_g = g;
		_b = b;
	}

	public void setGlColor3f(GL2 gl) {
		gl.glColor3f(_r, _g, _b);
	}

	public void setGlColor4f(GL2 gl, float alphaValue) {
		gl.glColor4f(_r, _g, _b, alphaValue);
	}
	
	@Override
	public String toString() {
		return String.format("rgb(%f, %f, %f)", _r, _g, _b);
	}
}
