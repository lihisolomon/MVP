package presenter;

import java.util.Observable;

import model.Model;
import view.View;

/**
 * The Class MyPresenter.
 */
public class MyPresenter extends CommonPresenter {

	/**
	 * CTOR
	 * @param model the model
	 * @param view the view
	 */
	public MyPresenter(Model model, View view) {
		super(model, view);
	}

	/**
	 * update gets the input from the user and start the command
	 * @param o 
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o==view){
			String commandLine = (String)arg;
		
			String arr[] = commandLine.split(" ");
			String command = arr[0];			
			
			if(!commands.containsKey(command)) {
				view.printOutput("Command doesn't exist");			
			}
			else {
				String[] args = null;
				if (arr.length > 1) {
					String commandArgs = commandLine.substring(commandLine.indexOf(" ") + 1);
					args = commandArgs.split(" ");							
				}
				try {
					commands.get(command).doCommand(args);
				}
				catch (Exception e){
					view.printOutput("ERROR:");
					e.printStackTrace();
				}
			}
		}
		else {
			String message = (String)arg;
			view.printOutput(message);
		}
	}
}
