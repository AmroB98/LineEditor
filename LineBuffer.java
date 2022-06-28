
/**
 * @(#)LineEditorBuffer.java
 *
 * The buffer Class for the line Editor,
 * provides a container to store lines 
 * and methods to manipulate them.
 * 
 * @author Amro Batwa
 * @version 1.00 
 * @date 27/10/2021
**/
import java.util.ArrayList;
import java.util.LinkedList;

public class LineBuffer {

	// variables
	private int currentLine;
	private int currentChar;
	private int size;
	// ArrayList container to store Linked-List of characters
	ArrayList<LinkedList<Character>> lines;

	/**
	 * Constructor. Initializes a lines buffer
	 * 
	 * @param None.
	 */
	public LineBuffer() {
		lines = new ArrayList<LinkedList<Character>>();
		currentLine = 0;
		currentChar = 0;
		size = 0;
	}

	/**
	 * inserts a new line
	 *
	 * @param line string
	 */
	public void insert(String line) {
		LinkedList<Character> c = new LinkedList<Character>(); /* characters of the line stored in linked-list */
		for (int i = 0; i < line.length(); i++) {
			c.addLast(line.charAt(i));
		}
		lines.add(currentLine, c);
		size++;
	}

	/**
	 * Returns the number of lines
	 *
	 */
	public int getSize() {
		return size;
	}

	/**
	 * inserts a new character
	 *
	 */
	public void insertChar(int startChar, String s) {
		LinkedList<Character> chars = lines.get(currentLine); // Create a Linked-list to store characters
		for (int i = 0; i < s.length(); i++) {
			chars.add(startChar + i, s.charAt(i));
		}
	}

	/**
	 * add a new line at the end
	 *
	 * @param line string
	 */
	public void addLast(String line) {
		currentLine = lines.size();
		this.insert(line);
	}

	/**
	 * deletes current line
	 * 
	 * @param None.
	 *
	 */
	public void deleteCurrentLine() {
		if (size != 0) { // do nothing if no lines exist
			lines.remove(currentLine); // remove line
			size--; // decrement size
			if (currentLine == size)
				currentLine--; // decrement current line if deleted was last element.
			currentChar = 0; // set current character number = 0
		}
	}

	/**
	 * deletes next character
	 * 
	 * @param None.
	 */
	public void deleteNextChar() {
		LinkedList<Character> chars = lines.get(currentLine); // Store the current line temporary
		if (currentChar + 1 < chars.size())
			chars.remove(currentChar + 1); // Remove the character
		else if (chars.size() == 1) {
			chars.remove(currentChar); // if only one char remains, delete it.
		}
	}

	/**
	 * replaces characters
	 * 
	 * @param startChar: The character to start substitution at.
	 * @param s:         the text to replace with.
	 */
	public void replaceChar(int originalChar, String s) {
		LinkedList<Character> chars = lines.get(currentLine);
		for (int i = 0; i < s.length(); i++) {
			if (i + originalChar < chars.size()) {
				chars.set(originalChar + i, s.charAt(i));
			} else {
				chars.add(originalChar + i, s.charAt(i));
			}
		}
	}

	/**
	 * searches for specified word - current Character
	 * 
	 * @param word: The word to search for
	 * @return int: -1 if not found. Line number if found.
	 *
	 */
	public int find(String word) {
		// counting variables
		int i = 0;
		int wc = 0; /* words of characters */
		int lc = 0; /* lines of characters */

		int wordSize = word.length();
		boolean found = false;

		for (i = 0; i < size && !found; i++) {
			wc = 0;
			lc = 0;

			LinkedList<Character> line = lines.get(i);

			for (char c : line) {
				if (word.charAt(wc) == c) {
					wc++;
				} else {
					wc = 0;
				}
				lc++;
				if (wc == wordSize) {
					found = true;
					break;
				}
			}
		}

		if (found) { /* update if found */
			this.currentChar = lc - wordSize;
			this.currentLine = i - 1;
			return (currentLine);
		} else
			return -1;
	}

	/**
	 * move one line forward
	 * 
	 * @param None
	 * 
	 *
	 */
	public void nextLine() {
		if (currentLine + 1 < size)
			currentLine++;
		currentChar = 0;
	}

	/**
	 * move one line backward
	 * 
	 * @param None
	 * 
	 *
	 */
	public void prevLine() {
		if (currentLine != 0)
			currentLine--;
		currentChar = 0;
	}

	/**
	 * move to a specified line
	 * 
	 * @param None
	 * 
	 *
	 */
	public void goToLine(int lineNumber) {
		if (lineNumber >= 0 && lineNumber < size) {
			currentLine = lineNumber;
		}
	}

	/**
	 * move one char forward
	 * 
	 * @param None
	 * 
	 *
	 */
	public void nextChar() {
		if (lines.size() != 0 && lines.get(currentLine).size() != 0 && currentChar + 1 < lines.get(currentLine).size())
			currentChar++;
	}

	/**
	 * move one char backward
	 * 
	 * @param None
	 * 
	 *
	 */
	public void prevChar() {
		if (currentChar != 0)
			currentChar--;
	};

	/**
	 * Prints the first line from LineBuffer
	 * 
	 * @param None
	 * 
	 *
	 */
	public void printFirst() {
		this.printLine(0);
	}

	/**
	 * prints last line from LineBuffer
	 * 
	 * @param None
	 * 
	 *
	 */
	public void printLastLine() {
		this.printLine(lines.size() - 1);
	}

	/**
	 * gets current Line
	 * 
	 * @param None
	 * @return currentLine
	 *
	 */
	public int getCurrentLineNumber() {
		return currentLine;
	}

	/**
	 * get current char
	 * 
	 * @param None
	 * @return currentChar
	 *
	 */
	public int getCurrentCharNumber() {
		return currentChar;
	}

	/**
	 * Converts lines of characters into strings.
	 * 
	 * @param None
	 * @return line<String>
	 *
	 */
	public String linetoString(int lineNumber) {
		if (lineNumber < 0 || lineNumber >= size)
			return null;
		if (size == 0) {
			System.out.println("There are no Lines");
			return null;
		}
		goToLine(lineNumber);
		String line;
		// appending characters into a string line
		StringBuilder sb = new StringBuilder();
		for (char c : lines.get(currentLine)) {
			sb.append(c);
		}
		line = sb.toString();
		return line;
	}

	/**
	 * get line from line number
	 * 
	 * @param line number as an integer
	 * 
	 *
	 */
	public void printLine(int lineNum) {
		if (lineNum < 0 || lineNum >= size) {
			return;
		}

		currentLine = lineNum;
		String s = linetoString(lineNum);
		System.out.println(s);
	}

	/**
	 * get current's next line
	 * 
	 * @param None
	 * @return current's next line<String>
	 *
	 */
	public String getNext() {
		this.nextLine();
		return linetoString(currentLine);
	}

	/**
	 * get current's previous line
	 * 
	 * @param None
	 * @return current's previous line<String>
	 *
	 */
	public String getPrev() {
		this.prevLine();
		return linetoString(currentLine);
	}

	/**
	 * get the first line
	 * 
	 * @param None
	 * @return the first line<String>
	 *
	 */
	public String getFirst() {
		return linetoString(0);
	}

	/**
	 * get the last line
	 * 
	 * @param None
	 * @return the last line<String>
	 *
	 */
	public String getLast() {
		return linetoString(size - 1);
	}

}
