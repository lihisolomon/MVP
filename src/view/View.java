package view;

import java.io.File;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


/**
 * The Interface View.
 */
public interface View {
	
	/**
	 * Prints the output
	 * @param str
	 */
	public void printOutput(String str);
	
	/**
	 * Notify maze is ready
	 * @param name
	 */
	void notifyMazeIsReady(String name);
	
	/**
	 * Display maze
	 * @param maze
	 */
	void displayMaze(Maze3d maze);
	
	/**
	 * Start.
	 */
	void start();
	
	/**
	 * Display files
	 * @param listOfFiles the list of files
	 */
	public void displayFiles(File[] listOfFiles);
	
	/**
	 * Display solution
	 * @param solution
	 */
	public void displaySolution(Solution<Position> solution);
	
	/**
	 * Display cross section
	 * @param maze2d 
	 */
	public void displayCrossSection(int[][] maze2d);
	
}
