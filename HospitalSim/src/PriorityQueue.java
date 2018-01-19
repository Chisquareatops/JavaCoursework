import java.util.*;

public class PriorityQueue <E extends Comparable<E>> {

	protected LinkedList<E> innerList; // a LinkedList storing the values added to this PriorityQueue; can only take Comparable objects
	
	/**
	 * Creates an empty PriorityQueue
	 */
	public PriorityQueue() {
		this.innerList = new LinkedList<E>();
	}
	
	/**
	 * Inserts a passed comparable element into the list such that a greater element will appear earlier in the list
	 */
	public void insert(E o) {
		boolean added = false;
		// will add passed object to an empty list
		if (this.innerList.isEmpty()) { //if this this is empty
			this.innerList.add(o); //the passed item is now the first item	
			added = true;
		}
		// will iterate through non-empty list until appropriate place is found
		else {
			ListIterator<E> iter = this.innerList.listIterator(0);
			int currentIndex = 0;
			while (iter.hasNext()) { //iterate through linked list until end
				E next = iter.next(); //examine the next element
				if (o.compareTo(next) > 0) { //if the passed element o is greater than the next
					this.innerList.add(currentIndex, o); //inserts o in position of the element it is greater than
					added = true;
					break; // stop looking as soon as object is added
				}
				currentIndex ++;
			}
		}
		// will only execute if no items currently in the list are smaller than o
		if (!added) {
			this.innerList.add(this.innerList.size(), o);	
		}
			
	}
	
	/**
	 * Removes and returns the first element in this list
	 * @return E a comparable element
	 */
	public E remove() throws IllegalArgumentException {
		if (this.innerList.getFirst() != null) { //if this list has at least one element
			return this.innerList.remove(); //remove and return the first element
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Returns but does not remove the first element in this list
	 * @return E a comparable element
	 */
	public E front() {
		if (this.innerList.size() > 0) { //if this list has at least one element
			return this.innerList.peekFirst(); //return the first element
		}
		else {
			return null;
		}
	}
	
	/**
	 * Informs the user whether or not this PriorityQueue currently contains any elements
	 * @return boolean true if empty, false if not empty
	 */
	public boolean isEmpty() {
		return this.innerList.isEmpty();
	}
	
	
	/**
	 * Returns the current size of this PriorityQueue, the number of elements it currently contains
	 * @return int the current size
	 */
	public int size() {
		return this.innerList.size();
	}
	
	/**
	 * Returns a list of the items stored in this PriorityQueue in the order in which they are stored
	 * @return List a LinkedList
	 */
	public List testForwardTraversal() {
		return this.innerList;	
	}
	
	/**
	 * Returns a list of the items stored in this PriorityQueue in the reverse of the order in which they are stored
	 * @return patientVisitsReversed a LinkedList
	 */
	public List testReverseTraversal() {
		LinkedList<E> patientVisitsReversed = new LinkedList<E>();
		Iterator<E> iter = this.innerList.descendingIterator();
		while (iter.hasNext()) {
			patientVisitsReversed.add(iter.next());
		}
		return patientVisitsReversed;
	}
	
	/**
	 * Prints the elements of this list in a formatted way by calling the toString of each element.
	 * @return string a String of all of the elements in the list
	 */
	public String toString() {
		String string = "";
		for (int i=0; i<this.innerList.size(); i++) {
			string += this.innerList.get(i).toString() + " \n";
		}
		return string;
	}
	
}