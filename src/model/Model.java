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
	 * @param name
	 * @param floors
	 * @param rows 
	 * @param cols 
	 */
	public void generateMaze(String name, int floors,int rows, int cols);
	
	/**
	 * Gets the maze
	 * @param name
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
	 * @param mazeName
	 * @param fileName
	 */
	public void saveMaze(String mazeName, String fileName);
	
	/**
	 * Load maze
	 * @param mazeName
	 * @param fileName
	 */
	public void loadMaze(String mazeName, String fileName);
	
	/**
	 * Solve maze
	 * @param mazeName
	 * @param alg
	 */
	public void solveMaze(String mazeName,String alg);
	
	/**
	 * Gets the solution
	 * @param mazeName
	 * @return the solution
	 */
	public Solution<Position> getSolution(String mazeName);
	
	/**
	 * Gets the cross section
	 * @param axis 
	 * @param floor
	 * @param mazeName 
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
	 * @param fileName 
	 */
	public void loadProperties(String fileName);
	
	/**
	 * Save properties
	 * @param path the path
	 */
	public void saveProperties(String path);
	
	/**
	 * Edits the properties
	 * @param generateMaze
	 * @param solutionAlg
	 * @param numThreads
	 * @param viewStyle
	 */
	public void editProperties(String generateMaze, String solutionAlg, Integer numThreads, String viewStyle);
	
	/**
	 * Exit
	 */
	public void exit();
}
