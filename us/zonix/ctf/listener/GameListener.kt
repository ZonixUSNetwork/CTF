package us.zonix.ctf.listener

import com.lunarclient.bukkitapi.LunarClientAPI
import com.lunarclient.bukkitapi.`object`.LCWaypoint
import com.lunarclient.bukkitapi.nethandler.client.LCPacketTitle
import me.kansio.modmode.ModMode
import org.bukkit.*
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.*
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.*
import org.bukkit.util.Vector
import us.zonix.ctf.CTF
import us.zonix.ctf.events.FlagCaptureEvent
import us.zonix.ctf.events.FlagStealEvent
import us.zonix.ctf.events.GameStartEvent
import us.zonix.ctf.game.State
import us.zonix.ctf.game.Team
import us.zonix.ctf.utils.SoundUtil
import java.awt.Color

class GameListener : Listener {

    var countdown = 15;

    @EventHandler
    fun onGameStart(event: GameStartEvent) {
        for (player in Bukkit.getOnlinePlayers()) {
            if (ModMode.getInstance().modModeManager.isInModMode(player)) continue
            CTF.instance.gameManager.addToTeam(player)
            CTF.instance.gameManager.setKills(player, 0)
            CTF.instance.gameManager.setDeaths(player, 0)
            LunarClientAPI.getInstance().sendWaypoint(player, LCWaypoint("Red Flag", CTF.instance.mapManager.getRedFlag(), Color.RED.rgb, true))
            LunarClientAPI.getInstance().sendWaypoint(player, LCWaypoint("Blue Flag", CTF.instance.mapManager.getBlueFlag(), Color.BLUE.rgb, true))
            if (CTF.instance.gameManager.getTeam(player) == Team.RED) {
                player.teleport(CTF.instance.mapManager.getRedSpawn())
                CTF.instance.kitManager.giveRedKit(player, Team.RED)
                LunarClientAPI.getInstance().sendPacket(player, LCPacketTitle("title", "§a§lSTARTED", 5000, 1, 1))
                LunarClientAPI.getInstance().sendPacket(player, LCPacketTitle("subtitle", "§fYou are on the §cRED §fteam.", 5000, 1, 1))
            } else if (CTF.instance.gameManager.getTeam(player) == Team.BLUE) {
                player.teleport(CTF.instance.mapManager.getBlueSpawn())
                CTF.instance.kitManager.giveBlueKit(player, Team.BLUE)
                LunarClientAPI.getInstance().sendPacket(player, LCPacketTitle("title", "§a§lSTARTED", 5000, 1, 1))
                LunarClientAPI.getInstance().sendPacket(player, LCPacketTitle("subtitle", "§fYou are on the §9BLUE §fteam.", 5000, 1, 1))
            }
        }
        CTF.instance.gameManager.setState(State.GAME)
        /*/Bukkit.getScheduler().scheduleSyncRepeatingTask(CTF.instance, {

        }, 20L, 20L)*/


        /*/Bukkit.getScheduler().scheduleSyncRepeatingTask(CTF.instance, {
            var blueFlag: Location? = null
            var redFlag: Location? = null
            if (CTF.instance.flagManager.blueFlagHolder != null) {
                blueFlag = CTF.instance.flagManager.blueFlagHolder!!.location
            }
            if (CTF.instance.flagManager.redFlagHolder != null) {
                redFlag = CTF.instance.flagManager.redFlagHolder!!.location
            }
            for (player in Bukkit.getOnlinePlayers()) {
                if (redFlag != null) {
                    LunarClientAPI.getInstance().removeWaypoint(player, LCWaypoint())
                    LunarClientAPI.getInstance().sendWaypoint(player, LCWaypoint("Red Flag", redFlag, Color.RED.rgb, true))
                } else {
                    LunarClientAPI.getInstance().sendWaypoint(player, LCWaypoint("Red Flag", Location(Bukkit.getWorld("world"), 32.0, 66.0, 80.0), Color.RED.rgb, true))
                }
                if (blueFlag != null) {
                    LunarClientAPI.getInstance().sendWaypoint(player, LCWaypoint("Blue Flag", blueFlag, Color.BLUE.rgb, true))
                } else {
                    LunarClientAPI.getInstance().sendWaypoint(player, LCWaypoint("Blue Flag", Location(Bukkit.getWorld("world"), -31.5, 66.0, -87.5), Color.BLUE.rgb, true))
                }
            }
        }, 20L, 80L)*/
    }

    @EventHandler
    fun onItemDespawn(event: ItemDespawnEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onMobSpawn(event: EntitySpawnEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        event.isCancelled = !event.player.gameMode.equals(GameMode.CREATIVE)
    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        event.isCancelled = !event.player.gameMode.equals(GameMode.CREATIVE)
    }

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        val player = event.player
        if (event.getFrom().getBlockX() == event.getTo().getBlockX() &&
            event.getFrom().getBlockZ() == event.getTo().getBlockZ() &&
            event.from.getBlockY() == event.getTo().getBlockY()) {
            return
        }

        if (CTF.instance.gameManager.getState() != State.GAME) {
            return
        }

        if (event.to.block.getRelative(BlockFace.DOWN).type == Material.LAPIS_BLOCK) {
            if (CTF.instance.gameManager.getTeam(player) == Team.RED) { //if they're on the red team
                if (CTF.instance.flagManager.blueFlagHolder == null) { //check if blue's flag holder is null, if it is, give the player the flag.
                    CTF.instance.flagManager.blueFlagHolder = player //set the holder to the stealer
                    Bukkit.getPluginManager().callEvent(FlagStealEvent(Team.BLUE, player)) //blue's flag got stolen, call the event.
                }
            } else if (CTF.instance.gameManager.getTeam(player) == Team.BLUE) { //check if they're returning red's flag to blue flag
                if (CTF.instance.flagManager.redFlagHolder != null && CTF.instance.flagManager.redFlagHolder == player) { //not null & red holder = player
                    Bukkit.getPluginManager().callEvent(FlagCaptureEvent(Team.BLUE, player))
                    CTF.instance.flagManager.redFlagHolder = null //set it back to null
                }
            }
        }
        if (event.to.block.getRelative(BlockFace.DOWN).type == Material.REDSTONE_BLOCK) {
            if (CTF.instance.gameManager.getTeam(player) == Team.BLUE) { //if they're on the blue team
                if (CTF.instance.flagManager.redFlagHolder == null) { //check if red's flag holder is null, if it is, give the player the flag.
                    CTF.instance.flagManager.redFlagHolder = player //set the holder to the stealer
                    Bukkit.getPluginManager().callEvent(FlagStealEvent(Team.RED, player)) //reds's flag got stolen, call the event.
                }
            } else if (CTF.instance.gameManager.getTeam(player) == Team.RED) { //check if they're returning blue's flag to red flag
                if (CTF.instance.flagManager.blueFlagHolder != null && CTF.instance.flagManager.blueFlagHolder == player) { //not null & blue holder = player
                    Bukkit.getPluginManager().callEvent(FlagCaptureEvent(Team.RED, player))
                    CTF.instance.flagManager.blueFlagHolder = null //set it back to null
                }
            }
        }
    }

    @EventHandler
    fun onPlayerItemMove(event: InventoryClickEvent) {
        event.isCancelled = !event.whoClicked.gameMode.equals(GameMode.CREATIVE)
    }

    @EventHandler
    fun onPlayerItemDropEvent(event: PlayerDropItemEvent) {
        event.isCancelled = !event.player.gameMode.equals(GameMode.CREATIVE)
    }

    @EventHandler
    fun onInteractEvent(event: PlayerInteractEvent) {
        if (event.action != Action.PHYSICAL) {
            return
        }
        if (event.clickedBlock.type == Material.IRON_PLATE) {
            val vector: Vector = event.player.getEyeLocation().getDirection()
            vector.multiply(1.0F)
            vector.setY(2.0)
            event.player.velocity = vector
            SoundUtil.playSound(Sound.HORSE_ARMOR, event.player.location)
        }

    }

    /*/@EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity
        event.deathMessage = null
        if (CTF.instance.flagManager.redFlagHolder == player) {
            CTF.instance.flagManager.redFlagHolder = null
            Bukkit.broadcastMessage("§7§m------------------------------------------")
            Bukkit.broadcastMessage("§9${player.name} §fhas died with the §c§lRED §fflag")
            Bukkit.broadcastMessage("§fThe §cflag §fhas been returned.")
            Bukkit.broadcastMessage("§7§m------------------------------------------")
        }
        if (CTF.instance.flagManager.blueFlagHolder == player) {
            CTF.instance.flagManager.blueFlagHolder = null
            Bukkit.broadcastMessage("§7§m------------------------------------------")
            Bukkit.broadcastMessage("§c${player.name} §fhas died with the §9§lBLUE §fflag")
            Bukkit.broadcastMessage("§fThe §cflag §fhas been returned.")
            Bukkit.broadcastMessage("§7§m------------------------------------------")
        }
    }*/

    @EventHandler
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        if (event.entity !is Player) {
            return
        }
        if (event.damager !is  Player) {
            return
        }
        var player = event.entity as Player
        var damager = event.damager as Player
        if (CTF.instance.gameManager.getTeam(player) == Team.RED && CTF.instance.gameManager.getTeam(damager) == Team.RED) {
            damager.sendMessage("§cYou cannot damage your teammates.")
            event.isCancelled = true
            return
        }
        if (CTF.instance.gameManager.getTeam(player) == Team.BLUE && CTF.instance.gameManager.getTeam(damager) == Team.BLUE) {
            damager.sendMessage("§cYou cannot damage your teammates.")
            event.isCancelled = true
            return
        }
    }

    @EventHandler
    fun onEntityDamage(event: EntityDamageEvent) {
        if (event.entity !is Player) {
            return
        }
        if (event.cause == EntityDamageEvent.DamageCause.FALL) {
            event.isCancelled = true
        }
        var player = event.entity as Player

        if ((player.health - event.damage) <= 0.0) {
            event.isCancelled = true
            player.health = 20.0

            if (CTF.instance.flagManager.redFlagHolder == player) {
                CTF.instance.flagManager.redFlagHolder = null
                Bukkit.broadcastMessage("§7§m------------------------------------------")
                Bukkit.broadcastMessage("§9${player.name} §fhas died with the §c§lRED §fflag")
                Bukkit.broadcastMessage("§fThe §cflag §fhas been returned.")
                Bukkit.broadcastMessage("§7§m------------------------------------------")
                player.teleport(CTF.instance.mapManager.getBlueSpawn())
                SoundUtil.playSound(Sound.IRONGOLEM_DEATH)
                CTF.instance.kitManager.giveBlueKit(player, Team.RED)
                return
            }
            if (CTF.instance.flagManager.blueFlagHolder == player) {
                CTF.instance.flagManager.blueFlagHolder = null
                Bukkit.broadcastMessage("§7§m------------------------------------------")
                Bukkit.broadcastMessage("§c${player.name} §fhas died with the §9§lBLUE §fflag")
                Bukkit.broadcastMessage("§fThe §cflag §fhas been returned.")
                Bukkit.broadcastMessage("§7§m------------------------------------------")
                player.teleport(CTF.instance.mapManager.getRedSpawn())
                SoundUtil.playSound(Sound.IRONGOLEM_DEATH)
                CTF.instance.kitManager.giveRedKit(player, Team.RED)
                return
            }

            if (CTF.instance.gameManager.getTeam(player) == Team.RED) {
                CTF.instance.kitManager.giveRedKit(player, Team.RED)
                player.teleport(CTF.instance.mapManager.getRedSpawn())
                return
            }
            if (CTF.instance.gameManager.getTeam(player) == Team.BLUE) {
                CTF.instance.kitManager.giveBlueKit(player, Team.BLUE)
                player.teleport(CTF.instance.mapManager.getBlueSpawn())
                return
            }
        }
    }

    @EventHandler
    fun onHungerLoss(event: FoodLevelChangeEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onPlayerLeave(event: PlayerQuitEvent) {
        var player = event.player
        if (CTF.instance.gameManager.getState() != State.GAME) {
            return
        }
        if (CTF.instance.gameManager.getTeam(player) == Team.RED) {
            CTF.instance.gameManager.removeRed(player)
            Bukkit.broadcastMessage("§c" + player.name + " §fhas disconnected.")
        }
        if (CTF.instance.gameManager.getTeam(player) == Team.BLUE) {
            CTF.instance.gameManager.removeBlue(player)
            Bukkit.broadcastMessage("§9" + player.name + " §fhas disconnected.")
        }
    }
}

    /*/@EventHandler
    fun onPlayerRespawn(event: PlayerRespawnEvent) {
        val player = event.player
        if (CTF.instance.gameManager.getTeam(player) == Team.RED) {
            CTF.instance.kitManager.giveRedKit(player, Team.RED)
            event.respawnLocation = Location(Bukkit.getWorld("world"), -38.5, 72.0, 76.5)
        }
        if (CTF.instance.gameManager.getTeam(player) == Team.BLUE) {
            CTF.instance.kitManager.giveBlueKit(player, Team.BLUE)
            event.respawnLocation = Location(Bukkit.getWorld("world"), 39.5, 72.0, -83.5)
        }
    }/*/