package org.bspoones.zephyr.core.command.register

import org.bspoones.zephyr.core.command.enums.CommandType
import kotlin.reflect.KFunction

object CommandForest {

    private val leaves: MutableList<CommandLeaf> = mutableListOf()

    fun getFunction(type: CommandType, name: String): KFunction<*>? {
        println(leaves.map { it.name })
        println("LEAF:  ${getLeaf(type, name)}")
        return getLeaf(type, name)?.function
    }

    private fun getLeaf(type: CommandType, name: String): CommandLeaf? {
        return getLeaves(type).find { it.name == name }
    }

    private fun getLeaves(type: CommandType): List<CommandLeaf> {
        println("Below")
        println(leaves.filter {
            it.type == type
        }.map { it.name })
        return leaves.filter {
            it.type == type
        }
    }

    fun addLeaf(type: CommandType, name: String, function: KFunction<*>) {
        // Name presence check
        if (leaves.filter { it.type == type }.map { it.name }.contains(name)) {
            throw IllegalArgumentException("Command name already registered: $name")
        }
        // Function presence check
        if (leaves.filter { it.type == type }.map { it.function }.contains(function)) {
            throw IllegalArgumentException("Function already registered as a ${type.typeName}")
        }

        leaves.add(
            CommandLeaf(type, name, function)
        )
    }
}