package org.bspoones.zephyr.commands

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import org.bspoones.zeus.command.Command
import org.bspoones.zeus.command.annotations.CommandOption
import org.bspoones.zeus.command.annotations.command.SlashCommand

object TestCommand : Command() {
    @SlashCommand("test", "Test command")
    fun onTestCommand(
        event: SlashCommandInteractionEvent,

        @CommandOption("test","Test choice", isRequired = false)
        choice: String?
    ) {
    }
}

