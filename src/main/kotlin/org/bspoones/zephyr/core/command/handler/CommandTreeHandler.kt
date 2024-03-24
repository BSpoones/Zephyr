package org.bspoones.zephyr.core.command.handler

import net.dv8tion.jda.api.interactions.commands.Command
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData
import org.bspoones.zephyr.core.command.annotations.SlashCommandGroup
import org.bspoones.zephyr.core.command.annotations.command.MessageCommand
import org.bspoones.zephyr.core.command.annotations.command.SlashCommand
import org.bspoones.zephyr.core.command.annotations.command.context.MessageContextCommand
import org.bspoones.zephyr.core.command.annotations.command.context.UserContextCommand
import org.bspoones.zephyr.core.command.enums.CommandType
import org.bspoones.zephyr.core.command.register.CommandForest
import java.lang.RuntimeException
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

object CommandTreeHandler {

    fun buildCommandTree(clazz: KClass<*>): List<CommandData> {
        return clazz.findAnnotation<SlashCommandGroup>()?.let {
            listOf(buildGroup(clazz))
        } ?: buildCommands(clazz)
    }

    private fun buildGroup(clazz: KClass<*>): CommandData {
        val group = clazz.findAnnotation<SlashCommandGroup>()
            ?: throw RuntimeException("Parent class building when it shouldn't")
        val commandGroup = Commands.slash(group.name, group.description)
            .setDefaultPermissions(PermissionHandler.buildPermissions(clazz))
            .setNSFW(NsfwHandler.buildNsfw(clazz))
            .setGuildOnly(GuildOnlyHandler.buildGuildOnly(clazz))

        val subGroups: MutableList<SubcommandGroupData> = mutableListOf()
        clazz.nestedClasses
            .filter { it.hasAnnotation<SlashCommandGroup>() }
            .map { subClazz ->
                subGroups.add(buildSubGroup(subClazz, group.name))
            }
        if (subGroups.isNotEmpty()) {
            commandGroup.addSubcommandGroups(subGroups)
        }

        val subCommands = buildSubCommands(clazz, group.name)
        if (subCommands.isNotEmpty()) {
            commandGroup.addSubcommands(subCommands)
        }

        return commandGroup
    }

    private fun buildSubGroup(clazz: KClass<*>, parentName: String = ""): SubcommandGroupData {
        val subGroupAnnotation = clazz.findAnnotation<SlashCommandGroup>()
            ?: throw RuntimeException("Failed to find sub group!")
        val subGroup = SubcommandGroupData(
            subGroupAnnotation.name,
            subGroupAnnotation.description
        )

        val subCommands = buildSubCommands(clazz, "${if (parentName.isNotBlank()) "$parentName " else ""}${subGroupAnnotation.name}")
        if (subCommands.isNotEmpty()) {
            subGroup.addSubcommands(subCommands)
        }

        return subGroup
    }

    private fun buildSubCommands(clazz: KClass<*>, parentName: String = ""): List<SubcommandData> {
        val commands: List<SubcommandData> = clazz.declaredMemberFunctions
            .filter { it.hasAnnotation<SlashCommand>() }
            .flatMap { method ->
                val slashCommand = method.findAnnotation<SlashCommand>()?.let {
                    val commandName = "${if (parentName.isNotBlank()) "$parentName " else ""}${it.name}"
                    CommandForest.addLeaf(CommandType.SLASH, commandName, method)
                    SubcommandData(it.name, it.description)
                        .addOptions(OptionHandler.buildOptions(method, commandName))

                }

                listOfNotNull(slashCommand)
            }
        return commands
    }

    private fun buildCommands(clazz: KClass<*>, parentName: String = ""): List<CommandData> {
        val commands: List<CommandData> = clazz.declaredMemberFunctions
            .filter { it.hasAnnotation<SlashCommand>() || it.hasAnnotation<MessageCommand>() || it.hasAnnotation<UserContextCommand>() }
            .flatMap { method ->
                val slashCommand = method.findAnnotation<SlashCommand>()?.let {
                    val commandName = "${if (parentName.isNotBlank()) "$parentName " else ""}${it.name}"
                    CommandForest.addLeaf(CommandType.SLASH, commandName, method)
                    Commands.slash(it.name, it.description)
                        .addOptions(OptionHandler.buildOptions(method, commandName))
                        .setDefaultPermissions(PermissionHandler.buildPermissions(method))
                        .setNSFW(NsfwHandler.buildNsfw(method))
                        .setGuildOnly(GuildOnlyHandler.buildGuildOnly(method))
                }
                val messageCommand = method.findAnnotation<MessageCommand>()?.let {
                    CommandForest.addLeaf(CommandType.MESSAGE, it.name, method)
                    Commands.message(it.name)
                        .setDefaultPermissions(PermissionHandler.buildPermissions(method))
                        .setNSFW(NsfwHandler.buildNsfw(method))
                        .setGuildOnly(GuildOnlyHandler.buildGuildOnly(method))
                }

                val userContextCommand= method.findAnnotation<UserContextCommand>()?.let {
                    CommandForest.addLeaf(CommandType.USER_CONTEXT, it.name, method)
                    Commands.context(Command.Type.USER, it.name)
                }
                val messageContextCommand= method.findAnnotation<MessageContextCommand>()?.let {
                    CommandForest.addLeaf(CommandType.MESSAGE_CONTEXT, it.name, method)
                    Commands.context(Command.Type.USER, it.name)
                }

                listOfNotNull(slashCommand, messageCommand, userContextCommand, messageContextCommand)
            }
        return commands
    }

}