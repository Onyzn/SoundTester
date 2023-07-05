package com.github.onyzn.soundtester;

import com.github.onyzn.soundtester.command.SoundsCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Main extends JavaPlugin {

  private static Main instance;

  @Override
  public void onLoad() {
    instance = this;
  }

  @Override
  public void onEnable() {
    try {
      Command command = new SoundsCommand();

      SimpleCommandMap scm = (SimpleCommandMap) Bukkit.getServer().getClass()
          .getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());
      scm.register(command.getName(), command.getName(), command);
    } catch (ReflectiveOperationException ex) {
      getLogger().log(Level.SEVERE, "Command registration error: ", ex);
    }

    getLogger().info("Plugin ativado.");
  }

  public static Main getInstance() {
    return instance;
  }
}
