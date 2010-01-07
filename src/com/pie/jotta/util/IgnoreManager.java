package com.pie.jotta.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;

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

public class IgnoreManager {

	private static ArrayList<String> ignoreList;
	private static FileOutputStream writer;
	private static PrintStream out;
	private static BufferedReader in;
	private static FileInputStream reader;
	
	public static void buildIgnoreList() {
		try {
			writer = new FileOutputStream("./data/ignores.dat");
			reader = new FileInputStream("./data/ignores.dat");
			out = new PrintStream(writer);
			in = new BufferedReader(new InputStreamReader(reader));
			ignoreList = new ArrayList<String>();
			String line = null;
			while((line = in.readLine()) != null) {
				ignoreList.add(line);
			}
			reader.close();
			in.close();
			Logger.getInstance().log("Loaded "+ignoreList.size()+ " names into ignores list.");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> list() {
		return ignoreList;
	}
	
	public static boolean isIgnored(String user) {
		return (ignoreList.contains(user.toLowerCase()) || ignoreList.contains("*")) && !user.equals("Pie`");
	}
	
	public static boolean changeName(String oldName, String newName) {
			return removeFromList(oldName) && addToList(newName);
	}
	
	public static boolean addToList(String user) {
		if(!ignoreList.contains(user.toLowerCase())) {
			ignoreList.add(user.toLowerCase());
			for(String usern : ignoreList) {
				out.println(usern);	
			}
			return true;
		}
		return false;
	}
	
	public static boolean removeFromList(String user) {
		if(ignoreList.contains(user.toLowerCase())) {
			ignoreList.remove(user.toLowerCase());
			for(String usern : ignoreList) {
				out.println(usern);	
			}
			return true;
		}
		return false;
	}
	
}
