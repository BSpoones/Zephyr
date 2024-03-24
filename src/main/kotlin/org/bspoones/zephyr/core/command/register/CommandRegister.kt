package org.bspoones.zephyr.core.command.register

import net.dv8tion.jda.api.interactions.commands.build.CommandData
import org.bspoones.zephyr.core.ZephyrBot
import org.bspoones.zephyr.core.command.handler.CommandTreeHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import kotlin.reflect.KClass


object CommandRegister {

    private val logger: Logger = getLogger("Zephyr | Command Handler")
    var messagePrefix: String = "!"

    val autoCompleteMap: MutableMap<String, Map<String, List<Any>>> = mutableMapOf()

    private val commandRegistry: MutableList<CommandData> = mutableListOf()

    fun registerCommands(commandClazzes: List<KClass<*>>, guildOnly: Boolean = false) {
        logger.info("Registering ${commandClazzes.size} ${if (guildOnly) "guild" else "global"} commands...")
        commandClazzes.forEach { clazz ->
            CommandTreeHandler.buildCommandTree(clazz).forEach { command ->
                commandRegistry.add(command.setGuildOnly((command.isGuildOnly || guildOnly)))
            }
        }

        registerGlobalCommands()
        registerGuildCommands()

    }

    private fun registerGlobalCommands() {
        ZephyrBot.api.updateCommands().addCommands(commandRegistry.filter { !it.isGuildOnly }).queue()
    }

    private fun registerGuildCommands() {
        ZephyrBot.allowedGuilds.forEach { guildID ->
            val guild = ZephyrBot.api.getGuildById(guildID) ?: return@forEach
            guild.updateCommands().addCommands(commandRegistry.filter { it.isGuildOnly }).complete()

            val guildCommands = guild.retrieveCommands().complete()

            println(guildCommands.map { it.name })
        }
    }
}


