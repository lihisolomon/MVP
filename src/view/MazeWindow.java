package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import algorithms.mazeGenerators.Maze3d;

/**
 * MazeWindow class extends BasicWindow
 */
public class MazeWindow extends BasicWindow{
	protected Button generateMazeButton;
	protected Button solutionButton;
	protected Button hintButton;
	protected Button resetButton;
	
	protected Menu menuBar;
	protected Menu FileMenu;
	protected Menu HelpMenu;
	
    protected MenuItem cascadeFileMenu;
    protected MenuItem cascadeHelpMenu;
    protected MenuItem actionMenuItem;
    protected MenuItem newGameMenuItem;
    protected MenuItem loadMazeMenuItem;
    protected MenuItem saveMazeMenuItem;
    protected MenuItem editPropertiesMenuItem;
    protected MenuItem importPropertiesMenuItem;
    protected MenuItem exportPropertiesMenuItem;
    protected MenuItem aboutMenuItem;
    protected MenuItem exitMenuItem;
    
    protected Maze2D mazeDisplay;
	protected Maze3d maze;

    /**
     * CTOR
     * @param width of window
     * @param height of window
     */
	public MazeWindow(int width, int height) {
		super(width, height);	
	}

	/**
	 * initWidget- create the buttons
	 */
	@Override
	public void initWidgets() {
		initMenu();
		
		GridLayout gridLayout = new GridLayout(2,false);
		shell.setLayout(gridLayout);
		shell.setText("Maze3D Project");
		
		generateMazeButton=(new Button(shell, SWT.PUSH));
		generateMazeButton.setText("Genenrate Maze");
		generateMazeButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,1,1));
		
		mazeDisplay=new Maze2D(shell,SWT.BORDER);
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4));
		
		solutionButton=(new Button(shell, SWT.PUSH));
		solutionButton.setText("Solution");
		solutionButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,1,1));
		
		hintButton=(new Button(shell, SWT.PUSH));
		hintButton.setText("Hint");
		hintButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,1,1));
		
		resetButton=(new Button(shell, SWT.PUSH));
		resetButton.setText("Reset");
		resetButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,1,1));
	}
	
	/**
	 * initMenu- create the menu with items
	 */
    private void initMenu() {
    	menuBar=new Menu(shell, SWT.BAR);	
    	cascadeFileMenu=new MenuItem(menuBar, SWT.CASCADE);
    	cascadeFileMenu.setText("File");

    	FileMenu=new Menu(shell,SWT.DROP_DOWN);
    	cascadeFileMenu.setMenu(FileMenu);

    	newGameMenuItem=new MenuItem(FileMenu, SWT.PUSH);
    	newGameMenuItem.setText("New Game");

    	loadMazeMenuItem=new MenuItem(FileMenu, SWT.PUSH);
    	loadMazeMenuItem.setText("Load Maze");

    	saveMazeMenuItem=new MenuItem(FileMenu, SWT.PUSH);
    	saveMazeMenuItem.setText("Save Maze");

    	editPropertiesMenuItem=new MenuItem(FileMenu, SWT.PUSH);
    	editPropertiesMenuItem.setText("edit properties");

    	importPropertiesMenuItem=new MenuItem(FileMenu, SWT.PUSH);
    	importPropertiesMenuItem.setText("Import properties");

    	exportPropertiesMenuItem=new MenuItem(FileMenu, SWT.PUSH);
    	exportPropertiesMenuItem.setText("Export properties");

    	exitMenuItem=new MenuItem(FileMenu, SWT.PUSH);
    	exitMenuItem.setText("EXIT");
    	
    	cascadeHelpMenu =new MenuItem(menuBar, SWT.CASCADE);
    	cascadeHelpMenu.setText("Help");

    	HelpMenu=new Menu(shell,SWT.DROP_DOWN);
    	cascadeHelpMenu.setMenu(HelpMenu);

    	aboutMenuItem=new MenuItem(HelpMenu, SWT.PUSH);
    	aboutMenuItem.setText("about");

    	shell.setMenuBar(menuBar);
    }
    
  public void generateKeyListener(KeyAdapter listener)
  {
	  mazeDisplay.addKeyListener(listener);
  }
    
    
    /**
     * generateMazeSelectionListener- listener to generate maze
     * @param listener
     */
    public void generateMazeSelectionListener(SelectionListener listener){
    	generateMazeButton.addSelectionListener(listener);
    	newGameMenuItem.addSelectionListener(listener);
		mazeDisplay.setMaze(maze);
	}
	
    /**
     * solutionSelectionListener- listener for solving the maze
     * @param listener
     */
	public void solutionSelectionListener(SelectionListener listener){
		solutionButton.addSelectionListener(listener);
	}
	
	/**
     * hintSelectionListener- listener for get hint
     * @param listener
     */
	public void hintSelectionListener(SelectionListener listener){
		hintButton.addSelectionListener(listener);
	}
	
	/**
     * resetSelectionListener- listener for reset the game
     * @param listener
     */
	public void resetSelectionListener(SelectionListener listener){
		resetButton.addSelectionListener(listener);
	}
	
	/**
     * loadMazeSelectionListener- listener for load a maze
     * @param listener
     */
	public void loadMazeSelectionListener(SelectionListener listener){
		loadMazeMenuItem.addSelectionListener(listener);
	}
	
	/**
     * saveMazeSelectionListener- listener to save the maze
     * @param listener
     */
	public void saveMazeSelectionListener(SelectionListener listener){
		saveMazeMenuItem.addSelectionListener(listener);
	}
	
	/**
     * editPropertiesSelectionListener- listener for the edit properties
     * @param listener
     */
	public void editPropertiesSelectionListener(SelectionListener listener){
		editPropertiesMenuItem.addSelectionListener(listener);
	}

	/**
     * importPropertiesSelectionListener- listener for the import the properties
     * @param listener
     */
	public void importPropertiesSelectionListener(SelectionListener listener){
		importPropertiesMenuItem.addSelectionListener(listener);
	}
	
	/**
     * exportPropertiesSelectionListener- listener for the export the properties
     * @param listener
     */
	public void exportPropertiesSelectionListener(SelectionListener listener){
		exportPropertiesMenuItem.addSelectionListener(listener);
	}
	
	/**
     * exportPropertiesSelectionListener- listener for the export the properties
     * @param listener
     */
	public void aboutSelectionListener(SelectionListener listener){
		aboutMenuItem.addSelectionListener(listener);
	}
	
	/**
     * exitSelectionListener- listener to exit
     * @param listener
     */
	public void exitSelectionListener(SelectionListener listener){
		exitMenuItem.addSelectionListener(listener);
	};
	

	/**
	 * close the shell
	 */
	public void exit(){
		shell.dispose();
	}

	public Maze2D getMazeDisplay() {
		return mazeDisplay;
	}

	public void setMazeDisplay(Maze2D mazeD) {
		this.mazeDisplay = mazeD;
	}

	public Maze3d getMaze() {
		return maze;
	}

	public void setMaze(Maze3d maze) {
		this.maze = maze;
	}
	
	
}
