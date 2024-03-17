package org.bspoones.zephyr.utils

import java.util.logging.Logger

object ZLogger {

    private val LOGGER = Logger.getLogger("Zephyr")

    fun info(message: String) {
        log(message)
    }


    private fun log(message: String) {
        println(message)
    }


}