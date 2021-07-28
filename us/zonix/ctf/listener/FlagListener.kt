package us.zonix.ctf.listener

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import us.zonix.ctf.CTF
import us.zonix.ctf.events.FlagCaptureEvent
import us.zonix.ctf.events.FlagStealEvent
import us.zonix.ctf.game.Team

class FlagListener : Listener {

    @EventHandler
    fun onFlagSteal(event: FlagStealEvent) {
        var stolenFrom = event.flag
        var player = event.player
        when (stolenFrom) {
            Team.BLUE -> {
                Bukkit.broadcastMessage(" ")
                Bukkit.broadcastMessage("§c${player.name} §fpicked up the §9§lBLUE §fflag.")
                Bukkit.broadcastMessage(" ")
                CTF.instance.kitManager.giveBlueFlag(player, Team.BLUE)
            }
            Team.RED -> {
                Bukkit.broadcastMessage(" ")
                Bukkit.broadcastMessage("§9${player.name} §fpicked up the §c§lRED §fflag.")
                Bukkit.broadcastMessage(" ")
                CTF.instance.kitManager.giveRedFlag(player, Team.RED)
            }
        }

        @EventHandler
        fun onFlagCapture(event: FlagCaptureEvent) {
            var capturer = event.capturer
            var player = event.player
            when (capturer) {
                Team.BLUE -> {
                    Bukkit.broadcastMessage(" ")
                    Bukkit.broadcastMessage("§9${player.name} §fhas captured the §c§lRED §fflag.")
                    Bukkit.broadcastMessage(" ")
                }
                Team.RED -> {
                    Bukkit.broadcastMessage(" ")
                    Bukkit.broadcastMessage("§c${player.name} §fhas captured the §9§lRED §fflag.")
                    Bukkit.broadcastMessage(" ")
                }
            }
            CTF.instance.gameManager.endGame(capturer)
        }
    }
}