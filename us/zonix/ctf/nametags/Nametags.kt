package us.zonix.ctf.nametags

import cc.fyre.proton.nametag.construct.NameTagInfo
import cc.fyre.proton.nametag.provider.NameTagProvider
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import us.zonix.ctf.CTF
import us.zonix.ctf.game.Team
import java.util.*


class Nametags : NameTagProvider("Nametags Provider", 5) {

    override fun fetchNameTag(toRefresh: Player, refreshFor: Player): NameTagInfo {
        //val prefixColor = getNameColor(toRefresh, refreshFor)
        var color: ChatColor? = null

        /*/when (CTF.instance.gameManager.getTeam(toRefresh)) {
            Team.RED -> {
                color = ChatColor.RED
                return createNameTag(color.toString().replace("&", "§"), "")
            }
            Team.BLUE -> {
                color = ChatColor.BLUE
                return createNameTag(color.toString().replace("&", "§"), "")
            }
            Team.NONE -> {
                color = ChatColor.GRAY
                return createNameTag(color.toString().replace("&", "§"), "")
            }
        }*/
        return createNameTag(ChatColor.WHITE.toString(), "")
    }
}