package org.bspoones.zephyr.core.command.annotations.command.context

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class MessageContextCommand(val name: String)