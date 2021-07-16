package us.zonix.ctf.scoreboard

import cc.fyre.proton.scoreboard.construct.ScoreGetter
import org.bukkit.Bukkit
import java.util.LinkedList
import org.bukkit.entity.Player
import us.zonix.ctf.CTFPlugin
import us.zonix.ctf.game.State

class CTFScoreGetter : ScoreGetter {


    override fun getScores(scores: LinkedList<String>, player: Player): Array<String?> {
        if (CTFPlugin.instance.gameManager.getState() == State.GAME) {
            scores.add("§fWaiting for players")
            scores.add("§fPlayers§7: §4" + Bukkit.getOnlinePlayers().size)
        }
        if (CTFPlugin.instance.gameManager.getState() == State.GAME) {
            scores.add("§9§lBlue Team:")
            scores.add("  §fPlayers§7: §f" + CTFPlugin.instance.gameManager.getBlue())
            scores.add("  §fFlag: §c----")
            scores.add("§c§lRed Team:")
            scores.add("§c  §fPlayers§7: §f" + CTFPlugin.instance.gameManager.getRed())
            scores.add("§c  §fFlag: §c----")
            scores.add(" ")
            scores.add("§fKills: §c" + CTFPlugin.instance.gameManager.getKills(player))
            scores.add("§fDeaths: §c" + CTFPlugin.instance.gameManager.getDeaths(player))
        }
        if (!scores.isEmpty()) {
            scores.addFirst("&m&7&m--------------------")
            scores.add(" ")
            scores.add("&7www.zonix.us")
            scores.add("&9&7&m--------------------")
        }
        return arrayOfNulls(0)
    }
}