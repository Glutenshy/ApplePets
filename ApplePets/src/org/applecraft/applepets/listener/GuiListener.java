package org.applecraft.applepets.listener;

import org.applecraft.applepets.Main;
import org.applecraft.applepets.pet.ConfigManager;
import org.applecraft.applepets.pet.Pet;
import org.applecraft.applepets.pet.PetEntity;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiListener implements Listener {

    private Main plugin;

    public Main getPlugin() {
        return plugin;
    }

    public GuiListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        
        String title = event.getView().getTitle();
        if (title.equals("§4§lA§c§lP§4§lP§c§lL§4§lE§2§lP§a§lE§2§lT§a§lS")) {
            event.setCancelled(true);

            if (item.getType().equals(Material.APPLE)) {
                Pet pet = plugin.getConfigManager().getPet(ChatColor.stripColor(item.getItemMeta().getDisplayName()));

                if (pet == null) return;

                if (!player.hasPermission(pet.getPermission())) {
                    player.sendMessage("§4§lA§c§lP§4§lP§c§lL§4§lE§2§lP§a§lE§2§lT§a§lS §8§l> §cYou do not own this pet!");

                    return;
                } else {
                    ConfigManager cm = plugin.getConfigManager();

                    if (cm.getFollowing().containsKey(player.getUniqueId())) {
                        PetEntity entity = cm.getFollowing().get(player.getUniqueId());

                        entity.killEntity();

                        cm.getFollowing().remove(player.getUniqueId());
                    }

                    PetEntity custom = PetEntity.SPAWN(player, pet);

                    cm.getFollowing().put(player.getUniqueId(), custom);

                    player.sendMessage("§4§lA§c§lP§4§lP§c§lL§4§lE§2§lP§a§lE§2§lT§a§lS §8§l> §c" + pet.getName() + "§a is now following you!");
                    player.closeInventory();
                }
            }
        }
    }
}