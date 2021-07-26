package us.zonix.ctf.manager

import org.bukkit.Bukkit
import org.bukkit.Location
import us.zonix.ctf.CTF
import us.zonix.ctf.utils.ServerUtils
import java.lang.Exception

class MapManager {

    fun getRedSpawn(): Location? {
        try {
            var world = Bukkit.getWorld(CTF.instance.config.getString("spawnpoint.red.world"))

            var location = Location(
                world,
                CTF.instance.config.getInt("spawnpoint.red.x").toDouble(),
                CTF.instance.config.getInt("spawnpoint.red.y").toDouble(),
                CTF.instance.config.getInt("spawnpoint.red.z").toDouble(),
            )
            return location
        } catch (error: Exception) {
            ServerUtils.sendToAdmins("§f[§c§lSpawnpoint§f] §fThere was an issue whilst getting red's spawnpoint. Stacktrace: " + error.stackTraceToString())
            return Location(Bukkit.getWorld("world"), 0.0, 100.0, 0.0)
        }
    }

    fun getMapSpawn(): Location? {
        try {
            var world = Bukkit.getWorld(CTF.instance.config.getString("spawnpoint.lobby.world"))

            var location = Location(
                world,
                CTF.instance.config.getInt("spawnpoint.lobby.x").toDouble(),
                CTF.instance.config.getInt("spawnpoint.lobby.y").toDouble(),
                CTF.instance.config.getInt("spawnpoint.lobby.z").toDouble(),
            )
            return location
        } catch (error: Exception) {
            ServerUtils.sendToAdmins("§f[§7§lSpawnpoint§f] §fThere was an issue whilst getting lobby's spawnpoint. Stacktrace: " + error.stackTraceToString())
            return Location(Bukkit.getWorld("world"), 0.0, 100.0, 0.0)
        }
    }

    fun getBlueSpawn(): Location? {
        try {
            var world = Bukkit.getWorld(CTF.instance.config.getString("spawnpoint.blue.world"))

            var location = Location(
                world,
                CTF.instance.config.getInt("spawnpoint.blue.x").toDouble(),
                CTF.instance.config.getInt("spawnpoint.blue.y").toDouble(),
                CTF.instance.config.getInt("spawnpoint.blue.z").toDouble(),
            )
            return location
        } catch (error: Exception) {
            ServerUtils.sendToAdmins("§f[§9§lSpawnpoint§f] §fThere was an issue whilst getting blue's spawnpoint. Stacktrace: " + error.stackTraceToString())
            return Location(Bukkit.getWorld("world"), 0.0, 100.0, 0.0)
        }
    }

    fun getBlueFlag(): Location? {
        try {
            var world = Bukkit.getWorld(CTF.instance.config.getString("flag.blue.world"))

            var location = Location(
                world,
                CTF.instance.config.getInt("flag.blue.x").toDouble(),
                CTF.instance.config.getInt("flag.blue.y").toDouble(),
                CTF.instance.config.getInt("flag.blue.z").toDouble(),
            )
            return location
        } catch (error: Exception) {
            ServerUtils.sendToAdmins("§f[§9§lFlag§f] §fThere was an issue whilst getting blue's flag. Stacktrace: " + error.stackTraceToString())
            return Location(Bukkit.getWorld("world"), 0.0, 100.0, 0.0)
        }
    }

    fun getRedFlag(): Location? {
        try {
            var world = Bukkit.getWorld(CTF.instance.config.getString("flag.red.world"))

            var location = Location(
                world,
                CTF.instance.config.getInt("flag.red.x").toDouble(),
                CTF.instance.config.getInt("flag.red.y").toDouble(),
                CTF.instance.config.getInt("flag.red.z").toDouble(),
            )
            return location
        } catch (error: Exception) {
            ServerUtils.sendToAdmins("§f[§9§lFlag§f] §fThere was an issue whilst getting red's flag. Stacktrace: " + error.stackTraceToString())
            return Location(Bukkit.getWorld("world"), 0.0, 100.0, 0.0)
        }
    }
}