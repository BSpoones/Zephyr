package org.bspoones.zephyr.commands

import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.entities.emoji.EmojiUnion
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import org.bspoones.zephyr.config.Test
import org.bspoones.zephyr.utils.ErrorEmbed
import org.bspoones.zeus.config.getConfig
import org.bspoones.zeus.core.command.Command
import org.bspoones.zeus.core.command.annotations.CommandOption
import org.bspoones.zeus.core.command.annotations.command.SlashCommand
import org.bspoones.zeus.core.embed.AutoEmbed
import org.slf4j.LoggerFactory
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlin.math.log

const val UNICODE_EMOJI_URL = "https://raw.githubusercontent.com/discord/twemoji/master/assets/72x72/%s.png"


object UtilCommands : Command() {
    private val logger = LoggerFactory.getLogger("UtilCommands")

    @SlashCommand("big", "Enlarge an emoji")
    fun bigCommand(
        event: SlashCommandInteractionEvent,
        @CommandOption("emoji", "Emoji to enlarge")
        emojiInput: String
    ) {

        val emoji: EmojiUnion = Emoji.fromFormatted(emojiInput)

        val emojiUrl = when (emoji.type) {
            Emoji.Type.UNICODE -> {
                val imageUrl = UNICODE_EMOJI_URL.format(emoji.asUnicode().asCodepoints.replace("U+", ""))
                if (!checkUrl(imageUrl)) {
                    ErrorEmbed.dataNotFoundEmbed("Emoji", "`$emojiInput` not found", event)
                    return
                }
                imageUrl
            }

            Emoji.Type.CUSTOM -> {
                emoji.asCustom().imageUrl
            }
        }

        AutoEmbed.Builder(event)
            .setTitle("Showing an enlarged `${emoji.name}`")
            .setImageUrl(emojiUrl)
            .replyEmbed()
    }

    @SlashCommand("test", "Enlarge an emoji")
    fun testCommand(
        event: SlashCommandInteractionEvent,
    ) {

        AutoEmbed.Builder(event)
            .setTitle("Reading from config file")
            .setDescription(getConfig<Test>().t)
            .replyEmbed()
    }



    private fun checkUrl(imageUrl: String): Boolean {
        val url = URL(imageUrl)
        val connection = url.openConnection() as HttpsURLConnection
        connection.requestMethod = "GET"

        return (connection.responseCode == HttpsURLConnection.HTTP_OK)
    }


}
