package view;

import java.io.File;
import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class GuiView extends CommonView{
	protected Maze3d myMaze;
	protected String mazeName;
	protected MazeWindow mazeWindow;
	
	public GuiView() {
		mazeWindow=new MazeWindow(500, 500);
		
		mazeWindow.generateMazeSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				GenerateMazeWindow win = new GenerateMazeWindow(300,210);			
				win.run();
				mazeName=win.getName();
				setChanged();
				notifyObservers("generate_maze " +mazeName + " "+ win.getFloors()+ " " + win.getCols()+ " "+ win.getRows());
				setChanged();
				notifyObservers("display "+mazeName);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		mazeWindow.solutionSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers("solve "+ mazeName +" ");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		mazeWindow.hintSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		mazeWindow.resetSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Position start= myMaze.getStartPosition();
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		mazeWindow.loadMazeSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String fileName = mazeWindow.displayFileDialog(SWT.OPEN, "Load Maze", new String[] { "*.maz" },"C:\\");
				if(fileName != null) {
					setChanged();
					notifyObservers("load_maze "+mazeName+ " "+fileName);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		mazeWindow.saveMazeSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String fileName = mazeWindow.displayFileDialog(SWT.SAVE, "Save maze", new String[] { "*.maz" }, "C:\\");
				if(fileName != null) {
					setChanged();
					notifyObservers("save_maze "+mazeName+" "+fileName);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		mazeWindow.editPropertiesSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				EditPropertiesWindow win = new EditPropertiesWindow(300,210);			
				win.run();
				setChanged();
				notifyObservers("edit_properties " +win.getGenerateMaze()+ " "+win.getSolutionAlg()+ " " +
						win.getNumThreads()+" "+win.getViewStyle());
				mazeWindow.displayInfo("Edit Properties", "the Properties was edited succesfully");			
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		mazeWindow.importPropertiesSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String fileName = mazeWindow.displayFileDialog(SWT.SAVE, "Save properties", new String[] { "*.xml" }, "C:\\");
				if(fileName != null) {
					setChanged();
					notifyObservers("load_properties "+fileName);
					mazeWindow.displayInfo("Load Properties", "The properties was loaded succesfully");
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		mazeWindow.exportPropertiesSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String folderName = mazeWindow.displayDirectoryDialog(SWT.OPEN, "select folder", "c:\\");
				if(folderName != null) {
					setChanged();
					notifyObservers("save_properties "+folderName);
					mazeWindow.displayInfo("Save Properties", "The properties was saved succesfully");
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		mazeWindow.aboutSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				mazeWindow.displayInfo("About Maze3D", "Version: 1.0.0 \nDeveloped by Lihi Solomon And Lior Hagever");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		mazeWindow.exitSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				boolean response= mazeWindow.displayQuesion("Exit", "Are you sure you want to exit?");
				if (response){
					setChanged();
					notifyObservers("exit");
				}
				mazeWindow.exit();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
	
	
	mazeWindow.generateKeyListener(new KeyAdapter() {
		 public void keyPressed(KeyEvent e) {
					switch(e.keyCode) {
					case SWT.ARROW_LEFT:
						getMazeWindow().getMazeDisplay().moveLeft();
						break;
					case SWT.ARROW_RIGHT:
						getMazeWindow().getMazeDisplay().moveRight();
						break;
					/*case SWT.ARROW_UP:
						getMazeWindow().getMazeDisplay().moveBackward();
						break;
					case SWT.ARROW_DOWN:
						getMazeWindow().getMazeDisplay().moveForward();
						break;*/
					case SWT.PAGE_UP:
						getMazeWindow().getMazeDisplay().moveUp();
						break;
					case SWT.PAGE_DOWN:
						getMazeWindow().getMazeDisplay().moveDown();
						break;
					}
				
		 }
	
	 });
	}
	
	@Override
	public void printOutput(String str) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyMazeIsReady(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayMaze(Maze3d maze) {
		if (maze== null) 
			mazeWindow.displayError("Error","this maze does not exist");
		else
		{	
			setChanged();
			notifyObservers("display_cross_section x "+ maze.getStartPosition().getX() + " "+mazeName);
			//notifyObservers("display_cross_section x "+ mazeWindow.getMazeDisplay().getCharacter().getCurr().getX() + " "+mazeName);
			//mazeWindow.getMazeDisplay().setMazeData(maze.getCrossSectionByX(maze.getStartPosition().getX()));
			mazeWindow.getMazeDisplay().setMaze(maze);
			mazeWindow.displayInfo("Maze", "the maze was created succesfully");
		}
		
	}

	
	
	@Override
	public void start() {
		mazeWindow.run();
	}

	@Override
	public void displayFiles(File[] listOfFiles) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySolution(Solution<Position> solution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayCrossSection(int[][] maze2d) {
		this.mazeWindow.getMazeDisplay().setMazeData(maze2d);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == mazeWindow) {
			setChanged();
			notifyObservers(arg);
		}
		
	}

	public MazeWindow getMazeWindow() {
		return mazeWindow;
	}
	
	
}
