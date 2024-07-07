package org.bspoones.zephyr.core

import org.slf4j.LoggerFactory.getLogger
import org.bspoones.zephyr.commands.UtilCommands
import org.bspoones.zephyr.config.Test
import org.bspoones.zeus.Zeus
import org.bspoones.zeus.config.initConfig
import org.slf4j.Logger
import kotlin.reflect.KClass

object Zephyr : Zeus(true) {
    private val logger: Logger = getLogger("Zephyr")

    override fun initConfig() {

        initConfig(Test::class)
    }


    override fun getCommands(): List<KClass<*>> {
        return listOf(
            UtilCommands::class
        )
    }

}
