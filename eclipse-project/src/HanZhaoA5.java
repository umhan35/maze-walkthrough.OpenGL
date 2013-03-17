import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

import maze.Maze;
import maze.Textures;
import shape.Cube;
import util.MyGlToolkit;

/**
 * HanZhaoA5
 * 
 * An OpenGL program that will allow you to walk through a 2-dimensional maze with
 *  a 3-dimensional first-person view. That is, the viewpoint moves through 
 *  the open areas of the maze.
 * 
 * @instructor John Braico
 * @assignment A5
 * @author Zhao Han, 7633813
 * @date Dec 7, 2011
 * @platform Ubuntu, 32 bit
 * 
 */
public class HanZhaoA5 extends KeyAdapter implements GLEventListener {

	public static final boolean TRACE = false;

	public static final String WINDOW_TITLE = "COMP 3490 Assignment 5: [Zhao Han]";
	public static final int INITIAL_WIDTH = 900, INITIAL_HEIGHT = INITIAL_WIDTH / 16 * 10;

	private static final GLU glu = new GLU();

	public static void main(String[] args) {
		final JFrame frame = new JFrame(WINDOW_TITLE);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (TRACE)
					System.out.println("closing window '" + ((JFrame)e.getWindow()).getTitle() + "'");
				System.exit(0);
			}
		});

		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		final GLCapabilities capabilities = new GLCapabilities(profile);
		final GLCanvas canvas = new GLCanvas(capabilities);
		try {
			Object self = self().getConstructor().newInstance();
			self.getClass().getMethod("setup", new Class[] { GLCanvas.class }).invoke(self, canvas);
			canvas.addGLEventListener((GLEventListener)self);
			canvas.addKeyListener((KeyListener)self);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		canvas.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);

		frame.getContentPane().add(canvas);
		frame.pack();
		frame.setVisible(true);

		canvas.requestFocusInWindow();

		if (TRACE)
			System.out.println("-> end of main().");
	}

	private static Class<?> self() {
		// This ugly hack gives us the containing class of a static method 
		return new Object() { }.getClass().getEnclosingClass();
	}

	/*** Instance variables and methods ***/

	private Maze maze;
	
	private float ar; // aspect ratio

	private float currRotationAngle, offsetRotationAngle;
	private float currX, currZ;

	public void setup(final GLCanvas canvas) {
		// Called for one-time setup
		if (TRACE)
			System.out.println("-> executing setup()");

		setTimer(canvas);

		maze = new Maze("sample_maze.txt");
		System.out.println(maze);
		
		currRotationAngle = 90 * maze.getOrientation();
		offsetRotationAngle = 0;
		
		currX = maze.currEyeX();
		currZ = maze.currEyeZ();
	}
	
	private void setTimer(final GLCanvas canvas) {

		final float OFFSET = 0.01f;
		
		new Timer().scheduleAtFixedRate(new TimerTask() {
			public void run() {

				checkAdjustX();
				checkAdjustZ();
				checkAdjustRotation();
				
				canvas.repaint();
			}

			private void checkAdjustRotation() {
				
				if (offsetRotationAngle > 0) {
					currRotationAngle++;
					offsetRotationAngle--;
				}
				else if (offsetRotationAngle < 0) {
					currRotationAngle--;
					offsetRotationAngle++;
				}
			}
			
			private void checkAdjustX() {
				float destX = maze.currEyeX();

				if (Math.abs(destX - currX) < 0.01 && Math.abs(destX - currX) > 0) {
					// solve float precision problem
					return;
				}
				
				if (currX < destX) {
					currX += OFFSET;
				}
				else if (currX > destX) {
					currX -= OFFSET;
				}
			}

			private void checkAdjustZ() {
				float destZ = maze.currEyeZ();
				
				if (Math.abs(destZ - currZ) < 0.01 && Math.abs(destZ - currZ) > 0) {
					// solve float precision problem
					return;
				}
				
				if (currZ < destZ) {
					currZ += OFFSET;
				}
				else if (currZ > destZ) {
					currZ -= OFFSET;
				}
			}

		}, 1000, 1000/100);
	}
	
	@Override
	public void init(GLAutoDrawable drawable) {
		// Called when the canvas is (re-)created - use it for initial GL setup
		if (TRACE)
			System.out.println("-> executing init()");

		final GL2 gl = drawable.getGL().getGL2();
		
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		
		MyGlToolkit.lineSmooth(gl);
		
		gl.glEnable(GL2.GL_CULL_FACE); // enable backface culling

		Textures.init(gl);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		// Draws the display
		if (TRACE)
			System.out.println("-> executing display()");

		final GL2 gl = drawable.getGL().getGL2();

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		_initProjection(gl);
		_initModelView(gl);
		
		_draw(gl);
	}

	private void _draw(final GL2 gl) {
		gl.glEnable(GL2.GL_TEXTURE_2D);
		maze.draw(gl);
		gl.glDisable(GL2.GL_TEXTURE_2D);
		
//		MyGlToolkit.drawPositiveAxes(gl);
		
		gl.glFlush();
	}

	private void _initProjection(final GL2 gl) {
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		
		if (maze.isTopView() ) {
			glu.gluPerspective(75, ar, 1, 100);
		}
		else {
			glu.gluPerspective(75, ar, .1, 100);
		}
	}

	private void _initModelView(final GL2 gl) {
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	
		if (maze.isTopView() ) {
			glu.gluLookAt(maze.getLength() / 2, 10, maze.getHeight() / -2, 
						  maze.getLength() / 2,  0, maze.getHeight() / -2, 
						  0, 0, -1);
		}
		else {
			float x = currX,
				  y = Cube.halfWidth(),
				  z = currZ;

			// move to the position
			gl.glTranslatef(-x, -y, -z);
			
			// adjust orientation
			gl.glTranslatef(x, y, z);
			gl.glRotatef(currRotationAngle, 0, 1, 0);
			gl.glTranslatef(-x, -y, -z);
			
//			gl.glRotatef(currRotationAngle, 0, 1, 0);
//			gl.glTranslatef(x, y, z);
		}
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// Called when the canvas has been resized
		// Note: glViewport(x, y, width, height) has already been called so don't bother if that's what you want
		if (TRACE)
			System.out.println("-> executing reshape(" + x + ", " + y + ", " + width + ", " + height + ")");
	
		ar = (float) width / (height == 0 ? 1 : height);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// Called when the canvas is destroyed (reverse anything from init) 
		if (TRACE)
			System.out.println("-> executing dispose()");
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int typedChar = e.getKeyCode();
		
		if (typedChar == KeyEvent.VK_LEFT || e.getKeyChar() == 'a') {
			maze.turnLeft();
			offsetRotationAngle -= 90;
			
			System.out.println("turned left");
		}
		else if (typedChar == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd') {
			maze.turnRight();
			offsetRotationAngle += 90;
			
			System.out.println("turned right");
		}
		else if (typedChar == KeyEvent.VK_UP || e.getKeyChar() == 'w') {
			maze.moveForward();
			System.out.println("moved forward");
		}
		else if (typedChar == KeyEvent.VK_DOWN || e.getKeyChar() == 's') {
			maze.moveBackward();
			System.out.println("moved backward");
		}
		else if (typedChar == KeyEvent.VK_SPACE) {
			maze.reverseTopView();
			System.out.println("Top view: " + maze.isTopView());
		}
		
		if (typedChar != KeyEvent.VK_SPACE) {
			System.out.println(maze);
		}
		
		((GLCanvas)e.getSource()).repaint();
	}

}