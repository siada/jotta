package com.pie.jotta.util.command;

import static com.pie.jotta.Constants.CMD_PREFIX;
import static com.pie.jotta.net.IRCMethods.sendMessage;
import net.roarsoftware.lastfm.Track;
import net.roarsoftware.lastfm.User;

import com.pie.jotta.event.IRCMessage;
import com.pie.jotta.net.IRCMethods;

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

public class lastfm implements Command {

	public void parse(IRCMessage m) {
		if(m.getMessage().startsWith(CMD_PREFIX+"lastfm")) {
			if(m.getMessageArgs().size() == 0) {
				String user = IRCMethods.lastFmNicks.get(m.getSender());
				try {
					Track track = (Track)User.getRecentTracks(user, 1, "34b800583b555c935321ff7d4822c72f").iterator().next();
					//sendMessage(m.getSource(), user+" listened to: "+track.getName()+" - "+track.getArtist());
					System.out.println(user+" listened to: "+track.getName()+" - "+track.getArtist());
				} catch(Exception e) {
					//sendMessage(m.getSource(), "User set does not exist");
					System.out.println("User not set.");
					e.printStackTrace();
				}
			} else {
				Track track = (Track)User.getRecentTracks(m.getMessageArgs().get(0), 1, "34b800583b555c935321ff7d4822c72f").iterator().next();
				System.out.println(track.getName()+" - "+track.getArtist());
			}
		}
		
	}

	public void help(IRCMessage m) {
		sendMessage(m.getSource(), "No help defined thus far.");
	}
	
}
