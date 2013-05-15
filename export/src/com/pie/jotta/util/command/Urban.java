package com.pie.jotta.util.command;

import static com.pie.jotta.net.IRCMethods.sendMessage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import com.pie.jotta.event.IRCMessage;

public class Urban implements Command {

	@Override
	public void help(IRCMessage m) throws Exception {
		// TODO Auto-generated method stub

	}

	public void parse(IRCMessage m) throws Exception {
		ArrayList<String> args = m.getMessageArgs();
		if(args.size() == 0) {
			URL u = new URL("http://www.urbandictionary.com/iphone/search/random");
			BufferedReader urlReader = new BufferedReader(new InputStreamReader(u.openStream()));
			ArrayList<String> lines = new ArrayList<String>();
			String line = null;
			while((line = urlReader.readLine()) != null) {
				lines.add(line);
			}
			String word = lines.get(44);
			String def = lines.get(47);
			sendMessage(m.getSource(), m.getSender()+": "+word+" - "+def);
		} else {
			StringBuilder def = new StringBuilder();
			for(String word : args) {
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
				sendMessage(m.getSource(), m.getSender()+": Word not defined.");
			} else {
				lines.add(48, lines.get(48).replaceAll("&quot;", "\""));
				sendMessage(m.getSource(), m.getSender()+": "+lines.get(45)+" - "+lines.get(48));
			}
		}
	}

}
