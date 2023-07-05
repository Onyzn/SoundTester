package com.github.onyzn.soundtester.utility;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemUtils {

  public static ItemStack createItemStack(Material material, String displayName, String... lore) {
    ItemStack item = new ItemStack(material);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(displayName);
    if (lore != null) {
      meta.setLore(Arrays.asList(lore));
    }
    item.setItemMeta(meta);
    return item;
  }
}
