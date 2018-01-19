/** 
 * An HShape object the length of whose sides are determined by a user specified int
 * such that the HShape is composed of 9 squares whose sides are all 1/3 the user 
 * specified value.
 * 
 * @author H Ertman
 */

import java.awt.*;

public class HShape extends AbstractShape {
		
	int size; // the total length of the side of one HShape
	int children; //the number of children this shape has	
	
	/**
	 * Creates an H based on position, color, size, and number of children.
	 * @param int x the x coordinate of the center of the H
	 * @param int y the y coordinate of the center of the H
	 * @param Color c the color of the H
	 * @param int size the length of one side of an entire H
	 * @param int children the number of children this H will have
	 */	
	public HShape(int x, int y, Color c, int size, int children) {
		super(x, y, c);
		this.size = size;	
		this.children = children;
		this.childrenShapes = new Shape[7];
		if (this.children >0) {
			childrenShapes[0] = new HShape(this.x-(size/3), this.y-(size/3), this.c, this.size/3, this.children-1);
			childrenShapes[1] = new HShape(this.x-(size/3), this.y, this.c, this.size/3, this.children-1);
			childrenShapes[2] = new HShape(this.x-(size/3), this.y+(size/3), this.c, this.size/3, this.children-1);
			childrenShapes[3] = new HShape(this.x, this.y, this.c, this.size/3, this.children-1);
			childrenShapes[4] = new HShape(this.x+(size/3), this.y-(size/3), this.c, this.size/3, this.children-1);
			childrenShapes[5] = new HShape(this.x+(size/3), this.y, this.c, this.size/3, this.children-1);
			childrenShapes[6] = new HShape(this.x+(size/3), this.y+(size/3), this.c, this.size/3, this.children-1);
		}
		
	}

	/**
	 * Creates an H based on position, color, and size.
	 * @param int x the x coordinate of the center of the H
	 * @param int y the y coordinate of the center of the H
	 * @param Color c the color of the H
	 * @param int size the length of one side of an entire H
	 */	
	public HShape(int x, int y, Color c, int size) {
		super(x, y, c);
		this.size=size;
		this.children = 0;
		this.childrenShapes = new Shape[7];
	}

	
	/**
	 * Draws an H whose overall height and width are based on the size of an initial H (instantiated in the model to which this shape belongs)
	 * and the number of recursive levels between this H and its top-level parent (the initial H).
	 * H is comprised of 7 filled in squares
	 * @param Graphics g
	 */	
	@Override
	public void drawShape(Graphics g) { //IS THIS CORRECT??????
		g.setColor(this.c);
		int subSize = size/3;
		if (this.children == 0) {
			g.fillRect((x-size/2), y-(size/2), subSize, subSize);
			g.fillRect(x-(size/2), y-(size/6), subSize, subSize);
			g.fillRect(x-(size/2), y+(size/6), subSize, subSize);
			g.fillRect(x-(size/6), y-(size/6), subSize, subSize);
			g.fillRect(x+(size/6), y-(size/2), subSize, subSize);
			g.fillRect(x+(size/6), y-(size/6), subSize, subSize);
			g.fillRect(x+(size/6), y+(size/6), subSize, subSize);
		}
		else {
			for (int i=0; i<childrenShapes.length; i++) {
				childrenShapes[i].drawShape(g);
			}
		}
	}
	
//NOTE: In previous versions (such as my submitted work for assignment 3) the H was drawn using two helper methods for greater efficiency.
//For a single shape the helper methods made the drawing slightly more efficient.
//However the use of multiple methods made the recursive drawing less convenient. Therefore drawHSide() and drawHMid() have been removed.
	
	/**
	 * Fetches the size of the H Shape object
	 * @return size the total length of the side of one HShape
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * Removes one level of recursion by removing the children from the lowest-level child of the H on which this method is called.
	 */	
	@Override
	public void removeLevel() {
		if (this.children >= 1) {
			for (int i =0; i<7; i++) {
				childrenShapes[i].removeLevel();
			}
			this.children = children-1;
		}
	}

	/**
	 * Adds one level of recursion by instantiating the children of the lowest-level child of the H on which this method is called.
	 */	
	public void addLevel() {
		this.children = children+1;
		childrenShapes[0] = new HShape(this.x-(size/3), this.y-(size/3), this.c, this.size/3, this.children-1);
		childrenShapes[1] = new HShape(this.x-(size/3), this.y, this.c, this.size/3, this.children-1);
		childrenShapes[2] = new HShape(this.x-(size/3), this.y+(size/3), this.c, this.size/3, this.children-1);
		childrenShapes[3] = new HShape(this.x, this.y, this.c, this.size/3, this.children-1);
		childrenShapes[4] = new HShape(this.x+(size/3), this.y-(size/3), this.c, this.size/3, this.children-1);
		childrenShapes[5] = new HShape(this.x+(size/3), this.y, this.c, this.size/3, this.children-1);
		childrenShapes[6] = new HShape(this.x+(size/3), this.y+(size/3), this.c, this.size/3, this.children-1);
	}

	/**
	 * Determines whether the H on which this level is called can add another level of recursion by checking whether the children of the
	 * lowest level child will have a side length of 5 or more pixels.
	 * @return canIncrease a boolean value determining whether the H on which this level is called can add another level of recursion
	 */	
	public boolean canIncrease() {
		if (this.children == 0) {
			return this.size/3 >= 5;
		}
		else {
			return childrenShapes[0].canIncrease();
		}
	}

	/**
	 * Provides a String to give the Shape object's class, color, and current level of recursion.
	 * @return myString a String supplying all of the information in the super toString method as well as this H's level of recursion
	 */	
	public String toString(){
		String myString = "";
		if (this.children > 0) {
			for (int i=0; i<this.childrenShapes.length; i++){
				HShape child = (HShape) this.childrenShapes[i];
				myString += child.toString(this.model.hLevel);
			}
		}
		else {
			myString = super.toString() + ", level: " + this.model.hLevel+ "\n";
		}
		return myString;
	}

	/**
	 * Provides a String to give the Shape object's class, color, and current level of recursion.
	 * @param hLevel the current level of the Shape in the model
	 * @return myString a String supplying all of the information in the super toString method as well as this H's level of recursion
	 */	
	public String toString(int hLevel){
		String myString = "";
		if (this.children > 0) {
			for (int i=0; i<this.childrenShapes.length; i++){
				HShape child = (HShape) this.childrenShapes[i];
				myString += child.toString(hLevel);
			}
		}
		else {
			myString = super.toString() + ", level: " + hLevel + "\n";
		}
		return myString;
	}
	

	
}
