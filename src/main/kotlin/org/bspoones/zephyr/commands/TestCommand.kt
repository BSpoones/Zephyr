package org.bspoones.zephyr.commands

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder
import org.bspoones.zeus.command.Command
import org.bspoones.zeus.command.annotations.command.SlashCommand
import org.bspoones.zeus.component.button.AutoButton
import org.bspoones.zeus.component.modal.AutoModal
import org.bspoones.zeus.component.modal.AutoModalInput
import org.bspoones.zeus.component.select.AutoSelectMenu

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class StringChoices(
    val choices: Array<String>
)


object TestCommand : Command() {
    @SlashCommand("test", "Test command")
    fun onTestCommand(
        event: SlashCommandInteractionEvent
    ) {

        val menu = AutoSelectMenu.EntityOptionBuilder()
            .customId("test")
            .placeholder("Test")
            .selectTarget(EntitySelectMenu.SelectTarget.ROLE)
            .build()

        val message = MessageCreateBuilder()
            .setContent("Test")
            .addActionRow(menu.toEntityMenu())
            .build()


        event.reply(message).queue()
    }
}

