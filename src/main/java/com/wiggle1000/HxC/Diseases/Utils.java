package com.wiggle1000.HxC.Diseases;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class Utils {

    public static EntityLivingBase GetTargetEntityLiving(World world, EntityPlayer player, int scanRadius)
    {

        double targetDistance = Math.pow(scanRadius,2);

        EntityLivingBase target = null;
        //int tryCount = 0;
        List lst = world.getEntitiesWithinAABBExcludingEntity(player, AxisAlignedBB.getBoundingBox(player.posX - scanRadius, player.posY - scanRadius, player.posZ - scanRadius, player.posX + scanRadius, player.posY + scanRadius, player.posZ + scanRadius));
        for (Object aLst : lst) {
            Entity ent = (Entity) aLst;
            if (ent instanceof EntityLivingBase && ent.boundingBox != null) {
                float distance = player.getDistanceToEntity(ent) + 0.1f;
                Vec3 look = player.getLookVec();
                Vec3 targetVec = Vec3.createVectorHelper(player.posX + look.xCoord * distance, player.getEyeHeight() + player.posY + look.yCoord * distance, player.posZ + look.zCoord * distance);
                if (ent.boundingBox.isVecInside(targetVec)) {
                    if (distance < targetDistance && distance > 0) {
                        targetDistance = distance;
                        target = (EntityLivingBase) ent;
                        System.out.println(ent);
                    }
                }
            }
        }
        if( target!=null && target.canEntityBeSeen(player)) {
            return target;
        }
        return null;
    }
}
