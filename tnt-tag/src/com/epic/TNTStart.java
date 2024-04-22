package com.epic;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TNTStart implements CommandExecutor {
	
	public static Game game = new Game();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length != 0) {
			return false;
		} else if (TNTTag.getInstance().ongoingGame) {
			sender.sendMessage("§cA game is already running");
			return true;
		} else if (TNTTag.getInstance().getPlayerList().size() < 2) {
			sender.sendMessage("§cThere aren't enough people to start");
			return true;
		} else {
			game.main();
			sender.sendMessage("§eThe game has been started!");
			return true;
		}
	}

}
