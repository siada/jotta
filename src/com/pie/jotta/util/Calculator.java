package com.pie.jotta.util;

public class Calculator {
	// These are the token types. 
	final int NONE = 0;
	final int DELIMITER = 1;
	final int VARIABLE = 2;
	final int NUMBER = 3;
 
	// These are the types of syntax errors. 
	final int SYNTAX = 0;
	final int UNBALPARENS = 1;
	final int NOEXP = 2;
	final int DIVBYZERO = 3;
 
	// This token indicates end-of-expression. 
	final String EOE = "\0";
 
	private String exp; // refers to expression string  
	private int expIdx; // current index into the expression  
	private String token; // holds current token  
	private int tokType; // holds token's type  
 
	// Array for variables.  
	private double vars[] = new double[26];
 
	// Parser entry point.  
	public double evaluate(String expstr) throws ParserException {
		double result;
		exp = expstr;
		expIdx = 0;
 
		getToken();
		if (token.equals(EOE))
			handleErr(NOEXP); // no expression present  
 
		// Parse and evaluate the expression. 
		result = evalExp1();
 
		if (!token.equals(EOE)) // last token must be EOE  
			handleErr(SYNTAX);
 
		return result;
	}
 
	// Process an assignment.  
	private double evalExp1() throws ParserException {
		double result;
		int varIdx;
		int ttokType;
		String temptoken;
 
		if (tokType == VARIABLE) {
			// save old token  
			temptoken = new String(token);
			ttokType = tokType;
 
			// Compute the index of the variable.  
			varIdx = Character.toUpperCase(token.charAt(0)) - 'A';
 
			getToken();
			if (!token.equals("=")) {
				putBack(); // return current token  
				// restore old token -- not an assignment  
				token = new String(temptoken);
				tokType = ttokType;
			}
			else {
				getToken(); // get next part of exp  
				result = evalExp2();
				vars[varIdx] = result;
				return result;
			}
		}
 
		return evalExp2();
	}
 
	// Add or subtract two terms.  
	private double evalExp2() throws ParserException {
		char op;
		double result;
		double partialResult;
 
		result = evalExp3();
 
		while ((op = token.charAt(0)) == '+' || op == '-') {
			getToken();
			partialResult = evalExp3();
			switch (op) {
				case '-':
					result = result - partialResult;
					break;
				case '+':
					result = result + partialResult;
					break;
			}
		}
		return result;
	}
 
	// Multiply or divide two factors.  
	private double evalExp3() throws ParserException {
		char op;
		double result;
		double partialResult;
 
		result = evalExp4();
 
		while ((op = token.charAt(0)) == '*' || op == '/' || op == '%') {
			getToken();
			partialResult = evalExp4();
			switch (op) {
				case '*':
					result = result * partialResult;
					break;
				case '/':
					if (partialResult == 0.0)
						handleErr(DIVBYZERO);
					result = result / partialResult;
					break;
				case '%':
					if (partialResult == 0.0)
						handleErr(DIVBYZERO);
					result = result % partialResult;
					break;
			}
		}
		return result;
	}
 
	// Process an exponent.  
	private double evalExp4() throws ParserException {
		double result;
		double partialResult;
		double ex;
		int t;
 
		result = evalExp5();
 
		if (token.equals("^")) {
			getToken();
			partialResult = evalExp4();
			ex = result;
			if (partialResult == 0.0) {
				result = 1.0;
			}
			else
				for (t = (int) partialResult - 1; t > 0; t--)
					result = result * ex;
		}
		return result;
	}
 
	// Evaluate a unary + or -.  
	private double evalExp5() throws ParserException {
		double result;
		String op;
 
		op = "";
		if ((tokType == DELIMITER) && token.equals("+") || token.equals("-")) {
			op = token;
			getToken();
		}
		result = evalExp6();
 
		if (op.equals("-"))
			result = -result;
 
		return result;
	}
 
	// Process a parenthesized expression.  
	private double evalExp6() throws ParserException {
		double result;
 
		if (token.equals("(")) {
			getToken();
			result = evalExp2();
			if (!token.equals(")"))
				handleErr(UNBALPARENS);
			getToken();
		}
		else
			result = atom();
 
		return result;
	}
 
	// Get the value of a number or variable.  
	private double atom() throws ParserException {
		double result = 0.0;
 
		switch (tokType) {
			case NUMBER:
				try {
					result = Double.parseDouble(token);
				}
				catch (NumberFormatException exc) {
					handleErr(SYNTAX);
				}
				getToken();
				break;
			case VARIABLE:
				result = findVar(token);
				getToken();
				break;
			default:
				handleErr(SYNTAX);
				break;
		}
		return result;
	}
 
	// Return the value of a variable.  
	private double findVar(String vname) throws ParserException {
		if (!Character.isLetter(vname.charAt(0))) {
			handleErr(SYNTAX);
			return 0.0;
		}
		return vars[Character.toUpperCase(vname.charAt(0)) - 'A'];
	}
 
	// Return a token to the input stream.  
	private void putBack() {
		if (token == EOE)
			return;
		for (int i = 0; i < token.length(); i++)
			expIdx--;
	}
 
	// Handle an error.  
	private void handleErr(int error) throws ParserException {
		String[] err = { "Syntax Error", "Unbalanced Parentheses",
			"No Expression Present", "Division by Zero" };
 
		throw new ParserException(err[error]);
	}
 
	// Obtain the next token.  
	private void getToken() {
		tokType = NONE;
		token = "";
 
		// Check for end of expression.  
		if (expIdx == exp.length()) {
			token = EOE;
			return;
		}
 
		// Skip over white space. 
		while (expIdx < exp.length()
				&& Character.isWhitespace(exp.charAt(expIdx)))
			++expIdx;
 
		// Trailing whitespace ends expression. 
		if (expIdx == exp.length()) {
			token = EOE;
			return;
		}
 
		if (isDelim(exp.charAt(expIdx))) { // is operator  
			token += exp.charAt(expIdx);
			expIdx++;
			tokType = DELIMITER;
		}
		else
			if (Character.isLetter(exp.charAt(expIdx))) { // is variable  
				while (!isDelim(exp.charAt(expIdx))) {
					token += exp.charAt(expIdx);
					expIdx++;
					if (expIdx >= exp.length())
						break;
				}
				tokType = VARIABLE;
			}
			else
				if (Character.isDigit(exp.charAt(expIdx))) { // is number  
					while (!isDelim(exp.charAt(expIdx))) {
						token += exp.charAt(expIdx);
						expIdx++;
						if (expIdx >= exp.length())
							break;
					}
					tokType = NUMBER;
				}
				else { // unknown character terminates expression 
					token = EOE;
					return;
				}
	}
 
	// Return true if c is a delimiter.  
	private boolean isDelim(char c) {
		if ((" +-/*%^=()".indexOf(c) != -1))
			return true;
		return false;
	}
}
