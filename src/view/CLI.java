package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;


/**
 * * CLI Class.
 */
public class CLI extends Observable {
	
	private BufferedReader in;
	private PrintWriter out;	
	
	/**
	 * CTOR.
	 *
	 * @param in -BufferedReader
	 * @param out -PrintWriter
	 */
	public CLI(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;		
	}
	
	/**
	 * Start.
	 */
	public void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					printMenu();
					try {
						String commandLine = in.readLine();
						setChanged();
						notifyObservers(commandLine);
						
						if (commandLine.equals("exit"))
							break;
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}			
		}).start();		
	}
	
	  /**
  	 * print using the out object
  	 * @param str the string we want to print
  	 */
    public void printOutput(String str) {
    	out.println(str);
        out.flush();
    }
    
    /**
     * Print a menu to the viewer.
     */
	public void printMenu(){
    	printOutput("\n\n********************************");
    	printOutput("what do want to do? ");
    	printOutput("		dir <path>");
    	printOutput("		generate_maze <mazeName> <x> <y> <z>");
    	printOutput("		display <name>");
    	printOutput("		display_cross_section <axle> <index> <mazeName>");
    	printOutput("		save_maze <mazeName> <fileName>");
    	printOutput("		load_maze <mazeName> <fileName>");
    	printOutput("		solve <mazeName> <algorithm>");
    	printOutput("		display_solution <mazeName>");
    	printOutput("		load_properties <path>");
    	printOutput("		save_properties <FolderName>");
    	printOutput("		edit_properties <generateMaze> <SolveAlgorthm> <threadsNumber> <viewStyle>");
    	printOutput("		exit");
    	printOutput("********************************\n");
	}
}
