package Quantcast;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Cell {

	private String input;
	public String name;

	private double evaluatedValue;
	public boolean isEvaluated;

	public List<String> references;
	public List<Token> tokens;
	
	public int edgeCount;
	
	public Cell(){
		references = new ArrayList<>();
		tokens = new ArrayList<>();
	}

	public void setTokens(String input) {
		this.input = input.toUpperCase();
		String[] tokenList = input.trim().toUpperCase().split("\\s+");
		
		for(String token : tokenList) {
			
			if (token.equals("+")) {
				tokens.add(new Token(2));
			} else if (token.equals("-")) {
				tokens.add(new Token(3));
			} else if (token.equals("*")) {
				tokens.add(new Token(4));
			} else if (token.equals("/")) {
				tokens.add(new Token(5));
			} else if (token.equals("++")) {
				tokens.add(new Token(6));
			} else if (token.equals("--")) {
				tokens.add(new Token(7));
			} else {
				char c = token.charAt(0);
				if ('A' <= c && c <= 'Z') {
					// reference
					tokens.add(new Token(token));
					references.add(token);
				} else {
					// integer
					double d = Double.valueOf(token);
					tokens.add(new Token(d));
				}
			}
		}
	}

	public double evaluate() throws CircularDependancyException {		
		if (isEvaluated)
			return evaluatedValue;

		Stack<Double> stack = new Stack<Double>();
		double eval, arg2, arg1;
		for (Token token : tokens) {
			switch (token.type) {
			case 0:
				stack.push(token.value);
				break;
			case 1:
				Cell reference = Spreadsheet.cells[token.referenceRow][token.referenceColumn];
				eval = reference.evaluate();
				stack.push(eval);
				break;
			case 2:
				arg1 = stack.pop();
				arg2 = stack.pop();
				eval = arg2 + arg1;
				stack.push(eval);
				break;
			case 3:
				arg1 = stack.pop();
				arg2 = stack.pop();
				eval = arg2 - arg1;
				stack.push(eval);
				break;
			case 4:
				arg1 = stack.pop();
				arg2 = stack.pop();
				eval = arg2 * arg1;
				stack.push(eval);
				break;
			case 5:
				arg1 = stack.pop();
				arg2 = stack.pop();
				eval = arg2 / arg1;
				stack.push(eval);
				break;
			case 6:
				arg1 = stack.pop();
				eval = arg1 + 1;
				stack.push(eval);
				break;
			case 7:
				arg1 = stack.pop();
				eval = arg1 - 1;
				stack.push(eval);
				break;
			}
		}
		evaluatedValue = stack.pop();
		isEvaluated = true;
		return evaluatedValue;
	}
	
	
	public void setEvaluated(boolean isEvaluated) {
		this.isEvaluated = isEvaluated;
	}
	
	public boolean isEvaluated() {
		return isEvaluated;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName(String name) {
		return this.name;
	}

	public String toString() {
		return name;
	}
}
