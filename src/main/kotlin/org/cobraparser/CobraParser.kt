package org.cobraparser

import java.util.logging.Level
import java.util.logging.Logger

object CobraParser {

    private val logger = Logger.getLogger(CobraParser::class.java.name)

    @JvmField
    var isDebugOn = false

    @JvmStatic
    fun setDebug(debug:Boolean) {
        CobraParser.isDebugOn = debug
        logger.log(Level.INFO, "Logging has been " + (if (debug) "Enabled" else "Disabled"))
    }

}