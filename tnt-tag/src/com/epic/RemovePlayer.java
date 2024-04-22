package com.epic;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemovePlayer implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length != 1) {
			return false;
		} else {
			TNTTag instance = TNTTag.getInstance();
			ArrayList<Player> playerList = instance.getPlayerList();
			ArrayList<Player> tntList = instance.getTntList();
			Player player = Bukkit.getPlayerExact(args[0]);
			if (playerList.contains(player)) {
				if (tntList.contains(player)) {
					tntList.remove(player);
				}
				playerList.remove(player);
				instance.setPlayerList(playerList);
				instance.setTntList(tntList);
				if (sender instanceof Player) {
					sender.sendMessage("§a" + args[0] + " has been removed from the game");
				}

			} else {
				if (sender instanceof Player) {
					sender.sendMessage("§c" + args[0] + " was not in the game");
				}
			}
			return true;
		}
	}

}
