package model;

import java.io.File;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * Model interface.
 */
public interface Model {
	
	/**
	 * Generate maze
	 * @param name- maze name
	 * @param floors -number of floors
	 * @param rows  - number of rows
	 * @param cols -number of columns
	 */
	public void generateMaze(String name, int floors,int rows, int cols);
	
	/**
	 * Gets the maze
	 * @param name -maze name
	 * @return the maze
	 */
	public Maze3d getMaze(String name);
	
	/**
	 * List files
	 * @param path the path
	 * @return the file[]
	 */
	public File[] listFiles(String path);
	
	/**
	 * Save maze
	 * @param mazeName -maze name
	 * @param fileName -the file name
	 */
	public void saveMaze(String mazeName, String fileName);
	
	/**
	 * Load maze
	 * @param mazeName -maze name
	 * @param fileName -the file name
	 */
	public void loadMaze(String mazeName, String fileName);
	
	/**
	 * Solve maze
	 * @param mazeName -maze name
	 * @param alg -the algorithm to solve with
	 */
	public void solveMaze(String mazeName,String alg);
	
	/**
	 * Gets the solution
	 * @param mazeName -maze name
	 * @return the solution
	 */
	public Solution<Position> getSolution(String mazeName);
	
	/**
	 * Gets the cross section
	 * @param axis -the axis
	 * @param floor -the floor
	 * @param mazeName -maze name
	 * @return the cross section
	 */
	public int[][] getCrossSection(String axis,Integer floor,String mazeName);
	
	/**
	 * Save cache
	 */
	public void saveCache();
	
	/**
	 * Load solutions
	 */
	public void loadSolutions();
	
	/**
	 * Load properties
	 * @param fileName -the file name of the properties
	 */
	public void loadProperties(String fileName);
	
	/**
	 * Save properties
	 * @param path the path
	 */
	public void saveProperties(String path);
	
	/**
	 * Edits the properties
	 * @param generateMaze- how to generate maze: simple or growing
	 * @param solutionAlg -how to solve the maze: BFS or DFS
	 * @param numThreads - number of threads
	 * @param viewStyle - the view style of the gui
	 */
	public void editProperties(String generateMaze, String solutionAlg, Integer numThreads, String viewStyle);
	
	/**
	 * Exit
	 */
	public void exit();
}
