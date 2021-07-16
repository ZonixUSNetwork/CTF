package us.zonix.ctf.manager

import org.bukkit.Color
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import us.zonix.ctf.game.Team
import us.zonix.ctf.utils.ArmorUtil
import us.zonix.ctf.utils.ItemBuilder

class KitManager {

    fun giveRedKit(player: Player, team: Team) {
        var sword = ItemBuilder(Material.STONE_SWORD, 1).setName("Sword").toItemStack()
        var bread = ItemBuilder(Material.BREAD, 16).toItemStack()

        var helmet = ArmorUtil.generate(Material.LEATHER_HELMET, Color.RED)
        var chestplate = ArmorUtil.generate(Material.LEATHER_CHESTPLATE, Color.RED)
        var leggings = ArmorUtil.generate(Material.LEATHER_LEGGINGS, Color.RED)
        var boots = ArmorUtil.generate(Material.LEATHER_BOOTS, Color.RED)
        player.inventory.helmet = helmet
        player.inventory.chestplate = chestplate
        player.inventory.leggings = leggings
        player.inventory.boots = boots

        player.inventory.setItem(0, sword)
        player.inventory.setItem(8, bread)
    }

    fun giveBlueKit(player: Player, team: Team) {
        var sword = ItemBuilder(Material.STONE_SWORD, 1).setName("§fSword").toItemStack()
        var bread = ItemBuilder(Material.BREAD, 16).toItemStack()

        var helmet = ArmorUtil.generate(Material.LEATHER_HELMET, Color.BLUE)
        var chestplate = ArmorUtil.generate(Material.LEATHER_CHESTPLATE, Color.BLUE)
        var leggings = ArmorUtil.generate(Material.LEATHER_LEGGINGS, Color.BLUE)
        var boots = ArmorUtil.generate(Material.LEATHER_BOOTS, Color.BLUE)
        player.inventory.helmet = helmet
        player.inventory.chestplate = chestplate
        player.inventory.leggings = leggings
        player.inventory.boots = boots

        player.inventory.setItem(0, sword)
        player.inventory.setItem(8, bread)
    }

    fun giveRedFlag(player: Player, team: Team) {
        var helmet = ArmorUtil.generate(Material.LEATHER_HELMET, Color.BLUE)
        var chestplate = ArmorUtil.generate(Material.LEATHER_CHESTPLATE, Color.BLUE)
        var leggings = ArmorUtil.generate(Material.LEATHER_LEGGINGS, Color.BLUE)
        var boots = ArmorUtil.generate(Material.LEATHER_BOOTS, Color.BLUE)
        var wool = ItemBuilder(Material.WOOL).setDyeColor(DyeColor.RED).toItemStack()

        player.inventory.helmet = wool
        player.inventory.chestplate = chestplate
        player.inventory.leggings = leggings
        player.inventory.boots = boots

        for (int in 0..8) {
            player.inventory.setItem(int, wool)
        }
    }

    fun giveBlueFlag(player: Player, team: Team) {
        var helmet = ArmorUtil.generate(Material.LEATHER_HELMET, Color.RED)
        var chestplate = ArmorUtil.generate(Material.LEATHER_CHESTPLATE, Color.RED)
        var leggings = ArmorUtil.generate(Material.LEATHER_LEGGINGS, Color.RED)
        var boots = ArmorUtil.generate(Material.LEATHER_BOOTS, Color.RED)
        var wool = ItemBuilder(Material.WOOL).setDyeColor(DyeColor.BLUE).toItemStack()

        player.inventory.helmet = wool
        player.inventory.chestplate = chestplate
        player.inventory.leggings = leggings
        player.inventory.boots = boots

        for (int in 0..8) {
            player.inventory.setItem(int, wool)
        }
    }

}