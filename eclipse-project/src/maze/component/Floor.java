package maze.component;

import javax.media.opengl.GL2;

import maze.Textures;
import shape.Cube;
import shape.face.Polygon;
import shape.face.util.Color;
import shape.face.util.Vertex3D;

import com.jogamp.opengl.util.texture.Texture;

/**
 * Floor
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
public class Floor implements MazeComponent {

	private Polygon _bottomSquareSurface;
	
	public Floor(Vertex3D bottomLeftVertex) {
		_bottomSquareSurface = new Polygon();
		_bottomSquareSurface.setColor(Cube.FILL_COLOR);
		setUpVertices(bottomLeftVertex);
	}
	
	private void setUpVertices(Vertex3D bottomLeftVertex) {
		float ox = bottomLeftVertex.x(), oy = bottomLeftVertex.y(), oz = bottomLeftVertex.z();
		int width = Cube.WIDTH;
		
		_bottomSquareSurface.addVertex(bottomLeftVertex);
		_bottomSquareSurface.addVertex(ox + width, oy, oz);
		_bottomSquareSurface.addVertex(ox + width, oy, oz - width);
		_bottomSquareSurface.addVertex(ox, oy, oz - width);
	}
	
	public void draw(GL2 gl) {
		Texture texture = Textures.get(0);
		texture.enable(gl);
		texture.bind(gl);
		
		_bottomSquareSurface.draw(gl);
		_bottomSquareSurface.drawOutline(gl, Color.WHITE);
		
		texture.disable(gl);
	}
}
