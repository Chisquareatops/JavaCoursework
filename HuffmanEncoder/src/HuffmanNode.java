
public class HuffmanNode implements Comparable<HuffmanNode> {
	
		protected int character; // a given character (represented by its ASCII value)
		protected int count; // the number of times that character appears in a file
		
		protected HuffmanNode leftChild; // the left child node of this node, if this node has children
		protected HuffmanNode rightChild; // the right child node of this node, if this node has children
     
		/**
		 * Constructs a new HuffmanNode object using the ASCII encoding of a character
		 * and the number of times that character appears in a given file
		 * @param character an int representing the ASCII code of a character
		 * @param count an int specifying the number of times that character appears in a file
		 **/
		public HuffmanNode(int character, int count) {
			this.character = character;
			this.count = count;
		}
      
		/**
		 * Constructs a new HuffmanNode object using the ASCII encoding of a character, 
		 * the number of times that character appears in a given file, and two child 
		 * nodes to which the created node will be connected.
		 * @param character an int representing the ASCII code of a character
		 * @param count an int specifying the number of times that character appears in a file
		 * @param leftChild a HuffmanNode object specifying the left child node of this node
		 * @param rightChild a HuffmanNode object specifying the right child node of this node
		 **/
		public HuffmanNode(int character, int count, HuffmanNode leftChild, HuffmanNode rightChild){
			this.character = character;
			this.count = count;
			this.leftChild = leftChild;
			this.rightChild = rightChild;
		}
      
		/**
		 * Fetches the count field of this node (the number of times a given character is used in a file).
		 * @return this.count the count associated with this node's character
		 **/
		public int getCount() {
			return this.count;
		}
      
		/**
		 * Fetches the character field of this node (the character for which this node will store the count).
		 * @return this.character an ASCII representation of a character
		 **/
		public int getCharacter() {
			return this.character;
		}
      
		/**
		 * Compares this HuffmanNode object to another HuffmanNode object. Attempts to compare this
		 * node to any other type of object will throw an error. HuffmanNodes are compared such that
		 * a higher count is considered greater.
		 * @return an integer representing the relationship bewteen this node and a passed node (-1, 0, or 1)
		 **/
		public int compareTo(HuffmanNode o) throws IllegalArgumentException {
			if (o != null && o.getClass() == this.getClass()) { // if the passed object is not null and is a HuffmanNode object
				if (this.count > o.getCount()) { // if this node has a higher frequency count
					return 1; // it is greater
				}
				else if (this.count < o.getCount()) { // if this node has a lower frequency count
					return -1; // it is less
				}
				else { // if neither of those are true the counts must be the same
					return 0; // in which case the nodes are equal
				}
				//return count - other.count;
			}
			else { // if passed object is null or is not a HuffmanNode object
				throw new IllegalArgumentException(); // throw an exception
			}
		}
      
		/**
		 * Prints the contents of this node in the legible format character xcount. Method
		 * is for diagnostic purposes.
		 * @return a String conveying the fields of this node
		 **/
		// for diagnostic purposes primarily
		public String toString() {
			return character + " x" + count;
		}


	}
