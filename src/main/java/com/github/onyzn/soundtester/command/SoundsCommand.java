package com.github.onyzn.soundtester.command;

import com.github.onyzn.soundtester.menu.SoundsMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SoundsCommand extends Command {

  public SoundsCommand() {
    super("sounds");
  }

  @Override
  public boolean execute(CommandSender commandSender, String s, String[] strings) {
    if (commandSender instanceof ConsoleCommandSender) {
      commandSender.sendMessage("§cApenas jogadores podem executar este comando.");
      return true;
    }

    new SoundsMenu((Player) commandSender);
    return true;
  }
}
