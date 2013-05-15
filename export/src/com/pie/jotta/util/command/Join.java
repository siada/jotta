package com.pie.jotta.util.command;

import java.util.ArrayList;

import com.pie.jotta.Constants;
import com.pie.jotta.event.IRCMessage;
import com.pie.jotta.net.IRCMethods;

import static com.pie.jotta.net.IRCMethods.sendMessage;

public class Join implements Command {

	public void parse(IRCMessage m) {
		ArrayList<String> args = m.getMessageArgs();
		if(m.getMessageArgs().size() > 0 && 
				m.getSender().equals(Constants.BOT_OWNER)) {
			String chan = args.get(0);
			IRCMethods.joinChan(chan);
			sendMessage(m.getSource(), m.getSender()+": joined channel "+(chan.charAt(0) == '#' ? chan : "#"+chan));
		} else {
			help(m);
		}
	}
	
	public void help(IRCMessage m) {
		sendMessage(m.getSource(), "Usage: "+
				Constants.CMD_PREFIX+"join [#]channel");
	}
	
}
