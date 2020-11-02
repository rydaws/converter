package postfixInfix;

/*
* Converts equations from infix -> postfix notation and vice verse.
* Stores converted and original values to linked list to be viewed upon closing program.
* @author Ryan Dawson - 10/30/20
* @class CSI 213
*/

import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;

public class MainFix{
	//Number choices when taken from input
	static final int inToPost = 1;
	static final int postToIn = 2;
	static final int printEq = 3;
	static final int exitMenu = 4;
	public static LinkedList<String> myList = new LinkedList<String>();

	public static void run() {
		//Run method is where the main operation takes place
		//Takes input from user and calls respected method
		
		//Initializes scanner and several variables
		int choice = 0;
		Scanner input = new Scanner(System.in);
		String infix = "";
		String postFix = "";
		
		//try statement to catch exceptions
		try {
			//While statement is used to take input from user and repeat after each iteration.
			while (choice != 4) {
				System.out.println("\nPlease select what type of conversion you would like to do: ");
				System.out.println("1) Infix to postfix");
				System.out.println("2) Postfix to infix");
				System.out.println("3) Print Equations");
				System.out.println("4) Exit");
				choice = input.nextInt();
				input.nextLine();
				//If statement checks to see if choice is within range, if not, tells user why.
				if(choice > 4 || choice == 0) {
					System.out.println("Invalid choice! Try again.");
				}
				switch (choice) {
				//Infix -> Postfix notation, takes input and calls respected method.
				case inToPost: {
					System.out.println(
							"Enter your infix equation without the '=' sign \nand do not space out characters: (x+b*y)");
					infix = input.nextLine();
					System.out.println("Infix -> Postfix Result = " + inToPost(infix));

					break;
				}
				//Postfix -> infix notation, takes input and calls respected method
				case postToIn: {
					System.out.println("Enter your postfix equation without the '=' sign: (xy+)");
					postFix = input.nextLine();
				System.out.println("Postfix -> Infix Result = \n" + postToIn(postFix));
				
					break;
				}
				//Prints values which are stored in the linked list
				case printEq: {
					for(int i = 0; i<myList.size(); i++) {
						System.out.println("Converted equation " + (i+1) + ": \n" +myList.get(i));
					}
					break;
				}
				//Terminates program and prints values which are stored in the linked list.
				case exitMenu: {
					for(int i = 0; i<myList.size(); i++) {
						System.out.println("Converted equation " + (i+1) + ": \n" +myList.get(i));
					}
					break;
				}
				}
			}
			//Catches invalid choice exception (i.e. not a number)
		} catch (Exception e){
			System.out.println("Invalid choice, try again.");
			run();

		}finally {
			System.out.println("Terminated");
		}
		input.close();
	}

	/*Infix -> Postfix method.
	 * Converts equation from infix to postfix notation while utilizing a stack to store operators.
	 */
	public static String inToPost(String I) {
		//Initializes variables and stack to hold operators
		Stack<Character> stack = new Stack<Character>();
		String postString = "";
		String org = I;
		//Uses for loop to increment through the string parameter
		for (int i = 0; i <= I.length() - 1; i++) {
			char ch = I.charAt(i);
			//Checks to make sure character is letter or digit, then adds it to the output string.
			if (Character.isLetterOrDigit(ch)) {
				postString += ch;
			//If current char = '(' or stack is empty, push current char onto stack
			} else if (ch == '(' || stack.isEmpty()) {
				stack.push(ch);
				//If current char = ')', then adds the the stack to the string until '(' is reached
			} else if (ch == ')') {
				while (!stack.isEmpty() && stack.peek() != '(') {
					postString += stack.pop();
				}
				//Pops off '(' to prevent interference.
				stack.pop();
			}else {
				//Pops off stack and adds to string when precedence of current char is less than or = to
				//the operator on top of stack
				while (!stack.isEmpty() && stack.peek() != '(' && precedence(ch) <= precedence(stack.peek())) {					
					postString += stack.pop();
					
				}
				stack.push(ch);
			}
			//Prints out current stack so user can visuailze what is going on
			System.out.println("Current Stack:" + stack);
			System.out.println(postString + "\n");
		}
		//pops off remaining items on stack if they are = to '(' or ';' then appends the rest of the items to the string
		while (!stack.isEmpty()) {
			if(stack.peek() == '(' || stack.peek() == ';') {
				stack.pop();
			}else {
			postString += stack.pop();
			}
		}
		//Adds orginal and converted equation to linked list.
		myList.add("Original infix: " + org + " \nConverted postfix: " + postString + "\n");
		return postString;

	}
	
	public static String postToIn(String I) {
		//Initializes variables for this method as well as new stack
		Stack<String> stackStr = new Stack<String>();
		String org = I;
		String inString = null;
		String val1 = "";
		String val2 = "";
		
		//Uses for loop to increment through the string parameter
		for(int i = 0; i <= I.length() - 1; i++) {
			char ch = I.charAt(i);
			
			//Checks to make sure that the next charater is a letter/digit to push it to stack
			if(Character.isLetterOrDigit(ch)) {
				stackStr.push(ch + "");
			//If not letter or digit, stack is popped and current operator is placed in between 2 popped values.
			}else {
				val1 = stackStr.pop();
				val2 = stackStr.pop();
				stackStr.push("(" + val2 + ch + val1 + ")");
				System.out.println(stackStr.peek());
			}
		}
		//Adds original and converted equation to linked list.
		inString = stackStr.peek();
		myList.add("Original postfix: " + org + " \nConverted infix: " + inString + "\n");
		return inString;
	}
	//precedence method checks precedence of operators in infix -> postfix to properly assign values.
	public static int precedence(char ch) {
		switch (ch) {
		case '-':
		case '+':
			return 1;
		case '/':
		case '*':
			return 2;
		case '^':
			return 3;
		}
		return -1;
	}
	//Calls run method to start program.
	public static void main(String args[]) {
		run();
	}
	
	
}
