package org.bspoones.zephyr

import org.bspoones.zephyr.core.ZephyrBot
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val LOGGER: Logger = LoggerFactory.getLogger("launcher")

fun main(){
    ZephyrBot().run()
}