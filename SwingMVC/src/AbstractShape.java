/**
 * Abstract shape class from which all Shape objects inherit. 
 * Implements the methods in interface Shape and adds general instance variables for Shape objects.
 * 
 * @author H Ertman
 */

import java.awt.*;

public abstract class AbstractShape implements Shape {

	protected int x; // x coordinate of the center point of the Shape object
	protected int y; // y coordinate of the center point of the Shape object
	protected Color c; // Color of the Shape object
	protected Shape[] childrenShapes; //the array of Shape objects that are children of the current Shape object
	protected DrawingModel model; //the model to which the Shape has been added
	protected int children;

	/**
	 * Creates a Shape object with specified color and location.
	 * @param x the x coordinate of the center point of the Shape object
	 * @param y the y coordinate of the center point of the Shape object
	 * @param c the Color of the Shape object
	 */
	public AbstractShape(int x, int y, Color c) {
		this.x = x;
		this.y = y;
		this.c = c;
	}

	/**
	 * Fetches the x coordinate of the Shape object.
	 * @return x the x coordinate of the center point of the Shape object
	 */
	public int getx() {
		return this.x;
	}

	/**
	 * Sets the x coordinate of the Shape object.
	 * @param x the desired x coordinate of the center point of the Shape object
	 */
	public void setx(int x) {
		this.x = x;
	}

	/**
	 * Fetches the y coordinate of the Shape object.
	 * @return y the y coordinate of the center point of the Shape object
	 */
	public int gety() {
		return this.y;
	}

	/**
	 * Sets the y coordinate of the Shape object.
	 * @param y the desired y coordinate of the center point of the Shape object
	 */
	public void sety(int y) {
		this.y = y;
	}

	/**
	 * Fetches the Color of the Shape object.
	 * @return c the Color of the Shape object
	 */
	public Color getColor() {
		return this.c;
	}

	/**
	 * Sets the Color of the Shape object.
	 * @param c the desired Color of the Shape object
	 */
	public void setColor(Color c) {
		this.c = c;
	}
	
	/**
	 * Adds one level of recursion
	 */
	public void addLevel() {
	}
	
	/**
	 * A boolean specifying whether the given Shape object can add a level of recursion
	 * @return boolean
	 */
	public boolean canIncrease(){
		return false;
		
	}
	
	/**
	 * Provides a String to give the Shape object's position and color.
	 * @return String a String supplying the Shape objects position and color
	 */	
	public String toString() {
		return(this.getClass() + " centered at: (" + this.x + "," + this.y + "), color: " + this.getColor());
	}

}