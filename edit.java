
/**
 * @(#)edit.java
 *
 * a simple line Editor program. provide input
 * and output text files to start. use Command "H"
 * to view all the commands implemented in the editor.
 * 
 * @author Amro Batwa
 * @version 1.00, 27/10/2021
 * 
**/

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class edit {

	// input & output variables
	private static String inputFile = "";
	private static FileReader input = null;
	private static String outputFile = null;
	private static FileWriter output = null;
	private static BufferedReader bReader = null;
	private static BufferedWriter bWriter = null;

	/**
	 * Print all editor commands
	 * 
	 * @return None
	 */

	public static void printCommands() {
		System.out.println(
				"--------------------------------------------------------------------------------------------------------------------------------------------\n");
		System.out.println("Editor Commands:-	\n");
		System.out.println("A: Appends a new line of text to the existing lines of the buffer");
		System.out.println("D: Deletes the current line and moves to the next line.");
		System.out.println(
				"E: Start character editing mode at the beginning of the current line, allows the following commands:\n");
		System.out.println("	L:  Move cursor one character left; do nothing if at beginning.");
		System.out.println("	R:  Move cursor one character right; do nothing if at end.");
		System.out.println("	As: Append a word, s, of one or more characters at the end of the line.");
		System.out.println("	Is: Insert a word, s, of one or more characters just after the cursor.");
		System.out.println("	Cs: Change to a word, s, all characters from curser to length of s.");
		System.out.println("	D:  Delete the character after the cursor, only one character, if any.");
		System.out.println("	E:  Exit the character editing mode.\n");
		System.out.println("F: Display the first line.");
		System.out.println("L: Display the last line.");
		System.out.println("G: Display a specific line (enter the <N> line you want to display).");
		System.out.println("S: Search for the specified word (enter the word <W>");
		System.out.println("H: Display all the Editor's Commands.");
		System.out.println("I: Inserts a single new line before the current line. (enter Line <L>).");
		System.out.println("P: Print all existing lines.");
		System.out.println("N: Move to the next line.");
		System.out.println("B: Move to the previous line.");
		System.out.println("Q: Terminate Program without Saving (See Command X to save your progress).");
		System.out.println("R: Read a new file (Unsaved progress will be lost), enter file name <F>.");
		System.out.println(
				"W: Writes all lines into the specified output file, if not specified, overwrites to the input file. if no input and output files specified \n   please Enter a file name to be created.");
		System.out.println("X: Save and Quit.");
		System.out.println();
		System.out.println();
		System.out.println(
				"--------------------------------------------------------------------------------------------------------------------------------------------\n");

	}

	/**
	 * Reads input into the Line Buffer.
	 *
	 * @param LineBuffer
	 * @return None
	 */
	public static void read(LineBuffer lb) {
		try {
			input = new FileReader(inputFile);
			bReader = new BufferedReader(input);
			String currentLine;
			while ((currentLine = bReader.readLine()) != null) {
				lb.addLast(currentLine);

			}
			System.out.println("input file read succesfully\n");
			System.out.println("First Line is:");
			
			printOneLine(lb.getFirst(), 1, 0, ">", false); /*print first line*/
			System.out.println();
			System.out.println();

			//IO closing
			bReader.close();
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input File Not specified.\n");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write all lines into a file.
	 *
	 * @param lb: LineBuffer object.
	 * @return true: if write Successful.
	 */
	public static boolean write(LineBuffer lb) {
		try {

			output = new FileWriter(outputFile);
			bWriter = new BufferedWriter(output);

			for (int i = 0; i < lb.getSize(); i++) {
				bWriter.append(lb.linetoString(i));
				bWriter.newLine();

			}
			bWriter.flush();
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("Error in Naming file, Enter a Valid name.");
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * method to request saving prompts from the user.
	 *
	 * @param lb: LineBuffer object.
	 * @param in: Scanner object.
	 * @return none
	 */
	public static void save(LineBuffer lb, Scanner in) {
		String tempInput;
		boolean savedFlag = false;
		do {
			if (output == null || bWriter == null) {
				System.out.println(
						"--Note: No output file found, overwrite input file? ([Y] to confirm, [N] to create output file): ");
				tempInput = in.nextLine().toUpperCase();
				if (tempInput.equals("Y")) {
					if (input == null || bReader == null) {
						System.out.println("No input file was specified");
						System.out.println("New file will be created, Enter output file name:");
						System.out.println("F: ");
						tempInput = in.nextLine();

						outputFile = tempInput;
					} else {
						outputFile = inputFile;
						System.out.println("Input File " + inputFile + " was overwritten");
					}
				}
				if (tempInput.equals("N")) {
					System.out.println("New output file will be created, Enter output file name:");
					System.out.println("F: ");
					tempInput = in.nextLine();
					outputFile = tempInput;
					System.out.println(outputFile + " Was Created");
				}

			}
			savedFlag = write(lb);
		} while (savedFlag == false);
	}

	/**
	 * prints lines format
	 *
	 * @param lineNum: Specified Line number to be printed.
	 * @param symbol   (":" or ">").
	 * @return none
	 */
	public static void printLineNumber(int lineNum, String symbol) {
		System.out.print((lineNum) + symbol + " ");
	}

	/**
	 * prints cursor under a character.
	 *
	 * @param currentLine: current line number.
	 * @param currentChar: current character.
	 * @return none
	 */
	public static void printCursor(int currentLine, int currentChar) {
		int lastChar = String.valueOf(currentLine).length() + 2;
		for (int i = 0; i < currentChar + lastChar; i++) {
			System.out.print(" ");
		}
		System.out.println("^");
	}

	/**
	 * prints a one line
	 *
	 * @param line:    The line to print
	 * @param lineNum: line number to print
	 * @param charNum: character number to put the cursor under.
	 * @param symbol   (":" or ">")
	 * @return none
	 */
	public static void printOneLine(String line, int lineNum, int charNum, String symbol, boolean curser) {
		if (line == null) {
			System.out.println("the Line " + lineNum + " does not exist.");
			return;
		}
		printLineNumber(lineNum, symbol);
		System.out.println(line);
		if (curser)
			printCursor(lineNum, charNum);
	}

	/**
	 * prints all lines in the buffer.
	 *
	 * @param lb: LineBuffer object.
	 * @return none
	 */
	public static void printAll(LineBuffer lb) {
		for (int i = 0; i < lb.getSize(); i++) {
			System.out.printf("%4d%s%s\n", i + 1, ":", lb.linetoString(i));
		}
	}

	// main method
	public static void main(String args[]) {

		Scanner in = new Scanner(System.in);

		// user input
		String temp = null;

		// LineBuffer instance
		LineBuffer lb = new LineBuffer();

		// Welcome message
		System.out.println("\t-------------------------------------------------------");
		System.out.println("\t|\t\tThe Line Editor			      |");
		System.out.println("\t-------------------------------------------------------");

		// Account for command line arguments
		if (args.length == 1 || args.length == 2) {
			inputFile = args[0];
		}
		if (args.length == 2) {
			outputFile = args[1];
		}

		// read the buffer

		read(lb);

		// check and handle file errors
		try {
			// check first argument
			while (outputFile != null) {
				// check if already exists
				if (new File(outputFile).exists()) {
					// confirmation form user
					System.out.print("procced to use (" + outputFile + ") for output? ([Y] if yes,"
							+ " or choose a different file): ");
					temp = in.nextLine();
				} else {
					System.out.println("output file not found.");
				}

				// process user inputs and break the loop
				if (temp != null && temp.equalsIgnoreCase("y")) { /* ignore case */
					break;
				} else if (temp != null && temp.length() > 0) { /* save Name */
					outputFile = temp;
					break;
				} else {

					System.out.println("File Created"); /* prompt file creation */
					break;
				}
			}

			// Open the output file if it exists.
			if (outputFile != null) {
				System.out.print("Output File: ");
				output = new FileWriter(outputFile);
				bWriter = new BufferedWriter(output);
				System.out.println(outputFile);
			} else {
				System.out.println("output file not specified.");
			}
		} catch (FileNotFoundException e) {

			System.out.println("No output file\n"); /* prompt output file state */
			outputFile = "";
		} catch (IOException e) {
			System.out.println(e);
		} finally {

			printCommands(); /* Start program with printing all commands */

			boolean exitFlag = false;
			while (!exitFlag) { /* Run Program until exit flag is true through user commands */
				temp = "";
				System.out.print("Command> ");
				temp = in.nextLine(); /* Command Variable */

				switch (temp.toUpperCase()) { /* Take Commands */
				// return if no command was entered
				case "":
					break;
				case " ":
					break;
				case "A": /* new line at the end */
					System.out.print("APPEND: ");
					temp = in.nextLine();
					lb.addLast(temp);
					break;
				case "D": /* Delete line */
					int cur = lb.getCurrentLineNumber();
					System.out.print("Are you sure to delete line " + (cur + 1) + " ?"); /* ask for confirmation */
					System.out.println(" ([Y] if yes, [N] if no)");
					temp = in.nextLine();
					if (temp.equalsIgnoreCase("y")) {
						lb.deleteCurrentLine();
						System.out.println("line number " + (cur + 1) + " is deleted");

						System.out.println("New Current line:");
						printOneLine(lb.linetoString(cur), cur + 1, 0, ">", false);
					}
					break;
				case "E": /* edit mode */
					if (lb.lines.size() == 0) /* skip when no lines */
					{
						System.out.println("No lines found.");
						break;
					}
					// Initialize editing temporary variables
					int lineNum = lb.getCurrentLineNumber();
					int charNum = 0;
					String currentLine = null;
					while (true) { /* Editing loop */

						charNum = lb.getCurrentCharNumber();
						currentLine = lb.linetoString(lineNum);

						printOneLine(currentLine, lineNum + 1, charNum, ">", true); /* print updated lines */
						System.out.print("Character Editing Command> ");
						temp = in.nextLine();
						String Ecomand = temp.substring(0, 1).toUpperCase(); /* first character */
						// char editing commands
						if (temp.length() == 1) {
							if (Ecomand.equals("L")) {
								lb.prevChar(); /* move left */
							} else if (Ecomand.equals("R")) {
								lb.nextChar(); /* move right */
							} else if (Ecomand.equals("D")) {
								lb.deleteNextChar(); /* delete char */
							} else if (Ecomand.equals("E")) {
								break; /* exit editing mode */
							} else {
								System.out.println("command not found");
							}
						}
						// String editing commands
						if (temp.length() > 1) {
							if (Ecomand.equals("A")) { // insert character(s) at end
								lb.insertChar(currentLine.length(), temp.substring(1));
							} else if (Ecomand.equals("I")) { // Insert character(s) after cursor
								lb.insertChar(charNum + 1, temp.substring(1));
							} else if (Ecomand.equals("C")) { // replace character(s) starting at the cursor.
								lb.replaceChar(charNum, temp.substring(1));
							}

							/* if wrong command */
							else {
								System.out.println("command does not exist");
							}
							if (temp.length() < 1) { // If the user input was empty

							}

						}

					}

					break; /* Exit edit mode */
				case "H": /* Print Commands list */
					printCommands();
					break;
				case "F": /* Display the first line */
					printOneLine(lb.getFirst(), 1, 0, ">", false);
					break;
				case "L":
					String s = lb.getLast(); /* display the last line */
					printOneLine(s, lb.getCurrentLineNumber() + 1, 0, ">", false);
					break;
				case "G": /* Go to line number */
					System.out.print("N: ");
					int ln = 0;
					try {
						ln = Integer.parseInt(in.nextLine());
					} catch (NumberFormatException ne) {
						System.out.println("* input has to be an integer");
					}
					; /* check for invalid input */
					printOneLine(lb.linetoString(ln - 1), ln, 0, ">", true);
					break;
				case "S": /* find user specified word */
					System.out.print("W: ");
					temp = in.nextLine();
					ln = lb.find(temp);
					if (ln != -1) { /* print if found */
						printOneLine(lb.linetoString(ln), ln + 1, lb.getCurrentCharNumber(), ">", false);
					} else
						System.out.println("not found.");
					break;
				case "I": /* insert line */
					System.out.print("L: ");
					temp = in.nextLine();
					lb.insert(temp);
					System.out.println("Insert done");
					break;
				case "P": /* print all lines */
					printAll(lb);
					break;

				case "N": /* print next line */
					printOneLine(lb.getNext(), lb.getCurrentLineNumber() + 1, 0, ">", true);
					break;
				case "B": /* print previous line */
					printOneLine(lb.getPrev(), lb.getCurrentLineNumber() + 1, 0, ">", true);
					break;
				case "R": /* read different file */
					System.out.println(
							"Enter a name of a file to read from\n ");
					System.out.print("F: ");
					inputFile = in.nextLine();
					
					System.out.print("Are you sure you want to read " + inputFile
							+ " ? All current changes will be discarded!"); /* confirm */
					System.out.println(" ([Y] for yes, [n] for no)");
					temp = in.nextLine();
					if (temp.equalsIgnoreCase("y")) {// if confirmed
						lb = new LineBuffer();// create a new buffer
						read(lb); // Read new file.
					}
					break;
				case "Q": /* quit without saving */

					try {
						bWriter.close();
						output.close();
					} catch (IOException e) {
					} catch (NullPointerException e) {
					}

					exitFlag = true; /* exit flag to terminate the program */
					break;
				case "W": /* write data */
					save(lb, in);
					break;
				case "X": /* Write lines into output file then terminate program (save) */
					save(lb, in);
					try {
						bWriter.close();
						output.close();
					} catch (IOException e) {
					} catch (NullPointerException e) {
					}
					exitFlag = true; /* exit flag to terminate the program */
					break;

				default:
					// A Special command
					if (temp.length() > 1 && (temp.charAt(0) == 'A' || temp.charAt(0) == 'a')) {
						lb.addLast(temp.substring(1));
						continue;
					}
					// Invalid command prompt
					else {
						System.out.println("Command not found");
						break;
					}

				}
			}
			// End of program loop.
			System.out.println("Program Concluded");
			System.exit(0);
		}
	}

}
