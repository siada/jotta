package com.pie.jotta.util.command;

import java.net.URL;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONUrl;

import com.pie.jotta.Constants;
import com.pie.jotta.event.IRCMessage;

import static com.pie.jotta.net.IRCMethods.sendMessage;

public class Geoip implements Command {

		public void parse(IRCMessage m) {
			ArrayList<String> args = m.getMessageArgs();
			try {
				if(args.size() > 0) {
					String ip = args.get(0);
					JSONObject obj = JSONUrl.getStringFromURL(new URL("http://www.geoplugin.net/json.gp?ip="+ip));
					String country = obj.getString("geoplugin_countryName");
					String region = obj.getString("geoplugin_region");
					String city = obj.getString("geoplugin_city");
					String currency = obj.getString("geoplugin_currencyCode");
					if(currency == "null") {
						sendMessage(m.getSource(), "d");
					} else {
						sendMessage(m.getSource(), m.getSender()+": Country: "+country+", Region: "+region+", City: "+city+", Currency: "+currency);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		public void help(IRCMessage m) {
			sendMessage(m.getSource(), "Usage: "+Constants.CMD_PREFIX+"geoip "+
					"{ip}");
		}
		
}
