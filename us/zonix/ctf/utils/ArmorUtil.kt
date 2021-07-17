package us.zonix.ctf.utils

import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta

object ArmorUtil {

    fun generate(mat: Material?, color: Color?): ItemStack {
        val armor = ItemStack(mat)
        val meta4 = armor.itemMeta as LeatherArmorMeta
        meta4.color = color
        armor.itemMeta = meta4
        return armor
    }
}