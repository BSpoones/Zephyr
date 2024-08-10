package org.bspoones.zephyr.commands.purge

import net.dv8tion.jda.api.interactions.components.text.TextInputStyle
import org.bspoones.zeus.component.modal.config.ConfigModal
import org.bspoones.zeus.component.modal.config.ConfigModalInput
import org.bspoones.zeus.config.annotations.ConfigDirectory
import org.bspoones.zeus.embed.EmbedType
import org.bspoones.zeus.embed.config.ConfigEmbed
import org.bspoones.zeus.embed.config.ConfigEmbedField
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
@ConfigDirectory("commands")
class PurgeCommandConfig {

    var maxMessages = 1000

    var messagePurgeWarnModal: ConfigModal = ConfigModal(
        title = "Delete Messages<user>",
        inputs = mutableListOf(
            ConfigModalInput(
                inputStyle = TextInputStyle.PARAGRAPH,
                id = "",
                label = "🗑️ Confirm message deletion",
                placeholder = "Clicking Submit will delete all messages below and including the selected message!",
                required = false
            )
        )
    )

    var userPurgeWarnModal: ConfigModal = ConfigModal(
        title = "Click to confirm action!",
        inputs = mutableListOf(
            ConfigModalInput(
                inputStyle = TextInputStyle.SHORT,
                id = "user-purge-messages",
                label = "🗑️ Select amount",
                placeholder = "Enter the amount of messages to search",
                required = true
            ),
            ConfigModalInput(
                inputStyle = TextInputStyle.PARAGRAPH,
                id = "",
                label = "🗑️ Confirm Action",
                placeholder = "Delete <user>'s messages in this channel from the last x messages",
                required = false
            )
        )
    )

    var purgeSuccessEmbed = ConfigEmbed(
        type = EmbedType.CONTEXT,
        title = "Purged!",
        titleUrl = "https://www.youtube.com/watch?v=cRsK49mJ0iA",
        fields = mutableListOf(
            ConfigEmbedField(
                name = "🗑️ Messages Deleted",
                value = "```py\n<amount>\n```",
                inline = true
            ),
            ConfigEmbedField(
                name = "👥 Total Users",
                value = "```py\n<users>\n```",
                inline = true
            )
        ),
    )

    var userPurgeSuccessEmbed = ConfigEmbed(
        type = EmbedType.CONTEXT,
        title = "Purged!",
        description = mutableListOf(
            "`<amount>` messages from `@<user>` in <channel> have been purged!",
        ),
        titleUrl = "https://www.youtube.com/watch?v=cRsK49mJ0iA"
    )
}