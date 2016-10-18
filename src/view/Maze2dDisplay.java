package view;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


/**
 * The Class Maze2dDisplay.
 */
public class Maze2dDisplay extends MazeDisplay{
	
	Image startIm; 
	Image finishIm;
	Image characterIm;
	Image wallIm;
	Image winIm;

	int[][] mazeData;
	Position start;
	Position end;
	Thread t;
	
	/**
	 * Instantiates a new 2dmaze display
	 * @param parent -Composite
	 * @param style of the display
	 */
	public Maze2dDisplay(Composite parent, int style) {
		super(parent,style);
		
		startIm = new Image(getDisplay(), "./resources/start.jpeg"); //get image of start position
		finishIm = new Image(getDisplay(), "./resources/finish.jpeg"); //get image of goal position
		characterIm = new Image(getDisplay(), "./resources/towelie.jpeg"); //get image of character
		wallIm = new Image(getDisplay(), "./resources/wall.jpeg"); //get image of the walls
		winIm = new Image(getDisplay(), "./resources/win.jpeg"); //get image of winning
		
		addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_YELLOW));
				
				if(mazeData!=null){
					int width=getSize().x;
					int height=getSize().y;

				   int w=width/mazeData[0].length;
				   int h=height/mazeData.length;

				   for(int i=0;i<mazeData.length;i++){
                       for(int j=0;j<mazeData[i].length;j++){
                           int x=j*w;
                           int y=i*h;
                           if(mazeData[i][j]!=0)
                               e.gc.drawImage(wallIm, 0, 0, wallIm.getBounds().width, wallIm.getBounds().height, x, y, w, h);
                       }
                    }
				   
                    setStart(maze.getStartPosition());
                    if(characterPosition.getX() == start.getX())
                    	 e.gc.drawImage(startIm, 0, 0, startIm.getBounds().width,startIm.getBounds().height,start.getZ()*w,start.getY()*h,w,h);
                    
                    setEnd(maze.getGoalPosition());
                    if(characterPosition.getX() == end.getX())
                    	 e.gc.drawImage(finishIm, 0, 0, finishIm.getBounds().width,finishIm.getBounds().height,end.getZ()*w,end.getY()*h,w,h);
                   
                   printCharacter(e, characterPosition);
                   if(characterPosition.equals(end)) {
                    	e.gc.drawImage(winIm, 0, 0, winIm.getBounds().width,winIm.getBounds().height,0,0,width,height);
                    }  
                    
                    
                 }
			}
		});
	}

	/**
	 * set character position
	 * @param position- character position 
	 */
	public void setCharacterPosition(Position position) {
		characterPosition = position;
	}
	
    /**
     *  move the character to position
     * @param p- the position to move
     */
	@Override
	public void move(Position p) {
		characterPosition=new Position(p);
		redraw();
				
	}
	
	
	/**
	 * move the character to the start position
	 */
	 @Override
     public void moveToStart() {
		 move(start);
		 setMazeData(maze.getMaze()[characterPosition.getX()]);
     }
	
	/**
	 * move the character left
	 */
	@Override
    public void moveLeft() {
		if(characterPosition.getZ() > 0) {
			if(maze.getCell(new Position(characterPosition.getX(), characterPosition.getY(), characterPosition.getZ()-1))==0) {
				characterPosition.setZ(characterPosition.getZ()-1);
               	redraw();                     
			}
		}
    }

	/**
	 * move the character right
	 */
    @Override
    public void moveRight() {
    	if(characterPosition.getZ() < mazeData[0].length-1){
    		if(maze.getCell(new Position(characterPosition.getX(), characterPosition.getY(), characterPosition.getZ()+1))==0) {
    			characterPosition.setZ(characterPosition.getZ() + 1);
    			redraw();                     
    		}
    	} 
    }

    /**
     * move the character up
     */
    @Override
    public void moveUp() {
    	if(characterPosition.getY() > 0) {
    		if(maze.getCell(new Position(characterPosition.getX(), characterPosition.getY()-1, characterPosition.getZ()))==0) {
    			characterPosition.setY(characterPosition.getY() - 1);
                redraw();                     
            }
        }
    }

    /** 
     * move the character down
     */
    @Override
    public void moveDown() {
    	if(characterPosition.getY() < mazeData.length -1) {
    		if(maze.getCell(new Position(characterPosition.getX(), characterPosition.getY()+1, characterPosition.getZ()))==0) {
    			characterPosition.setY(characterPosition.getY() + 1);
                redraw();                     
            }
        }
    }
   
    /**
     * move the character floor down
     */
    @Override
    public void movePageDown() {
    	if(characterPosition.getX() > 1) {
    		if(maze.getCell(new Position(characterPosition.getX()-2, characterPosition.getY(), characterPosition.getZ()))==0) {
    			characterPosition.setX(characterPosition.getX() - 2);
    			setMazeData(maze.getMaze()[characterPosition.getX()]);
                redraw();
    		}
    	}
    }

    /**
     *  move the character floor up
     */
    @Override
    public void movePageUp() {
    	if(characterPosition.getX() < getMaze().getMaze().length - 2) {
    		if(maze.getCell(new Position(characterPosition.getX()+2, characterPosition.getY(), characterPosition.getZ()))==0) {
    			characterPosition.setX(characterPosition.getX()+2);
    			setMazeData(maze.getMaze()[characterPosition.getX()]);
                redraw();                     
    		}
    	}    
    }

	/**
	 * set the 2dmaze
	 * @param mazeData the new maze data
	 */
	public void setMazeData(int[][] mazeData) {
		this.mazeData = mazeData;
		try
		{
			getDisplay().syncExec(new Runnable() {					

				@Override
				public void run() {	
					redraw();
				}
			});					
		}
		catch(Exception ex)
		{
			System.out.println(ex.toString());
		}
	}

	/**
	 * getter of the start position
	 * @return start position
	 */
	public Position getStart() {
		return start;
	}

	/**
	 * setter of the start position
	 * @param start position
	 */
	public void setStart(Position start) {
		this.start = start;
	}

	/**
	 * getter of the end position
	 * @return the end position
	 */
	public Position getEnd() {
		return end;
	}

	/**
	 * setter of the end position
	 * @param end position
	 */
	public void setEnd(Position end) {
		this.end = end;
	}
	
	/**
	 * print the character in the displayer
	 * @param e -PaintEvent
	 * @param characterP the character Position
	 */
	public void printCharacter(PaintEvent e,Position characterP)
	{
		int width=getSize().x;
		int height=getSize().y;

		int w=width/mazeData[0].length;
		int h=height/mazeData.length;
		e.gc.drawImage(characterIm, 0, 0, characterIm.getBounds().width,characterIm.getBounds().height,characterP.getZ()*w,characterP.getY()*h,w,h);
	}
	
	/**
	 * Solve the maze 
	 * @param solution- the solution of the maze
	 */
	public void solveMaze(Solution<Position> solution) {
		ArrayList<Position> s=solution.stackToArrayList();
		t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(500); 
					} catch (Exception e) {}
					Display.getDefault().asyncExec(new Runnable() {
						
						@Override
						public void run() {
								if(!characterPosition.equals(end)){
									String result="";
									try{
										result=movefromPtoP(characterPosition,s.get(0));
										switch (result) {
										case "pageup":
											movePageUp();
											break;
										case "pagedown":
											movePageDown();
											break;
										case "up":
											moveUp();
											break;
										case "down":
											moveDown();
											break;
										case "right":
											moveRight();
											break;
										case "left":
											moveLeft();
											break;
										default:
											break;
										}
										if(characterPosition.equals(s.get(0)))
											s.remove(0);
									}catch (Exception e) {
										t.interrupt();
									}
								}
						}
					});
					
				}
				
			}
		});
		t.start();
	}
	
	/**
	 * Move from position to Position
	 * @param f - from Position
	 * @param t - to Position
	 * @return the string to move
	 */
	public String movefromPtoP(Position f,Position t){
		if (f.getX()<t.getX()) 
			return "pageup";
		else if (f.getX()>t.getX())
			return "pagedown";
		else if (f.getY()<t.getY())
			return "down";
		else if (f.getY()>t.getY())
			return "up";
		else if (f.getZ()<t.getZ())
			return "right";
		else if (f.getZ()>t.getZ())
			return "left";
		else 
			return "";
	}

	/**
	 * Gets the thread
	 * @return the thread
	 */
	public Thread getThread() {
		return t;
	}
}
