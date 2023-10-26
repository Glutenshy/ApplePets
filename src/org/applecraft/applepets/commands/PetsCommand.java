package org.applecraft.applepets.commands;

import org.applecraft.applepets.Main;
import org.applecraft.applepets.gui.PetsGui;
import org.applecraft.applepets.pet.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.minecraft.world.entity.Entity;
import org.bukkit.entity.Player;

public class PetsCommand extends CommandBase {

    private Main plugin;

    public Main getPlugin() {
        return plugin;
    }

    public PetsCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    void execute(CommandSender sender, Command command, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                new PetsGui(plugin, player).open(player);
            } else {
                sender.sendMessage("Console has no access to this command!");
            }
        } else {
            if (args[0].equalsIgnoreCase("remove")) {
                Player player = (Player) sender;
                ConfigManager configManager = plugin.getConfigManager();

                if (configManager.getFollowing().containsKey(player.getUniqueId())) {
                    Entity as = configManager.getFollowing().get(player.getUniqueId());

                    as.killEntity();

                    configManager.getFollowing().remove(player.getUniqueId());
                    sender.sendMessage("§4§lA§c§lP§4§lP§c§lL§4§lE§2§lP§a§lE§2§lT§a§lS §8§l> §aYour pet is no longer following you.");
                } else {
                	sender.sendMessage("§4§lA§c§lP§4§lP§c§lL§4§lE§2§lP§a§lE§2§lT§a§lS §8§l> §cYou do not have a pet equipped.");
                }
            }
            if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("applepets.reload")) {
                try {
                    plugin.reload();
                } catch (Exception e) {
                    sender.sendMessage("§cAn error occurred while reloading Pets. The error has been logged to console.");

                    e.printStackTrace();
                }

                sender.sendMessage("§aReloaded ApplePets.");
            }
        }
    }
}