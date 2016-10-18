package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyModel;
import presenter.MyPresenter;
import view.GuiView;
import view.MyView;

/**
 * The Class Run
 */
public class Run {

	/**
	 * The main method
	 * @param args -args from the user
	 */
	public static void main(String[] args) {
		
		/*BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
				
		MyView view = new MyView(in, out);
		MyModel model = new MyModel();
		
		MyPresenter presenter = new MyPresenter(model, view);
		model.addObserver(presenter);
		view.addObserver(presenter);
				
		view.start();*/
		
		/*MazeWindow mz=new MazeWindow(500, 500);
		mz.run();*/
		
		/*GuiView gui=new GuiView();
		MyModel model = new MyModel();
		
		MyPresenter presenter = new MyPresenter(model, gui);
		model.addObserver(presenter);
		gui.addObserver(presenter);
				
		gui.start();*/
		
		MyModel model = new MyModel();
		String viewStyle = model.getViewStyle();
		
		if(viewStyle.equals("CLI")) {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter out = new PrintWriter(System.out);
			MyView view = new MyView(in,out);
			MyPresenter presenter = new MyPresenter(model, view);
			view.addObserver(presenter);
			model.addObserver(presenter);
			view.start();
		}
		else if (viewStyle.equals("GUI")) {
			GuiView gui = new GuiView();
			MyPresenter presenter = new MyPresenter(model, gui);
			gui.addObserver(presenter);
			model.addObserver(presenter);
			gui.start();
		}
	}

}
