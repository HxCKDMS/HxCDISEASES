package HxCKDMS.HxCDiseases;

import HxCKDMS.HxCCore.HxCCore;
import HxCKDMS.HxCCore.api.Handlers.NBTFileIO;
import HxCKDMS.HxCDiseases.entity.EntityVomitFX;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
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
    }

    @SubscribeEvent
    public void renderCape(RenderPlayerEvent.Post event){
        if(event.entityPlayer.getCommandSenderName().equals("wiggle1000")) {
            ((AbstractClientPlayer) event.entityPlayer).func_152121_a(MinecraftProfileTexture.Type.CAPE, new ResourceLocation(HxCDiseases.MODID, "textures/player/cape/wiggle1000.png"));
        }
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
