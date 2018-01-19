/**
 * A viewer class that extends JPanel and implements View. When notified of changes 
 * in the connected DrawingModel, draws all shapes from the model within the panel.
 * 
 * @author H Ertman
 */

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

public class Viewer extends JPanel implements View {

	protected DrawingModel model; // The model to which the Viewer is connected; field is null until Viewer is added to a model's ViewerCollection
	protected JRadioButton increaseButton;
	protected JRadioButton decreaseButton;
	protected JLabel hLevel;
	protected JLabel fLevel;
	protected JButton clear;
	
	/**
	 * Creates a new Viewer object with white background and various buttons
	 */
	public Viewer() {
		ButtonGroup buttons = new ButtonGroup();
		this.increaseButton = new JRadioButton("Increase");
		this.decreaseButton = new JRadioButton("Decrease");
		this.clear = new JButton ("Reset");
		
		buttons.add(this.increaseButton);	
		buttons.add(this.decreaseButton);
		buttons.setSelected(this.increaseButton.getModel(), true);
		
		this.hLevel = new JLabel("H Level is: 1");
		this.add(hLevel);
		
		this.add(this.increaseButton);
		this.add(this.clear);
		this.add(this.decreaseButton);
	
		this.fLevel = new JLabel("F Level is: 1");
		this.add(fLevel);
		
	}
	
	/**
	 * Attaches a MouseListener to appropriate buttons within Viewer
	 * @param ml a MouseListener object (the controller)
	 */
	public void addController(MouseListener ml) {
		super.addMouseListener(ml);
		this.increaseButton.addActionListener((ActionListener) ml);
		this.decreaseButton.addActionListener((ActionListener) ml);
		this.clear.addActionListener((ActionListener) ml);
	}
	
	
	/**
	 * Connects this model to a view
	 */
	public void connect(DrawingModel model) {
		this.model = model;
	}
	
	/**
	 * Updates the view to display all Shapes currently in the model
	 */
	@Override
	public void update() {
		super.repaint(); // eventually paintComponent is called by the OS (per Francois)
	}
	
	/**
	 * Paints the Shapes stored in the model to which this Viewer is connected
	 */
	//@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); //calls the default paintComponent
		
		if (this.model != null) {
			ArrayList<AbstractShape> shapes = this.model.getShapes();
			AbstractShape shapeH = (AbstractShape) shapes.get(0);
			shapeH.setx(this.getWidth()/4);
			shapeH.sety(this.getHeight()/2);
			shapeH.drawShape(g); //calls the drawShape method in each shape
			AbstractShape shapeF = (AbstractShape) shapes.get(1);	
			shapeF.setx((this.getWidth()/4)*3);
			shapeF.sety(this.getHeight()/2);
			shapeF.drawShape(g);
		}
		
	}
	









}