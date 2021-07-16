package us.zonix.ctf

import cc.fyre.proton.Proton
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import us.zonix.ctf.listener.GameListener
import us.zonix.ctf.listener.JoinListener
import us.zonix.ctf.manager.GameManager
import us.zonix.ctf.nametags.Nametags
import us.zonix.ctf.scoreboard.Scoreboard

class CTFPlugin : JavaPlugin() {

    val gameManager: GameManager = GameManager()

    override fun onEnable() {
        instance = this
        Proton.getInstance().commandHandler.registerAll(this);
        Proton.getInstance().scoreboardHandler.configuration = Scoreboard.create()

    }

    fun registerListeners() {
        Bukkit.getPluginManager().registerEvents(GameListener(), this)
        Bukkit.getPluginManager().registerEvents(JoinListener(), this)
    }

    companion object {
        @JvmStatic
        lateinit var instance: CTFPlugin
    }

}