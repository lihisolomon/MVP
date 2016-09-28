package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


/**
 * MyView extends CommonView.
 */
public class MyView extends CommonView {
	

	protected CLI cli;
	protected BufferedReader in;
	protected PrintWriter out;

	/**
	 * CTOR.
	 *
	 * @param in the input stream object
	 * @param out the output stream object
	 */
	public MyView(BufferedReader in, PrintWriter out) {
		super();
		this.in = in;
		this.out = out;		
		cli = new CLI(this.in, this.out);
		cli.addObserver(this);
	}
	
	/**
	 * print a message to the user
	 * @param str
	 */
	@Override
	public void printOutput(String str) {
		cli.printOutput(str);
	}

	/**
	 * notify to the client that the maze is ready
	 * @param name
	 */
	@Override
	public void notifyMazeIsReady(String name) {
		printOutput("maze " + name + " is ready");
	}

	/**
	 * Prints the maze
	 * @param maze 
	 */
	@Override
	public void displayMaze(Maze3d maze) {
		if (maze== null) 
		{
			System.out.println("this maze does not exist");
		} 
		else
		{
			printOutput("Start: ");
			maze.getStartPosition().printPosition();
			printOutput("End: ");
			maze.getGoalPosition().printPosition();
			System.out.println(maze);
		}
	}


	@Override
	public void start() {
		cli.start();
	}

	/**
	 * Updates the Observable
	 * @param o
	 * @param arg 
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o == cli) {
			setChanged();
			notifyObservers(arg);
		}
	}
	
	/**
	 * Prints all the files in specific folder
	 * @param listOfFiles the list of files
	 */
	@Override
	public void displayFiles(File[] listOfFiles)
	{			
		if (listOfFiles!=null)
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					printOutput("File " + listOfFiles[i].getName());
				} else if (listOfFiles[i].isDirectory()) {
					printOutput("Directory " + listOfFiles[i].getName());
				}
			}
	}
	
	/**
	 * display the solution to the user
	 * @param solution
	 */
	@Override
	public void displaySolution(Solution<Position> solution) {
		System.out.println("the solution is: ");
		System.out.println(solution);
	}
	
	/**
	 * display Cross Section
	 * @param maze2d 
	 */
	@Override
	public void displayCrossSection(int[][] maze2d){
		System.out.println("{");
		for (int i=0;i<maze2d.length;i++)
		{
			for (int j=0;j<maze2d[0].length;j++)
			{
				System.out.print(maze2d[i][j]+ ",");
			}System.out.println("");
		}System.out.println("}");
	}	
}
