package us.zonix.ctf.utils

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Sound

object SoundUtil {

    fun playSound(sound: Sound, loc: Location) {
        for (player in Bukkit.getOnlinePlayers()) {
            player.playSound(loc, sound, 10f, 10f)
        }
    }

    fun playSound(sound: Sound) {
        for (player in Bukkit.getOnlinePlayers()) {
            player.playSound(player.location, sound, 10f, 10f)
        }
    }

}