package org.applecraft.applepets.gui;

import org.applecraft.applepets.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


public abstract class PlayerGui extends Gui {

    private Player player;

    public Player getPlayer() {
        return player;
    }

    public PlayerGui(Main plugin, Player player) {
        super(plugin);

        this.player = player;
    }

    abstract Inventory getInventory(Player player);

    @Override
    Inventory getInventory() {
        return getInventory(player);
    }
}

