package view;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;


/**
 * The Class EditPropertiesWindow.
 */
public class EditPropertiesWindow extends BasicWindow {
	

	String generateMaze, solutionAlg, viewStyle;
	Integer numThreads;
	
	/**
	 * Instantiates a new edits the properties window
	 * @param width of the window
	 * @param height of the window
	 */
	public EditPropertiesWindow(int width, int height) {
		super(width, height);
	}

	@Override
	public void initWidgets() {
		shell.setText("Edit Properties");
				
		shell.setLayout(new GridLayout(2, false));	
		
		Label lblGenerateMaze = new Label(shell, SWT.NONE);
		lblGenerateMaze.setText("Generate Type: ");
		
		final Combo gCombo = new Combo(shell,SWT.READ_ONLY);
		gCombo.setBounds(50, 50, 150, 65);
		String gItems[] = {"growing","simple"};
		gCombo.setItems(gItems);
		gCombo.select(0);
		
		Label lblSolutionAlg = new Label(shell, SWT.NONE);
		lblSolutionAlg.setText("Solution Algorithm: ");
		
		final Combo sCombo = new Combo(shell,SWT.READ_ONLY);
		sCombo.setBounds(50, 50, 150, 65);
		String sItems[] = {"BFS", "DFS"};
		sCombo.setItems(sItems);
		sCombo.select(0);
		
		Label lblNumThreads = new Label(shell, SWT.NONE);
		lblNumThreads.setText("Number of Threads: ");
		
		Text txtNumThreads = new Text(shell, SWT.BORDER);
		txtNumThreads.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label lblViewStyle = new Label(shell, SWT.NONE);
		lblViewStyle.setText("View Style: ");
		
		final Combo vCombo = new Combo(shell,SWT.READ_ONLY);
		vCombo.setBounds(50, 50, 150, 65);
		String vItems[] = {"GUI", "CLI"};
		vCombo.setItems(vItems);
		vCombo.select(0);
				
		Button btnGenerateMaze = new Button(shell, SWT.PUSH);
		shell.setDefaultButton(btnGenerateMaze);
		btnGenerateMaze.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		btnGenerateMaze.setText("Edit");
		
		btnGenerateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {				
				generateMaze=gCombo.getText();
				solutionAlg=sCombo.getText();
				numThreads = Integer.parseInt(txtNumThreads.getText());
				viewStyle=vCombo.getText();
				shell.dispose();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});	
	}

	/**
	 * Gets the maze
	 * @return the generated maze
	 */
	public String getGenerateMaze() {
		return generateMaze;
	}

	/**
	 * Gets the solution alg
	 * @return the solution alg
	 */
	public String getSolutionAlg() {
		return solutionAlg;
	}

	/**
	 * Gets the view style
	 * @return the view style
	 */
	public String getViewStyle() {
		return viewStyle;
	}

	/**
	 * Gets the num threads
	 * @return the num threads
	 */
	public Integer getNumThreads() {
		return numThreads;
	}
	
	
}
