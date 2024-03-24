package org.bspoones.zephyr.core.command

import net.dv8tion.jda.api.entities.Message.Attachment
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.bspoones.zephyr.core.command.annotations.Option
import org.bspoones.zephyr.core.command.register.CommandForest
import org.bspoones.zephyr.core.command.register.CommandRegister
import org.bspoones.zephyr.core.command.enums.CommandType
import org.bspoones.zephyr.core.command.handler.NsfwHandler
import org.bspoones.zephyr.core.command.handler.OptionHandler
import kotlin.math.min
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.javaMethod

open class Command : ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val function = CommandForest.getFunction(CommandType.SLASH, event.fullCommandName) ?: return
        if (NsfwHandler.nsfwCheck(function, event)) return
        val functionObj = function.javaMethod?.declaringClass?.kotlin?.objectInstance ?: return
        val args = mutableListOf<Any>()
        function.parameters.forEach { parameter ->
            val optionAnnotation = parameter.findAnnotation<Option>() ?: return@forEach
            val value = event.getOption(optionAnnotation.name)
            if (value != null) {
                val optionValue = OptionHandler.getOptionValue(value, parameter.type)
                    ?: throw IllegalArgumentException("Option type cannot be ${parameter.type}")
                args.add(optionValue)
            }
        }
        function.call(functionObj, event, *args.toTypedArray())
        return
    }

    override fun onUserContextInteraction(event: UserContextInteractionEvent) {
        val function = CommandForest.getFunction(CommandType.USER_CONTEXT, event.fullCommandName) ?: return
        if (NsfwHandler.nsfwCheck(function, event)) return
        val functionObj = function.javaMethod?.declaringClass?.kotlin?.objectInstance ?: return
        function.call(functionObj, event)
    }

    override fun onMessageContextInteraction(event: MessageContextInteractionEvent) {
        val function = CommandForest.getFunction(CommandType.MESSAGE_CONTEXT, event.fullCommandName) ?: return
        if (NsfwHandler.nsfwCheck(function, event)) return
        val functionObj = function.javaMethod?.declaringClass?.kotlin?.objectInstance ?: return
        function.call(functionObj, event)
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) return

        val content = event.message.contentRaw
        if (!content.startsWith(CommandRegister.messagePrefix)) return

        val contentArgs = content.split(" ").filter { it != "" }
        val commandName = contentArgs.first().substring(1)
        val function = CommandForest.getFunction(CommandType.MESSAGE, commandName) ?: return
        val functionObj = function.javaMethod?.declaringClass?.kotlin?.objectInstance ?: return
        if (NsfwHandler.nsfwCheck(function, event)) return

        val args: MutableList<String?> = contentArgs.drop(1).toMutableList()
        val funcArgs = mutableListOf<Any>()

        var attachmentIndex = 0
        function.parameters.drop(1).forEachIndexed { index, parameter ->
            val optionAnnotation = parameter.findAnnotation<Option>() ?: return@forEachIndexed

            if (parameter.type::class.java == Attachment::class.java) attachmentIndex += 1

            val value = args.getOrNull(index - 1)
            if (value == null && !parameter.isOptional) {
                event.channel.sendMessage("<@${event.author.id}> Argument not provided: ${optionAnnotation.name}")
                    .setMessageReference(event.messageId).queue()
                return
            }
            if (value == null && parameter.isOptional) return@forEachIndexed

            val optionValue = OptionHandler.getMessageOption(
                value!!,
                parameter.type,
                event.message.attachments.getOrNull(attachmentIndex)
            )
                ?: {
                    event.channel.sendMessage("<@${event.author.id}> Invalid selection for `${optionAnnotation.name}`. It must be of type ${parameter.type}")
                        .setMessageReference(event.messageId).queue()
                }
            funcArgs.add(optionValue)
        }

        val minSize = function.parameters.size - 2 - function.parameters.filter { it.isOptional }.size

        if (args.size < minSize) {
            event.channel.sendMessage("<@${event.author.id}> Invalid arguments: ${args.joinToString(" ")}")
                .setMessageReference(event.messageId).queue()
            return
        }
//        args.addAll(List(function.parameters.filter { it.isOptional }.size - args.size) { null })
        function.call(functionObj, event, *args.toTypedArray())
        return

    }

    override fun onCommandAutoCompleteInteraction(event: CommandAutoCompleteInteractionEvent) {
        val options = CommandRegister.autoCompleteMap[event.fullCommandName] ?: return
        var choices = options[event.focusedOption.name] ?: return
        if (choices.isEmpty()) return

        choices = when (choices.first()) {
            is String -> choices as List<String>
            is Double -> choices as List<Double>
            is Long -> choices as List<Long>
            else -> return
        }
        event.replyChoiceStrings(
            (choices)
                .filter { it.toString().startsWith(event.focusedOption.value, ignoreCase = true) }
                    as List<String>
        ).queue()
    }


}