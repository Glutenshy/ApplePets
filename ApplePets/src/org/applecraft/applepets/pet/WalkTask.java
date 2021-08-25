package org.applecraft.applepets.pet;

import org.applecraft.applepets.Main;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EnumItemSlot;	


public class WalkTask extends BukkitRunnable {

    private Main plugin;
    private Pet pet;

    public Main getPlugin() {
        return plugin;
    }
    
    public WalkTask(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            ConfigManager cm = plugin.getConfigManager();
            if (cm.getFollowing().containsKey(player.getUniqueId())) {
                Entity customPet = cm.getFollowing().get(player.getUniqueId());
                LivingEntity p = (LivingEntity) customPet.getBukkitEntity();
                
                
                double m = p.getVelocity().getX() + p.getVelocity().getZ();
                
                if(m != 0) {
                	customPet.setSlot(EnumItemSlot.f, CraftItemStack.asNMSCopy(pet.getWalkingModel()));
                } else {
                	customPet.setSlot(EnumItemSlot.f, CraftItemStack.asNMSCopy(pet.getIdleModel()));
                }
            }
        }
    }
}
