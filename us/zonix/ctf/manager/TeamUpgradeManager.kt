package us.zonix.ctf.manager

import us.zonix.ctf.teamupgrades.Upgrade

class TeamUpgradeManager {

    var redUpgrades: ArrayList<Upgrade> = ArrayList()
    var blueUpgrades: ArrayList<Upgrade> = ArrayList()

    fun addRedUpgrade(upgrade: Upgrade) {
        redUpgrades.add(upgrade)
    }

    fun addBlueUpgrade(upgrade: Upgrade) {
        blueUpgrades.add(upgrade)
    }

}