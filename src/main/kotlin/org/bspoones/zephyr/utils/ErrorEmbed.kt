package org.bspoones.zephyr.utils

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import org.bspoones.zeus.core.embed.AutoEmbed
import java.awt.Color

object ErrorEmbed {

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