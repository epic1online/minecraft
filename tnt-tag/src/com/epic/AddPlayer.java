package com.epic;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddPlayer implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length != 1) {
			return false;
		} else {
			TNTTag instance = TNTTag.getInstance();
			ArrayList<Player> playerList = instance.getPlayerList();
			Player player = Bukkit.getPlayerExact(args[0]);
			if (!playerList.contains(player)) {
				playerList.add(player);
				instance.setPlayerList(playerList);
				if (sender instanceof Player) {
					sender.sendMessage("§a" + args[0] + " has been added to the game");
				}

			} else {
				if (sender instanceof Player) {
					sender.sendMessage("§c" + args[0] + " was already in the game");
				}
			}
			return true;
		}
	}

}
