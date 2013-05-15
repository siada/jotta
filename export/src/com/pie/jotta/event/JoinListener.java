package com.pie.jotta.event;

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

public class JoinListener implements MessageListener {

	public void recieve(IRCMessage message) {
		if(message.getCommand().equals("JOIN")) {
			Logger.getInstance().log(message.getSender()+" has joined "+ message.getMessage());
		}
	}
	
}
