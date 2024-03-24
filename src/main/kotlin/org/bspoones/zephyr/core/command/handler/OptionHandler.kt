package org.bspoones.zephyr.core.command.handler

import net.dv8tion.jda.api.entities.IMentionable
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.Message.Attachment
import net.dv8tion.jda.api.entities.Role
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.entities.channel.Channel
import net.dv8tion.jda.api.interactions.commands.OptionMapping
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import org.bspoones.zephyr.core.ZephyrBot
import org.bspoones.zephyr.core.command.annotations.Option
import org.bspoones.zephyr.core.command.annotations.choices.ChannelTypes
import org.bspoones.zephyr.core.command.register.CommandRegister
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.javaType

val USER_PING_REGEX = "<@!?(\\d+)>".toRegex()
val ROLE_PING_REGEX = "<@&!?(\\d+)>".toRegex()
val CHANNEL_PING_REGEX = "<#!?(\\d+)>".toRegex()
val NUMBER_ONLY_REGEX = "\\d+".toRegex()

object OptionHandler {
    fun buildOptions(method: KFunction<*>, commandName: String): List<OptionData> {
        val options = mutableListOf<OptionData>()

        method.parameters.forEach { parameter ->
            val option = parameter.findAnnotation<Option>() ?: return@forEach
            val optionType = getOptionType(parameter.type)
                ?: throw IllegalArgumentException("${parameter.name} has an invalid parameter type: ${parameter.type}")

            val optionData =
                OptionData(optionType, option.name, option.description, option.isRequired, option.autoComplete)

            if (option.autoComplete) {
                val optionChoice = CommandRegister.autoCompleteMap.getOrDefault(commandName, mapOf()).toMutableMap()
                optionChoice[option.name] = ChoiceHandler.getChoices(parameter)
                CommandRegister.autoCompleteMap[commandName] = optionChoice
            } else {
                optionData.addChoices(ChoiceHandler.buildChoices(parameter))
            }

            val channelTypesAnnotation = parameter.findAnnotation<ChannelTypes>()
            if (channelTypesAnnotation != null) {
                optionData.setChannelTypes(channelTypesAnnotation.channelTypes.toList())
            }

            options.add(optionData)
        }
        return options
    }

    private fun getOptionType(parameterType: KType): OptionType? {
        val javaClass = parameterType.javaType
        return when (javaClass) {
            String::class.java -> OptionType.STRING
            Int::class.java -> OptionType.INTEGER
            Boolean::class.java -> OptionType.BOOLEAN
            Double::class.java -> OptionType.NUMBER
            User::class.java -> OptionType.USER
            Channel::class.java -> OptionType.CHANNEL
            Role::class.java -> OptionType.ROLE
            IMentionable::class.java -> OptionType.MENTIONABLE
            Attachment::class.java -> OptionType.ATTACHMENT
            else -> null
        }
    }

    fun getOptionValue(option: OptionMapping, parameterType: KType): Any? {
        val javaClass = parameterType.javaType
        return when (javaClass) {
            String::class.java -> option.asString
            Int::class.java -> option.asInt
            Boolean::class.java -> option.asBoolean
            Double::class.java -> option.asDouble
            User::class.java -> option.asUser
            Channel::class.java -> option.asChannel
            Role::class.java -> option.asRole
            IMentionable::class.java -> option.asMentionable
            Attachment::class.java -> option.asAttachment
            else -> null
        }
    }

    fun getMessageOption(arg: String, parameterType: KType, attachment: Attachment? = null): Any? {
        return when (parameterType) {
            String::class.java -> arg
            Int::class.java -> arg.toInt()
            Boolean::class.java -> arg.toBoolean()
            Double::class.java -> arg.toDouble()
            User::class.java -> {
                if (!USER_PING_REGEX.matches(arg)) null
                else {
                    val id = NUMBER_ONLY_REGEX.find(arg)?.value ?: return null
                    ZephyrBot.api.getUserById(id.toLong())
                }
            }
            Channel::class.java -> {
                if (!CHANNEL_PING_REGEX.matches(arg)) null
                else {
                    val id = NUMBER_ONLY_REGEX.find(arg)?.value ?: return null
                    ZephyrBot.api.getChannelById((parameterType as Channel)::class.java, id.toLong())
                }
            }
            Role::class.java -> {
                if (!ROLE_PING_REGEX.matches(arg)) null
                else {
                    val id = NUMBER_ONLY_REGEX.find(arg)?.value ?: return null
                    ZephyrBot.api.getRoleById(id.toLong())
                }
            }
            IMentionable::class.java -> {
                val roleMatch = ROLE_PING_REGEX.matches(arg)
                val userMatch = USER_PING_REGEX.matches(arg)
                if (!roleMatch && !userMatch) null
                else {
                    val id = NUMBER_ONLY_REGEX.find(arg)?.value ?: return null
                    if (roleMatch) ZephyrBot.api.getRoleById(id.toLong())
                    else  ZephyrBot.api.getUserById(id.toLong())
                }
            }
            Attachment::class.java -> {
                if (attachment == null) return null
                attachment
            }
            else -> null
        }
    }
}