package us.zonix.ctf.manager

import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashMap

class EconomyManager {

    val economyMap: HashMap<UUID, Int> = HashMap()

    fun setBalance(player: Player, amount: Int) {
        economyMap.put(player.uniqueId, amount)
    }

    fun getBalance(player: Player): Int {
        return economyMap.getOrDefault(player.uniqueId, 0)
    }

    fun addBalance(player: Player, amount: Int) {
        economyMap.put(player.uniqueId, getBalance(player) + amount)
    }
}