package us.zonix.ctf.listener

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scheduler.BukkitScheduler
import org.bukkit.scheduler.BukkitTask
import us.zonix.ctf.CTF
import us.zonix.ctf.events.GameStartEvent
import us.zonix.ctf.game.State
import us.zonix.ctf.game.Team
import us.zonix.ctf.utils.message.MessageUtils

class JoinListener : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        event.joinMessage = null
        val player = event.player
        when (CTF.instance.gameManager.getState()) {
            State.LOBBY -> {
                if (!player.hasPermission("rank.staff"))
                    Bukkit.broadcastMessage("§a${player.name} has joined the game")
                for (i in 0..3) {
                    player.inventory.armorContents[i] = null
                }
                player.health = 20.0
                player.foodLevel = 20
                CTF.instance.kitManager.clearInventory(player)
                player.teleport(CTF.instance.mapManager.getMapSpawn())

                if (Bukkit.getOnlinePlayers().size >= 8) {
                    Bukkit.broadcastMessage("§aGame is starting in 10s.")
                    Bukkit.getScheduler().runTaskLater(CTF.instance, {
                        Bukkit.getPluginManager().callEvent(GameStartEvent())
                    }, 100L)
                }
            }
            State.GAME -> {
                CTF.instance.gameManager.addToTeam(player)
                if (CTF.instance.gameManager.getTeam(player) == Team.RED) {
                    CTF.instance.kitManager.giveRedKit(player, Team.RED)
                }
                if (CTF.instance.gameManager.getTeam(player) == Team.BLUE) {
                    CTF.instance.kitManager.giveBlueKit(player, Team.BLUE)
                }
            }
            State.END -> {
                player.teleport(CTF.instance.mapManager.getMapSpawn())
                player.health = 20.0
                player.foodLevel = 20
                CTF.instance.kitManager.clearInventory(player)
                player.sendMessage(" ")
                MessageUtils.sendCenteredMessage(player, "§c§lPlease wait.")
                MessageUtils.sendCenteredMessage(player, "§fThe game has §brecently §fended, please wait for a new round to start.")
                player.sendMessage(" ")
            }
        }
    }
}