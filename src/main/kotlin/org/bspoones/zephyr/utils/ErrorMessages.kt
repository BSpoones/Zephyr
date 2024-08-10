package org.bspoones.zephyr.utils

import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import org.bspoones.zephyr.config.ErrorConfig
import org.bspoones.zeus.config.getConfig
import org.bspoones.zeus.embed.AutoEmbed
import org.bspoones.zeus.embed.EmbedType
import java.awt.Color

fun sendErrorMessage(event: GenericCommandInteractionEvent, message: String) =
    ErrorMessages.replyBaseErrorEmbed(event, message)

fun sendGuildOnlyError(event: GenericCommandInteractionEvent) = ErrorMessages.sendGuildOnlyError(event)

object ErrorMessages {

    fun sendGuildOnlyError(event: GenericCommandInteractionEvent) {
        val embed = getConfig<ErrorConfig>().guildOnlyMessageEmbed.autoEmbed(event.user, event.member).toEmbed()
        event.replyEmbeds(embed).setEphemeral(true).queue()
    }


    fun replyBaseErrorEmbed(
        event: GenericCommandInteractionEvent,
        message: String
    ) {
        val embed = AutoEmbed.Builder()
            .setEmbedType(EmbedType.CONTEXT)
            .setSenderUser(event.user)
            .setSenderMember(event.member)
            .setColor(Color.RED)
            .setTitle("Error")
            .setDescription(message)
            .build()
            .toEmbed()

        event.replyEmbeds(embed).setEphemeral(true).queue()
    }


    fun dataNotFoundEmbed(
        notFoundItem: String,
        notFoundDescription: String,
        event: SlashCommandInteractionEvent
    ) {
        AutoEmbed.Builder(event)
            .setColor(Color.RED)
            .setTitle("$notFoundItem not found")
            .setDescription(notFoundDescription)
            .replyEmbed(true)
    }

}