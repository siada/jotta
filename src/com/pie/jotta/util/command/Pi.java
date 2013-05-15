package com.pie.jotta.util.command;

import com.pie.jotta.event.IRCMessage;

import static com.pie.jotta.net.IRCMethods.sendMessage;

public class Pi implements Command {

	public void help(IRCMessage m) throws Exception {
		sendMessage(m.getSource(), "No help is available for this command.");

	}

	public void parse(IRCMessage m) throws Exception {
		sendMessage(m.getSource(), ""+Math.PI);
	}

}
