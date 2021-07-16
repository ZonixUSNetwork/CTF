package us.zonix.ctf.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ArmorUtil {

    public static ItemStack generate(Material mat, Color color) {
        ItemStack armor = new ItemStack(mat);
        LeatherArmorMeta meta4 = (LeatherArmorMeta) armor.getItemMeta();
        meta4.setColor(color);
        armor.setItemMeta(meta4);
        return armor;
    }

}
