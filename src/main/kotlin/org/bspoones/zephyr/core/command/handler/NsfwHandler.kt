package org.bspoones.zephyr.core.command.handler

import net.dv8tion.jda.api.entities.channel.Channel
import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.entities.channel.concrete.NewsChannel
import net.dv8tion.jda.api.entities.channel.concrete.StageChannel
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.bspoones.zephyr.core.command.annotations.NSFW
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.hasAnnotation

object NsfwHandler {
    fun buildNsfw(method: KFunction<*>): Boolean {
        return method.hasAnnotation<NSFW>()
    }

    fun buildNsfw(clazz: KClass<*>): Boolean {
        return clazz.hasAnnotation<NSFW>()
    }

    fun isNsfw(method: KFunction<*>, channel: Channel): Boolean {
        val nsfwChannel = when (channel.type) {
            ChannelType.TEXT -> (channel as TextChannel).isNSFW
            ChannelType.NEWS -> (channel as NewsChannel).isNSFW
            ChannelType.STAGE -> (channel as StageChannel).isNSFW
            else -> false
        }

        return !nsfwChannel && method.hasAnnotation<NSFW>()
    }

    fun nsfwCheck(method: KFunction<*>, event: MessageReceivedEvent): Boolean {
        if (isNsfw(method, event.channel)) {
            event.channel.sendMessage("<@${event.author.id}> you cannot use an NSFW command in this channel!").setMessageReference(event.messageId).queue()
            return true
        }
        return false
    }

    fun nsfwCheck(method: KFunction<*>, event: GenericCommandInteractionEvent) : Boolean{
        if (event.channel == null) return false

        if (isNsfw(method, event.channel!!)) {
            event.reply("This command can only be used in an NSFW channel!").setEphemeral(true).queue()
            return true
        }
        return false
    }

}