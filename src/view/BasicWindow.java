package view;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;


/**
 * BasicWindow abstract class that extends Observable and implements Runnable.
 */
public abstract class BasicWindow extends Observable implements Runnable {

	protected Display display;
	protected Shell shell;
	boolean ownDisplay = false;
	
	/**
	 * CTOR.
	 *
	 * @param width of the window
	 * @param height of the window
	 */
	public BasicWindow(int width, int height) {

		display = Display.getCurrent();
 		if(display == null) {
 			display = new Display();
 			ownDisplay = true;
 		}
		shell=new Shell(display);
		shell.setSize(width, height);
		initWidgets();
			
	}
	
	/**
	 * initWidgets.
	 */
	public abstract void initWidgets();
	
	/**
	 * run method.
	 */
	@Override
	public void run() {
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
	 * @param iconNum - icon numbers
	 * @param title -the title of the message
	 * @param message -the message
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
	 * @param title- the title of the message
	 * @param message -the message
	 */
	public void displayInfo(String title,String message)
	{
		displayMessage(SWT.ICON_INFORMATION, title, message);
	}
	
	/**
	 * Displays error messages
	 * @param title - the title of the message
	 * @param message -the message
	 */
	public void displayError(String title,String message)
	{
		displayMessage(SWT.ICON_ERROR, title, message);
	}
	
	/**
	 * Display a Yes OR no Question
	 * @param title - the title of the message 
	 * @param message -the message
	 * @return true, if successful
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
	 * @param style of the dialog
	 * @param title of the dialog
	 * @param filterExtention of the dialog
	 * @param Path -default path to show
	 * @return filename that choosen
	 */
	public String displayFileDialog(int style,String title,String [] filterExtention,String Path)
	{
		FileDialog fd=new FileDialog(shell,style);
		fd.setText(title);
		fd.setFilterPath(Path);
		fd.setFilterExtensions(filterExtention);
		return fd.open();
	}
	
	/**
	 * Display directory dialog
	 * @param style of the dialog
	 * @param title of the dialog
	 * @param Path -default path to show
	 * @return the string
	 */
	public String displayDirectoryDialog(int style,String title,String Path){
		DirectoryDialog d=new DirectoryDialog(shell, style);
		d.setFilterPath(Path);
		return d.open();
	}
	

}
