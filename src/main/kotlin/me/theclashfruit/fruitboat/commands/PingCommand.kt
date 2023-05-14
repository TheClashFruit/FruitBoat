package me.theclashfruit.fruitboat.commands

import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.interaction.GuildChatInputCommandInteraction
import me.theclashfruit.fruitboat.util.SlashCommand

class PingCommand : SlashCommand() {
    override fun getCommandName(): String {
        return "ping"
    }

    override fun getCommandDescription(): String {
        return "The bot runnin' slow? Check it's ping!"
    }

    override suspend fun executeCommand(interaction: GuildChatInputCommandInteraction, kord: Kord) {
        val response = interaction.deferPublicResponse()

        response.respond {
            content = "Ping: `${kord.gateway.averagePing}`"
        }
    }
}