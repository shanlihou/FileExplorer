/*
Copyright 2009 David Revell

This file is part of SwiFTP.

SwiFTP is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

SwiFTP is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with SwiFTP.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.swiftp;

import android.content.Context;
import android.util.Log;

public class Defaults {
	protected static int inputBufferSize = 256;
	protected static int dataChunkSize = 65536;  // do file I/O in 64k chunks 
	protected static int sessionMonitorScrollBack = 10;
	protected static int serverLogScrollBack = 10;
	protected static int uiLogLevel = Defaults.release ? Log.INFO : Log.DEBUG;
	protected static int consoleLogLevel = Defaults.release ? Log.INFO : Log.DEBUG;
	protected static String settingsName = "SwiFTP";
	//protected static String username = "user";
	//protected static String password = "";
	public static int portNumber = 2121; 
//	protected static int ipRetrievalAttempts = 5;
	public static final int tcpConnectionBacklog = 5;
	public static final String chrootDir =  net.micode.fileexplorer.Util.getSdDirectory();
	public static final boolean acceptWifi = true;
	public static final boolean acceptNet = false; // don't incur bandwidth charges
	public static final boolean stayAwake = false;
	public static final int REMOTE_PROXY_PORT = 2222;
	public static final String STRING_ENCODING = "UTF-8";
	public static final int SO_TIMEOUT_MS = 30000; // socket timeout millis
	// FTP control sessions should start out in ASCII, according to the RFC.
	// However, many clients don't turn on UTF-8 even though they support it,
	// so we just turn it on by default.
	public static final String SESSION_ENCODING = "UTF-8"; 
	
	// This is a flag that should be true for public builds and false for dev builds
	public static final boolean release = true;
	
	// Try to fix the transfer stall bug, reopen the destination file periodically
	//public static final boolean do_reopen_hack = false;
	//public static final int bytes_between_reopen = 4000000;
	
	// Try to fix the transfer stall bug, flush the file periodically
	//public static final boolean do_flush_hack = false;
	//public static final int bytes_between_flush = 500000;
	
	public static final boolean do_mediascanner_notify = true;
	
	
//	public static int getIpRetrievalAttempts() {Log.d("shanlihou", "../../mifile//src/org/swiftp/Defaults.java: getIpRetrievalAttempts");
//		return ipRetrievalAttempts;
//	}

//	public static void setIpRetrievalAttempts(int ipRetrievalAttempts) {Log.d("shanlihou", "../../mifile//src/org/swiftp/Defaults.java: setIpRetrievalAttempts");
//		Defaults.ipRetrievalAttempts = ipRetrievalAttempts;
//	}

	public static int getPortNumber() {Log.d("shanlihou", "../../mifile//src/org/swiftp/Defaults.java: getPortNumber");
		return portNumber;
	}

	public static void setPortNumber(int portNumber) {Log.d("shanlihou", "../../mifile//src/org/swiftp/Defaults.java: setPortNumber");
		Defaults.portNumber = portNumber;
	}

	public static String getSettingsName() {Log.d("shanlihou", "../../mifile//src/org/swiftp/Defaults.java: getSettingsName");
		return settingsName;
	}

	public static void setSettingsName(String settingsName) {Log.d("shanlihou", "../../mifile//src/org/swiftp/Defaults.java: setSettingsName");
		Defaults.settingsName = settingsName;
	}

	public static int getSettingsMode() {Log.d("shanlihou", "../../mifile//src/org/swiftp/Defaults.java: getSettingsMode");
		return settingsMode;
	}

	public static void setSettingsMode(int settingsMode) {Log.d("shanlihou", "../../mifile//src/org/swiftp/Defaults.java: setSettingsMode");
		Defaults.settingsMode = settingsMode;
	}

	public static void setServerLogScrollBack(int serverLogScrollBack) {Log.d("shanlihou", "../../mifile//src/org/swiftp/Defaults.java: setServerLogScrollBack");
		Defaults.serverLogScrollBack = serverLogScrollBack;
	}

	protected static int settingsMode = Context.MODE_WORLD_WRITEABLE;
	
	public static int getUiLogLevel() {Log.d("shanlihou", "../../mifile//src/org/swiftp/Defaults.java: getUiLogLevel");
		return uiLogLevel;
	}

	public static void setUiLogLevel(int uiLogLevel) {Log.d("shanlihou", "../../mifile//src/org/swiftp/Defaults.java: setUiLogLevel");
		Defaults.uiLogLevel = uiLogLevel;
	}

	public static int getInputBufferSize() {Log.d("shanlihou", "../../mifile//src/org/swiftp/Defaults.java: getInputBufferSize");
		return inputBufferSize;
	}

	public static void setInputBufferSize(int inputBufferSize) {Log.d("shanlihou", "../../mifile//src/org/swiftp/Defaults.java: setInputBufferSize");
		Defaults.inputBufferSize = inputBufferSize;
	}

	public static int getDataChunkSize() {Log.d("shanlihou", "../../mifile//src/org/swiftp/Defaults.java: getDataChunkSize");
		return dataChunkSize;
	}

	public static void setDataChunkSize(int dataChunkSize) {Log.d("shanlihou", "../../mifile//src/org/swiftp/Defaults.java: setDataChunkSize");
		Defaults.dataChunkSize = dataChunkSize;
	}

	public static int getSessionMonitorScrollBack() {Log.d("shanlihou", "../../mifile//src/org/swiftp/Defaults.java: getSessionMonitorScrollBack");
		return sessionMonitorScrollBack;
	}

	public static void setSessionMonitorScrollBack(
			int sessionMonitorScrollBack) 
	{
		Defaults.sessionMonitorScrollBack = sessionMonitorScrollBack;
	}

	public static int getServerLogScrollBack() {Log.d("shanlihou", "../../mifile//src/org/swiftp/Defaults.java: getServerLogScrollBack");
		return serverLogScrollBack;
	}

	public static void setLogScrollBack(int serverLogScrollBack) {Log.d("shanlihou", "../../mifile//src/org/swiftp/Defaults.java: setLogScrollBack");
		Defaults.serverLogScrollBack = serverLogScrollBack;
	}

	public static int getConsoleLogLevel() {Log.d("shanlihou", "../../mifile//src/org/swiftp/Defaults.java: getConsoleLogLevel");
		return consoleLogLevel;
	}

	public static void setConsoleLogLevel(int consoleLogLevel) {Log.d("shanlihou", "../../mifile//src/org/swiftp/Defaults.java: setConsoleLogLevel");
		Defaults.consoleLogLevel = consoleLogLevel;
	}


}
