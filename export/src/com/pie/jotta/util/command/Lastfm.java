package com.pie.jotta.util.command;

import static com.pie.jotta.net.IRCMethods.sendMessage;

import java.util.ArrayList;
import java.util.Iterator;

import net.roarsoftware.lastfm.Track;
import net.roarsoftware.lastfm.User;

import com.pie.jotta.Constants;
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

public class Lastfm implements Command {

	public void parse(IRCMessage m) {
		ArrayList<String> args = m.getMessageArgs();
		if(args.size() == 0) {
			String user = IRCMethods.lastFmNicks.get(m.getSender());
			try {
				Track track = (Track)User.getRecentTracks(user, 1,
						"34b800583b555c935321ff7d4822c72f").iterator().next();
				
				sendMessage(m.getSource(), m.getSender()+": "+user+" listened to: "+
						track.getName()+" - "+track.getArtist());
				
			} catch(Exception e) {
				sendMessage(m.getSource(), m.getSender()+": User set does not exist");
				e.printStackTrace();
			}
		} else {
			Iterator<Track> i = User.getRecentTracks(args.get(0), 1, Constants.API_KEY).iterator();
			if(i.hasNext()) {
				Track track = (Track)i.next();
				sendMessage(m.getSource(), m.getSender()+": "+args.get(0)+
						" listened to: "+track.getName()+" - "+track.getArtist());
			} else {
				sendMessage(m.getSource(), "Unable to find user.");
			}
		}
	}

	public void help(IRCMessage m) {
		sendMessage(m.getSender(), "Usage: "+Constants.CMD_PREFIX+"lastfm "+
				"[last.fm user] OR "+Constants.CMD_PREFIX+"lastfm set "+
				"{last.fm user}");
	}
	
}
