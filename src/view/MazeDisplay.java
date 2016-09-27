package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public abstract class MazeDisplay extends Canvas{

	Maze3d maze;
	Position characterPosition;
	
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
	 */
	public abstract  void setCharacterPosition(Position position);
	
	public Position getCharacterPosition() {
		return characterPosition;
	}

	/**
	 * move character one step up
	 */
	public abstract void moveUp();
	/**
	 * move character one step down
	 */
	public abstract  void moveDown();
	/**
	 * move character one step left
	 */
	public abstract  void moveLeft();
	/**
	 * move character one step Right
	 */
	public  abstract void moveRight();
	/**
	 * move character one step floor up
	 */
	public  abstract void movePageUp();
	/**
	 * move character one step floor down
	 */
	public  abstract void movePageDown();
	/**
	 * move character to start position of the maze
	 */
	public abstract void moveToStart();
	/**
	 * move character to new position
	 * @param p position to move to
	 */
	public abstract void move(Position p);

}
