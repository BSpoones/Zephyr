package org.bspoones.zephyr.core.command.annotations.choices

import net.dv8tion.jda.api.entities.channel.ChannelType

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class ChannelTypes(
    val channelTypes: Array<ChannelType>
)
