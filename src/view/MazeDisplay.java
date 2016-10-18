package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

/**
 * The Class MazeDisplay.
 */
public abstract class MazeDisplay extends Canvas{


	Maze3d maze;
	Position characterPosition;
	
	/**
	 * Instantiates a new maze display
	 * @param parent- Composite
	 * @param style of the display
	 */
	public MazeDisplay(Composite parent, int style) {
		super(parent, style);
	}
	
	/**
	 * get the maze
	 * @return Maze3d
	 */
	public Maze3d getMaze() {
		return maze;
	}

	/**
	 * set maze
	 * @param maze  Maze3d
	 */
	public void setMaze(Maze3d maze) {
		this.maze = maze;
		if(maze!=null) 
            characterPosition = new Position(maze.getStartPosition());
	}
	
	/**
	 * set the character in a position
	 * @param position the new character position
	 */
	public abstract  void setCharacterPosition(Position position);
	
	/**
	 * Gets the character position
	 * @return the character position
	 */
	public Position getCharacterPosition() {
		return characterPosition;
	}


	public abstract void moveUp();
	
	public abstract  void moveDown();

	public abstract  void moveLeft();

	public  abstract void moveRight();

	public  abstract void movePageUp();

	public  abstract void movePageDown();

	public abstract void moveToStart();
	
	/**
	 * move character to new position
	 * @param p position to move to
	 */
	public abstract void move(Position p);

}
