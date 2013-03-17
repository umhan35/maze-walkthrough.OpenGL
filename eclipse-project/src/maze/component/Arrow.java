package maze.component;

import javax.media.opengl.GL2;

import maze.Maze;
import shape.Cube;
import shape.face.Polygon;
import shape.face.util.Color;
import shape.face.util.Vertex3D;

/**
 * Arrow
 * 
 * is the arrow shown in the top view
 * 
 * @instructor John Braico
 * @assignment A5
 * @author Zhao Han, 7633813
 * @date Dec 7, 2011
 * @platform Ubuntu, 32 bit
 * 
 */
public class Arrow {

	private Vertex3D _bottomLeftVertex;

	private Polygon leftArrow, rightArrow;

	public Arrow(Vertex3D bottomLeftVertex) {
		_bottomLeftVertex = bottomLeftVertex;

		leftArrow = new Polygon();
		rightArrow = new Polygon();

		leftArrow.setColor(Color.DARK_VIOLET);
		rightArrow.setColor(Color.DARK_VIOLET);
		
		setUpVertices();
	}

	private void setUpVertices() {

		int width = Cube.WIDTH;
		float halfWidth = (float)width * 0.5f;
		
		Vertex3D left = new Vertex3D(_bottomLeftVertex.x(), _bottomLeftVertex.y(), _bottomLeftVertex.z() );
		Vertex3D backArrowVertex = new Vertex3D(_bottomLeftVertex.x() + halfWidth, _bottomLeftVertex.y(), _bottomLeftVertex.z() - halfWidth * 1f/4f);

		Vertex3D right = new Vertex3D(_bottomLeftVertex.x() + width, _bottomLeftVertex.y(), _bottomLeftVertex.z() );
		Vertex3D frontArrowVertex = new Vertex3D(_bottomLeftVertex.x() + halfWidth,
				_bottomLeftVertex.y() + 0.01f,
				_bottomLeftVertex.z() - width );

		leftArrow.addVertex(left);
		leftArrow.addVertex(backArrowVertex);
		leftArrow.addVertex(frontArrowVertex);

		rightArrow.addVertex(backArrowVertex);
		rightArrow.addVertex(right);
		rightArrow.addVertex(frontArrowVertex);
	}

	public void draw(GL2 gl, int currFacing) {
		gl.glPushMatrix();
			
			float halfWidth = (float)Cube.WIDTH * 0.5f;
			float x = _bottomLeftVertex.x() + halfWidth;
			float z = _bottomLeftVertex.z() * -1 + halfWidth;
			gl.glTranslatef(x, 0, -z);
			glRotate(gl, currFacing);
			gl.glTranslatef(-x, 0, z);
			
			draw(gl);
			
		gl.glPopMatrix();
	}
	
	private void glRotate(GL2 gl, int currFacing) {
		int rotateAngle = 0;
		
		switch (currFacing) {
			case Maze.FRONT:
				rotateAngle = 0;
				break;
			case Maze.RIGHT:
				rotateAngle = -90;
				break;
			case Maze.BACK:
				rotateAngle = 180;
				break;
			case Maze.LEFT:
				rotateAngle = 90;
				break;

			default:
				break;
		}

		gl.glRotatef(rotateAngle, 0, 1, 0);
	}
	
	private void draw(GL2 gl) {
		leftArrow.draw(gl);
		rightArrow.draw(gl);
	}
}
