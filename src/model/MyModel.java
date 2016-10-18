package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.BFS;
import algorithms.search.DFS;
import algorithms.search.Maze3dSearchable;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

/**
 * MyModel class extends Observable implements Model.
 */
public class MyModel extends CommonModel {
	
	protected String generateType;
	protected String solveAlg;
	protected Integer threadNum;
	protected String viewStyle;
	
	/**
	 * CTOR
	 */
	public MyModel() {
		super();
		//load mazes & solutions
		loadSolutions();
		//load from properties file
		loadProperties("./resources/properties.xml");
	}		
	
	/**
	 * generate the maze
	 * @param name -maze name
	 * @param floors -number of floors
	 * @param rows -number of rows
	 * @param cols -number of columns
	 */
	@Override
	public void generateMaze(String name,int floors, int rows, int cols) {
		Future<Maze3d> future=executor.submit(new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				Maze3d maze;
				Boolean mazeExist=false;
				if (generateType.toUpperCase().equals("GROWING")){
					maze = new GrowingTreeGenerator().generate(floors,rows, cols);
				}
				else {//it is a simple
					maze = new SimpleMaze3dGenerator().generate(floors,rows, cols);
				}
				for(Entry<String, Maze3d> current:mazes.entrySet())
				{
					if(current.getValue().equals(maze)){
						mazeExist=true;
						setChanged();
						notifyObservers("The maze name already exist with the name: "+current.getKey() );
					}
				}
				if (!mazeExist)
				{
					if(mazes.containsKey(name))	
					{
						setChanged();
						notifyObservers("the maze name is already exist");
					}
					else{
						mazes.put(name, maze);
						setChanged();
						notifyObservers("maze is ready " + name);						
					}
						
				}
				return maze;
			}
		});
		
		while(!future.isDone())
		{
			
		}
		setChanged();
		try {
			if(future.get()!=null)
				notifyObservers("done");
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * getter of maze
	 * @param name- maze name
	 * @return the maze
	 */
	@Override
	public Maze3d getMaze(String name) {
		return mazes.get(name);
	}

	/**
	 * return the list of the files
	 * @param path -from where get the list of files
	 * @return the file[]
	 */
	@Override
	public File[] listFiles(String path) {
		if (path!=null){
			File f = new File(path);
			if (f.exists() && f.isDirectory()) {
				return new File(path).listFiles();
			}
			else{
				setChanged();
				notifyObservers("ERROR: directory :" + path +" dosent exist");
				return null;
			}
		}
		else{			
			setChanged();
			notifyObservers("ERROR: the path is empty");
			return null;
		}
	}
	
	/**
	 * save the maze into a file
	 * @param mazeName -maze name
	 * @param fileName -the file name to save the maze
	 */
	@Override
	public void saveMaze(String mazeName,String fileName) {
		if (mazeName!=null && fileName!=null){
			if(mazes.containsKey(mazeName))
			{
				try {
				OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileName));
				byte[] arr=mazes.get(mazeName).toByteArray();
				int counter=arr.length;
				while(counter>=255)
				{
					out.write(255);
					counter-=255;
				}
				out.write(counter);
				out.write(arr);			
				out.flush();
				out.close();
				setChanged();
				notifyObservers("the Maze was saved");
				}
				catch (Exception e) {
					setChanged();
					notifyObservers("Error on saving the maze");
				}
			}
			else{
				setChanged();
				notifyObservers("Error: maze doesn't exist");
			}
		}
		else{
			setChanged();
			notifyObservers("Error: the maze name or file name is empty");
		}
	}
	
	/**
	 * load the maze from a file
	 * @param mazeName - maze name
	 * @param fileName- the file name to load the maze
	 */
	@Override
	public void loadMaze(String mazeName,String fileName) {
		if (mazeName!=null && fileName!=null){
			if(!mazes.containsKey(mazeName))
			{
				try {
					InputStream in=new MyDecompressorInputStream(new FileInputStream(fileName));
					int size=in.read();
					int sum=0;
					while(size==255)
					{	sum+=size;
						size=in.read();
					}
					sum+=size;
					byte b[]=new byte[sum];
					try{
						in.read(b);
						Maze3d loaded=new Maze3d(b);
						mazes.put(mazeName, loaded);
					}
					catch (IOException e) {
						e.printStackTrace();
					}
					finally {
						try {
							in.close();
						} 
						catch (IOException e) {
							setChanged();
							notifyObservers("Error to close file");
						}
					}
					setChanged();
					notifyObservers("Maze was loaded succesfully");
				}
				catch (Exception e) {
					setChanged();
					notifyObservers("Error in loading file");
				}
			}
			else {
				setChanged();
				notifyObservers("ERROR: maze name is incorrect ");
			}
		}
		else {
			setChanged();
			notifyObservers("Error: the maze name or file name is empty");
		}
	}
	
	/**
	 * solve the maze
	 * @param mazeName- maze name
	 * @param alg -the algorithm
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void solveMaze(String mazeName,String alg){
		Future<Solution> future=executor.submit(new Callable<Solution>() {

			@Override
			public Solution call() throws Exception {
				
				if (mazeName!=null){
					if (solutions.containsKey(mazeName)){
						setChanged();
						notifyObservers("the solution is ready");
						return solutions.get(mazeName);
					}
					else if (mazes.containsKey(mazeName)){
						Maze3d maze=getMaze(mazeName);
						Solution<Position> solution = new Solution<>();
						Maze3dSearchable mazeAdapter = new Maze3dSearchable(maze);
						if (alg!=null){
							if(alg.toUpperCase().equals("BFS")) {
								BFS<Position> searcher = new BFS<>();
								solution = searcher.search(mazeAdapter);
								solutions.put(mazeName, solution);
								notifyObservers("The Solution is ready");
							}
							else {
								DFS<Position> searcher = new DFS<>();
								solution = searcher.search(mazeAdapter);
								solutions.put(mazeName, solution);
								notifyObservers("The Solution is ready");
								
							}
						}
						else {
							if (solveAlg.toUpperCase().equals("BFS")){
							BFS<Position> searcher = new BFS<>();
							solution = searcher.search(mazeAdapter);
							solutions.put(mazeName, solution);
							notifyObservers("The Solution is ready");
							}
							else {
								DFS<Position> searcher = new DFS<>();
								solution = searcher.search(mazeAdapter);
								solutions.put(mazeName, solution);
								notifyObservers("The Solution is ready");
								
							}
						}
						return solution;
					}
					else{
						notifyObservers("ERROR: maze doesn't exist");
						return null;
					}
				}
				else{
					notifyObservers("Error: the maze name or algorithm is empty");
					return null;
				}
				
			}			
		});
		
		while(!future.isDone())
		{
			
		}
		setChanged();
		try {
			if(future.get()!=null)
				notifyObservers("done");
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * get the solution of the maze
	 * @param mazeName -the maze name
	 * @return the solution
	 */
	public Solution<Position> getSolution(String mazeName){
		if ( mazeName!=null )
		{
			if (mazes.containsKey(mazeName)){
					Solution<Position> sol= solutions.get(mazeName);
					if(sol!=null)
						return sol;
					else{
						setChanged();
						notifyObservers("ERROR: there is no solution yet");
						return null;
					}
				}
			else{
				setChanged();
				notifyObservers("ERROR: the maze doesn't exits");
				return null;
			}
		}
		else{
			setChanged();
			notifyObservers("Error: the maze name is empty");
			return null;
		}
	}
	
	/**
	 * get Cross Section
	 * axle index of the maze 
	 * @param axis- the axis
	 * @param floors-the floor
	 * @param mazeName -maze name
	 * @return the cross section
	 */
	public int[][] getCrossSection(String axis,Integer floors,String mazeName){
		if (axis!=null && floors!=null && mazeName!=null){
			Maze3d maze=this.mazes.get(mazeName);
			if (maze!=null)
				if(axis.toUpperCase().equals("X"))
					return maze.getCrossSectionByX(floors);
				else if(axis.toUpperCase().equals("Y"))
					return maze.getCrossSectionByY(floors);
				else if(axis.toUpperCase().equals("Z"))
					return maze.getCrossSectionByZ(floors);
				else{
					setChanged();
					notifyObservers("ERROR: the axle dosen't exist");
					return null;
				}
			else{
				setChanged();
				notifyObservers("ERROR: the maze is not exist");
				return null;
			}
		}
		else{
			setChanged();
			notifyObservers("ERROR: the axis of the floor or the maze name is empty");
			return null;
		}
		
	}
	
	/**
	 * Saves the maze
	 */
	@Override
	public void saveCache()
	{
		ObjectOutputStream oos = null;
		try {
		    oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("./resources/solutions.zip")));
			oos.writeObject(mazes);
			oos.writeObject(solutions);			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Loads the maze
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void loadSolutions() {
		File file = new File("./resources/solutions.zip");
		if (!file.exists() | !file.canRead())
			return;
		
		ObjectInputStream ois = null;
		
		try {
			ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));
			mazes = (Map<String, Maze3d>)ois.readObject();
			solutions = (Map<String, Solution<Position>>)ois.readObject();	
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally{
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	/**
	 * Load the xml file
	 * @param fileName -the file name to load the properties
	 */
	@Override
	public void loadProperties(String fileName)
	{
		try {
			FileInputStream fileInput = new FileInputStream(new File(fileName));
			java.util.Properties properties=new java.util.Properties();
			properties.loadFromXML(fileInput);
			fileInput.close();
			this.generateType = (String)properties.get("GenerateType");
			this.solveAlg = (String)properties.get("SolutionAlgorthim");
			String number=(String)(properties.get("NumberOfThreads"));
			this.threadNum=Integer.valueOf(number);
			this.executor = Executors.newFixedThreadPool(this.threadNum);
			this.viewStyle = (String)(properties.get("ViewStyle"));		
		} catch (Exception e1) {
			this.executor = Executors.newCachedThreadPool();
			this.generateType = "growing";
			this.solveAlg = "BFS";
			this.viewStyle="GUI";
		}
		setChanged();
		notifyObservers("the properties was loaded succesfully");
	}
	
	/**
	 * Save properties to xml
	 * @param folderName -the folder name to save the properties
	 */
	@Override
	public void saveProperties(String folderName)
	{
		presenter.Properties pro=new presenter.Properties(this.generateType,this.solveAlg,this.threadNum,this.viewStyle);
		Properties properties=new Properties();
		properties.setProperty("GenerateType", pro.getGenerateMaze());
		properties.setProperty("SolutionAlgorthim", pro.getSolutionAlg());
		properties.setProperty("NumberOfThreads", String.valueOf(pro.getNumThreads()));
		properties.setProperty("ViewStyle", pro.getViewStyle());
		String path=folderName+"/properties.xml";
		OutputStream os;
		try {
			os = new FileOutputStream(path);
			try {
				properties.storeToXML(os, "Properties of Maze3d","UTF-8");
				setChanged();
				notifyObservers("the properties was saved");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	 /**
 	 * edit properties
 	 * @param generateMaze - how to generate the maze
 	 * @param solutionAlg -how to solve the maze
 	 * @param numThreads -number of threds
 	 * @param viewStyle -the view style of the gui
 	 */
	public void editProperties(String generateMaze, String solutionAlg, Integer numThreads, String viewStyle){

		if (generateMaze!=null)
			this.generateType=generateMaze;
		if (solutionAlg!=null)
			this.solveAlg=solutionAlg;
		if (numThreads!=null){
			this.executor = Executors.newFixedThreadPool(numThreads);
			this.threadNum=numThreads;
		}
		if(viewStyle!=null)
			this.viewStyle=viewStyle;
			
		saveProperties("./resources/");
	
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

	/**
	 * exit command
	 */
	@Override
	public void exit() {
		executor.shutdownNow();
		saveCache();
	}
	
}
