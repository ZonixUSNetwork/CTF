package us.zonix.ctf.commands

import cc.fyre.proton.command.Command
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import us.zonix.ctf.events.GameStartEvent

object GameCommand {
    @Command(
        names = ["game"],
        permission = "rank.admin"
    )
    fun execute(sender: CommandSender) {
        sender.sendMessage("§cUsage /game <start>")
    }

    @Command(
        names = ["game start"],
        permission = "rank.admin"
    )
    fun executeStart(sender: CommandSender) {
        sender.sendMessage("§aStarting game....")
        Bukkit.getPluginManager().callEvent(GameStartEvent())
    }
}