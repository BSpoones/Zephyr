package org.bspoones.zephyr.utils

import org.slf4j.LoggerFactory
import org.slf4j.Logger

class ZephyrLoggerFactory {
    companion object{
        private val logger = LoggerFactory.getLogger(ZephyrLoggerFactory::class.java)
    }

    fun getLogger(name: String): ZephyrLogger{
        return ZephyrLogger(name)
    }
}

class ZephyrLogger(name: String){
    private val logger: Logger = LoggerFactory.getLogger(name)

    fun info(message: String){
        logger.info(message)
    }
}