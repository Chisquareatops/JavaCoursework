/** 
 * A FibonacciSquare object the length of whose sides are determined by a fibonacci number
 * such that the first Fibonacci number corresponds to a length of 10 pixels and the values
 * increase proportionally to the Fibonacci Series. A FibonacciSquare also contains 
 * an arc corresponding to one quarter of a circle, whose orientation is specified by the 
 * user such that an integer 1 through 4 indicates the quadrant the arc would lie in if it 
 * were part of a circle centered at the origin.
 * 
 * @author H Ertman
 */

import java.awt.*;

public class FibonacciSquare extends AbstractShape {
	
	int quadrant; // the quadrant of an arc of a circle with center (0,0) corresponding to the arc within the FibbonacciSquare (1, 2, 3, or 4)
	int n; // the length of the side of the square, determined by the nth number in the Fibonacci sequence
	int children; //the number of children this square has

	/**
	 * Creates a FibonacciSquare based on position, color, quadrant, and Fibonacci number.
	 * @param int x the x coordinate of the center of the H
	 * @param int y the y coordinate of the center of the H
	 * @param Color c the color of the H
	 * @param int quadrant the quadrant of an arc of a circle with center (0,0) corresponding to the arc within the FibbonacciSquare (1, 2, 3, or 4)
	 * @param n the Fibonacci number which determines the size of this FibonacciSquare
	 */	
	public FibonacciSquare(int x, int y, Color c, int quadrant, int n) {
		super(x, y, c);
		this.quadrant = quadrant;
		this.n = n;
		this.children = 0;
		this.childrenShapes = new Shape[1];
		
	}
	
	/**
	 * Creates a FibonacciSquare based on position, color, quadrant, Fibonacci number, and number of children.
	 * @param int x the x coordinate of the center of the H
	 * @param int y the y coordinate of the center of the H
	 * @param Color c the color of the H
	 * @param int quadrant the quadrant of an arc of a circle with center (0,0) corresponding to the arc within the FibbonacciSquare (1, 2, 3, or 4)
	 * @param n the Fibonacci number which determines the size of this FibonacciSquare
	 * @param int children the number of children this H will have
	 */	
	public FibonacciSquare(int x, int y, Color c, int quadrant, int n, int children) {
		super(x, y, c);
		this.quadrant = quadrant;
		this.n = n;
		this.children = children;
		this.childrenShapes = new Shape[1];
		int oldOldSide = findSide(this.n-1);
		int oldSide = findSide(this.n);
		int newSide = findSide(this.n+1);
		if (this.children >0) {
			if (this.quadrant == 1) {
				childrenShapes[0] = new FibonacciSquare(this.x-(oldSide/2+newSide/2), this.y+oldOldSide/2, c, 2, n+1, children-1);
			}
			else if (this.quadrant == 2) {
				childrenShapes[0] = new FibonacciSquare(this.x+oldOldSide/2, this.y+(oldSide/2+newSide/2), c, 3, n+1, children-1);
			}
			else if (this.quadrant == 3) {
				childrenShapes[0] = new FibonacciSquare(this.x+(oldSide/2+newSide/2), this.y-(oldOldSide/2), c, 4, n+1, children-1);
			}
			else if (this.quadrant == 4) {
				childrenShapes[0] = new FibonacciSquare(this.x-oldOldSide/2, this.y-(oldSide/2+newSide/2), c, 1, n+1, children-1);
			}
		}
		}

	/**
	 * Fetches the quadrant of the FibonacciSquare object.
	 * @return quadrant the quadrant of the FibonacciSquare object
	 */
	public int getQuadrant() {
		return this.quadrant;
	}
	
	/**
	 * Sets the quadrant of the FibonacciSquare object.
	 * @param quadrant the desired quadrant of the FibonacciSquare object
	 */
	public void setQuadrant(int quadrant) {
		this.quadrant = quadrant;
	}
	
	/**
	 * Fetches the n of the FibonacciSquare object.
	 * @return n the n of the FibonacciSquare object
	 */
	public int getn() {
		return this.n;
	}
	
	/**
	 * Sets the n of the FibonacciSquare object.
	 * @param n the desired n of the FibonacciSquare object
	 */
	public void setn(int n) {
		this.n = n;
	}
	
	/**
	 * Draws a FibbonacciSquare Shape using nth Fibbonacci number to determine the length of the side
	 * and the quadrant to determine the orientation of the included arc.
	 * @param g
	 */
	@Override
	public void drawShape(Graphics g) {
		g.setColor(c);
		int side = findSide(this.n);
		g.drawLine(this.x - (side/2), this.y - (side/2), this.x + (side/2), this.y - (side/2));
		g.drawLine(this.x - (side/2), this.y - (side/2), this.x - (side/2), this.y + (side/2));
		g.drawLine(this.x - (side/2), this.y + (side/2), this.x + (side/2), this.y + (side/2));
		g.drawLine(this.x + (side/2), this.y + (side/2), this.x + (side/2), this.y - (side/2));
		drawArc(g, this.quadrant, side);
		if (this.childrenShapes[0] != null) {
			this.childrenShapes[0].drawShape(g);
		}
	}
	
	/**
	 * A helper function for drawShape which finds the length of the side of the
	 * FibbonacciSquare object taking n as a parameter such that the side length 
	 * is determined by the nth number in the Fibbonacci Sequence (beginning at 1).
	 * @param n the integer specified by the user which will determine the length of the side
	 * @return total the length of the side of the FibonacciSquare, a Fibbonacci number
	 */
	public static int findSide(int n) {
		if (n==0) {
			return 0;
		}
		else if (n == 1 || n == 2) {
			return 10;
		}
		else {
			int first = 10; // in previous versions (homework 3) the 10s were 1s and the side length was equal to the Fibonacci number itself
			int second = 10; //in this version the side length is scaled so that the innermost box in a spiral will be 10px by 10px
			int total = 0;
			for (int i=3; i<=n; i++) {
				total = (first +  second);
				first = second;
				second = total;
			}
			return total;
		}
	
	}

	/**
	 * A helper function for drawShape which inscribes an appropriate arc within the
	 * FibonacciSquare object based on the specified quadrant
	 * @param Graphics g
	 * @param quadrant the quadrant of the Cartesian plane in which the arc would lie if it were part of a circle centered at the origin
	 * @param side the length of the side of the FibonacciSquare
	 */
	public void drawArc(Graphics g, int quadrant, int side) {
		if (quadrant == 1) {
			g.drawArc((this.x-(side+(side/2))), (this.y-(side/2)), side*2, side*2, 0, 90);
		}
		if (quadrant == 2) {
			g.drawArc((this.x-(side/2)), (this.y-(side/2)), side*2, side*2, 90, 90);
		}
		if (quadrant == 3) {
			g.drawArc((this.x-(side/2)), (this.y-(side+(side/2))), side*2, side*2, 180, 90);
		}
		if (quadrant == 4) {
			g.drawArc((this.x-(side+(side/2))), (this.y-(side+(side/2))), side*2, side*2, 270, 90);
		}
	}

	/**
	 * Removes one level of recursion by removing the child from the lowest-level child of the F on which this method is called.
	 */	
	@Override
	public void removeLevel() {
		if (this.children == 1) {
			this.children = children-1;
			this.childrenShapes[0] = null;
		}
		else if (this.children > 1){
			this.childrenShapes[0].removeLevel();
			this.children = children -1;
		}
	}

	/**
	 * Adds one level of recursion by instantiating the child of the lowest-level child of the F on which this method is called.
	 */
	@Override
	public void addLevel() {
		this.children = children+1; //increase to 1 children
		int oldOldSide = findSide(this.n-1);
		int oldSide = findSide(this.n);
		int newSide = findSide(this.n+1);
		if (this.quadrant == 1) {
			childrenShapes[0] = new FibonacciSquare(this.x-(oldSide/2+newSide/2), this.y+oldOldSide/2, c, 2, n+1, children-1);
		}
		else if (this.quadrant == 2) {
			childrenShapes[0] = new FibonacciSquare(this.x+oldOldSide/2, this.y+(oldSide/2+newSide/2), c, 3, n+1, children-1);
		}
		else if (this.quadrant == 3) {
			childrenShapes[0] = new FibonacciSquare(this.x+(oldSide/2+newSide/2), this.y+(oldOldSide/2), c, 4, n+1, children-1);
		}
		else if (this.quadrant == 4) {
			childrenShapes[0] = new FibonacciSquare(this.x-oldOldSide/2, this.y-(oldSide/2+newSide/2), c, 1, n+1, children-1);
		}		
	}

	/**
	 * Determines whether the F on which this level is called can add another level of recursion by checking whether the children of the
	 * lowest level child will be drawn outside any edge of the graphical Viewer.
	 * @return canIncrease a boolean value determining whether the F on which this level is called can add another level of recursion
	 */	
	public boolean canIncrease(int width, int height) {
		int newXRightSide = 0;//the x value of all points along the right edge of the next FSquare that would be created if the current FSquare could add a level
		int newXLeftSide = 0;//the x value of all points along the left edge of the next FSquare that would be created if the current FSquare could add a level
		int newYTop = 0;//the y value of all points along the top edge of the next FSquare that would be created if the current FSquare could add a level
		int newYBottom = 0;//the y value of all points along the bottom edge of the next FSquare that would be created if the current FSquare could add a level
		int oldOldSide = findSide(this.n-1);//the length of the sides of the parent of the current FSquare
		int oldSide = findSide(this.n);//the length of the side of the current FSquare
		int newSide = findSide(this.n+1);//the length of the side of the next FSquare that would be created if the current FSquare could add a level
		if (this.children == 0) {
			if (this.quadrant == 1) {//determines where the edges of the next FSquare would lie if the current FSquare is quadrant 1
				newXRightSide = this.x - ((oldSide/2) + newSide);
				newXLeftSide = this.x - (oldSide/2);
				newYTop = this.y + (oldOldSide/2) - newSide/2;
				newYBottom = this.y + (oldOldSide/2) + newSide/2;
			}
			else if (this.quadrant == 2) {//determines where the edges of the next FSquare would lie if the current FSquare is quadrant 2
				newXRightSide = this.x + (oldOldSide/2) + newSide/2;
				newXLeftSide = this.x + (oldOldSide/2) - newSide/2;
				newYTop = this.y + (oldSide/2);
				newYBottom = this.y + ((oldSide/2) + newSide);
			}
			else if (this.quadrant == 3) {//determines where the edges of the next FSquare would lie if the current FSquare is quadrant 3
				newXRightSide = this.x + ((oldSide/2) + (newSide/2));
				newXLeftSide = this.x + ((oldSide/2) - (newSide/2));
				newYTop = this.y + (oldOldSide/2) - newSide/2;
				newYBottom = this.y + (oldOldSide/2) + newSide/2;
			}
			else if (this.quadrant == 4) {//determines where the edges of the next FSquare would lie if the current FSquare is quadrant 4
				newXRightSide = (this.x - oldOldSide/2) + newSide;
				newXLeftSide = (this.x - oldOldSide/2);
				newYTop = this.y - (oldSide/2);
				newYBottom = this.y - ((oldSide/2) + newSide);
			}
			return (newXRightSide <= width && newXLeftSide >=0 && newYBottom <= height && newYTop >= 0);//checks that the edges are all within the frame in which the graphical Viewer lies
			
		}
		else {//calls itself recursively if the current FSquare is not the bottom-level child
			FibonacciSquare child = (FibonacciSquare) childrenShapes[0];
			return child.canIncrease(width, height);
		}
	}
	
	/**
	 * Provides a String to give the Shape object's class, color, and current level of recursion.
	 * @return myString a String supplying all of the information in the super toString method as well as this F's level of recursion
	 */	
	public String toString(){
		String myString = super.toString() + " level: " + this.model.fLevel + "\n";
		if (this.children > 0) {
			myString += ((FibonacciSquare) this.childrenShapes[0]).toString(this.model.fLevel);
		}
		return myString;
	}
	
	/**
	 * Provides a String to give the Shape object's class, color, and current level of recursion.
	 * @param fLevel the current level of the Shape in the model
	 * @return myString a String supplying all of the information in the super toString method as well as this F's level of recursion
	 */	
	public String toString(int fLevel) {
		String myString = super.toString() + " level: " + fLevel + "\n";
		if (this.children > 0) {
			myString += ((FibonacciSquare) this.childrenShapes[0]).toString(fLevel);
		}
		return myString;
	}



	




}
