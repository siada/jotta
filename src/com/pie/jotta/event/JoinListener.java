package com.pie.jotta.event;

import java.util.HashMap;

import com.pie.jotta.Constants;
import com.pie.jotta.irc.User;
import com.pie.jotta.net.IRCMethods;
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

public class JoinListener implements MessageListener {

	@SuppressWarnings("unchecked")
	public void recieve(IRCMessage message) {
		if(message.getCommand().equals("JOIN")) {
			if(message.getSender().equals(Constants.BOT_NICK)) {
				System.out.println("Chan1: "+message.getMessage().substring(1));
				IRCMethods.channels.put(message.getMessage().substring(1), new HashMap<String,Object>());
			}
			Logger.getInstance().log(message.getSender()+" has joined "+ message.getMessage());
		} else if(message.getCommand().equals("353")) {
			String chan = message.toString().split(" ")[4].substring(1);
			System.out.println("Chan2: "+chan);
			IRCMethods.channels.get(chan).put("users", new HashMap<String,User>());
			String[] users = message.getMessage().split(" ");
			for(String user : users) {
				System.out.println(user+" - "+ IRCMethods.getAccessLevel(user)+ " - "+user.length());
				if(user.startsWith("@") || user.startsWith("~") || user.startsWith("&") || user.startsWith("%") || user.startsWith("+")) {
					((HashMap<String,User>)IRCMethods.channels.get(chan).get("users")).put(user.substring(1), new User(user, IRCMethods.getAccessLevel(user)));
				} else {
					((HashMap<String,User>)IRCMethods.channels.get(chan).get("users")).put(user, new User(user, IRCMethods.getAccessLevel(user)));
				}
			}
		}
	}
	
}
