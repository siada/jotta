package com.pie.jotta.net;

import java.net.Socket;
import java.io.IOException;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.pie.jotta.Constants;
import com.pie.jotta.event.*;
import com.pie.jotta.util.IgnoreManager;
import com.pie.jotta.util.logger.Logger;
import com.pie.jotta.util.command.CommandLoader;

/*
 *  This file is part of Jotta.
 *
 *  Jotta is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Jotta is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Jotta.  If not, see <http://www.gnu.org/licenses/>.
 */

public class IRCSocket {

	private static Socket s;
	private static PrintStream out;
	private static BufferedReader in;
	private final ArrayList<MessageListener> listeners = new ArrayList<MessageListener>();
	
	public void connect() {
		try {
			addListeners();
			IRCMethods.buildLastFmNicks();
			IgnoreManager.buildIgnoreList();
			Logger.getInstance().log("Connecting...");
			CommandLoader.loadClasses();
			IRCMessage msg = new IRCMessage(null);
			auth("NO LOL");
			while(true) {
				if((msg.setMessage(in.readLine())) == null)
					break;
				synchronized(listeners) {
					for(MessageListener listener : listeners) {
						listener.recieve(msg);
					}
				}
			} 
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void auth(String pass) {
		out.println("NICK " + Constants.BOT_NICK);
		out.println("USER " + Constants.BOT_NICK + " \"\" \"\" :"+Constants.BOT_NAME);
		if(pass != null)
			out.println("PRIVMSG NickServ identify "+ pass);
		for(String chan : Constants.BOT_HOME) {
			out.println("JOIN "+chan);
		}
	}
	
	public void addMessageListener(MessageListener listener) {
		synchronized(listeners) {
			listeners.add(listener);
		}
	}
	
	public void removeMessageListener(MessageListener listener) {
		synchronized(listeners) {
			listeners.add(listener);
		}
	}
	
	public static PrintStream getOutputStream() {
		return out;
	}
	
	public static BufferedReader getInputStream() {
		return in;
	}
	
	public void addListeners() {
		addMessageListener(new PingListener());
		addMessageListener(new PrivMessageListener().build());
		addMessageListener(new QuitListener());
		addMessageListener(new JoinListener());
		addMessageListener(new PartListener());
		addMessageListener(new ModeListener());
		addMessageListener(new NickListener());
	}
	
	public IRCSocket() {
		try {
			s = new Socket(Constants.IRC_SERVER, Constants.IRC_PORT);
			out = new PrintStream(s.getOutputStream());
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch(IOException ioe) {
			//Logger.getInstance().log(ioe.getMessage());
			ioe.printStackTrace();
		}
	}
}
