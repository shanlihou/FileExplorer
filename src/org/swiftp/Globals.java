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
import android.util.Log;

import java.io.File;

import android.content.Context;

public class Globals {
    private static Context context;
    private static String lastError;
    private static File chrootDir = new File(Defaults.chrootDir);
    private static ProxyConnector proxyConnector = null;
    private static String username = null;

    public static ProxyConnector getProxyConnector() {Log.d("shanlihou", "../../mifile//src/org/swiftp/Globals.java: getProxyConnector");
        if(proxyConnector != null) {
            if(!proxyConnector.isAlive()) {
                return null;
            }
        }
        return proxyConnector;
    }

    public static void setProxyConnector(ProxyConnector proxyConnector) {Log.d("shanlihou", "../../mifile//src/org/swiftp/Globals.java: setProxyConnector");
        Globals.proxyConnector = proxyConnector;
    }

    public static File getChrootDir() {Log.d("shanlihou", "../../mifile//src/org/swiftp/Globals.java: getChrootDir");
        return chrootDir;
    }

    public static void setChrootDir(File chrootDir) {Log.d("shanlihou", "../../mifile//src/org/swiftp/Globals.java: setChrootDir");
        if(chrootDir.isDirectory()) {
            Globals.chrootDir = chrootDir;
        }
    }

    public static String getLastError() {Log.d("shanlihou", "../../mifile//src/org/swiftp/Globals.java: getLastError");
        return lastError;
    }

    public static void setLastError(String lastError) {Log.d("shanlihou", "../../mifile//src/org/swiftp/Globals.java: setLastError");
        Globals.lastError = lastError;
    }

    public static Context getContext() {Log.d("shanlihou", "../../mifile//src/org/swiftp/Globals.java: getContext");
        return context;
    }

    public static void setContext(Context context) {Log.d("shanlihou", "../../mifile//src/org/swiftp/Globals.java: setContext");
        if(context != null) {
            Globals.context = context;
        }
    }

    public static String getUsername() {Log.d("shanlihou", "../../mifile//src/org/swiftp/Globals.java: getUsername");
        return username;
    }

    public static void setUsername(String username) {Log.d("shanlihou", "../../mifile//src/org/swiftp/Globals.java: setUsername");
        Globals.username = username;
    }

}
