package com.pie.jotta.util.command;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.*;

import com.pie.jotta.event.IRCMessage;

public class Youtube implements Command {

	public void parse(IRCMessage m) throws Exception {
		ArrayList<String> args = m.getMessageArgs();
		Pattern p = Pattern.compile("v=(.+)");
		Matcher match = p.matcher(args.get(0));
		if(match.find()) {
			URL u = new URL("http://youtube.com/watch?"+match.group());
			BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
			ArrayList<String> lines = new ArrayList<String>();
			String line = null;
			while((line = in.readLine()) != null) {
				lines.add(line);
			}
			if(lines.size() > 60) {
				System.out.println("Valid youtube video");
			} else {
				System.out.println("Invalid URL");
			}
		} else {
			System.out.println("No match found");
		}
	}
	
	public void help(IRCMessage m) throws Exception {
		
	}
	
}
