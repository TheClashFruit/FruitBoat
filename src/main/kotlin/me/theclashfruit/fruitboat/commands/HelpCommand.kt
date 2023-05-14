package me.theclashfruit.fruitboat.commands

import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.interaction.GuildChatInputCommandInteraction
import me.theclashfruit.fruitboat.util.SlashCommand

class HelpCommand : SlashCommand() {
    override fun getCommandName(): String {
        return "help"
    }

    override fun getCommandDescription(): String {
        return "List all commands the bot has."
    }

    override suspend fun executeCommand(interaction: GuildChatInputCommandInteraction, kord: Kord) {
        val response = interaction.deferPublicResponse()

        response.respond {
            content = "nah you won't get help!"
        }
    }
}