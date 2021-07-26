package us.zonix.ctf.utils

import org.bukkit.entity.Player
import org.bukkit.Bukkit

object ServerUtils {

    fun sendToAdmins(string: String?) {
        for (player in Bukkit.getOnlinePlayers()) {
            if (player.isOp) {
                player.sendMessage(string)
            }
        }
    }
}