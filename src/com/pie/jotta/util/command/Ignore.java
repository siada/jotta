package com.pie.jotta.util.command;

import java.util.ArrayList;

import com.pie.jotta.Constants;
import com.pie.jotta.event.IRCMessage;
import com.pie.jotta.util.IgnoreManager;

import static com.pie.jotta.net.IRCMethods.sendMessage;

public class Ignore implements Command {

	public void help(IRCMessage m) throws Exception {
		sendMessage(m.getSource(), "Usage:"+Constants.CMD_PREFIX+"ignore {list|add|remove} {nick}");
	}

	public void parse(IRCMessage m) throws Exception {
		ArrayList<String> args = m.getMessageArgs();
		if(args.size() > 0) {
			if(args.get(0).equals("list")) {
				sendMessage(m.getSource(), m.getSender()+": "+IgnoreManager.list());
			} else if(args.get(0).equals("add") && args.size() > 1) {
				if(IgnoreManager.addToList(args.get(1))) {
					sendMessage(m.getSource(), m.getSender()+": Added to ignore list.");
				} else {
					sendMessage(m.getSource(), m.getSender()+": User already ignored.");
				}
			} else if(args.get(0).equals("remove") && args.size() > 1) {
				if(IgnoreManager.removeFromList(args.get(1))) {
					sendMessage(m.getSource(), m.getSender()+": removed from ignore list.");
				} else {
					sendMessage(m.getSource(), m.getSender()+": User not ignored.");
				}
			}
		}
	}

}
