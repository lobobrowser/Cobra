package org.cobraparser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CobraParser {

    private static Logger logger = Logger.getLogger(CobraParser.class.getName());
    private static boolean debug = false;

    public static boolean isDebugOn() {
        return CobraParser.debug;
    }

    public static void setDebug(boolean debug) {
        CobraParser.debug = debug;
        logger.log(Level.INFO, "Logging has been " + (debug ? "Enabled" : "Disabled"));
    }

}
