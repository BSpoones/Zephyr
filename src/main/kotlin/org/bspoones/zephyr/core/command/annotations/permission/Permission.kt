package org.bspoones.zephyr.core.command.annotations.permission

import net.dv8tion.jda.api.Permission

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class Permission(
    val permission: Permission
)