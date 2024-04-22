package com.Epic;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

public class cTrackerCMD implements TabExecutor {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		final String setup = "setup", reset = "reset";
		if (args.length == 1) {
			List<String> tabCmds = new ArrayList<String>();
			if (setup.startsWith(args[0])) {
				tabCmds.add(setup);
			}
			if (reset.startsWith(args[0])) {
				tabCmds.add(reset);
			}
			return tabCmds;
		} else {
			return null;
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("setup")) {
				ArrayList<Player> players = cTracker.getPlayers();
				ArrayList<Integer> compassTargets = cTracker.getCompassTargets();
				cTracker.getInstance().getServer().getOnlinePlayers().forEach(player -> {
					if (!players.contains(player)) {
						players.add(player);
						compassTargets.add(0);
						
					}
				});
				
				cTracker.setPlayers(players);
				cTracker.setCompassTargets(compassTargets);
				cTracker.setPEnabled(true);
				sender.sendMessage("§aSetup complete!");
				return true;
			} else if (args[0].equalsIgnoreCase("reset")) {
				cTracker.setPlayers(new ArrayList<Player>());
				cTracker.setPEnabled(false);
				sender.sendMessage("§cReset compass tracker");
				return true;
			}

		}
		return false;
	}

}
