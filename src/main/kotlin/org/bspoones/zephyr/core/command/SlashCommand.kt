package org.bspoones.zephyr.core.command

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class SlashCommand(val name: String)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class SlashCommandInput(val input: String)

open class SlashCommandHandler : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val commandName = event.name
        val methods = this.javaClass.methods
        methods.forEach {method ->
            val slashCommand = method.getAnnotation(SlashCommand::class.java) ?: return@forEach
            if (slashCommand.name == commandName) {
                method.invoke(this, event)
                return
            }
        }
    }
}