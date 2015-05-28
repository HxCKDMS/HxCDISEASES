package com.wiggle1000.HxC.Diseases.com.wiggle1000.HxC.Diseases.Entity;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class EntityVomitFX extends EntityFX{

    public EntityVomitFX(World world, double X, double Y, double Z, double mX, double mY, double mZ) {
        super(world, X, Y, Z, mX, mY, mZ);
        this.particleGravity=0.8f;
        this.setRotation(0, world.rand.nextInt(360));
    }

    @Override
    public void onUpdate(){
        super.onUpdate();
        if(this.ticksExisted>500){
            this.setVelocity(0, 0, 0);
            this.setSize(0.1f, 2f);
        }
    }
}
