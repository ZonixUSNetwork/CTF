package us.zonix.ctf.manager

import org.bukkit.entity.Player
import us.zonix.ctf.game.State
import us.zonix.ctf.game.Team
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

}