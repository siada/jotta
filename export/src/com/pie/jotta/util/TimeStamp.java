package com.pie.jotta.util;

import java.util.GregorianCalendar;
import java.util.Calendar;

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

public class TimeStamp {

	private static Calendar cal;
	
	private TimeStamp() {
	}

	private static String curTime() {
		cal = new GregorianCalendar();
		String hour = (Integer.toString(cal.get(Calendar.HOUR_OF_DAY)).length() > 1) ? 
				Integer.toString(cal.get(Calendar.HOUR_OF_DAY))
				: "0" + Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
		String min = (Integer.toString(cal.get(Calendar.MINUTE)).length() > 1) ? 
				Integer.toString(cal.get(Calendar.MINUTE))
				: "0" + Integer.toString(cal.get(Calendar.MINUTE));
		String sec = (Integer.toString(cal.get(Calendar.SECOND)).length() > 1) ?
				Integer.toString(cal.get(Calendar.SECOND))
				: "0" + Integer.toString(cal.get(Calendar.SECOND));
		return hour + ":" + min + ":" + sec;
	}

	public static String getCurTime() {
		return curTime();
	}
	
}