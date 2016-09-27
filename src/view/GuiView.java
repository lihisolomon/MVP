package view;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class GuiView extends CommonView{
	protected Maze3d myMaze;
	protected String mazeName;
	protected MazeWindow mazeWindow;
	protected Solution<Position> solution;
	protected Timer timer;
	protected TimerTask timerTask ;
	
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
				notifyObservers("solve "+ mazeName +"  ");
				setChanged();
				notifyObservers("display_solution "+ mazeName);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		mazeWindow.hintSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(mazeWindow.getMazeDisplay().getCharacterPosition().getX()>mazeWindow.getMazeDisplay().getEnd().getX())
					mazeWindow.displayInfo("Hint!!!", "Go down a floor (pageDown)");
				else if (mazeWindow.getMazeDisplay().getCharacterPosition().getX()<mazeWindow.getMazeDisplay().getEnd().getX())
					mazeWindow.displayInfo("Hint!!!", "Go up a floor (pageUp)");
				else
					mazeWindow.displayInfo("Hint!!!", "you are on the correct floor");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		mazeWindow.resetSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				boolean response= mazeWindow.displayQuesion("Exit", "Are you sure you want to reset the Game?");
                if (response){
                       mazeWindow.getMazeDisplay().moveToStart();
                }
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		mazeWindow.loadMazeSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String fileName = mazeWindow.displayFileDialog(SWT.OPEN, "Load Maze", new String[] { "*.maz" },"C:\\");
				if(fileName != null) {
					mazeName=fileName;
					setChanged();
					notifyObservers("load_maze "+mazeName+ " "+fileName);
					setChanged();
					notifyObservers("display "+mazeName);
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
		
		mazeWindow.zoomInOutScreen(new MouseWheelListener() {

			@Override
			public void mouseScrolled(MouseEvent g) {
				if((g.stateMask & SWT.CONTROL) == SWT.CONTROL) {
					mazeWindow.performZoom(g.count);
					}
				}
			});
		
		mazeWindow.exitSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				boolean response= mazeWindow.displayQuesion("Exit", "Are you sure you want to exit?");
				if (response){
					setChanged();
					notifyObservers("exit");
					mazeWindow.exit();
				}
				
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
            		case SWT.ARROW_UP:
            			getMazeWindow().getMazeDisplay().moveUp();
            			break;
            		case SWT.ARROW_DOWN:
            			getMazeWindow().getMazeDisplay().moveDown();
            			break;
            		case SWT.PAGE_UP:
            			getMazeWindow().getMazeDisplay().movePageUp();
            			break;
            		case SWT.PAGE_DOWN:
            			getMazeWindow().getMazeDisplay().movePageDown();
            			break;
            	}
            }
     });

		exitButton();
	}
	
	public void exitButton(){
		mazeWindow.shell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				setChanged();
				notifyObservers("exit");
			}
        });
	}
	
	@Override
	public void printOutput(String str) {}

	@Override
	public void notifyMazeIsReady(String name) {
		mazeWindow.displayInfo("Maze", "the maze was created succesfully");
		
	}

	@Override
	public void displayMaze(Maze3d maze) {
		if (maze== null) 
			mazeWindow.displayError("Error","this maze does not exist");
		else
		{	
			setChanged();
			notifyObservers("display_cross_section x "+ maze.getStartPosition().getX() + " "+mazeName);
			mazeWindow.getMazeDisplay().setMaze(maze);
			notifyMazeIsReady(mazeName);
		}	
	}
	
	@Override
	public void start() {
		mazeWindow.run();
	}

	@Override
	public void displayFiles(File[] listOfFiles) {}

	@Override
	public void displaySolution(Solution<Position> solution) {
		timer=new Timer();
		
		if (solution==null)
			mazeWindow.displayError("Error","TRY TO SOLVE IT YOURSELF, IT'S HARD!!!!");
		else{
			ArrayList<Position> s=solution.stackToArrayList();
			Position [] positions = new Position[s.size()];

			for (int i=0;i<s.size();i++)
				positions[i] = s.get(i);
				
			timerTask  = new TimerTask() {

				@Override
				public void run() {
					for (Position p:positions){
						mazeWindow.display.syncExec(new Runnable() {
							@Override
							public void run() {
								mazeWindow.getMazeDisplay().move(p);
								if(p.equals(mazeWindow.getMazeDisplay().getEnd()))
								{
									timer.cancel();
									timerTask.cancel();
								}
							}
						});
					
					}
				}
				
			};
			timer.scheduleAtFixedRate(timerTask, 0,300);
			
			}
		
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
