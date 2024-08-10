package org.bspoones.zephyr.core

import org.bspoones.zephyr.commands.purge.PurgeCommand
import org.slf4j.LoggerFactory.getLogger
import org.bspoones.zephyr.commands.UtilCommands
import org.bspoones.zephyr.commands.purge.PurgeCommandConfig
import org.bspoones.zephyr.config.ErrorConfig
import org.bspoones.zeus.Zeus
import org.bspoones.zeus.config.initConfig
import org.slf4j.Logger
import kotlin.reflect.KClass

object Zephyr : Zeus(true) {
    private val logger: Logger = getLogger("Zephyr")

    override fun initConfig() {
        initConfig(
            PurgeCommandConfig::class,
            ErrorConfig::class
        )
    }


    override fun getCommands(): List<KClass<*>> {
        return listOf(
            UtilCommands::class,
            PurgeCommand::class
        )
    }

}
