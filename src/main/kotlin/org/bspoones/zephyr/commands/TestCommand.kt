package org.bspoones.zephyr.commands

import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.bspoones.chloris.command.Command
import org.bspoones.chloris.command.annotations.NSFW
import org.bspoones.chloris.command.annotations.Option
import org.bspoones.chloris.command.annotations.SlashCommandGroup
import org.bspoones.chloris.command.annotations.choices.StringChoices
import org.bspoones.chloris.command.annotations.command.MessageCommand
import org.bspoones.chloris.command.annotations.command.SlashCommand
import org.bspoones.chloris.command.annotations.command.context.MessageContextCommand
import org.bspoones.chloris.command.annotations.command.context.UserContextCommand

object TestCommand : Command() {

    // User Context - Right-click a discord user for this
    @UserContextCommand("Click me!")
    fun onUserContextCommand(
        event: UserContextInteractionEvent
    ) {
        println("COMMAND RUNNING")
        event.reply("You clicked me! OW!!!").queue()
    }
    // Message Context - Right-click a discord user for this
    @MessageContextCommand("Click me!222")
    fun onMessageContextCommand(
        event: MessageContextInteractionEvent
    ) {
        println("MESSAGEDEG")
        event.reply("You clicked me! OW!!!").queue()
    }

    @SlashCommand("slash", "Slash Command Example")
    fun onSlashCommand(
        event: SlashCommandInteractionEvent
    ) {
        event.reply("Slash Command").queue()
    }
    @SlashCommand("optionexample", "Slash Command Example")
    fun onSlashCommandOption(
        event: SlashCommandInteractionEvent,
        @Option("option", "An option to choose from", autoComplete = true)
        @StringChoices(["Apple","Banana","Cherry"])
        option: String
    ) {
        event.reply("Slash Option is: $option").queue()
    }

    @MessageCommand("tes234t")
    fun onMessageCommand(
        event: MessageReceivedEvent,
        @Option("Name", "Item name") name: String = "Default"
    ) {
        event.channel.sendMessage("Name: $name").queue()
    }

    @SlashCommandGroup("group", "group")
    object SubCommand {
        @SlashCommand("subcommand", "Testing command for Zephyr")
        @NSFW
        fun onTestCommand(
            event: SlashCommandInteractionEvent,
        ) {
            event.reply("TEST REPLY on subcommandgroup!!!").queue()
        }
    }
}

