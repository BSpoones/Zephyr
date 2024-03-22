package org.bspoones.zephyr.core.command

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.bspoones.zephyr.core.ZephyrBot
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.findAnnotation

open class Command : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val commandName = event.name
        println(commandName)
        val commands = ZephyrBot.commands
        commands.forEach {
            val methods = it.declaredMemberFunctions
            methods.forEach method@ { method ->
                println("METHOD: ${method.name}")
                val slashCommand = method.findAnnotation<SlashCommand>() ?: return@method
                println("TGESAG")
                if (slashCommand.name == commandName) {
                    println("COMMAND NAME FOUND!")
                    val args = mutableListOf<Any>()
                    val parameters = method.parameters
                    parameters.forEach inner@{ parameter ->
                        val optionAnnotation = parameter.findAnnotation<Option>() ?: return@inner
                        val value = event.getOption(optionAnnotation.name)
                        if (value != null) {
                            args.add(value.asString) // Adjust this to match the parameter type
                        }
                    }
                    method.call(this, event, *args.toTypedArray())
                    return
                }
            }
        }
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {

    }


}