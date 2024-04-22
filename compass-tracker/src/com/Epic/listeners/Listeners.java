package com.Epic.listeners;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.Epic.cTracker;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Listeners implements Listener {

	@EventHandler(ignoreCancelled = false)
	public void onClick(PlayerInteractEvent event) {

		if (event.getHand() == EquipmentSlot.HAND
				&& event.getPlayer().getEquipment().getItemInMainHand().getType() == Material.COMPASS
				&& cTracker.isPEnabled()) {

			Player e = event.getPlayer();
			ArrayList<Player> players = cTracker.getPlayers();
			ArrayList<Integer> compassTargets = cTracker.getCompassTargets();

			if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {

				int index = compassTargets.get(players.indexOf(e));
				index++;

				if (index >= players.size()) {
					index = 0;
				}
				if (players.get(index) == e) {
					index++;
				}
				if (index >= players.size()) {
					index = 0;
				}
				compassTargets.set(players.indexOf(e), index);
				cTracker.setCompassTargets(compassTargets);

				Player target = players.get(compassTargets.get(players.indexOf(e)));

				e.setCompassTarget(target.getLocation());

				e.spigot().sendMessage(ChatMessageType.ACTION_BAR,
						new TextComponent("§aNow targetting " + target.getName()));
			} else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

				Player target = players.get(compassTargets.get(players.indexOf(e)));
				e.setCompassTarget(target.getLocation());
				e.spigot().sendMessage(ChatMessageType.ACTION_BAR,
						new TextComponent("§a" + target.getName() + "'s location has been updated"));
			}

		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		ArrayList<Player> players = cTracker.getPlayers();
		ArrayList<Integer> compassTargets = cTracker.getCompassTargets();

		if (!players.contains(event.getPlayer())) {
			players.add(event.getPlayer());
			compassTargets.add(0);
		}

		cTracker.setPlayers(players);
		cTracker.setCompassTargets(compassTargets);
	}

}
