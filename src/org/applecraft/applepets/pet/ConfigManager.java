package org.applecraft.applepets.pet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.applecraft.applepets.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ConfigManager {
	
    private Main plugin;
    private List<Pet> loadedPets;
    private Map<UUID, PetEntity> following;

    public Main getPlugin() {
        return plugin;
    }

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
        this.following = new HashMap<>();
    }

    public List<Pet> getLoadedPets() {
        return loadedPets;
    }

    public Map<UUID, PetEntity> getFollowing() {
        return following;
    }

    @SuppressWarnings("unchecked")
	public void loadPets() {
        if (loadedPets != null && loadedPets.size() > 0) loadedPets.clear();

        FileConfiguration config = plugin.getConfig();

        List<Pet> pets = new ArrayList<>();
        List<Map<?, ?>> maps = config.getMapList("pets");

        for (Map<?, ?> listItem :
             maps) {
            pets.add(new Pet((Map<String, Object>) listItem));
        }

        if (pets.size() == 0) {
            plugin.getLogger().warning("No pets configured!");

            return;
        }

        List<String> names = new ArrayList<>();

        for (Pet pet : pets) {
            if (names.contains(pet.getName())) {
                plugin.getLogger().warning(String.format("Multiple-registration for pet name '%s', disabling all...", pet.getName()));

                for (Pet innerPet :
                        pets) {
                    if (innerPet.getName().equals(pet.getName())) pets.remove(innerPet);
                }

                return;
            }

            names.add(pet.getName());
        }

        loadedPets = pets;

        plugin.getLogger().info(String.format("Loaded %s pets.", String.valueOf(loadedPets.size())));
    }

    public Pet getPet(String name) {
        for (Pet pet : loadedPets) {
            if (pet.getName().equals(name)) return pet;
        }

        return null;
    }

    public boolean hasPet(Player player, Pet pet) {
        if (pet.getPermission() == null) return true;

        return player.hasPermission(pet.getPermission());
    }

    public Pet getPetFromFrame(int frame) {
        for (Pet pet :
                getLoadedPets()) {
            if (pet.getIdleFrame() == (int) frame || pet.getWalkingFrame() == (int) frame) return pet;
        }

        return null;
    }
}
