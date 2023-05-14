package me.theclashfruit.fruitboat

import dev.kord.common.entity.PresenceStatus
import me.theclashfruit.fruitboat.commands.AboutCommand
import me.theclashfruit.fruitboat.commands.KotlinCommand
import dev.kord.core.Kord
import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.Intents
import dev.kord.gateway.PrivilegedIntent
import me.theclashfruit.fruitboat.commands.HelpCommand
import me.theclashfruit.fruitboat.commands.PingCommand
import org.slf4j.LoggerFactory

@OptIn(PrivilegedIntent::class)
suspend fun main(args: Array<String>) {
    val log = LoggerFactory.getLogger("Main")

    val allCommands = listOf(
        HelpCommand(),
        PingCommand(),
        AboutCommand(),

        KotlinCommand()
    )

    if(System.getenv("BOT_TOKEN") == null) {
        log.error("No bot token provided! Please provide a bot token in the BOT_TOKEN environment variable.")

        return
    }

    val kord = Kord(System.getenv("BOT_TOKEN")!!)

    log.info("Starting bot...")

    allCommands.forEach() {
        kord.createGlobalChatInputCommand(
            it.getCommandName(),
            it.getCommandDescription()
        ) {}
    }

    kord.on<GuildChatInputCommandInteractionCreateEvent> {
        val command = interaction.command

        log.info("Executing `${command.rootName}`...")

        allCommands.forEach {
            if (it.getCommandName() == command.rootName) {
                it.executeCommand(interaction, kord)
            }
        }
    }

    kord.login() {
        intents = Intents(Intent.Guilds, Intent.GuildMessages, Intent.GuildMessageReactions, Intent.GuildPresences)
        presence {
            status = PresenceStatus.Idle
            listening("commands!")
        }
    }

    log.info("Logged in as ${kord.getSelf().username}#${kord.getSelf().tag}!")
}