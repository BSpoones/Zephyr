package org.bspoones.zephyr.config

import org.bspoones.zeus.command.config.CommandConfig
import org.bspoones.zeus.command.config.SerialisedCommand
import org.bspoones.zeus.command.enums.CommandType
import org.bspoones.zeus.embed.EmbedType
import org.bspoones.zeus.embed.config.ConfigEmbed
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
class TestCommandConfig: CommandConfig() {

    override val guildIds: MutableList<Long> = mutableListOf(
        937749038856536125
    )

    override val commands: MutableList<SerialisedCommand> = mutableListOf(
        SerialisedCommand(
            "test",
            "Test command",
            mutableListOf(
                CommandType.SLASH,
            ),
            mutableListOf(),
            false,
            false,
            "Test message",
            mutableListOf(
                ConfigEmbed(
                    title = "Test",
                    description = mutableListOf(
                        "Test"
                    )
                )
            )
        )
    )

}