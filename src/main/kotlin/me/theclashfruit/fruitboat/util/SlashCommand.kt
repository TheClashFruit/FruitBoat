package me.theclashfruit.fruitboat.util

import dev.kord.core.Kord
import dev.kord.core.entity.interaction.GuildChatInputCommandInteraction

public open class SlashCommand {
    open fun getCommandName(): String {
        return ""
    }

    open fun getCommandDescription(): String {
        return ""
    }

    open suspend fun executeCommand(interaction: GuildChatInputCommandInteraction, kord: Kord) {
        return;
    }
}