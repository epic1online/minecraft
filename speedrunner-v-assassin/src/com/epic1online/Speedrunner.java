package com.epic1online;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public class Speedrunner implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (args.length != 2) {
			return false;
		}
		Server server = SpeedrunnerAssassin.getInstance().getServer();
		if (args[0].equalsIgnoreCase("add")) {
			if (SpeedrunnerAssassin.speedrunners.contains(args[1])) {
				sender.sendMessage(args[1] + " is already a speedrunner");
			} else if (!server.getOnlinePlayers().contains(server.getPlayer(args[1]))) {
				sender.sendMessage(args[1] + " is not online");
			} else {
				if (SpeedrunnerAssassin.assassins.contains(args[1])) {
					SpeedrunnerAssassin.compassTarget.remove(SpeedrunnerAssassin.assassins.indexOf(args[1]));
					SpeedrunnerAssassin.assassins.remove(args[1]);
					sender.sendMessage("Removed " + args[1] + " from assassins");
				}
				SpeedrunnerAssassin.speedrunners.add(args[1]);
				sender.sendMessage("Added " + args[1] + " to speedrunners");
			}
		} else if (args[0].equalsIgnoreCase("remove")) {
			if (!SpeedrunnerAssassin.speedrunners.contains(args[1])) {
				sender.sendMessage(args[1] + " is not a speedrunner");
			} else {
				SpeedrunnerAssassin.speedrunners.remove(args[1]);
				sender.sendMessage("Removed " + args[1] + " from speedrunners");
			}
		} else {
			return false;
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 1) {
			List<String> tabCmds = new ArrayList<>();
			tabCmds.add("add");
			tabCmds.add("remove");
			return tabCmds;
		} else {
			return Collections.emptyList();
		}
	}

}
