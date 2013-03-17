package maze.component;

import javax.media.opengl.GL2;


/**
 * MazeComponent
 * 
 * is made for the Maze class to store all maze components in the mazeComponents data member
 * 
 * @instructor John Braico
 * @assignment A5
 * @author Zhao Han, 7633813
 * @date Dec 7, 2011
 * @platform Ubuntu, 32 bit
 * 
 */
public interface MazeComponent {

	void draw(GL2 gl);
}
