package maze;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.media.opengl.GL2;

import maze.component.Arrow;
import maze.component.Floor;
import maze.component.MazeComponent;
import maze.component.Wall;
import shape.Cube;
import shape.face.util.Vertex3D;

/**
 * Maze
 * 
 * @instructor John Braico
 * @assignment A5
 * @author Zhao Han, 7633813
 * @date Dec 7, 2011
 * @platform Ubuntu, 32 bit
 * 
 */
public class Maze {

	private boolean topView;
	
	// location and the orientation facing
	private int currLocationRow, currLocationColumn;
	
	public final static int FRONT = 0, RIGHT = 1, BACK = 2, LEFT = 3;
	private int orientation;

	private char[][] map;
	private MazeComponent[][] mazeComponents;
	
	public Maze(String mazeFileName) {
		init(mazeFileName);
		initMazeComponents();
		
		topView = false;
	}

	public float currEyeX() {
		return getCurrCol() + Cube.halfWidth();
	}

	public float currEyeZ() {
		return getCurrRow() - Cube.halfWidth();
	}
	
	private int getCurrRow() { return currLocationRow - getHeight() + 1; }
	private int getCurrCol() { return currLocationColumn; }

	public int getOrientation() { return orientation; }
	
	public int getHeight() { return map.length; }
	public int getLength() { return map[0].length; }

	public boolean isTopView() { return topView; }
	public void reverseTopView() { topView = !topView; }
	
	private void initMazeComponents() {
		int height = map.length, insideLength = map[0].length;
		mazeComponents = new MazeComponent[height][insideLength];
		
		for (int i = height - 1, z = 0; i >= 0 ; i--, z--) {
			for (int j = 0; j < insideLength; j++) {
				
				Vertex3D origin = new Vertex3D(j, 0, z);
				
				char ch = map[i][j];
				if (ch == ' ')
					mazeComponents[i][j] = new Floor(origin);
				else {
					assert ch != ' ';
					mazeComponents[i][j] = new Wall(origin, Integer.parseInt(ch + "") );
				}
			}
		}
	}

	public void draw(GL2 gl) {
		int height = map.length, insideLength = map[0].length;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < insideLength; j++) {
				mazeComponents[i][j].draw(gl);
			}
		}
		
		if (topView) {
			Vertex3D origin = new Vertex3D(currLocationColumn, 0, getCurrRow() );
			new Arrow(origin).draw(gl, orientation);
		}
	}

	private void init(String filename) {
		String mazeMapText = "";
		
		BufferedReader input;
		String line;

		int width = 0, height = 0;
		
		try {
			input = new BufferedReader(new FileReader(filename));
			line = input.readLine();
			
			// get width and height of maze map;
			// fill out lines
			
			while (line != null) {
				width = Math.max(line.length(), width);
				
				if (line.length() > 0) {
					height++;
					mazeMapText += line;
				}
				
				line = input.readLine();
				if (line != null)
					mazeMapText += '\n';
			}

			initMap(height, width);
			
			setUpMapAndCurrentVals(mazeMapText);
			
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			assert false : "Error reading input file " + filename;
		}
	}

	private void setUpMapAndCurrentVals(String mazeMapText) {
		orientation = -1;
		
		int row = 0, column = 0;
		for (int i = 0; i < mazeMapText.length(); i++) {
			char ch = mazeMapText.charAt(i);
			
			if (ch >= '1' && ch <= '4') {
				map[row][column] = ch;
				column++;
			} else if (ch == ' ') {
				column++;
			} else if ("^>V<".indexOf("" + ch) >= 0) {
				currLocationRow = row;
				currLocationColumn = column;
				column++;
				orientation = "^>V<".indexOf("" + ch); // set up currFacing: left, right, up or down 
			} else if (ch == '\n') {
				row++;
				column = 0;
			} else {
				System.err.println("*** Error: invalid character " + ch + " in input file.");
				assert false;
			}
		}
		
		assert orientation != -1;
	}

	private void initMap(int height, int width) {
		int row, column;
		
		map = new char[height][width];
		
		for (row = 0; row < height; row++)
			for (column = 0; column < width; column++)
				map[row][column] = ' ';
	}

	public void turnLeft() {
		orientation = (orientation + 4 - 1) % 4;
		
		assert orientation <= 3 || orientation >= 1;
	}

	public void turnRight() {
		orientation = (orientation + 1) % 4;
		
		assert orientation <= 3 || orientation >= 1;
	}

	public boolean moveForward() {
		return move(orientation);
	}

	public boolean moveBackward() {
		return move((orientation + 2) % 4);
	}

	private boolean move(int facing) {
		boolean result = false;
		
		char [] currentRow = map[currLocationRow];
		
		switch (facing) {
		case 0:
			if (currLocationRow > 0)
				if (currLocationColumn >= currentRow.length || map[currLocationRow - 1][currLocationColumn] == ' ') {
					currLocationRow--;
					result = true;
				}
			break;
		case 1:
			if (currLocationColumn >= currentRow.length - 1 || map[currLocationRow][currLocationColumn + 1] == ' ') {
				currLocationColumn++;
				result = true;
			}
			break;
		case 2:
			if (currLocationRow < map.length - 1)
				if (currLocationColumn >= currentRow.length || map[currLocationRow + 1][currLocationColumn] == ' ') {
					currLocationRow++;
					result = true;
				}
			break;
		case 3:
			if (currLocationColumn > 0)
				if (currLocationColumn > currentRow.length || map[currLocationRow][currLocationColumn - 1] == ' ') {
					currLocationColumn--;
					result = true;
				}
			break;
		}
		
		return result;
	}

	public String toString() {
		String result = String.format("map: (current: %d, %d )\n", currLocationColumn, getCurrRow() );
		

		for (int r = 0; r < map.length; r++) {
			for (int c = 0; c < Math.max(map[r].length, currLocationColumn + 1); c++)
				if (r == currLocationRow && c == currLocationColumn)
					result += "^>V<".charAt(orientation);
				else if (r < map.length && c < map[r].length)
					result += map[r][c];
				else
					result += ' ';
			result += "\n";
		}
		
		return result;
	}
}