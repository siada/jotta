package com.pie.jotta.util.command;

import static com.pie.jotta.net.IRCMethods.sendMessage;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.pie.jotta.event.IRCMessage;

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

public class nslookup implements Command {

	public void parse(IRCMessage m) {
		try {
			InetAddress[] addrs = InetAddress.getAllByName(m.getMessageArgs().get(0));
			StringBuilder str = new StringBuilder();
			for(int i=0;i<addrs.length;i++) {
				if(addrs[i].getHostAddress().equals("67.215.65.132")) {
					str.append("Unknown address..");
				} else {
					str.append(addrs[i].getHostAddress()+", ");
				}
			}
			sendMessage(m.getSource(), str.toString().substring(0, str.length()-2));
		} catch(UnknownHostException ue) {
			sendMessage(m.getSource(), ue.getMessage());
		}
	}
	
	public void help(IRCMessage m) {
		sendMessage(m.getSource(), "Help is currently unavailable for this plugin.");
	}
	
}
