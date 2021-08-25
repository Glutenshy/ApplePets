package org.applecraft.applepets.listener;

import org.applecraft.applepets.Main;
import org.applecraft.applepets.pet.ConfigManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityClickListener implements Listener {

    private Main plugin;

    public Main getPlugin() {
        return plugin;
    }

    public EntityClickListener(Main plugin) {
        this.plugin = plugin;
    }

	@EventHandler
    public void onClickPet(final PlayerInteractAtEntityEvent event) {
        final Player player = event.getPlayer();

        if (event.getRightClicked().getType().equals(EntityType.SKELETON)) {
            ConfigManager cm = plugin.getConfigManager();

            if (cm.getFollowing().containsKey(player.getUniqueId())) {
        		String name = event.getRightClicked().getCustomName();
        		new BukkitRunnable() {
        			@Override
        			public void run() {
        				event.getRightClicked().setCustomName(name);
        			}
        		}.runTask(plugin);
            }
        }
	}
}