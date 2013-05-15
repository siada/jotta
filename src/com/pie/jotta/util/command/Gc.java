package com.pie.jotta.util.command;

import com.pie.jotta.event.IRCMessage;
import com.pie.jotta.net.IRCMethods;

public class Gc implements Command {

	public void help(IRCMessage m) throws Exception {
		IRCMethods.sendMessage(m.getSource(), "There is no help available for this command.");

	}

	public void parse(IRCMessage m) throws Exception {
		System.gc();
		IRCMethods.sendMessage(m.getSource(), m.getSender()+": Garbage collected.");
	}

}
