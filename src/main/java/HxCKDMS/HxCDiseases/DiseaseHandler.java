package HxCKDMS.HxCDiseases;

import HxCKDMS.HxCCore.HxCCore;
import HxCKDMS.HxCCore.api.Handlers.NBTFileIO;
import HxCKDMS.HxCDiseases.entity.EntityVomitFX;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.io.File;

public class DiseaseHandler {

    @SubscribeEvent
    public void LivingAttack(LivingAttackEvent event) {
        if(event.entityLiving instanceof EntityPlayer && event.source.getEntity() instanceof EntityZombie){
            applyDisease((EntityPlayer)event.entityLiving, "Zombie Flu");
        }
    }

    @SubscribeEvent
    public void OnLivingUpdate(LivingEvent.LivingUpdateEvent event) {

        if (event.entityLiving instanceof EntityPlayerMP) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            String UUID = player.getUniqueID().toString();
            File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");
            NBTTagCompound diseases = NBTFileIO.getNbtTagCompound(CustomPlayerData, "Diseases");
            HxCDiseases.diseases.forEach((diseasename, diseaseobj)-> {
                if(diseases.hasKey(diseasename)&&diseases.getBoolean(diseasename)) {
                    diseaseobj.tick(player);
                }
            });
        }

        /*if (event.entityLiving instanceof EntityPlayerMP) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            if ((player.worldObj.rand.nextInt((player.worldObj.isRaining() ? 80000 : 8000)) == 1)) {
                applyDisease(player, "Common Cold");
            }
            if (player.worldObj.rand.nextInt(100000)==1) {
                applyDisease(player, "Ebola");
            }
            if (player.worldObj.rand.nextInt(10000)==1) {
                applyDisease(player, "Swine Flu");
            }

            String UUID = player.getUniqueID().toString();
            File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");
            NBTTagCompound diseases;
            try {
                diseases = NBTFileIO.getNbtTagCompound(CustomPlayerData, "Diseases");
            }catch(Exception ignored){
                NBTFileIO.setNbtTagCompound(CustomPlayerData, "Diseases", new NBTTagCompound());
                diseases = NBTFileIO.getNbtTagCompound(CustomPlayerData, "Diseases");
            }
            if (diseases.hasKey("Swine Flu")&&diseases.getBoolean("Swine Flu")){
                if(player.worldObj.rand.nextInt(250)==1){
                    player.attackEntityFrom(new DamageSource("sflu").setDamageBypassesArmor(), 1);
                }
                if(player.worldObj.rand.nextInt(DiseaseConfig.vomitChance+1)==1) {
                    vomit(player,"Swine Flu");
                }
                if(player.worldObj.rand.nextInt(900)==1) {
                    player.playSound("hxcdiseases:cough", 1, 1+((player.worldObj.rand.nextFloat()-0.5f)/5));
                }
                if(player.worldObj.rand.nextInt(900)==1) {
                    player.playSound("hxcdiseases:cough2", 1, 1+((player.worldObj.rand.nextFloat()-0.5f)/5));
                }
                if(player.worldObj.rand.nextInt(9000)==1) {
                    disableDisease(player,"Swine Flu");
                }
            }

            if (diseases.hasKey("Common Cold")&&diseases.getBoolean("Common Cold")){
                if(player.worldObj.rand.nextInt(8000)==1){
                    player.attackEntityFrom(new DamageSource("ccold").setDamageBypassesArmor(), 1);
                }
                if(player.worldObj.rand.nextInt(900)==1) {
                    player.playSound("hxcdiseases:cough", 1, 1+((player.worldObj.rand.nextFloat()-0.5f)/5));
                }
                if(player.worldObj.rand.nextInt(900)==1) {
                    player.playSound("hxcdiseases:cough2", 1, 1+((player.worldObj.rand.nextFloat()-0.5f)/5));
                }
                if(player.worldObj.rand.nextInt(300)==1) {
                    player.playSound("hxcdiseases:sneeze", 1, 1+((player.worldObj.rand.nextFloat()-0.5f)/3));
                    player.addVelocity(0,0.2f,0);
                }
                if(player.worldObj.rand.nextInt(9000)==1) {
                    disableDisease(player,"Common Cold");
                }
            }

            if (diseases.hasKey("Ebola")&&diseases.getBoolean("Ebola")){
                if(player.worldObj.rand.nextInt(250)==1){
                    player.attackEntityFrom(new DamageSource("ebola").setDamageBypassesArmor(), 1);
                }
                if(player.worldObj.rand.nextInt(DiseaseConfig.vomitChance+1)==1) {
                    vomit(player, "Ebola");
                }
                if(player.worldObj.rand.nextInt(900)==1) {
                    player.playSound("hxcdiseases:cough", 1, 1+((player.worldObj.rand.nextFloat()-0.5f)/5));
                }
                if(player.worldObj.rand.nextInt(900)==1) {
                    player.playSound("hxcdiseases:cough2", 1, 1+((player.worldObj.rand.nextFloat()-0.5f)/5));
                }
                if(player.worldObj.rand.nextInt(90000)==1) {
                    disableDisease(player,"Ebola");
                }
            }else{
                if(DiseaseConfig.lookatme) {
                    EntityLivingBase other = Utils.GetTargetEntityLiving(player.worldObj, player, 10);
                    if(other!=null && other instanceof EntityPlayer) {
                        String OtherUUID = other.getUniqueID().toString();
                        File OtherPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + OtherUUID + ".dat");
                        NBTTagCompound otherdiseases = NBTFileIO.getNbtTagCompound(OtherPlayerData, "Diseases");
                        if(otherdiseases.hasKey("Ebola")&&otherdiseases.getBoolean("Ebola")&&player.worldObj.rand.nextInt(100)==1){
                            applyDisease(player, "Ebola");
                            other.playSound("hxcdiseases:ebola",1,1);
                        }
                    }
                }
            }

            @SuppressWarnings("unchecked")
            List<EntityPlayer> nearPlayers = player.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AABBUtils.getAreaBoundingBox((int) player.posX, (int) player.posY, (int) player.posY, 10));
            for(EntityPlayer curr : nearPlayers){
                if(player.worldObj.rand.nextInt(2000)==1){
                    //curr.playSound("hxcdiseases:cough2", 3, 1 + ((player.worldObj.rand.nextFloat() - 0.5f) / 5));
                    player.worldObj.playSoundToNearExcept(null,"hxcdiseases:cough2", 3, 1 + ((player.worldObj.rand.nextFloat() - 0.5f) / 5));
                    switch(player.worldObj.rand.nextInt(2)) {
                        case 0:
                            if (diseases.hasKey("Swine Flu") && diseases.getBoolean("Swine Flu")) {
                                applyDisease(player, "Swine Flu");
                            }
                            break;
                        case 1:
                            if (diseases.hasKey("Common Cold") && diseases.getBoolean("Common Cold")) {
                                applyDisease(player, "Common Cold");
                            }
                            break;
                    }
                }

            }
            @SuppressWarnings("unchecked")
            List<EntityPlayer> personalPlayers = player.worldObj.getEntitiesWithinAABB(EntityPlayer.class,AABBUtils.getAreaBoundingBox((int) player.posX, (int) player.posY, (int) player.posY, 1));
            for(EntityPlayer curr : nearPlayers){
                if(player.worldObj.rand.nextInt(20000)==1){
                    curr.playSound("hxcdiseases:vomit", 3, 1 + ((player.worldObj.rand.nextFloat() - 0.5f) / 5));
                    if (diseases.hasKey("Ebola") && diseases.getBoolean("Ebola")) {
                        applyDisease(player, "Ebola");
                    }
                }

            }
        }*/
    }

    @SubscribeEvent
    public void ItemTossEvent(ItemTossEvent event) {
        Utilities.playSoundAtPlayer(event.player,"hxcdiseases:vomit", 3, 1 + ((event.player.worldObj.rand.nextFloat() - 0.5f) / 5));
    }


    @SubscribeEvent
    public void InputEvent(InputEvent.KeyInputEvent event) {
        if(Minecraft.getMinecraft().gameSettings.keyBindDrop.getIsKeyPressed()) {
            HxCDiseases.networkWrapper.sendToServer(new PacketKey(1, Minecraft.getMinecraft().thePlayer.getDisplayName()));
        }else if(Keybinds.fart.isPressed() ) {
            HxCDiseases.networkWrapper.sendToServer(new PacketKey(2, Minecraft.getMinecraft().thePlayer.getDisplayName()));
        }
       // Utilities.playSoundAtPlayer(event.,"hxcdiseases:vomit", 3, 1 + ((event.player.worldObj.rand.nextFloat() - 0.5f) / 5));
    }

    @SubscribeEvent
    public void OnLivingDeath(LivingDeathEvent event) {
        if (event.entityLiving instanceof EntityPlayerMP) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            String UUID = player.getUniqueID().toString();
            File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");
            NBTTagCompound diseases = NBTFileIO.getNbtTagCompound(CustomPlayerData, "Diseases");
            HxCDiseases.diseases.forEach((diseasename, diseaseobj)-> {
                if(diseases.hasKey(diseasename)&&diseases.getBoolean(diseasename)) {
                    disableDisease(player,diseasename);
                }
            });
        }
    }

    public void disableDisease(EntityPlayer player, String disease) {
        Utilities.removeDisease(player,disease);
    }

    public void applyDisease(EntityPlayer player, String disease) {
        Utilities.applyDisease(player,disease);
    }

    public void vomit(EntityPlayer player, String disease) {
        player.playSound("hxcdiseases:vomit", 1, 1+((player.worldObj.rand.nextFloat()-0.5f)/5));
        float baseYaw = player.getRotationYawHead()+90;
        float basePitch = -(player.rotationPitch);
        for(int i = 0; i<(player.worldObj.rand.nextInt(800)+200)/ (Minecraft.getMinecraft().gameSettings.particleSetting + 1) * DiseaseConfig.uberVomit; i++) {
            float pitch = basePitch + player.worldObj.rand.nextInt(20) - 10;
            float yaw = baseYaw + player.worldObj.rand.nextInt(20) - 10;
            player.getEntityWorld().spawnEntityInWorld(new EntityVomitFX(player.worldObj, player.posX, player.posY+player.getEyeHeight(), player.posZ, (Math.cos(Math.toRadians(yaw))*50), (Math.tan(Math.toRadians(pitch))*50), (Math.sin(Math.toRadians(yaw))*50), disease, player));
        }
    }
}
