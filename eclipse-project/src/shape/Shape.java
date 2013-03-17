package shape;

import java.util.ArrayList;

import javax.media.opengl.GL2;

import shape.face.Polygon;
import shape.face.util.Color;
import shape.face.util.Vertex3D;

/**
 * Shape
 * 
 * A 3D object with multiple faces. It's formed based on the relative "tiptop" vertex
 * 
 * @instructor John Braico
 * @assignment A5
 * @author Zhao Han, 7633813
 * @date Dec 7, 2011
 * @platform Ubuntu, 32 bit
 * 
 */
public abstract class Shape {

	private final Vertex3D _relativeOrigin;
	
	private ArrayList<Polygon> _faces;

	private final Color OUTLINE_COLOR = Color.WHITE;
	
	public Shape(Vertex3D tiptop) {

		_relativeOrigin = tiptop;

		_faces = new ArrayList<Polygon>();
		initFaces();
	}
	
	protected Vertex3D getRelativeOrigin() {
		return _relativeOrigin;
	}
	
	protected abstract void initFaces();

	protected void addFaces(Polygon ... faces) {
		for (Polygon f : faces) {
			_faces.add(f);
		}
	}

	public void draw(GL2 gl) {
		for (Polygon f : _faces) {
			f.draw(gl);
			f.drawOutline(gl, OUTLINE_COLOR);
		}
	}

	protected abstract String getName();
}
