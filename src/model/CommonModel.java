package model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * The Class CommonModel.
 */
public abstract class CommonModel extends Observable implements Model {

	protected ExecutorService executor;
	protected Map<String, Maze3d> mazes;
	protected Map<String, Solution<Position>> solutions;
	
	/**
	 * CTOR
	 */
	public CommonModel() {
		this.mazes = new ConcurrentHashMap<String, Maze3d>();
		this.solutions = new HashMap<>();
	}

	@Override
	public abstract void generateMaze(String name, int floors,int rows, int cols) ;

	@Override
	public abstract Maze3d getMaze(String name);
	
	@Override
	public abstract void saveMaze(String mazeName, String fileName) ;

	@Override
	public abstract void loadMaze(String mazeName, String fileName);

	@Override
	public abstract void exit() ;

	@Override
	public abstract File[] listFiles(String path);

	@Override
	public abstract void solveMaze(String mazeName,String alg) ;

	@Override
	public abstract Solution<Position> getSolution(String mazeName) ;

	@Override
	public abstract int[][] getCrossSection(String axis,Integer floor,String mazeName);

	@Override
	public abstract void saveCache();

	@Override
	public abstract void loadSolutions();

	@Override
	public abstract void loadProperties(String fileName);

	@Override
	public abstract void saveProperties(String path);

	@Override
	public abstract void editProperties(String generateMaze, String solutionAlg, Integer numThreads, String viewStyle);
}
