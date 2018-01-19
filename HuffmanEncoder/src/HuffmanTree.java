import java.util.*;
import java.io.*;

public class HuffmanTree {
	
	protected HuffmanNode root; // the root of a HuffmanTree object; the HuffmanNode which has only children and no parents
	protected PriorityQueue<HuffmanNode> nodeQueue; // a priority queue of HuffmanNode objects from which a HuffmanTree is built
   
	//PART 1 BEGINS HERE
	
	//INSTRUCTIONS: "Constructs a Huffman tree using the given array of 
	//frequencies where count[i] is the number of occurrences of the 
	//character with ASCII value i."
	/**
	 * Takes an integer array representing the frequency of various characters in a file.
	 * Uses those counts to create HuffmanNode objects storing the character and the number
	 * of times that character appears in that file and places them in a priority queue
	 * (such that the characters with the highest counts are first in the queue).
	 * Uses the priority queue to build a tree such that the characters with high frequencies
	 * are near the root (to be reached quickly during traversal of the tree) and characters
	 * with low frequencies are further down the tree.
	 * @param count an int[] representing the counts of specified characters in a file
	 * @throws IllegalArgumentException if passed a null int[]
	 **/
	public HuffmanTree(int[] count) throws IllegalArgumentException { 
		if (count == null) { // if the passed int array is null
			throw new IllegalArgumentException(); // throw an exception
		}
		else {
			nodeQueue = new PriorityQueue<HuffmanNode>(); // create a priority queue of HuffmanNodes; a node is a character-frequency pair (initially all leaf nodes)
			for (int i = 0; i < count.length; i++) { // for each int in the passed int array
				if (count[i] > 0) { // if the associated letter has a nonzero count (i.e. if it appears in the file)
					nodeQueue.add(new HuffmanNode((char) i, count[i])); // add a node containing the letter and the count to the queue
				}
			}
			nodeQueue.add(new HuffmanNode((char) (count.length), 1)); // at the end of the queue add the specified end of file char (one higher than the highest passed value, or the length of the array itself); frequency is specified to be 1
			while (nodeQueue.size() > 1) { // while there is at least 1 pair of nodes in the queue
				HuffmanNode firstNode = nodeQueue.remove(); // take the first one (smallest)
				HuffmanNode secondNode = nodeQueue.remove(); // take the second one (second smallest)
				HuffmanNode newParent = new HuffmanNode(0, firstNode.getCount() + secondNode.getCount(), firstNode, secondNode); // create a new node with these two nodes as children
				nodeQueue.add(newParent);  // add the new node back into the priority queue				
			}
		}
	}
	
	
	//INSTRUCTIONS: "Writes the current tree to the given output stream in standard format."
	/**
	 * Takes a PrintStream object and outputs the data stored in a HuffmanTree. First creates an ArrayList to
	 * hold the integers in an individual code (the code that will be associated with one ASCII character).
	 * As the tree is traversed, a 0 is added to the code every time the traversal goes to a left node and a
	 * 1 is added every time a right node is traversed. When a leaf (node with no children) is reached the 
	 * ASCII character is printed followed by its associated code (which is essentially a map to its location
	 * in the tree).
	 * @param PrintStream output a PrintStream object used to write to a file
	 **/
	public void write(PrintStream output) {
		HuffmanNode currentNode = nodeQueue.remove(); // take first node in queue
		ArrayList<Integer> code = new ArrayList<Integer>(); // create an array list to hold the integers of the code
		if (currentNode.leftChild == null && currentNode.rightChild == null) { // if the current node has no children (it is the root) BASE CASE
			output.println(currentNode.getCharacter()); // output the int value of the ASCII character
			for (int i = 0; i < code.size(); i++) { // for each int in the encoding of that character
				output.print(code.get(i)); // output it
			}
			output.println(); // go to new line
		}
		else {
			ArrayList<Integer> leftCode = (ArrayList<Integer>) code.clone(); // make a copy of the code to use for left node
			leftCode.add(0); // add a 0 to left nodes
			ArrayList<Integer> rightCode = (ArrayList<Integer>) code.clone(); // made a copy of the clone to use for right node
			rightCode.add(1); // add a 1 to right nodes
			write(currentNode.leftChild, output, leftCode); // recursively continue to call write until root is reached
			write(currentNode.rightChild, output, rightCode); // must call for both children
		}
	}
	
	/**
	 * A helper method for write which allows the program to recursively continue to traverse the tree
	 * by calling write with an extra parameter (a partial code, which can be thought of as the path
	 * so far).
	 * @param currentNode a HuffmanNode object, the node currently reached by the traversal
	 * @param PrintStream output a PrintStream object used to write to a file
	 * @param code an ArrayList<Integer> object representing the path traveled so far
	 **/
	private void write(HuffmanNode currentNode, PrintStream output, ArrayList<Integer> code) {
		if (currentNode.leftChild == null && currentNode.rightChild == null) { // if the current node has no children (it is the root) BASE CASE
			output.println(currentNode.getCharacter()); // output the int value of the ASCII character
			for (int i = 0; i < code.size(); i++) { // for each int in the encoding of that character
				output.print(code.get(i)); // output it
			}
			output.println(); // go to new line
		}
		else {
			ArrayList<Integer> leftCode = (ArrayList<Integer>) code.clone(); // make a copy of the code to use for left node
			leftCode.add(0); // add a 0 to left nodes
			ArrayList<Integer> rightCode = (ArrayList<Integer>) code.clone(); // made a copy of the clone to use for right node
			rightCode.add(1); // add a 1 to right nodes
			write(currentNode.leftChild, output, leftCode); // recursively continue to call write until root is reached
			write(currentNode.rightChild, output, rightCode); // must call for both children
		}
	}
	
	//PART 2 BEGINS HERE
	
	//INSTRUCTIONS: "Constructs a Huffman tree from the Scanner.  Assumes the Scanner 
	//contains a tree description in standard format."
	/**
	 * An alternative constructor which creates a HuffmanTree object based on a passed Scanner.
	 * This constructor is used to create a HuffmanTree based on the coded output of the write
	 * method, which creates strings of 1s and 0s that can be used as paths down a tree to
	 * find leaf nodes representing ASCII characters. This method reads a code file and
	 * uses a helper method to build a tree based on the specified paths.
	 * @param input a Scanner object which must be attached to a code file in 'standard format' (as per assignment)
	 **/
	public HuffmanTree(Scanner input) {
		this.root = new HuffmanNode((char) 0, 0); // create an empty node to start with
		while (input.hasNextLine()) { // until end of file
			int n = Integer.parseInt(input.nextLine()); // suggested by F Lepeintre // the ASCII identifier of the character read in as an int
			String code = input.nextLine(); // suggested by F Lepeintre // the code read in as a string
			this.root = buildHuffmanTree(root, n, code); // root is reassigned until it is the very top node, the root
		}
	}
	
	/**
	 * A helper method for HuffmanTree2(Scanner input) which follows the paths laid out in
	 * a code file to correctly place the nodes in a HuffmanTree. This method first checks
	 * if the next character is 1 or 0, then checks if this is the final digit in the code,
	 * in which case a leaf node is created (with count assigned 0 as per assignment). If not,
	 * it then checks if the appropriate child node already exists, creates the next node if
	 * needed, and then recursively calls itself until a leaf node can be created.
	 * @param n an integer representing the ASCII identifier of the character
	 * @param code a String giving the path (or remaining path) to the leaf node
	 * @param currentNode a HuffmanNode object representing the node that has been reached when this method is called
	 * @return currentNode a HuffmanNode object representing the node that has been reached when this metho returns
	 **/
	private HuffmanNode buildHuffmanTree(HuffmanNode currentNode, int n, String code) { 
		switch (code.charAt(0)) { // check the first character in the code
			case '0': // if it is 0
				if (code.length() <= 1) { // if it is the only character; BASE CASE
					currentNode.leftChild = new HuffmanNode(n, 0); // store the ASCII character in the left child
				} // count above is designated 0 as per assignment; count does not matter
				else if (currentNode.leftChild == null) { // if it is not the last character and there is not already a left child
					currentNode.leftChild = buildHuffmanTree(new HuffmanNode(0, 0), n, code.substring(1, code.length())); // create the left child and continue building tree
				}
				else { // if it is not the last character and the left child already exists
					currentNode.leftChild = buildHuffmanTree(currentNode.leftChild, n, code.substring(1, code.length())); // go to the left child and continue building tree
				}
				
			case '1': // if it is 1
				if (code.length() <= 1) { // if it is the only character; BASE CASE
					currentNode.rightChild = new HuffmanNode(n, 0); // store the ASCII character in the right child
				} // count above is designated 0 as per assignment; count does not matter
				else if (currentNode.rightChild == null) { // if it is not the last character and there is not already a right child
					currentNode.rightChild = buildHuffmanTree(new HuffmanNode(0, 0), n, code.substring(1, code.length())); // create the right child and continue building tree
				}
				else { // if it is not the last character and the right child already exists
					currentNode.rightChild = buildHuffmanTree(currentNode.rightChild, n, code.substring(1, code.length())); // go to the right child and continue building tree
				}
		}
		return currentNode; // return the node
	}

	//INSTRUCTIONS: "Reads bits from the given input stream and writes the corresponding characters to 
	//the output.  Stops reading when it encounters a character with value equal to eof.  This is a 
	//pseudo-eof character, so it should not be written to the output file.  Assumes the input stream 
	//contains a legal encoding of characters for this tree's Huffman code."
	/**
	 * Decodes an encoded file using a code in 'standard format' as per assignment. Uses the codes as
	 * paths along a HuffmanTree to find leaf nodes which are ASCII characters. Once a leaf is found
	 * the method returns to the top of the tree and continues until it finds the end of file character
	 * (which is specified in Decode.java).
	 **/
	public void decode(BitInputStream input, PrintStream output, int eof) { // eof is specified as a final int in Decode
		HuffmanNode currentNode = this.root; // start at the root
		int currentBit; // initialize currentBit 
		while (currentNode.getCharacter() != eof) { // enter while loop until end of file character is reached
			currentBit = input.readBit(); // read input one bit at a time
			if (currentNode.rightChild == null && currentNode.leftChild == null) { // if this is a leaf node
				output.write(currentNode.getCharacter()); // write the character
				currentNode = this.root;   // return to root to search for next leaf
			}
			if (currentBit == 0) { // if current bit is 0
				currentNode = currentNode.leftChild; // move to left child
			} 
			else if (currentBit == 1) { // if current bit is 1
				currentNode = currentNode.rightChild; // move to right child
			}
		}
	}
	
	
	
	
}