package me.theclashfruit.fruitboat.commands

import dev.kord.common.Color
import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.interaction.GuildChatInputCommandInteraction
import dev.kord.rest.builder.component.ActionRowBuilder
import dev.kord.rest.builder.component.MessageComponentBuilder
import dev.kord.rest.builder.message.EmbedBuilder
import me.theclashfruit.fruitboat.util.SlashCommand

class AboutCommand : SlashCommand() {
    override fun getCommandName(): String {
        return "about"
    }

    override fun getCommandDescription(): String {
        return "Information about the bot."
    }

    override suspend fun executeCommand(interaction: GuildChatInputCommandInteraction, kord: Kord) {
        val response = interaction.deferPublicResponse()

        response.respond {
            content = ""
            embeds = ArrayList<EmbedBuilder>().apply {
                add(EmbedBuilder().apply {
                    title = "About"
                    description = "A multipurpose bot with moderation features and a lot more.\n"
                    color = Color(0x00FF00)
                    fields = ArrayList<EmbedBuilder.Field>().apply {
                        add(EmbedBuilder.Field().apply {
                            name = "Author"
                            value = "[TheClashFruit](https://github.com/TheClashFruit)"
                        })
                        add(EmbedBuilder.Field().apply {
                            name = "Contributors"
                            value = "-"
                        })
                        add(EmbedBuilder.Field().apply {
                            name = "Version"
                            value = "`0.1.0-alpha+discord`"
                        })
                        add(EmbedBuilder.Field().apply {
                            name = "Build Date"
                            value = "<t:1684054404>"
                        })
                    }
                })
            }
            components = ArrayList<MessageComponentBuilder>().apply {
                add(ActionRowBuilder().apply {
                    linkButton("https://fruitboat.app") {
                        label = "Website (Soon:tm:)"
                    }
                    linkButton("https://github.com/TheClashFruit/FruitBoat") {
                        label = "GitHub"
                    }
                    linkButton("https://discord.com/api/oauth2/authorize?client_id=849310812581265418&permissions=1634839424086&scope=bot") {
                        label = "Invite the bot"
                    }
                })
            }
        }
    }
}