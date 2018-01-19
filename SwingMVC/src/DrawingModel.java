/**
 * Holds the state of the drawing by maintaining a collection of Shape objects
 * and a collection of Viewer objects (the views connected to the model).
 * Provides methods for users to add Shapes and connect additional views. 
 * Updates views automatically when the state of the drawing changes.
 * 
 * @author H Ertman
 */

import java.awt.Color;
import java.util.ArrayList;


public class DrawingModel {
	
	protected ArrayList<AbstractShape> ShapeCollection; //stores a collection of Shape objects
	protected ArrayList<View> ViewerCollection; //stores a collection of Viewer objects
	protected int hLevel; //the current level of recursion for the HShape contained in this model
	protected int fLevel; //the current level of recursion for the FibonacciSquare contained in this model
	protected boolean increase; //a boolean that determines whether increase or decrease is currently selected by the controller
	
	//Create some new colors
	public static final Color DARKRED = new Color(153, 0, 0);
	public static final Color DARKGREEN = new Color(0, 102, 0);
	public static final Color LIGHTBLUE = new Color(51, 153, 255);
	public static final Color VLIGHTBLUE = new Color(51, 204, 255);
	
	/**
	 * Creates a DrawingModel object with an empty Shape Collection and an empty Viewer Collection
	 */
	public DrawingModel(){
		this.ShapeCollection = new ArrayList<AbstractShape>();
		this.ViewerCollection = new ArrayList<View>();
	}
	
	/**
	 * Connects a View to the model; adds it to the ViewerCollection array list
	 * @param view the Viewer object that will be connected to the model
	 */
	public void addView(View view) {
		ViewerCollection.add(view);
		view.connect(this);
	}
	
	/**
	 * Adds a Shape object to the ShapeCollection array list and makes this model the model within the Shape object.
	 * @param shape the Shape object to be added to the collection
	 */
	public void addShape(AbstractShape shape){
		ShapeCollection.add(shape);
		shape.model = this;
	}	

	/**
	 * Notifies all Viewers connected to the model when something is changed
	 * Iterates through all views in the ViewerCollection and calls their update method
	 */
	public void updateViewers() {
		for (int i=0; i<this.ViewerCollection.size(); i++) {
			ViewerCollection.get(i).update(); 
		}
	}
	
	/**
	 * Returns the ShapeCollection, the array list of shapes
	 * @return an array list of Shape objects
	 */
	public ArrayList<AbstractShape> getShapes() {
		return this.ShapeCollection;
	}
	
	/**
	 * Initializes the Shape objects that will belong to this model: one HShape and one FibonacciSquare. Adds those shapes to this model.
	 * @return an array list of Shape objects
	 */
	public void initializeShapes() {
		HShape H1 = new HShape(0, 0, DARKGREEN, 300);
		this.hLevel = 1;
		FibonacciSquare F1 = new FibonacciSquare(0, 0, DARKGREEN, 1, 1);
		this.fLevel = 1;
		this.addShape(H1);
		this.addShape(F1);
	}
	//NOTE: in previous versions (homework 3) shapes were created and added to the model within the MainClass.
	//In this implementation the shapes in the model are NOT within the control of the user. This implementation
	//is designed to work specifically on one configuration of model: an H on the left, and an FSquare on the right.
	//Therefore in this implementation the shapes are initialized by the model itself, removing control of the
	//model's configuration from any other class.
	
	/**
	 * Sets a boolean flag which tells the model whether Increase or Decrease is currently selected in the controller.
	 * If increase, the radio button for Increase is selected. if !increase, the radio button for decrease is selected.
	 * @param flag a boolean flag representing whether Increase or Decrease is selected
	 */
	public void setIncrease(boolean flag) {
		this.increase = flag;
	}

	/**
	 * Gets a boolean flag which tells the model whether Increase or Decrease is currently selected in the controller.
	 * If increase, the radio button for Increase is selected. if !increase, the radio button for decrease is selected.
	 * @return flag a boolean flag representing whether Increase or Decrease is selected
	 */
	public boolean getIncrease() {
		return this.increase;
	}
	
	/**
	 * Increments hLevel and adds one level of recursion to the model's HShape by calling addLevel() within HShape.
	 */
	public void addHLevel() {
		this.ShapeCollection.get(0).addLevel();
		hLevel += 1;
	}

	/**
	 * Decrements hLevel and removes one level of recursion to the model's HShape by calling removeLevel() within HShape.
	 */
	public void removeHLevel() {
		this.ShapeCollection.get(0).removeLevel();
		hLevel -= 1;
	}

	/**
	 * Increments fLevel and adds one level of recursion to the model's FibonacciSquare by calling addLevel() within FibonacciSquare.
	 */
	public void addFLevel() {
		this.ShapeCollection.get(1).addLevel();
		fLevel += 1;
	}

	/**
	 * Decrements fLevel and removes one level of recursion to the model's FibonacciSquare by calling removeLevel() within FibonacciSquare.
	 */
	public void removeFLevel() {
		this.ShapeCollection.get(1).removeLevel();
		fLevel -= 1;
	}
	
	/**
	 * Resets the model to its initial state.
	 */
	public void reset() {
		HShape H = (HShape) this.ShapeCollection.get(0);
		FibonacciSquare F = (FibonacciSquare) this.ShapeCollection.get(1);
		while (H.children > 0) {
			H.removeLevel();
		}
		while (F.children > 0) {
			F.removeLevel();
		}
		this.hLevel = 1;
		this.fLevel = 1;
	}
	
}
