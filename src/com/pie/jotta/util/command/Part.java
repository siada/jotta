package com.pie.jotta.util.command;

import static com.pie.jotta.net.IRCMethods.sendMessage;

import java.util.ArrayList;

import com.pie.jotta.Constants;
import com.pie.jotta.event.IRCMessage;
import com.pie.jotta.net.IRCMethods;

public class Part implements Command {

	public void parse(IRCMessage m) {
		ArrayList<String> args = m.getMessageArgs();
		if(args.size() > 0 && 
				(IRCMethods.getAccessLevel(m.getSender()) >= 5 || 
				m.getSender().equals(Constants.BOT_OWNER))) {
			String chan = args.get(0);
			IRCMethods.partChan(chan);
			sendMessage(m.getSource(), m.getSender()+": joined channel "+(chan.charAt(0) == '#' ? chan : "#"+chan));
		} else {
			help(m);
		}
	}
	
	public void help(IRCMessage m) {
		IRCMethods.sendMessage(m.getSource(), "Usage: "+
				Constants.CMD_PREFIX+"part [#]channel");
	}
	
}
