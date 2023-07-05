package com.github.onyzn.soundtester.lib;

import com.github.onyzn.soundtester.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

public abstract class ListedMenuPlayer extends ListedMenu implements Listener {

  protected final Player player;
  protected int currentPage;

  public ListedMenuPlayer(Player player, String title, int rows) {
    super(title, rows);
    this.player = player;
    Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
  }

  public Player getPlayer() {
    return player;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public Inventory getCurrentInventory() {
    return this.getPages().get(currentPage);
  }

  public void openNext() {
    if (currentPage < getPages().size() - 1) {
      currentPage++;
      this.open(player, currentPage);
    }
  }

  public void openPrevious() {
    if (currentPage > 0) {
      currentPage--;
      this.open(player, currentPage);
    }
  }

  public void open(int page) {
    currentPage = page;
    this.open(player, currentPage);
  }

  public abstract void onClick(InventoryClickEvent evt);
  public abstract void onClose();

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    if (evt.getPlayer().equals(this.getPlayer())) {
      onClose();
    }
  }

  @EventHandler
  public void onInventoryClose(InventoryCloseEvent evt) {
    if (evt.getInventory().equals(getCurrentInventory()) && evt.getInventory().equals(getCurrentInventory())) {
      onClose();
    }
  }

  @EventHandler
  public void onIntentoryClick(InventoryClickEvent evt) {
    if (!evt.getInventory().equals(getCurrentInventory())) {
      return;
    }

    evt.setCancelled(true);
    if (evt.getClickedInventory() == null || !evt.getClickedInventory().equals(getCurrentInventory())) {
      return;
    }

    if (evt.getCurrentItem() != null && evt.getCurrentItem().getType() != Material.AIR) {
      if (evt.getSlot() == this.getCurrentInventory().getSize() - 4) {
        openNext();
      } else if (evt.getSlot() == this.getCurrentInventory().getSize() - 6) {
        openPrevious();
      } else {
        onClick(evt);
      }
    }
  }
}
