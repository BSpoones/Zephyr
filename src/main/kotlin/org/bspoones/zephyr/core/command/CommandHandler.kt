package org.bspoones.zephyr.core.command

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands
import org.bspoones.zephyr.core.ZephyrBot
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import kotlin.reflect.KClass

class CommandRegister(val api: JDA) {

    private val logger: Logger = getLogger("Zephyr | Command Handler")
    fun registerCommands(commands: List<KClass<*>>) {
        logger.info("Loading ${commands.size} command modules")
        commands.forEach {
            register(it)
        }
    }

    private fun register(clazz: KClass<*>) {
        clazz.java.declaredMethods.forEach { method ->
            logger.info(method.name)
            logger.info("SLASH COMMAND FOUND!!!")
            method.getAnnotation(SlashCommand::class.java)?.run {
                logger.info("REGISTERING $name")
                registerCommand(Commands.slash(name, description))
            }
            method.getAnnotation(MessageCommand::class.java)?.run {
                registerCommand(Commands.message(name))
            }
        }
    }

    private fun registerCommand(command: CommandData) {
        logger.info(ZephyrBot.api.guilds.size.toString())
        ZephyrBot.allowedGuilds.forEach { guildID ->
            val guild = api.getGuildById(guildID)
            if (guild == null) {
                logger.error("FUCK")
                return@forEach
            }
            logger.info("GUILD: ${guild.name}")
            guild.updateCommands().addCommands(command).queue()
        }
    }
}