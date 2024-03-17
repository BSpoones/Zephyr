package org.bspoones.zephyr.core

import io.github.cdimascio.dotenv.Dotenv
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent
import org.bspoones.zephyr.commands.TestCommand
import org.bspoones.zephyr.core.command.CommandRegister
import org.bspoones.zephyr.utils.ZLogger

object ZephyrBot {
    lateinit var api: JDA

    private val config = Dotenv.configure().load()
    private val token = config["TOKEN"]

    private val commands: List<ListenerAdapter> = listOf(
        TestCommand
    )

    fun run() {
        api = JDABuilder.createDefault(
            this.token,
            GatewayIntent.entries.toList()
        ).build()
        commands.forEach {
            api.addEventListener(it)
        }

//        api.addEventListener(CommandListener)
        CommandRegister.registerCommands(commands)


        ZLogger.info("Run complete")
    }


}

//object CommandListener : ListenerAdapter() {
//    override fun onMessageReceived(event: MessageReceivedEvent) {
//        println("MESSAGE RECIEVED")
//        if (!event.author.isBot) {
//            println(event.message.contentRaw)
//            if (event.message.contentRaw == "!ping") {
//                event.channel.sendMessage("Pong!").queue()
//            }
//        }
//    }
//}

