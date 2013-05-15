package com.pie.jotta.util.command;

import java.util.ArrayList;

import com.pie.jotta.Constants;
import com.pie.jotta.event.IRCMessage;

import static com.pie.jotta.net.IRCMethods.sendMessage;

public class Rot13 implements Command {

    public static String doConvert(String in) {
    	StringBuffer tempReturn = new StringBuffer();
    	int abyte = 0;
    	for (int i=0; i<in.length(); i++) {
    		abyte = in.charAt(i);
    		int cap = abyte & 32;
    		abyte &= ~cap;
    		abyte = ( (abyte >= 'A') && (abyte <= 'Z') ? ((abyte - 'A' + 13) % 26 + 'A') : abyte) | cap;
    		tempReturn.append((char)abyte);
    	}
    	return tempReturn.toString();
    }
	
	public void help(IRCMessage m) throws Exception {
		sendMessage(m.getSource(), "Usage: "+Constants.CMD_PREFIX+"rot13 {encrypt|decrypt} {str}");
	}

	public void parse(IRCMessage m) throws Exception {
		ArrayList<String> args = m.getMessageArgs();
		StringBuffer str = new StringBuffer();
		for(int i=1;i<args.size();i++) {
			str.append(args.get(i)+" ");
		}
		if(args.size() > 0) {
			if(args.get(0).equals("encrypt")) {
				sendMessage(m.getSource(), doConvert(str.toString().trim()));
			} else if(args.get(0).equals("decrypt")) {
				sendMessage(m.getSource(), doConvert(str.toString().trim()));
			} else {
				help(m);
			}
		} else{
			help(m);
		}
	}

}
