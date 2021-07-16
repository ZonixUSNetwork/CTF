package us.zonix.ctf.scoreboard

import cc.fyre.proton.scoreboard.construct.ScoreGetter
import org.bukkit.Bukkit
import java.util.LinkedList
import org.bukkit.entity.Player
import us.zonix.ctf.CTF
import us.zonix.ctf.game.State

class CTFScoreGetter : ScoreGetter {


    override fun getScores(scores: LinkedList<String>, player: Player): Array<String?> {
        if (CTF.instance.gameManager.getState() == State.LOBBY) {
            scores.add("§fWaiting for players")
            scores.add("§fPlayers§7: §4" + Bukkit.getOnlinePlayers().size)
        }
        if (CTF.instance.gameManager.getState() == State.GAME) {
            scores.add("§9§lBlue Team:")
            scores.add("  §fPlayers§7: §4" + CTF.instance.gameManager.getBlue())
            scores.add("  §fFlag: §c" + (
                    if (CTF.instance.flagManager.blueFlagHolder == null)
                        "At base"
                    else CTF.instance.flagManager.blueFlagHolder!!.name
                    ))
            scores.add("§c§lRed Team:")
            scores.add("§c  §fPlayers§7: §4" + CTF.instance.gameManager.getRed())
            scores.add("§c  §fFlag: §c" + (
                if (CTF.instance.flagManager.redFlagHolder == null)
                    "At base"
                else CTF.instance.flagManager.redFlagHolder!!.name
            ))
            scores.add(" ")
            scores.add("§fKills: §c" + CTF.instance.gameManager.getKills(player))
            scores.add("§fDeaths: §c" + CTF.instance.gameManager.getDeaths(player))
        }
        if (CTF.instance.gameManager.getState() == State.END) {
            scores.add("§fGame has §cended§f.")
            scores.add(" ")
            scores.add("§cThanks for playing.")
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