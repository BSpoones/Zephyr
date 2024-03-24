package org.bspoones.zephyr.core.command.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Option(
    val name: String,
    val description: String = " ",
    val isRequired: Boolean = true,
    val autoComplete: Boolean = false
)
