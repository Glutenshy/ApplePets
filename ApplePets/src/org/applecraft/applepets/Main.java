package org.applecraft.applepets;

import org.applecraft.applepets.commands.PetsCommand;
import org.applecraft.applepets.commands.PetsTabCompleter;
import org.applecraft.applepets.listener.DimensionListener;
import org.applecraft.applepets.listener.EntityClickListener;
import org.applecraft.applepets.listener.GameModeChangeListener;
import org.applecraft.applepets.listener.GuiListener;
import org.applecraft.applepets.listener.QuitListener;
import org.applecraft.applepets.pet.ConfigManager;
import org.applecraft.applepets.pet.Pet;
import org.applecraft.applepets.pet.WalkTask;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private Main plugin;
    private ConfigManager configManager;
    
    public static Main getInstance() {
    	return instance;
    }

    public Main getPlugin() {
        return plugin;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public void reload() throws Exception {
        reloadConfig();

        if (configManager != null) configManager.loadPets();
    }

    private void registerCommand(String pets, CommandExecutor exec, TabCompleter tab) {
        getCommand(pets).setExecutor(exec);
        getCommand(pets).setTabCompleter(tab);
    }
    
    private void registerListeners(Listener... listeners) {
        for (Listener listener :
                listeners) {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
        }
    }

    @SuppressWarnings("deprecation")
	@Override
    public void onEnable() {
        plugin = this;
        instance = this;

        getConfig().options().copyDefaults(true);
        saveConfig();

        ConfigurationSerialization.registerClass(Pet.class);
        configManager = new ConfigManager(this);
        configManager.loadPets();
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new WalkTask(this), 0, 1);
        { registerCommand("pets", new PetsCommand(this), new PetsTabCompleter()); }
        { registerListeners(new GuiListener(this), new QuitListener(this), new DimensionListener(this), new EntityClickListener(this), new GameModeChangeListener(this)); }
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }
}

