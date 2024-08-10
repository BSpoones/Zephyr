package org.bspoones.zephyr.commands.purge

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.MessageHistory
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent
import org.bspoones.zephyr.utils.sendErrorMessage
import org.bspoones.zephyr.utils.sendGuildOnlyError
import org.bspoones.zeus.command.Command
import org.bspoones.zeus.command.annotations.command.context.MessageContextCommand
import org.bspoones.zeus.command.annotations.command.context.UserContextCommand
import org.bspoones.zeus.command.annotations.permission.Permissions
import org.bspoones.zeus.config.getConfig
import org.bspoones.zeus.extensions.commaSeperated
import kotlin.math.max


object PurgeCommand : Command() {

    @MessageContextCommand("Purge from this message")
    @Permissions(permissions = [Permission.MESSAGE_MANAGE])
    fun purgeMessageCommand(
        event: MessageContextInteractionEvent
    ) {
        messageContextPurge(event, false)
    }

    @MessageContextCommand("Purge User's messages from here")
    @Permissions(permissions = [Permission.MESSAGE_MANAGE])
    fun purgeMessageTargetCommand(
        event: MessageContextInteractionEvent
    ) {
        messageContextPurge(event, true)
    }

    @UserContextCommand("Purge User's Messages")
    @Permissions(permissions = [Permission.MESSAGE_MANAGE])
    fun purgeUserCommand(
        event: UserContextInteractionEvent
    ) {
        val config = getConfig<PurgeCommandConfig>()
        if (event.guild == null || !event.channelType.isMessage) {
            sendGuildOnlyError(event)
            return
        }
        val channel = event.messageChannel

        val amountModal = config.userPurgeWarnModal.toModal(
            "user", event.target.name
        ) { amountEvent ->
            val amountStr = amountEvent.getValue("user-purge-messages")?.asString ?: "a"
            val amount = try {
                amountStr.toInt()
            } catch (e: Exception) {
                sendErrorMessage(event, "Please select a valid amount!")
                return@toModal
            }

            amountEvent.deferReply(true)

            channel.history.retrievePast(minOf(amount, config.maxMessages)).queue { history ->
                val messages = history.filter { it.author.id == event.target.id }
                channel.purgeMessages(messages)
                amountEvent.reply(
                    config.userPurgeSuccessEmbed.autoEmbed(
                        event.user,
                        event.member,
                        "user", event.target.name,
                        "amount", messages.size.commaSeperated(),
                        "channel", channel.asMention
                    ).toMessageCreateData()
                ).setEphemeral(true).queue()
            }
        }

        event.replyModal(amountModal).queue()
    }

    private fun messageContextPurge(
        event: MessageContextInteractionEvent,
        passUser: Boolean
    ) {
        val config = getConfig<PurgeCommandConfig>()
        val messageId = event.target.id
        if (event.guild == null || !event.channelType.isMessage) {
            sendGuildOnlyError(event)
            return
        }
        val channel = event.guildChannel

        MessageHistory.getHistoryAfter(channel, messageId).queue { history ->
            val historyMessages = if (passUser) {
                history.retrievedHistory.filter { it.author.id == event.target.author.id }
            } else {
                history.retrievedHistory
            }
            // Since original messages isn't included, one-message purges will appear to have no user
            val users = max(1, historyMessages.map { it.author.id }.toSet().size)
            val messages = history.size() + 1

            val modal = config.messagePurgeWarnModal.toConfigModal(
                "user", if (passUser) " from @${event.target.author.name}" else ""
            ) { modalEvent ->

                channel.purgeMessages(historyMessages)
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
            }.toModal()
            event.replyModal(modal).queue()
        }
    }



}