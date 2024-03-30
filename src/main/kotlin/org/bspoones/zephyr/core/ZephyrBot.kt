package org.bspoones.zephyr.core

import org.slf4j.LoggerFactory.getLogger
import io.github.cdimascio.dotenv.Dotenv
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import org.bspoones.zephyr.commands.TestCommand
import org.bspoones.zeus.Zeus
import org.bspoones.zeus.command.CommandRegistry
import org.slf4j.Logger
import kotlin.reflect.KClass

object ZephyrBot {
    private lateinit var _api: JDA
    val api: JDA
        get() = _api

    private val config = Dotenv.configure().load()
    private val token = config["TOKEN"]
    private val logger: Logger = getLogger("Zephyr")

    val commands: List<KClass<*>> = listOf(
        TestCommand::class
    )

    val allowedGuilds: List<Long> = listOf(
        937749038856536125
    )

    fun run() {
        this._api = JDABuilder.createDefault(
            this.token,
            GatewayIntent.entries.toList()
        ).build()

        _api.awaitReady()

        Zeus.setup(
            _api,
            "!",
            guilds = allowedGuilds
        )


        CommandRegistry.registerCommands(
            TestCommand::class,
            guildOnly = true
        )


        logger.info("Run complete")
    }
}

