package com.wiggle1000.HxC.Diseases;

import HxCKDMS.HxCCore.Handlers.NBTFileIO;
import HxCKDMS.HxCCore.HxCCore;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.io.File;

public class myEventHandler {
    @SubscribeEvent
    public void OnLivingUpdate(LivingEvent.LivingUpdateEvent event){
        if(event.entityLiving instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            String UUID = player.getUniqueID().toString();
            File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");
            NBTTagCompound diseases = NBTFileIO.getNbtTagCompound(CustomPlayerData, "Diseases");
            if (diseases.getBoolean("Swine Flu")){
                if(player.worldObj.rand.nextInt(250)==1){
                    player.attackEntityFrom(new DamageSource("sflu").setDamageBypassesArmor(), 1);
                }
                if(player.worldObj.rand.nextInt(1)==1) {
                    player.playSound("hxcdiseases:vomit", 1, 1);
                    for(int i=0;i<(player.worldObj.rand.nextInt(800)+200)/ (Minecraft.getMinecraft().gameSettings.particleSetting+1);i++) {
                        player.worldObj.spawnParticle("slime", player.posX, player.getEyeHeight(), player.posZ, 0,0,0);
                    }
                }
                if(player.worldObj.rand.nextInt(900)==1) {
                    player.playSound("hxcdiseases:cough", 1, 1);
                }
                if(player.worldObj.rand.nextInt(900)==1) {
                    player.playSound("hxcdiseases:cough2", 1, 1);
                }
            }
        }
    }
}
