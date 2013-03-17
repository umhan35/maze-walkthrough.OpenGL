package shape;

import java.util.ArrayList;

import shape.face.Polygon;
import shape.face.util.Color;
import shape.face.util.Vertex3D;

/**
 * Cube
 * 
 * has five faces: front, right, back, left and top.
 * 
 * @instructor John Braico
 * @assignment A5
 * @author Zhao Han, 7633813
 * @date Dec 7, 2011
 * @platform Ubuntu, 32 bit
 * 
 */
public class Cube extends Shape {

	public static final int WIDTH = 1;
	
	public static final Color FILL_COLOR = Color.WHITE;
	
	public Cube(Vertex3D bottomLeft) {
		super(bottomLeft);
	}

	public static float halfWidth() {
		return 0.5f;
	}
	
	@Override
	protected void initFaces() {

		ArrayList<Vertex3D> vertices = initFacesVertices();
		
		Polygon front = new Polygon(FILL_COLOR, vertices.get(0), vertices.get(1), vertices.get(2), vertices.get(3));
		Polygon right = new Polygon(FILL_COLOR, vertices.get(1), vertices.get(4), vertices.get(5), vertices.get(2));
		Polygon back  = new Polygon(FILL_COLOR, vertices.get(4), vertices.get(7), vertices.get(6), vertices.get(5));
		Polygon left  = new Polygon(FILL_COLOR, vertices.get(7), vertices.get(0), vertices.get(3), vertices.get(6));
		Polygon top   = new Polygon(FILL_COLOR, vertices.get(3), vertices.get(2), vertices.get(5), vertices.get(6));
		
		addFaces(new Polygon[] {front, right, back, left, top} );
	}

	private ArrayList<Vertex3D> initFacesVertices() {

		ArrayList<Vertex3D> vertices = new ArrayList<Vertex3D>();
		
		Vertex3D origin = getRelativeOrigin();
		float ox = origin.x(), oy = origin.y(), oz = origin.z();
		
		// front
		vertices.add(new Vertex3D(origin) ); // bottom left
		vertices.add(new Vertex3D(ox + WIDTH, oy, oz)); // bottom right
		vertices.add(new Vertex3D(ox + WIDTH, oy + WIDTH, oz)); // top right
		vertices.add(new Vertex3D(ox, oy + WIDTH, oz)); // top left
		
		// right
		vertices.add(new Vertex3D(ox + WIDTH, oy, oz - WIDTH)); // bottom right
		vertices.add(new Vertex3D(ox + WIDTH, oy + WIDTH, oz - WIDTH)); // top right

		// left
		vertices.add(new Vertex3D(ox, oy + WIDTH, oz - WIDTH)); // top left
		vertices.add(new Vertex3D(ox, oy, oz - WIDTH)); // bottom left
		
		return vertices;
	}

	@Override
	protected String getName() {
		return "cube";
	}
}
