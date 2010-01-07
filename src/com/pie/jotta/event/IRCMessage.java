package com.pie.jotta.event;

import java.util.ArrayList;

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

public class IRCMessage {

	private String in;
	
	public IRCMessage(String in) {
		this.in = in;
	}
	
	public String setMessage(String in) {
		return this.in = in;
	}
	
	public String getSource() {
		if(in.split(" ")[0].charAt(0) == 'P') {
			return in.split(" ")[1];
		} else {
			return (in.split(" ")[2].startsWith("#")) ? in.split(" ")[2] : getSender();
		}
	}
	
	public String getSender() {
		return in.split("!")[0].replace(":", "");
	}
	
	public String getMode() {
		return in.split(" ")[3].charAt(0) == '+' ? in.split(" ")[3] : null;
	}
	
	public String getModeOn() {
		return (in.split(" ").length > 4) && (in.split(" ")[3].charAt(0) != ':') ? in.split(" ")[4] : null;
	}
	
	public String getCommand() {
		return (in.split(" ")[0].charAt(0) == 'P') ? "PING" : in.split(" ")[1];
	}
	
	public String getCmd() {
		return getMessage().split(" ")[0];
	}
	
	public String getMessage() {
		return (in.indexOf(":", 1) > -1) ? in.substring(in.indexOf(":", 1)+1) : null;
	}
	
	public String getHost() {
		return in.split(" ")[0].substring(getSender().length()+2);
	}
	
	public ArrayList<String> getMessageArgs() {
		ArrayList<String> args = new ArrayList<String>();
		for(String arg : getMessage().split(" ")) {
			args.add(arg);
		}
		args.remove(0);
		return args;
	}
	
	public String toString() {
		return this.in;
	}
	
}
