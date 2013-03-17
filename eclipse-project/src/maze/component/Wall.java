package maze.component;

import javax.media.opengl.GL2;

import maze.Textures;
import shape.Cube;
import shape.face.util.Vertex3D;

import com.jogamp.opengl.util.texture.Texture;

/**
 * Wall
 * 
 * is one kind of component in the maze.
 * 
 * @instructor John Braico
 * @assignment A5
 * @author Zhao Han, 7633813
 * @date Dec 7, 2011
 * @platform Ubuntu, 32 bit
 * 
 */
public class Wall extends Cube implements MazeComponent {

	private int _textureIndex;
	
	public Wall(Vertex3D bottomLeft, int textureIndex) {
		super(bottomLeft);
		
		_textureIndex = textureIndex;
	}
	
	@Override
	public void draw(GL2 gl) {
		Texture texture = Textures.get(_textureIndex);
		texture.enable(gl);
		texture.bind(gl);
		
		super.draw(gl);
		
		texture.disable(gl);
	}

	@Override
	protected String getName() {
		return "wall";
	}
}
