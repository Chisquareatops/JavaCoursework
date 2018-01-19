/**
 * A class which create a DrawingModel Viewers, and a controller.
 * places the Viewer into the frame, connects both Viewer and TextViewer to the model, and feeds everything to the controller.
 * 
 * @author H Ertman
 */

import java.awt.*;

import javax.swing.*;


public class MainClass {
	
	//Create some new colors
	public static final Color DARKRED = new Color(153, 0, 0);
	public static final Color DARKGREEN = new Color(0, 102, 0);
	public static final Color LIGHTBLUE = new Color(51, 153, 255);
	public static final Color VLIGHTBLUE = new Color(51, 204, 255);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
				
		// Create the frame to which a view can be added
		JFrame frame = new JFrame("MVC: Increase HLevel and FLevel");		
		int width = 1200;
		int height= 800;
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true); //not required but recommended per Francois
		
		//Create the Viewer (the graphical View), which is a Panel, and add that panel to the frame
		Viewer northPanel = new Viewer();
		northPanel.setBackground(Color.white);
		northPanel.setPreferredSize(new Dimension(width, height-75));			
		frame.add(northPanel, BorderLayout.NORTH);

		// Create a model to hold the Shape objects and connected views
		DrawingModel model = new DrawingModel();

		//Create the textViewer (the textual View)
		TextViewer textViewer = new TextViewer(); 
		
		//create the controller and feed it the Viewer, the TextViewer, and the model
		Controller controller = new Controller(model, northPanel, textViewer); 		
	}
	
	
}



