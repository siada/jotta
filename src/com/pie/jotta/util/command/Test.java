package com.pie.jotta.util.command;

import com.pie.jotta.Constants;
import com.pie.jotta.event.IRCMessage;

import static com.pie.jotta.net.IRCMethods.sendMessage;

public class Test implements Command {

	public void help(IRCMessage m) throws Exception {
		sendMessage(m.getSource(), "There is no help for this command");
	}

	public void parse(IRCMessage m) throws Exception {
		sendMessage(m.getSource(), m.getSender()+": " + Constants.BOT_NICK + " is working!");
	}

}
