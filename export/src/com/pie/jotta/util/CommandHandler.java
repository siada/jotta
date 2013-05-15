package com.pie.jotta.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import net.roarsoftware.lastfm.User;
import net.roarsoftware.lastfm.Track;

import com.pie.jotta.Constants;
import com.pie.jotta.event.IRCMessage;
import com.pie.jotta.net.IRCMethods;

import static com.pie.jotta.net.IRCMethods.sendMessage;
import static com.pie.jotta.net.IRCMethods.joinChan;
import static com.pie.jotta.Constants.CMD_PREFIX;
import static com.pie.jotta.Constants.PI;

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

public class CommandHandler {
	
	public static void parse(IRCMessage command) {
		if(command.getMessage().startsWith(CMD_PREFIX+"test")) {
			sendMessage(command.getSource(), "Success!");
		} else if(command.getMessage().startsWith(CMD_PREFIX+"argtest")) {
			sendMessage(command.getSource(), command.getMessageArgs().toString());
		} else if(command.getMessage().startsWith(CMD_PREFIX+"ignore") && command.getHost().contains("accidently.a.whole.vhost")) {
			if(IgnoreManager.addToList(command.getMessageArgs().get(0))) {
				sendMessage(command.getSource(), "Successfully added "+command.getMessageArgs().get(0)+" to ignores list.");
			} else {
				sendMessage(command.getSource(), "User already ignored.");
			}
		} else if(command.getMessage().startsWith(CMD_PREFIX+"unignore") && command.getHost().contains("accidently.a.whole.vhost")) {
			if(IgnoreManager.removeFromList(command.getMessageArgs().get(0))) {
				sendMessage(command.getSource(), "Successfully removed "+command.getMessageArgs().get(0)+" from ignores list.");
			} else {
				sendMessage(command.getSource(), "User isn't on the ignores list");
			}
		} else if(command.getMessage().startsWith(CMD_PREFIX+"list")) {
			sendMessage(command.getSource(), IgnoreManager.list().toString());
		} else if(command.getMessage().startsWith(CMD_PREFIX+"myhost")) {
			sendMessage(command.getSource(), command.getHost());
		} else if(command.getMessage().startsWith(CMD_PREFIX+"nslookup")) {
			try {
				InetAddress[] addrs = InetAddress.getAllByName(command.getMessageArgs().get(0));
				StringBuilder str = new StringBuilder();
				for(int i=0;i<addrs.length;i++) {
					if(addrs[i].getHostAddress().equals("67.215.65.132")) {
						str.append("Unknown address..");
					} else {
						str.append(addrs[i].getHostAddress()+", ");
					}
				}
				sendMessage(command.getSource(), str.toString().substring(0, str.length()-2));
			} catch(UnknownHostException ue) {
				sendMessage(command.getSource(), ue.getMessage());
			}
		} else if(command.getMessage().startsWith(CMD_PREFIX+"urban")) {
			try {
				StringBuilder def = new StringBuilder();
				for(String word : command.getMessageArgs()) {
					def.append("+"+word);
				}
				URL url = new URL("http://www.urbandictionary.com/iphone/search?term="+def.toString().substring(1));
				BufferedReader urlReader = new BufferedReader(new InputStreamReader(url.openStream()));
				String line = null;
				ArrayList<String> lines = new ArrayList<String>();
				while((line = urlReader.readLine()) != null) {
					lines.add(line);
				}
				if(lines.get(45).equals("<div class='list_item'>") || lines.get(45).equals("<div>")) {
					sendMessage(command.getSource(), "Word not defined.");
				} else {
					lines.add(48, lines.get(48).replaceAll("&quot;", "\""));
					sendMessage(command.getSource(), lines.get(45)+" - "+lines.get(48));
				}
			} catch(Exception e) {
				sendMessage(command.getSource(), e.getMessage());
			}
		} else if(command.getMessage().startsWith(CMD_PREFIX+"lastfm")) {
			if(command.getMessageArgs().size() == 0) {
				String user = IRCMethods.lastFmNicks.get(command.getSender());
				System.out.println(user);
				try {
					Track track = (Track)User.getRecentTracks(user, 1, "34b800583b555c935321ff7d4822c72f").iterator().next();
					sendMessage(command.getSource(), user+" listened to: "+track.getName()+" - "+track.getArtist());
				} catch(Exception e) {
					sendMessage(command.getSource(), "User set does not exist");
					e.printStackTrace();
				}
			} else if(command.getMessageArgs().get(0).equals("set")) {
				sendMessage(command.getSource(), "Added "+command.getMessageArgs().get(1)+" under the nick "+command.getSender());
				IRCMethods.lastFmNicks.put(command.getSender(), command.getMessageArgs().get(1));
				IRCMethods.writeLastFmNicks();
			} else {
				String user = command.getMessageArgs().get(0);
				try {
					Track track = (Track)User.getRecentTracks(user, 1, "34b800583b555c935321ff7d4822c72f").iterator().next();
					sendMessage(command.getSource(), user+" listened to: "+track.getName()+" - "+track.getArtist());
				} catch(Exception e) {
					sendMessage(command.getSource(), "User not found.");
				}
			}
		} else if(command.getMessage().startsWith(CMD_PREFIX+"pi")) {
			int pos = -1;
			if(command.getMessageArgs().size() == 1) {
				try {
					pos = Integer.parseInt(command.getMessageArgs().get(0));
					if(pos > 402) {
						sendMessage(command.getSource(), "Number too large, maximum: 402 places");
					} else {
						sendMessage(command.getSource(), PI.substring(0, pos));
					}
				} catch(Exception e) {
					sendMessage(command.getSource(), "Error: "+e.getMessage());
				}
			} else {
				sendMessage(command.getSource(), PI.substring(0, 10));
			}
		} else if(command.getMessage().startsWith(CMD_PREFIX+"qdb")) {
			
		} else if(command.getMessage().startsWith(CMD_PREFIX+"fib")) {
			int idx = -1;
			try {
				idx = Integer.parseInt(command.getMessageArgs().get(0))	;
				if(command.getMessageArgs().size() >= 1) {
					if(idx <= Constants.fibSequence.length-1 && idx > 0) {
						sendMessage(command.getSource(), ""+Constants.fibSequence[idx+-1]);
					} else {
						sendMessage(command.getSource(), "Please supply a number between 1 & "+(Constants.fibSequence.length));
					}
				}
			} catch(Exception e) {
				sendMessage(command.getSource(), "2nd argument must be a number between 1 and "+(Constants.fibSequence.length));
			}
		} else if(command.getMessage().startsWith(CMD_PREFIX+"rcon")) {
			//sendMessage(command.getSource(), RconPacket.create("127.0.0.1", 6777, "loldix", "status"));
		} else if(command.getMessage().startsWith(CMD_PREFIX+"say")) {
			String line = command.getMessageArgs().get(0);
			line.replaceAll("&quot;","");
			//line.replaceAll("","");
			sendMessage(command.getSource(), line);
		} else if(command.getMessage().startsWith(CMD_PREFIX+"geoip")) {
			try {
				/*URL u = new URL("http://www.geoplugin.net/json.gp?ip=80.176.230.183");
				BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
				JSONArray array = (JSONArray)JSONValue.parse(in.readLine());
				Object[] arr = array.toArray();
				for(int i=0;i<arr.length;i++) {
					System.out.println(arr[i]);
				}*/
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.getMessage().startsWith(CMD_PREFIX+"join")) {
			joinChan(command.getMessageArgs().get(0));
		} else if(command.getMessage().startsWith(CMD_PREFIX+"up")) {
			long up = System.currentTimeMillis()-Constants.START_TIME;
			if(up < 60000) {
				sendMessage(command.getSource(), ""+(up/1000)+" Seconds.");
			} else if(up >= 60000 && up < 3600000) {
				long t = up/60000;
				sendMessage(command.getSource(), (t==1)?t+" Minute.":t+" Minutes.");
			} else if(up >= 36000000 && up < 86400000) {
				long t = up/3600000;
				sendMessage(command.getSource(), (t == 1) ? t+" Hour." : t+" Hours.");
			} else {
				long t = up/864000000;
				sendMessage(command.getSource(), (t==1)?t+" Day.":t+" Days.");
			}
		}
	}
	
}
