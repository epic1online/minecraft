package com.epic;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpectate implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length > 3 || !(sender instanceof Player)) {
			return false;
		} else if (args.length == 3) {
			World world = ((Player) sender).getWorld();
			double x = Double.parseDouble(args[0]);
			double y = Double.parseDouble(args[1]);
			double z = Double.parseDouble(args[2]);
			TNTTag.getInstance().setSpectate(new Location(world, x, y, z));
			sender.sendMessage("§aThe spectator location has been set to " + x + " " + y + " " + z);
			
			return true;
		} else if (args.length == 0) {
			TNTTag.getInstance().setSpectate(((Player) sender).getLocation());
			sender.sendMessage("§aThe spectator location has been set to your location");
			return true;
		} else {
			return false;
		}
	}

}
