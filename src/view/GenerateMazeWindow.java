package view;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;


/**
 * The Class GenerateMazeWindow.
 */
public class GenerateMazeWindow extends BasicWindow {
	
	
	String name;

	int floors, rows, cols;
	
	/**
	 * Instantiates a new generate maze window.
	 *
	 * @param width 
	 * @param height
	 */
	public GenerateMazeWindow(int width, int height) {
		super(width, height);
	}


	@Override
	public void initWidgets() {
		shell.setText("Generate maze");
				
		shell.setLayout(new GridLayout(2, false));	
		
		Label lblName = new Label(shell, SWT.NONE);
		lblName.setText("Maze name: ");
		
		Text txtName = new Text(shell, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label lblFloors = new Label(shell, SWT.NONE);
		lblFloors.setText("Floors: ");
		
		Text txtFloors = new Text(shell, SWT.BORDER);
		txtFloors.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label lblRows = new Label(shell, SWT.NONE);
		lblRows.setText("Rows: ");
		
		Text txtRows = new Text(shell, SWT.BORDER);
		txtRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label lblCols = new Label(shell, SWT.NONE);
		lblCols.setText("Cols: ");
		
		Text txtCols = new Text(shell, SWT.BORDER);
		txtCols.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
				
		Button btnGenerateMaze = new Button(shell, SWT.PUSH);
		shell.setDefaultButton(btnGenerateMaze);
		btnGenerateMaze.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		btnGenerateMaze.setText("Generate maze");
		
		btnGenerateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try{
					name=txtName.getText();
					floors=Integer.parseInt(txtFloors.getText());
					rows = Integer.parseInt(txtRows.getText());
					cols = Integer.parseInt(txtCols.getText());
					//displayInfo("Info","maze name : " + name + "\nfloors: "+floors +"\nrows: " + rows + "\ncols: " + cols);
					shell.dispose();
				}
				catch (Exception e) {
					displayError("Error", "Please enter all parameters");
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});	
		
		shell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				setChanged();
				notifyObservers("exit");
			}
        });
	}

	/**
	 * Gets the name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the floors
	 * @return the floors
	 */
	public int getFloors() {
		return floors;
	}

	/**
	 * Gets the rows
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Gets the cols
	 * @return the cols
	 */
	public int getCols() {
		return cols;
	}
	
}
