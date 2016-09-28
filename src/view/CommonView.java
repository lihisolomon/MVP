package view;

import java.io.File;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Command;
import presenter.Presenter;


/**
 * The Class CommonView.
 */
public abstract class CommonView extends Observable implements View,Observer{
	
	protected HashMap<String, Command> commands;
	protected Presenter Presenter;	
	
	/**
	 * Instantiates a new common view.
	 */
	public CommonView() {
		this.commands=new HashMap<>();
		
	}
	
	@Override
	public abstract void start();

	@Override
	public abstract void printOutput(String str);

	@Override
	public abstract void notifyMazeIsReady(String name);


	@Override
	public abstract void displayMaze(Maze3d maze);

	@Override
	public abstract void displayFiles(File[] listOfFiles);

	@Override
	public abstract void displaySolution(Solution<Position> solution);

	@Override
	public abstract void displayCrossSection(int[][] maze2d) ;

}
