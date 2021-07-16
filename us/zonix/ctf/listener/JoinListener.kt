package us.zonix.ctf.listener

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import us.zonix.ctf.CTFPlugin
import us.zonix.ctf.game.State

class JoinListener : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.joinMessage = null
        val player = event.player
        if (CTFPlugin.instance.gameManager.getState() == State.LOBBY) {
            if (!player.hasPermission("rank.staff"))
                Bukkit.broadcastMessage("§a${player.name} has joined the game")
            return
        }
        if (CTFPlugin.instance.gameManager.getState() == State.GAME) {
            CTFPlugin.instance.gameManager.addToTeam(player)
            return
        }
        if (CTFPlugin.instance.gameManager.getState() == State.END) {
            player.kickPlayer("§cThis game has ended. Please wait for a new one to start.")
            return
        }
    }
}