package com.github.onyzn.soundtester.menu;

import com.github.onyzn.soundtester.lib.ListedMenuPlayer;
import com.github.onyzn.soundtester.utility.ItemUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SoundsMenu extends ListedMenuPlayer {

  private Float[] currentPicths;

  public SoundsMenu(Player player) {
    this(player, 0, null);
  }

  public SoundsMenu(Player player, int page, Float[] currentPicths) {
    super(player, "Sound Tester",6);

    Sound[] values = Sound.values();
    if (currentPicths == null) {
      this.currentPicths = new Float[values.length];
      for (int i = 0; i < values.length; i++) {
        this.currentPicths[i] = 1.0F;
      }
    } else {
      this.currentPicths = currentPicths;
    }

    List<ItemStack> items = new ArrayList<>();
    for (int i = 0; i < values.length; i++) {
      items.add(ItemUtils.createItemStack(Material.GREEN_RECORD,
          "§a" + values[i].name(),
          "",
          "§7Clique §6shift + direito§7 para aumentar o tom.",
          "§7Clique §6shift + esquerdo§7 para diminuir o tom.",
          "",
          "§fTom atual: §a" + this.currentPicths[i],
          "",
          "§aDrop para ver no chat."));
    }
    
    setItems(items, new HashMap<>());
    open(page);
  }

  @Override
  public void onClick(InventoryClickEvent evt) {
    Sound[] values = Sound.values();
    for (int i = 0; i < values.length; i++) {
      Sound sound = values[i];
      if (evt.getCurrentItem().getItemMeta().getDisplayName().equals("§a" + sound.name())) {
        if (evt.getClick() == ClickType.SHIFT_RIGHT) {
          currentPicths[i] = Math.min(currentPicths[i] + 0.5F, 2F);
        } else if (evt.getClick() == ClickType.SHIFT_LEFT) {
          currentPicths[i] = Math.max(currentPicths[i] - 0.5F, 0.5F);
        } else if (evt.getClick() == ClickType.LEFT || evt.getClick() == ClickType.RIGHT) {
          player.playSound(player.getLocation(), sound, 1F, currentPicths[i]);
        } else if (evt.getClick() == ClickType.DROP) {
          String text = sound.name() + " " + currentPicths[i];
          TextComponent component = new TextComponent(text);
          component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§7Copiar")));
          component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, text));

          player.spigot().sendMessage(component);
          player.closeInventory();
          return;
        }

        new SoundsMenu(player, currentPage, currentPicths);
        return;
      }
    }
  }

  @Override
  public void onClose() {
    currentPicths = null;
    HandlerList.unregisterAll(this);
  }
}
