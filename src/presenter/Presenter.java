package presenter;

import java.util.Observable;

/**
 * The Interface Presenter.
 */
public interface Presenter  {
	
	/**
	 * Update the presenter
	 * @param o - Observable
	 * @param arg-the command and his argument
	 */
	public void update(Observable o, Object arg);
}
