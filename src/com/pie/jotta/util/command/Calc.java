package com.pie.jotta.util.command;

import java.util.ArrayList;

import com.pie.jotta.Constants;
import com.pie.jotta.event.IRCMessage;
import com.pie.jotta.util.Calculator;

import static com.pie.jotta.net.IRCMethods.sendMessage;

public class Calc implements Command {

	private Calculator c = new Calculator(); 
	
	public void help(IRCMessage m) throws Exception {
		sendMessage(m.getSource(), "Usage: "+Constants.CMD_PREFIX+"calc "+
				"{sum}");

	}

	public void parse(IRCMessage m) throws Exception {
		ArrayList<String> args = m.getMessageArgs();
		String sum = "";
		for(String s : args) {
			sum += s;
		}
		sendMessage(m.getSource(), ""+c.evaluate(sum));

	}

}
