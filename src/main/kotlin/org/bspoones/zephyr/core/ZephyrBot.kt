package org.bspoones.zephyr.core

import io.github.cdimascio.dotenv.Dotenv
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent


class ZephyrBot {
    private val config = Dotenv.configure().load()
    private val token = config["TOKEN"]
    fun run(){
        println("Running") //TODO --> Replace with logging stupid
        val builder: JDABuilder = JDABuilder.createDefault(
            this.token,
            GatewayIntent.entries.toList()
        )


        builder.build()
    }
}

