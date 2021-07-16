package us.zonix.ctf.scoreboard

import cc.fyre.proton.scoreboard.config.ScoreboardConfiguration
import cc.fyre.proton.scoreboard.construct.TitleGetter

//import cc.fyre.proton.scoreboard.config.ScoreboardConfiguration;;
//import cc.fyre.proton.scoreboard.construct.TitleGetter;


object Scoreboard {

    @JvmStatic
    fun create(): ScoreboardConfiguration {
        val configuration = ScoreboardConfiguration()
        configuration.titleGetter = TitleGetter("&4&lCapture The Flag")
        configuration.scoreGetter = CTFScoreGetter()
        return configuration
    }
}