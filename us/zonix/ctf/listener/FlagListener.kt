package us.zonix.ctf.listener

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import us.zonix.ctf.CTF
import us.zonix.ctf.events.FlagStealEvent
import us.zonix.ctf.game.Team

class FlagListener : Listener {

    @EventHandler
    fun onFlagSteal(event: FlagStealEvent) {
        var stolenFrom = event.flag
        var player = event.player
        when (stolenFrom) {
            Team.BLUE -> {
                Bukkit.broadcastMessage("§c${player.name} §fpicked up the §9§lBLUE §fflag.")
                CTF.instance.kitManager.giveBlueFlag(player, Team.BLUE)
            }
            Team.RED -> {
                Bukkit.broadcastMessage("§9${player.name} §fpicked up the §c§lRED §fflag.")
                CTF.instance.kitManager.giveRedFlag(player, Team.RED)
            }
        }
    }

}