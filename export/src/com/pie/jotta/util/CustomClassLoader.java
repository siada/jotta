package com.pie.jotta.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

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

public class CustomClassLoader extends ClassLoader{
	
    public CustomClassLoader(ClassLoader parent) {
        super(parent);
    }

    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if(!name.contains("com.pie.jotta.util.command"))
                return super.loadClass(name);
        try {
        	String[] nameSplit = name.split("[\\.]");
        	name = name.split("[\\.]")[nameSplit.length-1];
			String url = "file:" + System.getProperty("user.dir")
					+ "/bin/com/pie/jotta/util/command/"
					+ (name.endsWith(".class") ? name : name + ".class");
            URL myUrl = new URL(url);
            InputStream input = myUrl.openConnection().getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = input.read();

            while(data != -1){
                buffer.write(data);
                data = input.read();
            }

            input.close();

            byte[] classData = buffer.toByteArray();

            return defineClass("com.pie.jotta.util.command."+name,
                    classData, 0, classData.length);
        } catch(Exception e) {
        	e.printStackTrace();
        }

        return null;
    }

}

