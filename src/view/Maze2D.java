package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Position;

public class Maze2D extends MazeDisplay{
	Image startIm; 
	Image finishIm;
	Image characterIm;
	Image wallIm;
	int[][] mazeData;
	
	Position start, end;
	
	public Maze2D(Composite parent, int style) {
		super(parent,style);
		startIm = new Image(getDisplay(), "./resources/start.jpeg"); //get image of start position
		finishIm = new Image(getDisplay(), "./resources/finish.jpeg"); //get image of goal position
		characterIm = new Image(getDisplay(), "./resources/towelie.jpeg"); //get image of character
		wallIm = new Image(getDisplay(), "./resources/wall.jpeg"); //get image of the walls
		
		
		
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
				   setCharacterPosition(maze.getStartPosition());
				   setStart(maze.getStartPosition());
				   setEnd(maze.getGoalPosition());
				   e.gc.drawImage(startIm, 0, 0, startIm.getBounds().width,startIm.getBounds().height, start.getY()*w, start.getZ()*h,w,h);
				   e.gc.drawImage(finishIm, 0, 0, finishIm.getBounds().width,finishIm.getBounds().height,end.getY()*w,end.getZ()*h,w,h);
				}
			}
		});
	}

	
	public void setCharacterPosition(Position position) {
		characterPosition = position;
		//redraw();
}


	@Override
	public void moveUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveRight() {
		int length= getSize().x;
		int w = length/mazeData[0].length;
		if(characterPosition.getY() < mazeData.length - 1) {
			if(mazeData[characterPosition.getY()][characterPosition.getZ() +1] == 0) {
				characterPosition.setY((characterPosition.getY() + 2) * w);
				redraw();
			}
		}
		
	}

	@Override
	public void movePageUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void movePageDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(Position p) {
		// TODO Auto-generated method stub
	}

	public void setMazeData(int[][] mazeData) {
		this.mazeData = mazeData;
		try
		{
			getDisplay().syncExec(new Runnable() {					

				@Override
				public void run() {	
					//setCharacterPosition(maze.getStartPosition());
					redraw();
				}
			});					
		}
		catch(Exception ex)
		{
			System.out.println(ex.toString());
		}
	}


	public Position getStart() {
		return start;
	}


	public void setStart(Position start) {
		this.start = start;
	}


	public Position getEnd() {
		return end;
	}


	public void setEnd(Position end) {
		this.end = end;
	}
	
	public void printCharacter(PaintEvent e,Position characterP)
	{
		int width=getSize().x;
		int height=getSize().y;

	   int w=width/mazeData[0].length;
	   int h=height/mazeData.length;
		e.gc.drawImage(characterIm, 0, 0, finishIm.getBounds().width,finishIm.getBounds().height,characterP.getY()*w,characterP.getZ()*h,w,h);
	}
}
