package presenter;

import java.util.Observable;

/**
 * The Interface Presenter.
 */
public interface Presenter  {
	
	/**
	 * Update
	 * @param o 
	 * @param arg
	 */
	public void update(Observable o, Object arg);
}
