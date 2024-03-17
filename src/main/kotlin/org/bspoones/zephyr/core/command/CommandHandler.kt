package org.bspoones.zephyr.core.command

import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData
import org.bspoones.zephyr.core.ZephyrBot
import kotlin.reflect.KClass

object CommandRegister {

    fun registerCommands(commands: List<KClass<*>>) {
        commands.forEach{
            register(it)
        }
    }

    fun register(clazz: KClass<*>) {
        val methods = clazz.java.methods
        for (method in methods) {
            val annotation = method.getAnnotation(SlashCommand::class.java)
            if (annotation != null) {
                // Call a function to register the command
                val slashCommand = Commands.slash("TEMP", "TEMP")

                registerCommand(slashCommand)
            }
        }
    }

    private fun registerCommand(command: SlashCommandData) {
        ZephyrBot.api.guilds.forEach {
            it.updateCommands().addCommands(command)
        }
    }
}