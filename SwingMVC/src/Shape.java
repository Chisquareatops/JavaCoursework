/**
 * An interface specifying the methods in the AbstractShape class from which all Shape objects inherit.
 * 
 * @author H Ertman
 */


import java.awt.Graphics;

public interface Shape {

	/**
	 * draws the Shape object
	 * @param g
	 */	
	public void drawShape(Graphics g);
	
	/**
	 * removes one level of recursion
	 */	
	void removeLevel();

	/**
	 * adds one level of recursion
	 */	
	void addLevel();

	/**
	 * determines whether a given Shape can add a level of recursion
	 */	
	public boolean canIncrease();
	
}