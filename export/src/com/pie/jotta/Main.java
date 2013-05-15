package com.pie.jotta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.pie.jotta.event.PingListener;
import com.pie.jotta.net.IRCSocket;
import com.pie.jotta.util.logger.Logger;

import javax.swing.Timer;
//import java.util.TimerTask;

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

public class Main implements ActionListener {

	public static void main(String args[]) {
		new Main();
		new Timer(5000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(System.currentTimeMillis()-PingListener.pingTimes[1] >= PingListener.pingDifference) {
					System.exit(0);
				}
			}
		}).start();

	}
	
	public Main() {
		Timer t = new Timer(300000, this);
		t.start();
		new IRCSocket().connect();
	}
	
	public void actionPerformed(ActionEvent e) {
		new Thread(new Runnable() {
			public void run() {
				System.gc();
				Logger.getInstance().log("[SYSTEM] Garbage Collection.");
			}
		}).start();
	}
	
}
