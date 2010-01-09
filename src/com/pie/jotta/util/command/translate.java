package com.pie.jotta.util.command;

import java.net.URL;

import org.json.JSONObject;
import org.json.JSONUrl;

import com.pie.jotta.event.IRCMessage;

import static com.pie.jotta.net.IRCMethods.sendMessage;

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

public class translate implements Command {

	public void parse(IRCMessage m) {
		String toTranslate = "";
		for(String s : m.getMessageArgs()) {
			toTranslate += "%20"+s;
		}
		try {
			JSONObject jo = JSONUrl.getStringFromURL(new URL("http://ajax.googleapis.com/ajax/services/language/translate?v=1.0&q="+toTranslate+"&langpair=|en"));
			String translation = jo.getJSONObject("responseData").getString("translatedText");
			if(!translation.equals(toTranslate.replaceAll("%20", " "))) {
				sendMessage(m.getSource(), jo.getJSONObject("responseData").getString("translatedText"));	
			} else {
				sendMessage(m.getSource(), "Unablet to find a suitable translation.");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void help(IRCMessage m) {
		
	}
	
}
