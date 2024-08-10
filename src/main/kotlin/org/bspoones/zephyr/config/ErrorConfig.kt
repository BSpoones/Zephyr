package org.bspoones.zephyr.config

import org.bspoones.zeus.config.annotations.ConfigDirectory
import org.bspoones.zeus.embed.EmbedType
import org.bspoones.zeus.embed.config.ConfigEmbed
import org.bspoones.zeus.embed.config.ConfigEmbedAuthor
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
@ConfigDirectory("error")
class ErrorConfig {

    var guildOnlyMessageEmbed = ConfigEmbed(
        type = EmbedType.CONTEXT,
        author = ConfigEmbedAuthor(
            "⚠️ Error"
        ),
        title = "**Server Only**",
        description = mutableListOf(
            "This command can only be run on a server!"
        ),
        color = "FF0000"
    )



}