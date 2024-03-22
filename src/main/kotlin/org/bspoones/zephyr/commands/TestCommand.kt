package org.bspoones.zephyr.commands

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import org.bspoones.zephyr.core.command.*

object TestCommand : Command() {
    @SlashCommand("test", "Testing command for Zephyr")
    fun onTestCommand(
        event: SlashCommandInteractionEvent
    ) {
        event.channel.sendMessage("TEST")
    }
}