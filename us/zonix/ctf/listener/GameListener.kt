package us.zonix.ctf.listener

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import us.zonix.ctf.CTFPlugin
import us.zonix.ctf.events.GameStartEvent

class GameListener : Listener {

    @EventHandler
    fun onGameStart(event: GameStartEvent) {
        for (player in Bukkit.getOnlinePlayers()) {
            if (player.hasMetadata("modmode")) continue
            CTFPlugin.instance.gameManager.addToTeam(player)
            CTFPlugin.instance.gameManager.setKills(player, 0)
            CTFPlugin.instance.gameManager.setDeaths(player, 0)
        }
    }

}