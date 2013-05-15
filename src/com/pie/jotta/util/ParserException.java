package com.pie.jotta.util;

public class ParserException extends Exception {


	private static final long serialVersionUID = 4620370393981003549L;
	
	String errStr; // describes the error 
 
	public ParserException(String str) {
		errStr = str;
	}
 
	public String toString() {
		return errStr;
	}
}
