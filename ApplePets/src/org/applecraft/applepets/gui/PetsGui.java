package org.applecraft.applepets.gui;

import org.applecraft.applepets.Main;
import org.applecraft.applepets.pet.Pet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


public class PetsGui extends PlayerGui {

    public PetsGui(Main plugin, Player player) {
        super(plugin, player);
    }

    @Override
    Inventory getInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "§4§lA§c§lP§4§lP§c§lL§4§lE§2§lP§a§lE§2§lT§a§lS");

        for (Pet pet :
                getPlugin().getConfigManager().getLoadedPets()) {
            inventory.addItem(pet.getInvModel(player));
        }

        return inventory;
    }
}
