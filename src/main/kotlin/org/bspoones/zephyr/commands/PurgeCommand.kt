package org.bspoones.zephyr.commands

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.MessageHistory
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle
import org.bspoones.zephyr.config.command.PurgeCommandConfig
import org.bspoones.zeus.command.Command
import org.bspoones.zeus.command.annotations.command.context.MessageContextCommand
import org.bspoones.zeus.command.annotations.permission.Permissions
import org.bspoones.zeus.component.modal.AutoModal
import org.bspoones.zeus.component.modal.AutoModalInput
import org.bspoones.zeus.config.getConfig
import org.bspoones.zeus.extensions.commaSeperated

object PurgeCommand : Command() {

    @MessageContextCommand("Purge all messages below")
    @Permissions(permissions = [Permission.MESSAGE_MANAGE])
    fun purgeMessageCommand(
        event: MessageContextInteractionEvent
    ) {
        val config = getConfig<PurgeCommandConfig>()
        val messageId = event.target.id
        val channel = event.channel!!

        MessageHistory.getHistoryAfter(channel, messageId).queue { history ->
            val users = history.retrievedHistory.map { it.author.id }.toSet().size
            val messages = history.size() + 1
            val modal = AutoModal.Builder()
                .setTitle("Click Submit to confirm action!")
                .setInputs(
                    listOf(
                        AutoModalInput(
                            TextInputStyle.PARAGRAPH,
                            "",
                            "🗑️ Confirm message deletion",
                            "Clicking Submit will delete all messages below and including the selected message!",
                            required = false,
                            maxLength = minOf(messages, 4000)
                        )
                    )
                )
                .setOnSubmit { modalEvent ->
                    channel.purgeMessages(history.retrievedHistory)
                    modalEvent.reply(
                        config.purgeSuccessEmbed.autoEmbed(
                            event.user,
                            event.member,
                            "amount", messages.commaSeperated(),
                            "users", users.commaSeperated()
                        ).toMessageCreateData()
                    ).setEphemeral(true).queue {
                        channel.deleteMessageById(messageId).queue()
                    }
                }
                .build().toModal()

            event.replyModal(modal).queue()
        }

    }
}