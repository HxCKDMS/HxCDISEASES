package com.wiggle1000.HxC.Diseases;

import HxCKDMS.HxCCore.Handlers.NBTFileIO;
import HxCKDMS.HxCCore.HxCCore;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
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
            if (diseases.hasKey("Swine Flu")&&diseases.getBoolean("Swine Flu")){
                if(player.worldObj.rand.nextInt(250)==1){
                    player.attackEntityFrom(new DamageSource("sflu").setDamageBypassesArmor(), 1);
                }
                if(player.worldObj.rand.nextInt(Config.vomitChance+1)==1) {
                    player.playSound("hxcdiseases:vomit", 1, 1);
                    for(int i=0;i<(player.worldObj.rand.nextInt(800)+200)/ (Minecraft.getMinecraft().gameSettings.particleSetting+1)*Config.uberVomit;i++) {
                        player.worldObj.spawnParticle("slime", player.posX, player.posY+player.getEyeHeight(), player.posZ, 0,0,0);
                    }
                }
                if(player.worldObj.rand.nextInt(900)==1) {
                    player.playSound("hxcdiseases:cough", 1, 1);
                }
                if(player.worldObj.rand.nextInt(900)==1) {
                    player.playSound("hxcdiseases:cough2", 1, 1);
                }
                if(player.worldObj.rand.nextInt(9000)==1) {
                    disableDisease(player,"Swine Flu");
                }
            }
            if (diseases.hasKey("Ebola")&&diseases.getBoolean("Ebola")){
                if(Config.lookatme) {
                    Entity other = Utils.GetTargetEntityLiving(player.worldObj, player, 10);
                    if(other instanceof EntityPlayer) {
                        String OtherUUID = ((EntityPlayer)other).getUniqueID().toString();
                        File OtherPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");
                        NBTTagCompound otherdiseases = NBTFileIO.getNbtTagCompound(CustomPlayerData, "Diseases");
                        if(otherdiseases.hasKey("Ebola")&&otherdiseases.getBoolean("Ebola")){
                            applyDisease(player, "Ebola");
                        }
                    }
                }
                if(player.worldObj.rand.nextInt(250)==1){
                    player.attackEntityFrom(new DamageSource("ebola").setDamageBypassesArmor(), 1);
                }
                if(player.worldObj.rand.nextInt(Config.vomitChance+1)==1) {
                    player.playSound("hxcdiseases:vomit", 1, 1);
                    for(int i=0;i<(player.worldObj.rand.nextInt(800)+200)/ (Minecraft.getMinecraft().gameSettings.particleSetting+1)*Config.uberVomit;i++) {
                        Vec3 look = player.getLookVec();
                        player.worldObj.spawnParticle("slime", player.posX, player.posY+player.getEyeHeight(), player.posZ, look.xCoord*500, look.yCoord*500,look.zCoord*500);
                    }
                }
                if(player.worldObj.rand.nextInt(900)==1) {
                    player.playSound("hxcdiseases:cough", 1, 1);
                }
                if(player.worldObj.rand.nextInt(900)==1) {
                    player.playSound("hxcdiseases:cough2", 1, 1);
                }
                if(player.worldObj.rand.nextInt(80000)==1) {
                    disableDisease(player,"Swine Flu");
                }
            }
        }
    }
    @SubscribeEvent
    public void OnLivingDeath(LivingDeathEvent event){
        if(event.entityLiving instanceof EntityPlayer) {
            disableDisease((EntityPlayer)event.entityLiving,"Ebola", false);
            disableDisease((EntityPlayer)event.entityLiving,"Swine Flu", false);
        }
    }

    public void disableDisease(EntityPlayer player, String disease) {
        disableDisease(player,disease,true);
    }

    public void disableDisease(EntityPlayer player, String disease, boolean doChat){
        if(!player.worldObj.isRemote) {
            String UUID = player.getUniqueID().toString();
            File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");
            if(doChat) {
                player.addChatMessage(new ChatComponentText("You no longer have '" + disease + "'!"));
            }
            NBTTagCompound Diseases = NBTFileIO.getNbtTagCompound(CustomPlayerData, "Diseases");
            try {
                Diseases.setBoolean(disease, false);
                NBTFileIO.setNbtTagCompound(CustomPlayerData, "Diseases", Diseases);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void applyDisease(Entity player, String disease){
        if(!player.worldObj.isRemote){
            if(player instanceof EntityPlayerMP){
                String UUID = player.getUniqueID().toString();
                File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");
                ((EntityPlayer)player).addChatMessage(new ChatComponentText("You now have '"+disease+"'!"));
                NBTTagCompound Diseases = NBTFileIO.getNbtTagCompound(CustomPlayerData, "Diseases");
                try {
                    Diseases.setBoolean(disease, true);
                    NBTFileIO.setNbtTagCompound(CustomPlayerData, "Diseases", Diseases);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //NBTTagCompound Disease = Diseases.getCompoundTag( this.diseasename);
            }
        }
    }
}
