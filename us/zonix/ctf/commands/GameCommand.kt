package us.zonix.ctf.commands

import cc.fyre.proton.command.Command
import org.apache.commons.lang.StringUtils
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import us.zonix.ctf.CTF
import us.zonix.ctf.events.GameStartEvent

object GameCommand {

    @JvmStatic
    @Command(
        names = ["game start"],
        permission = "rank.admin"
    )
    fun start(sender: CommandSender) {
        sender.sendMessage("§aStarting game....")
        Bukkit.getPluginManager().callEvent(GameStartEvent())
    }

    @JvmStatic
    @Command(
        names = ["game end"],
        permission = "rank.admin"
    )
    fun restart(sender: CommandSender) {
        sender.sendMessage("§aEnding game...")
        CTF.instance.gameManager.restartGame()
    }


    @JvmStatic
    @Command(
        names = ["game checkteams"],
        permission = "rank.admin"
    )
    fun checkTeams(sender: CommandSender) {
        sender.sendMessage("§c§lRED TEAM:")
        for (uuid in CTF.instance.gameManager.getRedTeam()) {
            sender.sendMessage("  ${Bukkit.getPlayer(uuid).name} - $uuid")
        }
        sender.sendMessage("     ")
        sender.sendMessage("§9§lRED TEAM:")
        for (uuid in CTF.instance.gameManager.getRedTeam()) {
            sender.sendMessage("  ${Bukkit.getPlayer(uuid).name} - $uuid")
        }
    }
}