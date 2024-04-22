package com.epic.listeners;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import com.epic.TNTTag;

public class Listeners implements Listener {

	private final TNTTag instance = TNTTag.getInstance();

	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player && instance.ongoingGame) {
			ArrayList<Player> tntList = instance.getTntList();
			ArrayList<Player> playerList = instance.getPlayerList();
			Player wasHit = (Player) e.getEntity();
			Player damager = (Player) e.getDamager();
			e.setDamage(0);

			if (tntList.contains(damager) && !tntList.contains(wasHit) && playerList.contains(wasHit)) {

				tntList.remove(damager);
				damager.getInventory().clear();
				damager.removePotionEffect(PotionEffectType.SPEED);
				instance.playerSpeed.apply(damager);
				
				tntList.add(wasHit);
				wasHit.getEquipment().setHelmet(new ItemStack(Material.TNT));
				
				for (int i = 0; i < 9; i++) {
					wasHit.getInventory().setItem(i, new ItemStack(Material.TNT));
				}
				
				wasHit.playSound(wasHit.getLocation(), Sound.BLOCK_NOTE_BELL, 1, 1);
				wasHit.removePotionEffect(PotionEffectType.SPEED);
				instance.tntSpeed.apply(wasHit);
				instance.setTntList(tntList);
				
				instance.getServer().broadcastMessage("§a" + wasHit.getName() + " was given a TNT by " + damager.getName() + "!");
				damager.sendMessage("§eYou gave §a" + wasHit.getName() + " §ea TNT!");
				wasHit.sendMessage("§4You got a TNT!");
//				wasHit.sendTitle("§4You got a TNT!", null, 0, 35, 10);
			}
		}
	}

	@EventHandler
	public void inventoryClick(InventoryClickEvent e) {
		if (instance.getPlayerList().contains(e.getWhoClicked()) && instance.ongoingGame) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void itemDrop(PlayerDropItemEvent e) {
		if (instance.getPlayerList().contains(e.getPlayer()) && instance.ongoingGame) {
			e.setCancelled(true);
		}
		
	}

}
