package org.applecraft.applepets.gui;

import org.applecraft.applepets.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


public abstract class Gui {

    private Main plugin;

    public Main getPlugin() {
        return plugin;
    }

    public Gui(Main plugin) {
        this.plugin = plugin;
    }

    abstract Inventory getInventory();

    public void open(Player... players) {
        for (Player player :
                players) {
            player.openInventory(getInventory());
        }
    }
}
