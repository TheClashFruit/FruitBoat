package me.theclashfruit.fruitboat.commands

import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.interaction.GuildChatInputCommandInteraction
import me.theclashfruit.fruitboat.util.SlashCommand

class KotlinCommand : SlashCommand() {
    override fun getCommandName(): String {
        return "kotlin"
    }

    override fun getCommandDescription(): String {
        return "Hello from Kotlin!"
    }

    override suspend fun executeCommand(interaction: GuildChatInputCommandInteraction, kord: Kord) {
        val response = interaction.deferPublicResponse()

        response.respond {
            content = "Hello from Kotlin!"
        }
    }
}