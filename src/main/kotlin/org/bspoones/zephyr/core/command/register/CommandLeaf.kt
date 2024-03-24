package org.bspoones.zephyr.core.command.register

import org.bspoones.zephyr.core.command.enums.CommandType
import kotlin.reflect.KFunction

class CommandLeaf(
    val type: CommandType,
    val name: String,
    val function: KFunction<*>
)