package us.zonix.ctf.commands

import cc.fyre.proton.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import us.zonix.ctf.CTF
import kotlin.math.roundToInt

object SetupCommand {

    @JvmStatic
    @Command(
        names = ["setup redspawn"],
        permission = "rank.admin"
    )
    fun execute(sender: Player) {
        CTF.instance.config.set("spawnpoint.red.x", sender.location.x.roundToInt())
        CTF.instance.config.set("spawnpoint.red.y", sender.location.y.roundToInt())
        CTF.instance.config.set("spawnpoint.red.z", sender.location.z.roundToInt())
        CTF.instance.config.set("spawnpoint.red.world", sender.world.name)
        sender.sendMessage("You've set the red spawnpoint.")
        CTF.instance.saveConfig()
    }

    @JvmStatic
    @Command(
        names = ["setup bluespawn"],
        permission = "rank.admin"
    )
    fun execute2(sender: Player) {
        CTF.instance.config.set("spawnpoint.blue.x", sender.location.x.roundToInt())
        CTF.instance.config.set("spawnpoint.blue.y", sender.location.y.roundToInt())
        CTF.instance.config.set("spawnpoint.blue.z", sender.location.z.roundToInt())
        CTF.instance.config.set("spawnpoint.blue.world", sender.world.name)
        sender.sendMessage("You've set the blue spawnpoint.")
        CTF.instance.saveConfig()
    }

    @JvmStatic
    @Command(
        names = ["setup blueflag"],
        permission = "rank.admin"
    )
    fun execute3(sender: Player) {
        CTF.instance.config.set("flag.blue.x", sender.location.x.roundToInt())
        CTF.instance.config.set("flag.blue.y", sender.location.y.roundToInt())
        CTF.instance.config.set("flag.blue.z", sender.location.z.roundToInt())
        CTF.instance.config.set("flag.blue.world", sender.world.name)
        sender.sendMessage("You've set the blue flag.")
        CTF.instance.saveConfig()
    }

    @JvmStatic
    @Command(
        names = ["setup redflag"],
        permission = "rank.admin"
    )
    fun execute4(sender: Player) {
        CTF.instance.config.set("flag.red.x", sender.location.x.roundToInt())
        CTF.instance.config.set("flag.red.y", sender.location.y.roundToInt())
        CTF.instance.config.set("flag.red.z", sender.location.z.roundToInt())
        CTF.instance.config.set("flag.red.world", sender.world.name)
        sender.sendMessage("You've set the red flag.")
        CTF.instance.saveConfig()
    }
}