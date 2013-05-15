package com.pie.jotta.util.command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.pie.jotta.Constants;
import com.pie.jotta.event.IRCMessage;

import static com.pie.jotta.net.IRCMethods.sendMessage;

public class Time implements Command {

	@Override
	public void help(IRCMessage m) throws Exception {
		sendMessage(m.getSource(), "Usage: "+Constants.CMD_PREFIX+"time "+
				"[timezone]");
	}

	public void parse(IRCMessage m) throws Exception {
		ArrayList<String> args = m.getMessageArgs();
		String[] ids = TimeZone.getAvailableIDs();
		if(args.size() == 0) {
			TimeZone tz = TimeZone.getDefault();
			GregorianCalendar cal = new GregorianCalendar(tz);
			
			SimpleDateFormat sdf = new SimpleDateFormat("EEEE MMMM dd, "+
					"KK:mm:ssaa");
			
			sdf.setCalendar(cal);
			String d = sdf.format(new Date());
			
			sendMessage(m.getSource(), m.getSender()+": "+d.toString()+" "+
					tz.getID()+"("+tz.getDisplayName()+").");
			
		} else if(args.get(0).equals("zonelist")) {
			for(String str : ids) {
				System.out.println(str);
			}
		} else {
			String timezone = null;
			for(String str : ids) {
				if(str.equalsIgnoreCase(args.get(0))) {
					timezone = str;
				}
			}
			if(timezone == null) {
				sendMessage(m.getSource(), m.getSender()+": Unable to find "+
						"timezone.");
			} else {
				TimeZone tz = TimeZone.getTimeZone(timezone);
				GregorianCalendar cal = new GregorianCalendar(tz);
				
				SimpleDateFormat sdf = new SimpleDateFormat("EEEE MMMM dd, "+
						"KK:mm:ssaa");
				
				sdf.setCalendar(cal);
				String d = sdf.format(new Date());
				
				sendMessage(m.getSource(), m.getSender()+": "+d.toString()+" "+
						tz.getID()+"("+tz.getDisplayName()+").");
			}
		}
	}
}
