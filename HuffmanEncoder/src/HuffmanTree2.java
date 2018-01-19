import java.util.*;
import java.io.*;

public class HuffmanTree2 {
	
	protected HuffmanNode root; // the root of a HuffmanTree object; the HuffmanNode which has only children and no parents
	protected PriorityQueue<HuffmanNode> nodeQueue; // a priority queue of HuffmanNode objects from which a HuffmanTree is built
	
	//INSTRUCTIONS: "Constructs a Huffman tree using the given array of frequencies where count[i] is the number of 
	//occurrences of the character with ASCII value i."
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
	public HuffmanTree2(int[] count){
		if (count == null) { // if the passed int array is null
			throw new IllegalArgumentException(); // throw an exception
		}
		else {
			nodeQueue = new PriorityQueue<HuffmanNode>(count.length + 1); // make a new priority queue
			for (int i = 0; i < count.length; i++) { // for each int in the passed int array
				if (count[i] > 0) { // if the associated letter has a nonzero count (i.e. if it appears in the file)
					nodeQueue.add(new HuffmanNode(i, count[i])); // add a node containing the letter and the count to the queue
				}
			}
			nodeQueue.add(new HuffmanNode((count.length), 1));
			while (nodeQueue.size() > 1) { // while there is at least 1 pair of nodes in the queue
				HuffmanNode firstNode = nodeQueue.remove(); // take the first one (smallest)
				HuffmanNode secondNode = nodeQueue.remove(); // take the second one (second smallest)
				
				//FOUND THE PROBLEM!!!!!!!!! was not adding parent back to queue correctly... why did I even change this method????
				HuffmanNode newParent = new HuffmanNode(-1, firstNode.getCount() + secondNode.getCount(), firstNode, secondNode); // create a new parent node with nonsensical character value
				
				//DIAGNOSTIC PRINT LINE
				//System.out.print((char)firstNode.getCharacter() + " ++ " + firstNode.getCount());
				//System.out.print((char)secondNode.getCharacter() + " ++ " + secondNode.getCount());
				////END DIAGNOSTIC

				nodeQueue.add(newParent);  // add the new node back into the priority queue
				// AT THE END WHERE IT BELONGS THIS TIME UGH
			}
			this.root = nodeQueue.remove(); // when finished, the root is set to the topmost node
		}
	}
	
	/**
	 * An alternative constructor which creates a HuffmanTree2 object based on a passed Scanner.
	 * This constructor is used to create a HuffmanTree based on the coded output of the write
	 * method, which creates strings of 1s and 0s that can be used as paths down a tree to
	 * find leaf nodes representing ASCII characters. This method reads a code file and
	 * uses a helper method to build a tree based on the specified paths.
	 * @param input a Scanner object which must be attached to a code file in 'standard format' (as per assignment)
	 **/
	//INSTRUCTIONS: "Constructs a Huffman tree from the Scanner.  Assumes the Scanner contains a tree description in standard format."
	public HuffmanTree2(Scanner input) {
		this.root = new HuffmanNode(-1, -1, null, null); // create an empty node to start with nonsensical count and character values
		while (input.hasNextLine()) { // continue until end of file
			int n = Integer.parseInt(input.nextLine()); // suggested by F Lepeintre // the ASCII identifier of the character read in as an int
			String code = input.nextLine(); // suggested by F Lepeintre // the code read in as a string
			this.root = buildHuffmanTree(root, n, code); // root is reassigned until it is the very top node, the ultimate root
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
	//NOT MODIFIED FROM PREVIOUS ASSIGNMENT
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
	
	/**
	 * Constructs a HuffmanTree2 object from a passed input stream. Uses a recursive helper method to read 1 bit at a time
	 * from the passed input stream until it reaches a 1 (indicating a leaf node), then calls another (provided) method to 
	 * read 9 bits and reconstruct a stored integer. 
	 * @param input a BitInputStream object used to read bits from a file
	 **/
	//INSTRUCTIONS: "Constructs a Huffman tree from the given input stream.
	// Assumes that the standard bit representation has been used for the tree."   
	public HuffmanTree2(BitInputStream input){
		this.root = buildHuffmanTreeStream(input); // begin the tree from the root
	}

	/**
	 * A helper method for HuffmanTree2(BitInputStream input). Reads bits from the passed input stream one at a time. When
	 * a 0 is read, indicating a branch node, the method creates two new child nodes and calls itself recursively for both
	 * children. When a 1 is read indicating a leaf node, it calls a (provided) helper method to read the next 9 bits and
	 * reconstruct the stored integer.
	 * @param input a BitInputStream object used to read bits from a file
	 * @return currentNode a HuffmanNode object
	 **/
	private HuffmanNode buildHuffmanTreeStream(BitInputStream input){
		HuffmanNode currentNode = null; // start with a null node (to initialize it)
		int nextBit = input.readBit(); // read one bit from the input stream
		if (nextBit == 1) { // if the bit is 1 (leaf node) BASE CASE
			int character = read9(input); // use read9 to read 9 bits and reconstruct the int representing the ASCII character
			//DIAGNOSTIC PRINT LINE
			//System.out.println(character); 
			//END DIAGNOSTIC
			currentNode = new HuffmanNode(character, 0); // encode that character in the leaf node (count does not matter anymore)
		}
		else if (nextBit == 0) { // if the bit is 0 (branch node)
			HuffmanNode leftChild = (buildHuffmanTreeStream(input)); // call this method again for the left child node
			HuffmanNode rightChild = (buildHuffmanTreeStream(input)); // call this method again for the right child node
			currentNode = new HuffmanNode(-1, -1, leftChild, rightChild); // make a new branch node (only child fields matter, nonsensical character and count)
			
		}
		return currentNode;
	}

	/**
	 * Iterates through a passed array of null values and replaces each null with a code indicating the path through
	 * the Huffman tree to the leaf node corresponding to a given character (the character corresponding to that
	 * index in the array).
	 * @param codes a String[] a string of null values, one for each character encoded in the HuffmanTree2
	 **/
	//INSTRUCTIONS: "Assigns codes for each character of the tree.
	//Assumes the array has null values before the method is called.
	//Fills in a String for each character in the tree indicating its code."
	public void assign(String[] codes){
		assign(this.root, codes, ""); // start with the root and an empty string
	}
	
	/**
	 * A recursive helper method for assign which iterates through the Huffman tree creating path codes. Fetches
	 * the character for the current node. If that character is -1 (a nonsensical character) it is a branch node.
	 * The method that recursively calls itself for each child node, adding a 0 to the path when it 'goes left'
	 * (i.e. calls the method for the left node) and a 1 when it 'goes right'. When it encounters a character
	 * other than -1 it has reached a leaf node and assigns the full path (a code of 1s and 0s) to the correct
	 * index in the passed array (the index corresponding to that characters ASCII code).
	 * @param currentNode a HuffmanNode object, the current node that has been reached in the traversal so far
	 * @param codes a String[] a string of null values, one for each character encoded in the HuffmanTree2
	 * @param path a String giving the 1s and 0s representing the path traveled so far from the root to the current node
	 **/
	private void assign(HuffmanNode currentNode, String[] codes, String path){
		int currentChar = currentNode.getCharacter(); // fetch the character of the current node
		if (currentChar != -1) { // if node is a leaf node (BASE CASE)
			codes[currentChar] = path; // the path that has been created so far is the full path to the node for this character
			//DIAGNOSTIC PRINT LINE
			//System.out.println((char)currentNode.getCharacter() + " -code- " + code);
			//END DIAGNOSTIC
		}
		else { // if node is a branch node
			assign(currentNode.leftChild, codes, path + "0");  // add a 0 to the path and go left
			assign(currentNode.rightChild, codes, path + "1"); // add a 1 to the path and go right
		}
	}
	
	/**
	 * Writes a HuffmanTree2 object to a passed output stream using a recursive helper method to fetch the
	 * character of each node, determine when a leaf is found, and use another (provided) method to
	 * write the 9 bits which encode that character.
	 * @param output a BitOutputStream object that lets you write bits to a file (or other output)
	 **/
	//INSTRUCTIONS: "Writes the current tree to the output stream using the standard bit representation."
	public void writeHeader(BitOutputStream output){
		writeHeader(this.root, output); // start with the root
	}
	
	/**
	 * A recursive helper method for writeHeader(BitOutputStream output) which searches for leaf nodes by
	 * fetching the character of each node and looking for any character which is not -1. When a leaf node
	 * is found it writes a 1, on branch nodes it writes a 0 (specified in assignment). Recursively calls
	 * itself on children until a leaf is reached.
	 * @param currentNode a HufmanNode object, the node that has been reached in the traversal so far
	 * @param output a BitOutputStream object that lets you write bits to a file (or other output)
	 **/
	private void writeHeader(HuffmanNode currentNode, BitOutputStream output){
		int currentChar = currentNode.getCharacter(); // fetch the character of the current node
		if(currentChar != -1){ // if node is a leaf node (BASE CASE)
			output.writeBit(1);
			//DIAGNOSTIC PRINT LINE
			//System.out.println(currentNode.getCharacter());
			//END DIAGNOSTIC
			write9(output, currentChar); // use provided method to encode its character
		}
		else{ // if node is a branch node
			output.writeBit(0);
			writeHeader(currentNode.leftChild, output); // recursively call method for the left child
			writeHeader(currentNode.rightChild, output); // and recursively call method for the right child		
		}
	}

	/**
	 * Decodes an encoded file using a code in 'standard format' as per assignment. Uses the codes as
	 * paths along a HuffmanTree2 to find leaf nodes which are ASCII characters. Once a leaf is found
	 * the method returns to the top of the tree and continues until it finds the end of file character
	 * (which is specified in Decode2.java).
	 * @param input a BitInputStream allowing you to read bits indicating branch and leaf nodes
	 * @param output a PrintStream object allowing you to write the character stored in a leaf node
	 * @param eof an int specifying the end of file character (specified in Decode2)
	 **/
	//INSTRUCTIONS: "Reads bits from the given input stream and writes the corresponding characters to the output.  Stops reading 
	//when it encounters a character with value equal to eof.  This is a pseudo-eof character, so it should not be written to the 
	//output file.  Assumes the input stream contains a legal encoding of characters for this tree's Huffman code."
	//NOT CHANGED FROM PREVIOUS ASSIGNMENT
	public void decode(BitInputStream input, PrintStream output, int eof) { // eof is specified as a final int in Decode //Decode2 for this assignment
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
   
   //METHOD GIVEN
   // pre : an integer n has been encoded using write9 or its equivalent
   // post: reads 9 bits to reconstruct the original integer
   private int read9(BitInputStream input) {
      int multiplier = 1;
      int sum = 0;
      for (int i = 0; i < 9; i++) {
         sum += multiplier * input.readBit();
         multiplier *= 2;
      }
      return sum;
   }

   //METHOD GIVEN
   // pre : 0 <= n < 512
   // post: writes a 9-bit representation of n to the given output stream
   private void write9(BitOutputStream output, int n) {
      for (int i = 0; i < 9; i++) {
         output.writeBit(n % 2);
         n /= 2;
      }
   }
   
}