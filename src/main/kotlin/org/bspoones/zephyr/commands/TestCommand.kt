package org.bspoones.zephyr.commands

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import org.bspoones.zephyr.core.command.SlashCommand
import org.bspoones.zephyr.core.command.SlashCommandHandler

object TestCommand: SlashCommandHandler() {
    @SlashCommand("test")
    private fun onTestCommand(event: SlashCommandInteractionEvent) {
        event.channel.sendMessage("TEST")
    }
}