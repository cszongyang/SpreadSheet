package Quantcast;


public class Token {
	
	// 0 = value
	// 1 = reference
	// 2 = operator+
	// 3 = operator-
	// 4 = operator*
	// 5 = operator/
	// 6 = operator ++
	// 7 = operator --
	public int type;
	
	public int operatorType;
	public double value;
	public int referenceRow;
	public int referenceColumn;

	
	public Token(String token) {
		
		if (token.equals("+")) {
			type = 2;
		} else if (token.equals("-")) {
			type = 3;
		} else if (token.equals("*")) {
			type = 4;
		} else if (token.equals("/")) {
			type = 5;
		} else if (token.equals("++")) {
			type = 6;
		} else if (token.equals("--")) {
			type = 7;
		} else {
			char c = token.charAt(0);
			if ('A' <= c && c <= 'Z') {
				// reference
				type = 1;
				referenceRow = token.charAt(0)-65;
				referenceColumn = Integer.valueOf(token.substring(1))-1;
				
			} else {
				// number
				type = 0;
				value = Double.valueOf(token);
			}
		}
	}
}
