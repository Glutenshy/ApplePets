package org.applecraft.applepets.pet;

import java.util.EnumSet;

import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;

public class PathfinderGoalPet extends PathfinderGoal {
	
    private final EntityInsentient a;
    private EntityLiving b;
 
    private final double f;
    private final double g;
 
    private double c;
    private double d;
    private double e;
	
	public PathfinderGoalPet(EntityInsentient a, double speed, float distance) {
		this.a = a;
	    this.f = speed;
	    this.g = distance;
	    this.a(EnumSet.of(Type.a));
		
	}
	@Override
    public boolean a() {
    	this.b = this.a.getGoalTarget();
    	
    	 if (this.b == null) {
             return false;
         } else if (this.a.getDisplayName() == null) {
             return false;
         } else if (!(this.a.getDisplayName().toString().contains(this.b.getUniqueIDString()))) {
             return false;                                                             
        
         } else if (this.b.f(this.a) > (double) (this.g * this.g)) {
             a.setPosition(this.b.locX(), this.b.locY(), this.b.locZ());
             return true;
         } else {        
             this.c = this.b.locX();
             this.d = this.b.locY();
             this.e = this.b.locZ();
        
             return true;
         }
    }

    @Override
    public void c() {
        this.a.getNavigation().a(this.c, this.d, this.e, this.f);
    }
    @Override
    public boolean b() {
        return !this.a.getNavigation().m() && this.b.f(this.a) < (double) (this.g * this.g);
    }
 
    @Override
    public void d() {
        this.b = null;
    }

}
