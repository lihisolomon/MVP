package presenter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * The Class MainProp.
 */
public class MainProp {

	/**
	 * The main method
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		presenter.Properties pro=new presenter.Properties("growing","BFS",10,"GUI");
		Properties properties=new Properties();
		
		properties.setProperty("GenerateType", pro.getGenerateMaze());
		properties.setProperty("SolutionAlgorthim", pro.getSolutionAlg());
		properties.setProperty("NumberOfThreads", String.valueOf(pro.getNumThreads()));
		properties.setProperty("ViewStyle", pro.getViewStyle());
		
		OutputStream os = new FileOutputStream("properties.xml");
		properties.storeToXML(os, "Properties of Maze3d","UTF-8");
		
	}

}
