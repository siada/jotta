package com.pie.jotta.util.command;

import java.util.ArrayList;

import com.pie.jotta.Constants;
import com.pie.jotta.event.IRCMessage;

import static com.pie.jotta.net.IRCMethods.sendMessage;

public class Ord implements Command {

	@Override
	public void help(IRCMessage m) throws Exception {
		sendMessage(m.getSource(), "Usage: "+Constants.CMD_PREFIX+"ord {chracter}");
	}

	@Override
	public void parse(IRCMessage m) throws Exception {
		ArrayList<String> args = m.getMessageArgs();
		if(args == null) {
			help(m);
		} else if(args.size() > 1) {
			if(args.get(0).length() > 1) {
				sendMessage(m.getSource(), m.getSender()+": One character at a time please!");
			}
		} else if(args.size() == 0) {
			sendMessage(m.getSource(), "You must specify a character to grab an id from.");
		} else {
			sendMessage(m.getSource(), m.getSender()+": "+((byte)args.get(0).charAt(0)));
		}
	}

}
