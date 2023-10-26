package org.applecraft.applepets.pet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.applecraft.applepets.Main;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permission;

public class Pet implements ConfigurationSerializable {
	
	private String name, description;
    private Permission permission;
    private int idleFrame, walkingFrame;
    
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Permission getPermission() {
        return permission;
    }

    public int getIdleFrame() {
        return idleFrame;
    }

    public int getWalkingFrame() {
        return walkingFrame;
    }
    
    public Pet(String name, String description, String permission, int idleFrame, int walkingFrame) {
        this.name = name;
        this.description = description;
        this.permission = new Permission(permission);
        this.idleFrame = idleFrame;
        this.walkingFrame = walkingFrame;
    }
    
    public Pet(Map<String, Object> map) {
        this.name = (String) map.get("name");
        this.description = (String) map.get("description");
        this.permission = new Permission((String) map.get("permission"));
        if (map.get("idle") != null) this.idleFrame = (int) map.get("idle");
        if (map.get("walking") != null) this.walkingFrame = (int) map.get("walking");
    }
    
    public static Pet deserialize(Map<String, Object> map) {
        return new Pet(map);
    }
    
    public static Pet valueOf(Map<String, Object> map) {
        return new Pet(map);
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("name", name);
        map.put("description", description);
        map.put("permission", permission.toString());
        map.put("walking", walkingFrame);

        return map;
    }
    
    private ItemStack getModel(int frame) {
		ItemStack item = new ItemStack(Material.APPLE, 1); {
            ItemMeta meta = item.getItemMeta();
            
            meta.setCustomModelData(frame);
            meta.setDisplayName("§c" + getName());
            item.setItemMeta(meta);
        }

        return item;
    }
    
    public ItemStack getInvModel(Player player) {
        ItemStack inv = getModel((int) getIdleFrame()); {
            ItemMeta meta = inv.getItemMeta();

            String owned = Main.getInstance().getConfigManager().hasPet(player, this) ? "§2You own this pet" : "§4You do not own this pet";

            meta.setLore(Arrays.asList("", "§a" + getDescription(), "", owned));
            
            inv.setItemMeta(meta);
        	}

        return inv;
    }
    
    public ItemStack getIdleModel() {
        return getModel((int) getIdleFrame());
    }
    
    public ItemStack getWalkingModel() {
        return getModel((int) getWalkingFrame());
    }
}

