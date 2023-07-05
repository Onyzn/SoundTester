package com.github.onyzn.soundtester.lib;

import com.github.onyzn.soundtester.utility.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListedMenu implements Listener {

  private final List<Inventory> pages;
  private int[] availableSlots = {
      10, 11, 12, 13, 14, 15, 16,
      19, 20, 21, 22, 23, 24, 25,
      28, 29, 30, 31, 32, 33, 34
  };

  public ListedMenu(String title, int rows) {
    this.pages = new ArrayList<>();
    this.pages.add(Bukkit.createInventory(null, rows * 9, title));
  }

  public void setAvailableSlots(int... availableSlots) {
    this.availableSlots = availableSlots;
  }

  public void setItems(List<ItemStack> itemsList, Map<Integer, ItemStack> items) {
    int size = pages.get(0).getSize();
    String title = pages.get(0).getTitle();

    int index = 0, page = 0;
    for (ItemStack item : itemsList) {
      if (availableSlots.length == index) {
        this.pages.get(page).setItem(size - 4, ItemUtils.createItemStack(Material.ARROW, "§aIr para página " + (page + 2)));

        index = 0;
        page++;

        if (pages.size() < (page + 1)) {
          Inventory menu = Bukkit.createInventory(null, size, title);
          menu.setItem(size - 6, ItemUtils.createItemStack(Material.ARROW, "§aIr para página " + page));
          pages.add(menu);
        }
      }

      Inventory menu = pages.get(page);

      if (index == 0) {
        items.forEach(menu::setItem);
      }

      menu.setItem(this.availableSlots[index], item);
      index++;
    }
  }

  public List<Inventory> getPages() {
    return pages;
  }

  public void open(Player player, int page) {
    player.openInventory(pages.get(page));
  }
}
