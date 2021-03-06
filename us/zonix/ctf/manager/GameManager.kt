package us.zonix.ctf.manager

import com.lunarclient.bukkitapi.LunarClientAPI
import com.lunarclient.bukkitapi.nethandler.client.LCPacketTitle
import org.apache.commons.lang.StringUtils
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Location
import org.bukkit.entity.Player
import us.zonix.ctf.CTF
import us.zonix.ctf.game.State
import us.zonix.ctf.game.Team
import us.zonix.ctf.utils.InstantFirework
import us.zonix.ctf.utils.RandomFireWork
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class GameManager {

    private val blue: ArrayList<UUID> = ArrayList()
    private val red: ArrayList<UUID> = ArrayList()
    private val spectators: ArrayList<UUID> = ArrayList()
    private var gameState: State = State.LOBBY

    private val kills: HashMap<UUID, Int> = HashMap()
    private val deaths: HashMap<UUID, Int> = HashMap()

    fun isSpectator(player: Player): Boolean {
        return spectators.contains(player.uniqueId)
    }

    fun removeRed(player: Player) {
        red.remove(player.uniqueId)
    }

    fun removeBlue(player: Player) {
        blue.remove(player.uniqueId)
    }

    fun isPlaying(player: Player): Boolean {
        return !isSpectator(player)
    }

    fun setKills(player: Player, value: Int) {
        kills[player.uniqueId] = value
    }

    fun setDeaths(player: Player, value: Int) {
        deaths[player.uniqueId] = value
    }

    fun getDeaths(player: Player): Int {
        if (deaths[player.uniqueId] != null) {
            return deaths[player.uniqueId]!!
        } else {
            return 0
        }
    }

    fun getKills(player: Player): Int {
        if (kills[player.uniqueId] != null) {
            return kills[player.uniqueId]!!
        } else {
            return 0
        }
    }

    fun endGame(winner: Team) {
        gameState = State.END
        /*/
        todo: make it compatible with the new game system which allows games to continue without rebooting.
        Bukkit.getScheduler().scheduleSyncRepeatingTask(CTF.instance, {
            var randomInt = kotlin.random.Random.nextInt(-100, 100)
            var location = Location(Bukkit.getWorld("world"), (0 + randomInt).toDouble(), 90.0, (0 + randomInt).toDouble())
            InstantFirework(FireworkEffect.builder().with(FireworkEffect.Type.BURST).withColor(RandomFireWork.getRandomColor()).build(), location)
        }, 5L, 5L)*/
        for (player in Bukkit.getOnlinePlayers()) {
            player.inventory.clear()
            player.allowFlight = true
            if (getTeam(player) == winner) {
                LunarClientAPI.getInstance()
                    .sendPacket(player, LCPacketTitle("title", "??6??lVICTORY!", 5000, 1, 1))
                LunarClientAPI.getInstance()
                    .sendPacket(player, LCPacketTitle("subtitle", "??fYour team ??bcaptured??f the ??cenemy??f's flag!", 5000, 1, 1))
            } else {
                LunarClientAPI.getInstance()
                    .sendPacket(player, LCPacketTitle("title", "??c??lGAME OVER!", 5000, 1, 1))
                LunarClientAPI.getInstance()
                    .sendPacket(player, LCPacketTitle("subtitle", "??fYour flag was ??bcaptured??f by the ??cenemy team??f.", 500, 1, 1))
            }

        }
        Bukkit.getScheduler().runTaskLater(CTF.instance, {
            restartGame()
        }, 20 * 30L) //20 ticks = 1s
    }

    fun restartGame() {
        blue.clear();
        red.clear()

        //kills and deaths could just be set to 0, but that'd cause the map to grow in size as more players join, so it's just better to reset.
        kills.clear()
        deaths.clear()

        CTF.instance.economyManager.economyMap.clear()
        CTF.instance.upgradeManager.blueUpgrades.clear()
        CTF.instance.upgradeManager.redUpgrades.clear()

        CTF.instance.flagManager.blueFlagHolder = null
        CTF.instance.flagManager.redFlagHolder = null



        gameState = State.LOBBY


        for (player in Bukkit.getOnlinePlayers()) {
            player.allowFlight = false
            player.teleport(CTF.instance.mapManager.getMapSpawn())
            CTF.instance.kitManager.clearInventory(player)
        }
    }

    fun getRed(): Int {
        return red.size
    }

    fun getBlue(): Int {
        return blue.size
    }

    fun getTeam(player: Player): Team {
        if (red.contains(player.uniqueId)) return Team.RED
        if (blue.contains(player.uniqueId)) return Team.BLUE

        return Team.NONE
    }

    fun addToTeam(player: Player) {
        if (red.size > blue.size) {
            blue.add(player.uniqueId)
        } else if (blue.size > red.size) {
            red.add(player.uniqueId)
        } else {
            val r = Random()
            if (r.nextBoolean()) {
                red.add(player.uniqueId)
            } else {
                blue.add(player.uniqueId)
            }
        }
    }

    fun getState(): State {
        return gameState
    }

    fun setState(game: State) {
        gameState = game
    }

    fun getRedTeam(): ArrayList<UUID> {
        return red;
    }

    fun getBlueTeam(): ArrayList<UUID> {
        return blue;
    }

}