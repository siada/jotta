package com.pie.jotta.util.command;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.pie.jotta.Constants;
import com.pie.jotta.Main;
import com.pie.jotta.util.CustomClassLoader;
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

public class CommandLoader {

	private static HashMap<String, Class<?>> list = new HashMap<String, Class<?>>();
	private static ArrayList<String> classNames = new ArrayList<String>();
	
	public static HashMap<String, Class<?>> list() {
		return list;
	}
	
	public static Class<?> command(String key) {
		if(list.containsKey(key)) {
			return list.get(key);
		} else {
			return null;
		}
	}
	
	public static boolean reloadClass(String name) {
		try {
			if(list.containsKey(Constants.CMD_PREFIX+name.toLowerCase())) {
				if(classNames.contains(name)) {
					CustomClassLoader c = new CustomClassLoader(Main.class.getClassLoader());
					list.remove(Constants.CMD_PREFIX+name);
					list.put(Constants.CMD_PREFIX+name, c.loadClass("com.pie.jotta.util.command."+name));
					return true;
				} else {
					return false;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static int loadClasses() {
		CustomClassLoader c = new CustomClassLoader(Main.class.getClassLoader());
		int i = 0;
		for(Object s : iterateFilesAsList()) {
			try {
				list.put("$"+((String)s).toLowerCase(), c.loadClass("com.pie.jotta.util.command."+(String)s));
				classNames.add((String)s);
				i++;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		Logger.getInstance().log("Loaded "+ i +" plugins.");
		return i;
	}
	
	@SuppressWarnings("deprecation")
	private static Object[] iterateFilesAsList() {
		try {
			File dir = new File(Constants.PLUGIN_DIR);
			String[] fnames = dir.list();
			ArrayList<String> names = new ArrayList<String>();
			for(int i=0;i<fnames.length;i++) {
				URL u = new File(dir.getAbsolutePath()+"\\"+ fnames[i]).toURL();
				String f = u.getFile();
				if(f.endsWith(".class") && !f.contains("Command")) {
					String[] split = f.split("/");
					String[] split2 = split[split.length-1].split("\\\\");
					names.add(split2[split2.length-1].replace(".class", ""));
				}
			}
			return names.toArray();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
