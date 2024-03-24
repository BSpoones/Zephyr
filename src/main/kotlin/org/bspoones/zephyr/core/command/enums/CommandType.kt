package org.bspoones.zephyr.core.command.enums

enum class CommandType(val typeName: String) {
    SLASH("Slash Command"),
    MESSAGE("Message Command"),
    USER_CONTEXT("User Context Command"),
    MESSAGE_CONTEXT("Message Context Command"),
}