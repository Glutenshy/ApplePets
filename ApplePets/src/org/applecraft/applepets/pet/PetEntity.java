package org.applecraft.applepets.pet;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStroll;
import net.minecraft.world.entity.monster.EntitySkeleton;

public class PetEntity extends EntitySkeleton {
	
	public UUID player;
	
	public PetEntity(WorldServer world, Player p, Pet pet) {
		super(EntityTypes.aB, ((CraftWorld) world.getWorld()).getHandle());
		player = p.getUniqueId();
		this.setPosition(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
		
		LivingEntity entity = (LivingEntity) this.getBukkitEntity();
		
        entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999999, 1, true));
        this.setInvulnerable(true);
        this.setSilent(true);
        this.setCanPickupLoot(false);
        this.setCustomNameVisible(false);
        this.setCustomName(new ChatComponentText(player.toString()));
        
        this.setSlot(EnumItemSlot.f, CraftItemStack.asNMSCopy(pet.getIdleModel()));

		this.setGoalTarget((EntityLiving)((CraftPlayer)p).getHandle(), TargetReason.CUSTOM, true);
	}
	
	@Override
	public void initPathfinder() {
		this.bP.a(0, new PathfinderGoalFloat(this));
		this.bP.a(1, new PathfinderGoalRandomLookaround(this));
		this.bP.a(2, new PathfinderGoalRandomStroll(this, 0.75));
		this.bP.a(3, new PathfinderGoalPet(this, 1.5, 15));
	}
	
    public static PetEntity SPAWN(Player p, Pet pet) {
        WorldServer w = ((CraftWorld) p.getWorld()).getHandle();
        
        final PetEntity customPet = new PetEntity(w, p, pet);
        w.addEntity(customPet, SpawnReason.CUSTOM);
        customPet.setLocation(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());

        return customPet;
    }
	
    @SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public static void registerEntity() {
        try {
			Class<EntityTypes> entityTypeClass = EntityTypes.class;

            Field c = entityTypeClass.getDeclaredField("c");
            c.setAccessible(true);
            HashMap<String, Class<?>> c_map = (HashMap) c.get(null);
            c_map.put("customPet", PetEntity.class);

            Field d = entityTypeClass.getDeclaredField("d");
            d.setAccessible(true);
            HashMap<Class<?>, String> d_map = (HashMap) d.get(null);
            d_map.put(PetEntity.class, "customPet");

            Field e = entityTypeClass.getDeclaredField("e");
            e.setAccessible(true);
            HashMap<Integer, Class<?>> e_map = (HashMap) e.get(null);
            e_map.put(Integer.valueOf(63), PetEntity.class);

            Field f = entityTypeClass.getDeclaredField("f");
            f.setAccessible(true);
            HashMap<Class<?>, Integer> f_map = (HashMap) f.get(null);
            f_map.put(PetEntity.class, Integer.valueOf(63));

            Field g = entityTypeClass.getDeclaredField("g");
            g.setAccessible(true);
            HashMap<String, Integer> g_map = (HashMap) g.get(null);
            g_map.put("customPet", Integer.valueOf(63));

        } catch (Exception exc) {
            Field d;
            int d_map;
            Method[] e;
            Class[] paramTypes = { Class.class, String.class, Integer.TYPE };
            try {
                Method method = EntityTypes.class.getDeclaredMethod(
                        "addMapping", paramTypes);
                method.setAccessible(true);
            } catch (Exception ex) {
                exc.addSuppressed(ex);
                try {
                    d_map = (e = EntityTypes.class.getDeclaredMethods()).length;
                    for (int d1 = 0; d1 < d_map; d1++) {
                        Method method = e[d1];
                        if (Arrays.equals(paramTypes,
                                method.getParameterTypes())) {
                            method.invoke(null, new Object[] {
                                    PetEntity.class,
                                    "customPet", Integer.valueOf(63) });
                        }
                    }
                } catch (Exception exe) {
                    exc.addSuppressed(exe);
                }
                exc.printStackTrace();
            }
        }
    }
	
}
