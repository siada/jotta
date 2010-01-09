package com.pie.jotta.net;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;

import com.pie.jotta.Constants;
import com.pie.jotta.util.logger.Logger;

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

public class IRCMethods {

	public static HashMap<String,String> lastFmNicks = new HashMap<String,String>();
	public static HashMap<String,HashMap<String,Object>> channels = new HashMap<String,HashMap<String,Object>>();
	
	public static void sendMessage(String dest, String message) {
		IRCSocket.getOutputStream().println("PRIVMSG "+dest+" :"+message);
		Logger.getInstance().log("<"+Constants.BOT_NICK+"> ("+dest+") "+message);
	}
	
	public static void joinChan(String chan) {
		IRCSocket.getOutputStream().println("JOIN #"+chan);
	}

	public static void ping(String dest) {
		IRCSocket.getOutputStream().println("PONG "+dest);
	}

	public static void buildLastFmNicks() {
		try {
			int count = 0;
			BufferedReader in = new BufferedReader(new FileReader("./data/lastfm.dat"));
			String line = null;
			while((line = in.readLine()) != null) {
				String[] split = line.split("-");
				lastFmNicks.put(split[0], split[1]);
				count++;
			}
			Logger.getInstance().log("Loaded "+count+" users into Last.FM nick list");
			in.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeLastFmNicks() {
		try {
			FileWriter out = new FileWriter("./data/lastfm.dat");
			Iterator<String> i = lastFmNicks.keySet().iterator();
			while(i.hasNext()) {
				String n = i.next();
				out.append(n+"-"+lastFmNicks.get(n)+"\n");
			}
			out.flush();
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void changeLastFmNick(String oldNick, String newNick) {
		if(lastFmNicks.containsKey(oldNick)) {
			lastFmNicks.put(newNick, lastFmNicks.get(oldNick));
			lastFmNicks.remove(oldNick);
		}
	}
	
	public static int getAccessLevel(String nick) {
		int access = 0;
		switch(nick.substring(0,1).toCharArray()[0]) {
		
		case '~':
			access = 9999;
		break;
		
		case '&':
			access = 10;
		break;
		
		case '@':
			access = 5;
		break;
		
		case '%':
			access = 4;
		break;
		
		case '+':
			access = 3;
		break;
		
		default: 
			access = 0;
		break;
		
		}
		return access;
	}
	
}
