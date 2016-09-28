package presenter;

import java.io.Serializable;

/**
 * Properties class to use implements Serializable
 */
public class Properties implements Serializable{

	private static final long serialVersionUID = 1L;
	private String generateMaze;
	private String solutionAlg;
	private int numThreads;
	private String viewStyle;

	/**
	 * CTOR.
	 */
	public Properties() {
		this.generateMaze=null;
		this.solutionAlg=null;
		this.viewStyle=null;
	}
	
	/**
	 * CTOR.
	 * @param generateMaze the Algorithm we will use to generate maze
	 * @param solutionAlg the Algorithm we will use to solve a maze
	 * @param numThreads the number we will put in the threadPool
	 * @param viewStyle the view style
	 */
	public Properties(String generateMaze, String solutionAlg, int numThreads, String viewStyle){
		this.generateMaze=generateMaze;
		this.solutionAlg=solutionAlg;
		this.numThreads=numThreads;
		this.viewStyle=viewStyle;
	}
	
	/**
	 * copy CTOR
	 * @param pr-properties
	 */
	public Properties(Properties pr) {
		this.generateMaze=pr.generateMaze;
		this.solutionAlg=pr.solutionAlg;
		this.numThreads=pr.numThreads;
		this.viewStyle=pr.viewStyle;
	}
	
	/**
	 * get the generateMaze
	 * @return the generate maze
	 */
	public String getGenerateMaze() {
		return generateMaze;
	}

	/**
	 * setter the generateMaze
	 * @param generateMaze the new generate maze
	 */
	public void setGenerateMaze(String generateMaze) {
		this.generateMaze = generateMaze;
	}

	/**
	 * get the solutionAlg
	 * @return the solution alg
	 */
	public String getSolutionAlg() {
		return solutionAlg;
	}

	/**
	 * setter the solutionAlg
	 * @param solutionAlg the new solution alg
	 */
	public void setSolutionAlg(String solutionAlg) {
		this.solutionAlg = solutionAlg;
	}

	/**
	 * get the number of thread
	 * @return number of thread
	 */
	public int getNumThreads() {
		return numThreads;
	}

	/**
	 * setter the number of thread
	 * @param numThreads the new num threads
	 */
	public void setNumThreads(int numThreads) {
		this.numThreads = numThreads;
	}

	/**
	 * Gets the view style
	 * @return the view style
	 */
	public String getViewStyle() {
		return viewStyle;
	}

	/**
	 * Sets the view style
	 * @param viewStyle the new view style
	 */
	public void setViewStyle(String viewStyle) {
		this.viewStyle = viewStyle;
	}

	
	
}
