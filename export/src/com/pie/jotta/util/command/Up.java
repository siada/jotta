package com.pie.jotta.util.command;

import static com.pie.jotta.net.IRCMethods.sendMessage;

import com.pie.jotta.Constants;
import com.pie.jotta.event.IRCMessage;

public class Up implements Command {

	public void help(IRCMessage m) throws Exception {
		sendMessage(m.getSource(), "There is no help available for this command.");
	}

	public void parse(IRCMessage m) throws Exception {
		long up = System.currentTimeMillis()-Constants.START_TIME;
		if(up < 60000) {
			sendMessage(m.getSource(), m.getSender()+": "+(up/1000)+" Seconds.");
		} else if(up >= 60000 && up < 3600000) {
			long t = up/60000;
			sendMessage(m.getSource(), m.getSender()+": "+((t==1)?t+" Minute.":t+" Minutes."));
		} else if(up >= 36000000 && up < 86400000) {
			long t = up/3600000;
			sendMessage(m.getSource(), m.getSender()+": "+((t == 1) ? t+" Hour." : t+" Hours."));
		} else {
			long t = up/864000000;
			sendMessage(m.getSource(), m.getSender()+": "+((t==1)?t+" Day.":t+" Days."));
		}

	}

}
