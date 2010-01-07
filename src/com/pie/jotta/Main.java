package com.pie.jotta;

import com.pie.jotta.net.IRCSocket;
import java.util.Timer;
import java.util.TimerTask;

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

public class Main {

	public static void main(String args[]) {
		new Thread(new Runnable() {
			public void run() {
				Timer t = new Timer();
				t.scheduleAtFixedRate(new TimerTask() {
					public void run() {
						System.gc();
						System.out.println("[System] Garbage collection.");
					}
				}, 0, 3000000);
			};
		});
		new IRCSocket().connect();
	}
}
