package model;

import java.util.Scanner;

public class HuffmanEncoding {
	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			while (scanner.hasNext()) {
				String message = scanner.nextLine();
				HuffmanEncoding encoding = new HuffmanEncoding();
				String output = encoding.compressThenExpand(message);
				System.out.println(output);
			}
		}
	}
	
	public String compressThenExpand(String message) {
		Node root = createTree(message);
		System.out.println("NODE:" + root);
		String compressedMessage = compressMessage(root, message);
		String expandedMessage = expandMessage(root, compressedMessage);
		return compressedMessage + "\n" + expandedMessage;
	}
	
	// compress message.
	private String compressMessage(Node root, String message) {
		return null;
	}
	
	// expand compressed message using codes.
	private String expandMessage(Node root, String message) {
		return null;
	}
	
	// build optimal prefix-free code from message and make a huffman tree.
	private Node createTree(String message) {
		// We are skipping using a HashTable, like was done here: https://aquarchitect.github.io/swift-algorithm-club/Huffman%20Coding/
		// Except instead of storing the character frequencies as nodes in an array (like that article did), we will just store them as integers in an array
		// and each index of the 256-index array corresponds to a character code in ASCII.
		int[] freqs = countCharacterFrequencies(message);
		MinimumPriorityQueue<Node> chars = createSortedCharNodes(freqs);
		Node root = combineCharNodesAndBuildTree(chars);
		return root;
	}

	private Node combineCharNodesAndBuildTree(MinimumPriorityQueue<Node> nodes) {
		while (nodes.size() > 1) {
			// Join the next two nodes with the lowest frequency.
			Node child1 = nodes.dequeue();
			Node child2 = nodes.dequeue();
			Node parent = new Node(child1.getFreq() + child2.getFreq(), child1, child2);
			nodes.enqueue(parent);
		}
		
		// We have only one node left in our queue.
		// This must be the root node of our Huffman tree.
		Node root = nodes.dequeue();
		return root;
	}

	private MinimumPriorityQueue<Node> createSortedCharNodes(int[] freqs) {
		MinimumPriorityQueue<Node> queue = new MinimumPriorityQueue<Node>();
		for (char asciiCode = 0; asciiCode < 256; asciiCode++) {
			int freq = freqs[asciiCode];
			if (freq > 0) {
				queue.enqueue(new Node(asciiCode, freq));
			}
		}
		return queue;
	}

	private int[] countCharacterFrequencies(String message) {
		// Note: Default value is 0 in each index of an integer array in Java. 
		int[] freqs = new int[256];
		for (int i = 0; i < message.length(); i++) {
			char asciiCode = message.charAt(i);
			freqs[asciiCode]++;
		}
		return freqs;
	}
}