package com.pie.jotta;

import java.math.BigInteger;

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

public class Constants {

	public static final String IRC_SERVER = "iwantddos.co.uk";
	public static final long START_TIME = System.currentTimeMillis();
	public static final int IRC_PORT = 6667;
	public static final String[] BOT_HOME = {"#test"};
	public static final String BOT_NICK = "Jotta2";
	public static final String BOT_NAME = "Jotta V3";
	public static final String PLUGIN_DIR = "bin/com/pie/jotta/util/command";
	public static final char CMD_PREFIX = '$';
	public static final String BOT_OWNER = "Pie`";
	public static final String API_KEY = "34b800583b555c935321ff7d4822c72f";
	public static final String SECRET_KEY = "8a3cf9215dd7e9e86863799620c70c1d";
	public static final String PI = "3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679821480865132823066470938446095505822317253594081284811174502841027019385211055596446229489549303819644288109756659334461284756482337867831652712019091456485669234603486104543266482133936072602491412737245870066063155881748815209209628292540917153643678925903600113305305488204665213841469519415116094";
	public static final BigInteger[] fibSequence = new BigInteger[5001];
	static {
		fibSequence[0] = fibSequence[1] = BigInteger.ONE;
		for(int n=2;n<fibSequence.length;n++) {
			fibSequence[n] = fibSequence[n-1].add(fibSequence[n-2]);
		}
	}
}
