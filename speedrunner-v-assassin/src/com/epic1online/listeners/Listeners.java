package com.epic1online.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.epic1online.SpeedrunnerAssassin;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Listeners implements Listener {

	@EventHandler
	public void onMovement(PlayerMoveEvent event) {
		if (SpeedrunnerAssassin.enabled && SpeedrunnerAssassin.frozen
				&& SpeedrunnerAssassin.assassins.contains(event.getPlayer().getName())
				&& SpeedrunnerAssassin.frozenPlayer == event.getPlayer()) {
			double x = event.getFrom().getX(), y = event.getFrom().getY(), z = event.getFrom().getZ();
			float yaw = event.getTo().getYaw(), pitch = event.getTo().getPitch();
			event.setTo(new Location(event.getPlayer().getWorld(), x, y, z, yaw, pitch));
		}

	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if (SpeedrunnerAssassin.enabled) {
			if ((SpeedrunnerAssassin.assassins.contains(event.getDamager().getName())
					&& event.getDamager() == SpeedrunnerAssassin.frozenPlayer && SpeedrunnerAssassin.enabled
					&& SpeedrunnerAssassin.frozen)
					|| (SpeedrunnerAssassin.assassins.contains(((Player) (event.getEntity())).getName())
							&& (Player) event.getEntity() == SpeedrunnerAssassin.frozenPlayer
							&& SpeedrunnerAssassin.enabled && SpeedrunnerAssassin.frozen)) {
				event.setCancelled(true);
			} else {
				Entity entity = event.getEntity();
				if (entity instanceof Player
						&& SpeedrunnerAssassin.speedrunners.contains(((Player) entity).getName())) {
					Entity damager = event.getDamager();
					if (damager instanceof Player
							&& SpeedrunnerAssassin.assassins.contains(((Player) damager).getName())) {
						event.setDamage(1000);
					}
				}
			}
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		if (SpeedrunnerAssassin.speedrunners.contains(event.getEntity().getName()) && SpeedrunnerAssassin.enabled
				&& !SpeedrunnerAssassin.assassins.isEmpty()) {
			event.getEntity().performCommand("spdasnEnable");
		}
	}

	@EventHandler(ignoreCancelled = false)
	public void onClick(PlayerInteractEvent event) {

		if (SpeedrunnerAssassin.enabled && event.getHand() == EquipmentSlot.HAND
				&& event.getPlayer().getEquipment().getItemInMainHand().getType() == Material.COMPASS
				&& SpeedrunnerAssassin.assassins.contains(event.getPlayer().getName())) {

			Player player = event.getPlayer();

			if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {

				int index = SpeedrunnerAssassin.compassTarget
						.get(SpeedrunnerAssassin.assassins.indexOf(player.getName()));
				index++;
				if (index >= SpeedrunnerAssassin.speedrunners.size()) {
					index = 0;
				}
				SpeedrunnerAssassin.compassTarget.set(SpeedrunnerAssassin.assassins.indexOf(player.getName()), index);

				Player target = SpeedrunnerAssassin.getInstance().getServer()
						.getPlayer(SpeedrunnerAssassin.speedrunners.get(SpeedrunnerAssassin.compassTarget
								.get(SpeedrunnerAssassin.assassins.indexOf(player.getName()))));

				player.setCompassTarget(target.getLocation());
				player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
						new TextComponent("Now targetting " + target.getName()));

			} else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

				Player target = SpeedrunnerAssassin.getInstance().getServer()
						.getPlayer(SpeedrunnerAssassin.speedrunners.get(SpeedrunnerAssassin.compassTarget
								.get(SpeedrunnerAssassin.assassins.indexOf(player.getName()))));
				player.setCompassTarget(target.getLocation());
				player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
						new TextComponent(target.getName() + "'s location has been updated"));

			}

		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (SpeedrunnerAssassin.assassins.contains(event.getPlayer().getName())
				&& event.getPlayer() == SpeedrunnerAssassin.frozenPlayer && SpeedrunnerAssassin.enabled
				&& SpeedrunnerAssassin.frozen) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (SpeedrunnerAssassin.assassins.contains(event.getPlayer().getName())
				&& event.getPlayer() == SpeedrunnerAssassin.frozenPlayer && SpeedrunnerAssassin.enabled
				&& SpeedrunnerAssassin.frozen) {
			event.setCancelled(true);
		}
	}

}
