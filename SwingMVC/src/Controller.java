/**
 * Controller which takes mouse input from a user and issues the appropriate instructions to a connected model, 
 * which in turn will update its connected Views. Controller is also connected directly to View object for the
 * purpose of accessing fields within the Viewer class.
 * 
 * @author H Ertman
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JRadioButton;
import javax.swing.JOptionPane;

public class Controller extends MouseAdapter implements ComponentListener, ActionListener {
	
	private DrawingModel model;	//the model to which this controller will send instructions
	private Viewer viewer; //the graphical viewer which belongs to the model
	private TextViewer tViewer; //the textual viewer which belongs to the model
	
	public Controller(DrawingModel m, Viewer v, TextViewer tv) {
		this.model = m;
		this.viewer = v;
		this.tViewer = tv;
		
		this.viewer.addController(this); //adds the controller to the viewer
		
		this.viewer.connect(this.model); //connects the viewer to the model
		this.tViewer.connect(this.model); //connects the tViewer to the model
		
		this.model.addView(this.viewer); //adds the viewer to the model's Viewer collection
		this.model.addView(this.tViewer); //adds the tViewer to the model's Viewer collection
		this.model.setIncrease(true); //set increase as the default state for the radio buttons
		
		this.model.initializeShapes(); //creates the initial shapes within the model (not within the control of the user)
		//this.viewer.update();
		this.model.updateViewers(); //updates all Viewers connected to the model
	}
	
    /**
     * Defines actions to be performed when user clicks mouse, dependent on specific mouse action
     * @param e represents a mouse event (a click)
     */
	@Override
	public void mouseClicked(MouseEvent e) {
		int x=e.getX();
		if (x<this.viewer.getWidth()/2) { //if click is in left half of panel
			if (this.model.getIncrease()) { //if Increase is currently selected
				if (this.model.ShapeCollection.get(0).canIncrease()) { //if level can be increased
					this.model.addHLevel();
					this.viewer.hLevel.setText("H Level is: " + this.model.hLevel);
					this.model.updateViewers();
				}
				else {
					JOptionPane.showMessageDialog(null, "Level cannot be increased; H Shapes would not be visible.");
				}
			}
			else { //if Decrease is currently selected
				if (this.model.hLevel>1) { //if levels can be decreased
					this.model.removeHLevel();
					this.viewer.hLevel.setText("H Level is: " + this.model.hLevel);
					this.model.updateViewers();
				}
				else {
					JOptionPane.showMessageDialog(null, "Level cannot be reduced below 1.");
				}
			}
		}
		else { //if click is in right half of panel
			if (this.model.getIncrease()) { //if Increase is currently selected
				FibonacciSquare f = (FibonacciSquare) this.model.ShapeCollection.get(1);
				if (f.canIncrease(this.viewer.getWidth(), this.viewer.getHeight())) { //if levels can be increased
					this.model.addFLevel();	
					this.viewer.fLevel.setText("F Level is: " + this.model.fLevel);
					this.model.updateViewers();
				}
				else {
					JOptionPane.showMessageDialog(null, "Level cannot be increased; F Shapes would leave the screen.");
				}
			}
			else { //if Decrease is currently selected
				if (this.model.fLevel>1) { //if level can be decreased
					this.model.removeFLevel();
					this.viewer.fLevel.setText("F Level is: " + this.model.fLevel);
					this.model.updateViewers();
				}
				else {
					JOptionPane.showMessageDialog(null, "Level cannot be reduced below 1.");
				}
			}
		}		
	}

    /**
     * Defines which button is considered clicked when a mouse event is detected
     * @param e represents a mouse event (a click)
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.viewer.clear) { //if the Reset button was clicked
			this.model.reset();
			this.viewer.fLevel.setText("F Level is: " + this.model.fLevel);
			this.viewer.hLevel.setText("H Level is: " + this.model.hLevel);
			this.model.updateViewers();
		}
		else {
			JRadioButton radio = (JRadioButton)e.getSource();
			if (radio.getText().equals("Increase")) { //if the Increase button was selected
				this.model.setIncrease(true);
			}
			else { //if the Decrease button was selected
				this.model.setIncrease(false);
			}
		}
	}

	
	//REQUIRED METHOD STUBS (due to inheritance)
	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	



	
	
}