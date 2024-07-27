package org.bspoones.zephyr.config.command

import org.bspoones.zeus.config.annotations.ConfigDirectory
import org.bspoones.zeus.embed.EmbedType
import org.bspoones.zeus.embed.config.ConfigEmbed
import org.bspoones.zeus.embed.config.ConfigEmbedField
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
@ConfigDirectory("commands")
class PurgeCommandConfig {
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
}