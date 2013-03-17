package maze;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.opengl.GL2;

import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

/**
 * Textures
 * 
 * encapulates all texture operations
 * 
 * @instructor John Braico
 * @assignment A5
 * @author Zhao Han, 7633813
 * @date Dec 7, 2011
 * @platform Ubuntu, 32 bit
 * 
 */
public class Textures {
	
	private static Texture[] textures = null;
	
	public static final String[] TEXTURE_FILES = { "gravel.jpg", "brick.jpg", "wood.jpg", "marble.jpg", "wallpaper.jpg" };
	public static final int[] TEXTURE_REPEAT = { 1, 1, 1, 1, 5 };

	public static void init(final GL2 gl) {
		textures = new Texture[TEXTURE_FILES.length];
		
		try {
			for (int i = 0; i < TEXTURE_FILES.length; i++) {
				File infile = new File("texture/" + TEXTURE_FILES[i]); 
				BufferedImage image = ImageIO.read(infile);
				ImageUtil.flipImageVertically(image);
				textures[i] = TextureIO.newTexture(AWTTextureIO.newTextureData(gl.getGLProfile(), image, false));

				// These apply on a per-texture basis: they are the JOGL equivalent to glTexParameteri
				textures[i].setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT); // GL_REPEAT or GL_CLAMP
				textures[i].setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT); 
				textures[i].setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR); // GL_LINEAR or GL_NEAREST or one of the mipmap ones
				textures[i].setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// on some implementations, GL_FASTEST will turn off perspective correction (use GL_NICEST instead)
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
		gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE); // GL_MODULATE is default; or try GL_REPLACE, GL_DECAL, GL_BLEND 
	}
	
	public static Texture get(int n) {
		assert textures[n] != null;
		return textures[n];
	}
}
