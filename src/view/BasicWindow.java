package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public abstract class BasicWindow implements Runnable{

	protected Display display;
	protected Shell shell;
	boolean ownDisplay = false;
	
	public BasicWindow(int width, int height) {
		//display=new Display();
		display = Display.getCurrent();
 		if(display == null) {
 			display = new Display();
 			ownDisplay = true;
 		}
		shell=new Shell(display);
		
		shell.setSize(width, height);	
	}
	
	public abstract void initWidgets();
	
	@Override
	public void run() {
		initWidgets();
		shell.open();
		
		while(!shell.isDisposed()){ // window isn't closed
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}
		if(ownDisplay)
			display.dispose();
	}
	
	/**
	 * Display a message box to the user 
	 * @param iconNum
	 * @param title
	 * @param message
	 */
	public void displayMessage(int iconNum,String title,String message)
	{
		display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				MessageBox messageBox=new MessageBox(shell,iconNum);
				messageBox.setText(title);
				messageBox.setMessage(message);
				messageBox.open();
				
			}
		});
	}
	
	/**
	 * Display info messages
	 * @param title
	 * @param message
	 */
	public void displayInfo(String title,String message)
	{
		displayMessage(SWT.ICON_INFORMATION, title, message);
	}
	
	/**
	 * Displays error messages
	 * @param title
	 * @param message
	 */
	public void displayError(String title,String message)
	{
		displayMessage(SWT.ICON_ERROR, title, message);
	}
	
	/**
	 * Display a Yes & no Question
	 * @param title
	 * @param message
	 * @return
	 */
	public boolean displayQuesion(String title,String message)
	{
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
			messageBox.setMessage(message);
			messageBox.setText(title);
			int	response = messageBox.open();
			if(response==SWT.YES)
				return true;
			else
				return false;
			
			
	}
	/**
	 * Open a file dialog with user input styles and extentions
	 * @param style
	 * @param title
	 * @param filterExtention
	 * @return
	 */
	public String displayFileDilog(int style,String title,String [] filterExtention,String Path)
	{
		FileDialog fd=new FileDialog(shell,style);
		fd.setText(title);
		fd.setFilterPath(Path);
		fd.setFilterExtensions(filterExtention);
		return fd.open();
	}

}
