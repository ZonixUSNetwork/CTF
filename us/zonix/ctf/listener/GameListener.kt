package us.zonix.ctf.listener

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.entity.ItemDespawnEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.inventory.ItemStack
import us.zonix.ctf.CTF
import us.zonix.ctf.events.GameStartEvent
import us.zonix.ctf.game.State
import us.zonix.ctf.game.Team

class GameListener : Listener {

    @EventHandler
    fun onGameStart(event: GameStartEvent) {
        for (player in Bukkit.getOnlinePlayers()) {
            if (player.hasMetadata("modmode")) continue
            CTF.instance.gameManager.addToTeam(player)
            CTF.instance.gameManager.setKills(player, 0)
            CTF.instance.gameManager.setDeaths(player, 0)
            if (CTF.instance.gameManager.getTeam(player) == Team.RED) {
                player.teleport(Location(Bukkit.getWorld("world"), -38.5, 72.0, 76.5))
                CTF.instance.kitManager.giveRedKit(player, Team.RED)
            } else if (CTF.instance.gameManager.getTeam(player) == Team.BLUE) {
                player.teleport(Location(Bukkit.getWorld("world"), 39.5, 72.0, -83.5))
                CTF.instance.kitManager.giveBlueKit(player, Team.BLUE)
            }
        }
        CTF.instance.gameManager.setState(State.GAME)
    }

    @EventHandler
    fun onItemDespawn(event: ItemDespawnEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onMobSpawn(event: CreatureSpawnEvent) {
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
            event.getFrom().getBlockY() == event.getTo().getBlockY()) {
            return
        }

        if (CTF.instance.gameManager.getState() != State.GAME) {
            return
        }

        if (event.to.block.getRelative(BlockFace.DOWN).type == Material.LAPIS_BLOCK) {
            if (CTF.instance.gameManager.getTeam(player) == Team.BLUE) {
                if (CTF.instance.flagManager.redFlagHolder == null) {
                    return
                }
                if (CTF.instance.flagManager.redFlagHolder != player) {
                    return
                }
                Bukkit.broadcastMessage("§7§m------------------------------------------")
                Bukkit.broadcastMessage("§9${player.name} §fhas captured the §c§lRED §fflag.")
                Bukkit.broadcastMessage("§7§m------------------------------------------")
                Bukkit.broadcastMessage(" ")
                Bukkit.broadcastMessage("§7§m------------------------------------------")
                Bukkit.broadcastMessage("§9§lBLUE §fteam has won the game!")
                Bukkit.broadcastMessage("§7§m------------------------------------------")
                CTF.instance.kitManager.giveBlueKit(player, Team.RED)
                CTF.instance.gameManager.endGame()
            }

            if (CTF.instance.gameManager.getTeam(player) == Team.RED) {
                if (CTF.instance.flagManager.redFlagHolder != null) {
                    return
                }
                Bukkit.broadcastMessage("§7§m------------------------------------------")
                Bukkit.broadcastMessage("§9${player.name} §fhas picked up the §c§lRED §fflag.")
                Bukkit.broadcastMessage("§7§m------------------------------------------")
                CTF.instance.flagManager.redFlagHolder = player
                CTF.instance.kitManager.giveBlueFlag(player, Team.RED)
            }
        }
        if (event.to.block.getRelative(BlockFace.DOWN).type == Material.REDSTONE_BLOCK) {
            if (CTF.instance.gameManager.getTeam(player) == Team.RED) {
                if (CTF.instance.flagManager.redFlagHolder == null) {
                    return
                }
                if (CTF.instance.flagManager.redFlagHolder != player) {
                    return
                }
                Bukkit.broadcastMessage("§7§m------------------------------------------")
                Bukkit.broadcastMessage("§c${player.name} §fhas captured the §9§lBLUE §fflag.")
                Bukkit.broadcastMessage("§7§m------------------------------------------")
                Bukkit.broadcastMessage(" ")
                Bukkit.broadcastMessage("§7§m------------------------------------------")
                Bukkit.broadcastMessage("§c§lRED §fteam has won the game!")
                Bukkit.broadcastMessage("§7§m------------------------------------------")
                CTF.instance.kitManager.giveRedFlag(player, Team.BLUE)
                CTF.instance.gameManager.endGame()
            }

            if (CTF.instance.gameManager.getTeam(player) == Team.BLUE) {
                if (CTF.instance.flagManager.redFlagHolder != null) {
                    return
                }
                Bukkit.broadcastMessage("§7§m------------------------------------------")
                Bukkit.broadcastMessage("§9${player.name} §fhas picked up the §c§lRED §fflag.")
                Bukkit.broadcastMessage("§7§m------------------------------------------")
                CTF.instance.kitManager.giveRedFlag(player, Team.RED)
                CTF.instance.flagManager.redFlagHolder = player
            }
        }
    }

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity
        event.deathMessage = null
        if (CTF.instance.flagManager.redFlagHolder == player) {
            CTF.instance.flagManager.redFlagHolder = null
            Bukkit.broadcastMessage("§9${player} §fhas died with the §c§lRED §fflag")
            Bukkit.broadcastMessage("§fThe §cflag §fhas been returned.")
        }
        if (CTF.instance.flagManager.blueFlagHolder == player) {
            CTF.instance.flagManager.blueFlagHolder = null
            Bukkit.broadcastMessage("§c${player} §fhas died with the §9§lBLUE §fflag")
            Bukkit.broadcastMessage("§fThe §cflag §fhas been returned.")
        }
    }

    @EventHandler
    fun onPlayerRespawn(event: PlayerRespawnEvent) {
        val player = event.player
        if (CTF.instance.gameManager.getTeam(player) == Team.RED) {
            CTF.instance.kitManager.giveRedFlag(player, Team.RED)
        }
        if (CTF.instance.gameManager.getTeam(player) == Team.BLUE) {
            CTF.instance.kitManager.giveRedFlag(player, Team.BLUE)
        }

    }

    /*/@EventHandler
    fun onItemPickup(event: PlayerPickupItemEvent) {
       val player = event.player

        if (CTF.instance.gameManager.getState() == State.LOBBY) {
            return
        }
        Bukkit.broadcastMessage(" fdf")

       if (CTF.instance.gameManager.getTeam(player) == Team.BLUE) {
           if (!event.item.itemStack.hasItemMeta()) {
               return
           }
           if (event.item.itemStack.type != Material.WOOL) {
               return
           }
           Bukkit.broadcastMessage(" fdf")
           if (event.item.itemStack.itemMeta.displayName.lowercase().contains("blue w")) { //enemy flag
               Bukkit.broadcastMessage("§7§m------------------------------------------")
               Bukkit.broadcastMessage("§c${event.player.name} §fhas picked up the §9§lBLUE §fflag.")
               Bukkit.broadcastMessage("§7§m------------------------------------------")
               CTF.instance.flagManager.blueFlagHolder = event.player
           } else if (event.item.itemStack.itemMeta.displayName.lowercase().contains("red w")) { //they picked up their own flag
               Bukkit.broadcastMessage("§7§m------------------------------------------")
               Bukkit.broadcastMessage("§9${event.player.name} §fhas captured the §c§lRED §fflag.")
               Bukkit.broadcastMessage("§7§m------------------------------------------")
               Bukkit.broadcastMessage(" ")
               Bukkit.broadcastMessage("§7§m------------------------------------------")
               Bukkit.broadcastMessage("§9§lBLUE §fteam has won the game!")
               Bukkit.broadcastMessage("§7§m------------------------------------------")
           }
           return
       }
        Bukkit.broadcastMessage(" fdf")
        if (CTF.instance.gameManager.getTeam(player) == Team.RED) {
            if (!event.item.itemStack.hasItemMeta()) {
                return
            }
            if (event.item.itemStack.type != Material.WOOL) {
                return
            }
            Bukkit.broadcastMessage(" fdf")
            if (event.item.itemStack.itemMeta.displayName.lowercase().contains("red w")) { //enemy flag
                Bukkit.broadcastMessage("§7§m------------------------------------------")
                Bukkit.broadcastMessage("§9${event.player.name} §fhas picked up the §c§lRED §fflag.")
                Bukkit.broadcastMessage("§7§m------------------------------------------")
                CTF.instance.flagManager.redFlagHolder = event.player
            } else if (event.item.itemStack.itemMeta.displayName.lowercase().contains("blue w")) { //they picked up their own flag
                Bukkit.broadcastMessage("§7§m------------------------------------------")
                Bukkit.broadcastMessage("§c${event.player.name} §fhas captured the §9§lBLUE §fflag.")
                Bukkit.broadcastMessage("§7§m------------------------------------------")
                Bukkit.broadcastMessage(" ")
                Bukkit.broadcastMessage("§7§m------------------------------------------")
                Bukkit.broadcastMessage("§c§lRED §fteam has won the game!")
                Bukkit.broadcastMessage("§7§m------------------------------------------")
                //end game, they won.
            }
        }
    }*/

}