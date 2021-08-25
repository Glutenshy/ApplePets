package org.applecraft.applepets.listener;

import org.applecraft.applepets.Main;
import org.applecraft.applepets.pet.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.minecraft.world.entity.Entity;

public class QuitListener implements Listener {

    private Main plugin;

    public Main getPlugin() {
        return plugin;
    }

    public QuitListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ConfigManager cm = plugin.getConfigManager();

        if (cm.getFollowing().containsKey(player.getUniqueId())) {
            Entity customPet = cm.getFollowing().get(player.getUniqueId());

            customPet.killEntity();

            cm.getFollowing().remove(player.getUniqueId());
        }
    }
}