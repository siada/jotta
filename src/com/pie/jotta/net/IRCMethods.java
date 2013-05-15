package com.pie.jotta.net;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
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

	public static final HashMap<String,String> lastFmNicks = new HashMap<String,String>();
	public static FileReader markovFile;
	public static final ArrayList<String> badWords = new ArrayList<String>();
	
	public static void sendMessage(String dest, String message) {
		IRCSocket.getOutputStream().println("PRIVMSG "+dest+" :"+message);
		Logger.getInstance().log("<"+Constants.BOT_NICK+"> ("+dest+") "+message);
	}
	
	public static void joinChan(String chan) {
		IRCSocket.getOutputStream().println("JOIN "+((chan.charAt(0) == '#') ? chan : "#"+chan));
	}

	public static void partChan(String chan) {
		IRCSocket.getOutputStream().println("PART "+((chan.charAt(0) == '#') ? chan : "#"+chan));
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
	
	public static char getAccessChar(int level) {
		char oper = ' ';
		switch(level) {
		
		case 9999:
			oper = '~';
		break;
		
		case 10:
			oper = '&';
		break;
			
		case 5:
			oper = '@';
		break;
		
		case 4:
			oper = '%';
		break;
		
		case 3:
			oper = '+';
		break;
		
		default:
			oper = ' ';
		break;
		
		}
		return oper;
	}
	
	public static String getDayName(int day) {
		String dayStr = null;
		if(day > 7 || day < 0) {
			dayStr =  "Day must be between 0 and 7";
		} else {
			switch(day) {
			
			case 1:
				dayStr = "Sunday";
				break;
			
			case 2:
				dayStr = "Monday";
				break;
			
			case 3:
				dayStr = "Tuesday";
				break;
			
			case 4:
				dayStr = "Wednesday";
				break;
			
			case 5:
				dayStr = "Thursday";
				break;
			
			case 6:
				dayStr = "Friday";
				break;

			case 7:
				dayStr = "Saturday";
				break;
				
			default:
				dayStr = "Not found";
			break;
				
			}
		}
		return dayStr;
	}
	
}
