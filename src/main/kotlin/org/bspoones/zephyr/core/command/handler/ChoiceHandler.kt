package org.bspoones.zephyr.core.command.handler

import net.dv8tion.jda.api.entities.channel.Channel
import net.dv8tion.jda.api.interactions.commands.Command
import org.bspoones.zephyr.core.command.annotations.choices.ChannelTypes
import org.bspoones.zephyr.core.command.annotations.choices.DoubleChoices
import org.bspoones.zephyr.core.command.annotations.choices.LongChoices
import org.bspoones.zephyr.core.command.annotations.choices.StringChoices
import kotlin.reflect.KParameter

object ChoiceHandler {
    private val CHOICES = mapOf(
        StringChoices::class to String::class,
        LongChoices::class to Int::class,
        DoubleChoices::class to Float::class,
        ChannelTypes::class to Channel::class
    )

    fun buildChoices(parameter: KParameter): List<Command.Choice> {
        return getChoices(parameter).mapNotNull { choice ->
            when (choice) {
                is String -> Command.Choice(choice, choice)
                is Double -> Command.Choice(choice.toString(), choice)
                is Long -> Command.Choice(choice.toString(), choice)
                else -> null
            }
        }
    }

    fun getChoices(parameter: KParameter): List<Any> {
        val choiceAnnotations = parameter.annotations.filter { annotation ->
            CHOICES.keys.map { it.simpleName }.contains(annotation.annotationClass.simpleName)
        }

        if (choiceAnnotations.isEmpty()) return listOf()
        if (choiceAnnotations.size > 1) throw IllegalArgumentException("You can only have one choice type")

        // IntelliJ is gaslighting by saying this is a warning. Bad IntelliJ >:(
        if (parameter.type.javaClass == CHOICES[choiceAnnotations.first().annotationClass])
            throw IllegalArgumentException("A parameter choice must be of the same type as the parameter")

        return when (val annotation = choiceAnnotations.first()) {
            is StringChoices -> annotation.choices.toList()
            is DoubleChoices -> annotation.choices.toList()
            is LongChoices -> annotation.choices.toList()
            else -> throw IllegalArgumentException("Invalid Choice annotation")
        }
    }

}