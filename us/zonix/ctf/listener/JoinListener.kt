package us.zonix.ctf.listener

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import us.zonix.ctf.CTF
import us.zonix.ctf.game.State

class JoinListener : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.joinMessage = null
        val player = event.player
        if (CTF.instance.gameManager.getState() == State.LOBBY) {
            if (!player.hasPermission("rank.staff"))
                Bukkit.broadcastMessage("§a${player.name} has joined the game")
            player.inventory.clear()
            player.health = 20.0
            player.foodLevel = 20
            player.teleport(Location(Bukkit.getWorld("world"), 0.5, 64.0, -3.5))

            return
        }
        if (CTF.instance.gameManager.getState() == State.GAME) {
            CTF.instance.gameManager.addToTeam(player)
            return
        }
        if (CTF.instance.gameManager.getState() == State.END) {
            player.kickPlayer("§cThis game has ended. Please wait for a new one to start.")
            return
        }
    }
}