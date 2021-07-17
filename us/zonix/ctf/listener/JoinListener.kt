package us.zonix.ctf.listener

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import us.zonix.ctf.CTF
import us.zonix.ctf.events.GameStartEvent
import us.zonix.ctf.game.State
import us.zonix.ctf.game.Team

class JoinListener : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.joinMessage = null
        val player = event.player
        if (CTF.instance.gameManager.getState() == State.LOBBY) {
            if (!player.hasPermission("rank.staff"))
                Bukkit.broadcastMessage("§a${player.name} has joined the game")
            player.inventory.clear()
            for (i in 0..3) {
                player.inventory.armorContents[i] = null
            }
            player.health = 20.0
            player.foodLevel = 20
            player.teleport(Location(Bukkit.getWorld("world"), 0.5, 64.0, -3.5))
            if (Bukkit.getOnlinePlayers().size >= 8) {
                Bukkit.broadcastMessage("§aGame is starting in 10s.")
                Bukkit.getScheduler().runTaskLater(CTF.instance, {
                    Bukkit.getPluginManager().callEvent(GameStartEvent())
                }, 100L)
            }
            return
        }
        if (CTF.instance.gameManager.getState() == State.GAME) {
            CTF.instance.gameManager.addToTeam(player)
            if (CTF.instance.gameManager.getTeam(player) == Team.RED) {
                CTF.instance.kitManager.giveRedKit(player, Team.RED)
            }
            if (CTF.instance.gameManager.getTeam(player) == Team.BLUE) {
                CTF.instance.kitManager.giveBlueKit(player, Team.BLUE)
            }
            return
        }
        if (CTF.instance.gameManager.getState() == State.END) {
            player.kickPlayer("§cThis game has ended. Please wait for a new one to start.")
            return
        }
    }
}