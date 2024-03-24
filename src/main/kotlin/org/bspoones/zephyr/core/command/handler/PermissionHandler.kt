package org.bspoones.zephyr.core.command.handler

import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions
import org.bspoones.zephyr.core.command.annotations.permission.Permission
import org.bspoones.zephyr.core.command.annotations.permission.Permissions
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation

object PermissionHandler {
    fun buildPermissions(method: KFunction<*>): DefaultMemberPermissions {
        val permission = method.findAnnotation<Permission>()?.permission
        val permissions = method.findAnnotation<Permissions>()?.permissions
        return buildPermissions(permission, permissions)
    }
    fun buildPermissions(clazz: KClass<*>): DefaultMemberPermissions {
        val permission = clazz.findAnnotation<Permission>()?.permission
        val permissions = clazz.findAnnotation<Permissions>()?.permissions
        return buildPermissions(permission, permissions)
    }

    private fun buildPermissions(permission: net.dv8tion.jda.api.Permission?, permissions: Array<net.dv8tion.jda.api.Permission>?): DefaultMemberPermissions {
        val allPermissions = (permissions ?: emptyArray()).toMutableList()
        permission?.let { allPermissions.add(it) }
        return DefaultMemberPermissions.enabledFor(allPermissions)
    }
}