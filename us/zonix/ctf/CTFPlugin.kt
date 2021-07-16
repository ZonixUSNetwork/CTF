package us.zonix.ctf

import cc.fyre.proton.Proton
import org.bukkit.plugin.java.JavaPlugin
import us.zonix.ctf.manager.GameManager
import us.zonix.ctf.scoreboard.Scoreboard

class CTFPlugin : JavaPlugin() {

    val gameManager: GameManager = GameManager()

    override fun onEnable() {
        instance = this
        Proton.getInstance().commandHandler.registerAll(this);
        Proton.getInstance().scoreboardHandler.configuration = Scoreboard.create();
    }

    companion object {
        @JvmStatic
        lateinit var instance: CTFPlugin
    }

}