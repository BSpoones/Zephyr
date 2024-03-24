package org.bspoones.zephyr.core.command.annotations.choices

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class DoubleChoices(
    val choices: DoubleArray = [],
)