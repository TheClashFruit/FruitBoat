package me.theclashfruit.fruitboat.commands

import dev.kord.common.Color
import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.interaction.GuildChatInputCommandInteraction
import dev.kord.rest.builder.message.EmbedBuilder
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

        val randomMessages = listOf("_Oh you need to know all the commands?_", "Looks like someone forgot the commands...", "Oh, hi... How are you?", "Checkin' out the new shiny commands?", "I know.")

        response.respond {
            content = ""
            embeds = ArrayList<EmbedBuilder>().apply {
                add(EmbedBuilder().apply {
                    title = "Help"
                    description = randomMessages.random()
                    color = Color(0x006064)
                    fields = ArrayList<EmbedBuilder.Field>().apply {
                        add(EmbedBuilder.Field().apply {
                            name = "General Commands"
                            value = "`help` - List all commands the bot has.\n`ping` - Check the bot's ping.\n`about` - Information about the bot."
                        })
                    }
                })
            }
        }
    }
}