package postfixInfix;

import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;

public class MainFix{
	static final int inToPost = 1;
	static final int postToIn = 2;
	static final int printEq = 3;
	static final int exitMenu = 4;
	//public static Stack<Character> stack = new Stack<Character>();
	public static LinkedList<String> myList = new LinkedList<String>();

	public static void run() {
		
		int choice = 0;
		Scanner input = new Scanner(System.in);
		String infix = "";
		String postFix = "";

		try {
			while (choice != 4) {
				System.out.println("\nPlease select what type of conversion you would like to do: ");
				System.out.println("1) Infix to postfix");
				System.out.println("2) Postfix to infix");
				System.out.println("3) Print Equations");
				System.out.println("4) Exit");
				choice = input.nextInt();
				input.nextLine();

				switch (choice) {
				case inToPost: {
					System.out.println(
							"Enter your infix equation without the '=' sign \nand do not space out characters: (x+b*y)");
					infix = input.nextLine();
					System.out.println("Infix -> Postfix Result = " + inToPost(infix));

					break;
				}
				case postToIn: {
					System.out.println("Enter your postfix equation without the '=' sign: (xy+)");
					postFix = input.nextLine();
				System.out.println("Postfix -> Infix Result = \n" + postToIn(postFix));
				
					break;
				}
				case printEq: {
					for(int i = 0; i<myList.size(); i++) {
						System.out.println("Converted equation " + (i+1) + ": \n" +myList.get(i));
					}
					break;
				}
				case exitMenu: {
					for(int i = 0; i<myList.size(); i++) {
						System.out.println("Converted equation " + (i+1) + ": \n" +myList.get(i));
					}
					break;
				}
				}
			}
		} finally {
			System.out.println("Invalid expression!");

		}
	}

	public static String inToPost(String I) {
		Stack<Character> stack = new Stack<Character>();
		String postString = "";
		String org = I;
		for (int i = 0; i <= I.length() - 1; i++) {
			char ch = I.charAt(i);

			if (Character.isLetterOrDigit(ch)) {
				postString += ch;
			} else if (stack.isEmpty() || stack.peek() == '(') {
				stack.push(ch);
			} else if ((ch == '(' || stack.isEmpty())) {
				stack.push(ch);
			} else if (ch == ')') {
				while (!stack.isEmpty() && stack.peek() != '(') {
					postString += stack.pop();
				}
				stack.pop();
			}else {
				while (!stack.isEmpty() && stack.peek() != '(' && precedence(ch) <= precedence(stack.peek())) {					
					postString += stack.pop();
					
				}
				stack.push(ch);
			}
			
			System.out.println(stack);
			System.out.println(postString);
//			System.out.println(i);
		}
		while (!stack.isEmpty()) {
			if(stack.peek() == '(' || stack.peek() == ';') {
				stack.pop();
			}else {
			postString += stack.pop();
			}
		}
		myList.add("Original infix: " + org + " \nConverted postfix: " + postString + "\n");
		return postString;

	}
	
	public static String postToIn(String I) {
		Stack<String> stackStr = new Stack<String>();
		String org = I;
		String inString = null;
		String val1 = "";
		String val2 = "";
		for(int i = 0; i <= I.length() - 1; i++) {
			char ch = I.charAt(i);
			
			if(Character.isLetterOrDigit(ch)) {
				stackStr.push(ch + "");
			}else {
				val1 = stackStr.pop();
				System.out.println(val1);
				val2 = stackStr.pop();
				System.out.println(val2);
				stackStr.push("(" + val2 + ch + val1 + ")");
				System.out.println(stackStr.peek());
			}
		}
		inString = stackStr.peek();
		myList.add("Original postfix: " + org + " \nConverted infix: " + inString + "\n");
		return inString;
	}

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
	public static void main(String args[]) {
		run();
	}
	

}
