package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;

/**
 * The Class CommonPresenter.
 */
public abstract class CommonPresenter implements Presenter,Observer {

	protected Model model;

	protected View view;
	
	protected CommandsManager commandsManager;
	
	protected HashMap<String, Command> commands;
	
	/**
	 * Instantiates a new common presenter.
	 * @param model -the model
	 * @param view-the view
	 */
	public CommonPresenter(Model model, View view) {
		this.model = model;
		this.view = view;
			
		commandsManager = new CommandsManager(this.model, this.view);
		commands = commandsManager.getCommandsMap();
	}

	public abstract void update(Observable o, Object arg);
	
	
}
