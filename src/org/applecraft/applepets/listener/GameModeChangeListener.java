package org.applecraft.applepets.listener;

import org.applecraft.applepets.Main;
import org.applecraft.applepets.pet.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import net.minecraft.world.entity.Entity;

public class GameModeChangeListener implements Listener {

    private Main plugin;

    public Main getPlugin() {
        return plugin;
    }

    public GameModeChangeListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChangeGameMode(final PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        ConfigManager cm = plugin.getConfigManager();
        
        if (cm.getFollowing().containsKey(player.getUniqueId())) {
            Entity customPet = cm.getFollowing().get(player.getUniqueId());

            customPet.killEntity();

            cm.getFollowing().remove(player.getUniqueId());
            
            player.sendMessage("§4§lA§c§lP§4§lP§c§lL§4§lE§2§lP§a§lE§2§lT§a§lS §8§l> §aYour pet is no longer following you.");
        }
    }
}