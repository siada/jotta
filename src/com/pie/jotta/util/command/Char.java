package com.pie.jotta.util.command;

import static com.pie.jotta.net.IRCMethods.sendMessage;

import java.util.ArrayList;

import com.pie.jotta.Constants;
import com.pie.jotta.event.IRCMessage;

public class Char implements Command {

	@Override
	public void help(IRCMessage m) throws Exception {
		sendMessage(m.getSource(), "Usage: "+Constants.CMD_PREFIX+"ord {integer between 0 and 128}");
	}

	@Override
	public void parse(IRCMessage m) {
		try {
			ArrayList<String> args = m.getMessageArgs();
			int num = Integer.parseInt(args.get(0));
			if(args.size() == 0) {
				help(m);
			} else if(num > 127 || num < 33) {
				sendMessage(m.getSource(), m.getSender()+": ASCII value must be more than 32, and less than 128.");
			} else if(args.size() > 1) {
				sendMessage(m.getSource(), m.getSender()+": One integer at a time please!");
			} else {
				sendMessage(m.getSource(), m.getSender()+": "+(char)num);
			}
		} catch(Exception e) {
			sendMessage(m.getSource(), m.getSender()+": Argument must be an integer.");
		}
	}

}
