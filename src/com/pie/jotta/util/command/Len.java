package com.pie.jotta.util.command;

import java.util.ArrayList;

import com.pie.jotta.Constants;
import com.pie.jotta.event.IRCMessage;
import com.pie.jotta.util.logger.Logger;

import static com.pie.jotta.net.IRCMethods.sendMessage;

public class Len implements Command {

	public void parse(IRCMessage m) {
		ArrayList<String> args = m.getMessageArgs();
		if(args.size() > 0) {
			StringBuilder str = new StringBuilder();
			for(String s : args) {
				str.append(s.trim()+" ");
			}
			if(str.length() > 1) {
				Logger.getInstance().log(m.getSource());
				sendMessage(m.getSource(), m.getSender()+": String length: "+str.toString().substring(0,str.toString().length()-1).length());
			} else {
				sendMessage(m.getSource(), m.getSender()+": Please supply a valid string.");
			}
		} else {
			help(m);
		}
	}
	
	public void help(IRCMessage m) {
		sendMessage(m.getSource(), "Usage: "+Constants.CMD_PREFIX+"len {string}");
	}
	
}
