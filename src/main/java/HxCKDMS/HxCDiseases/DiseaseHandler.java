package HxCKDMS.HxCDiseases;

import HxCKDMS.HxCDiseases.entity.EntityVomitFX;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import hxckdms.hxccore.libraries.GlobalVariables;
import hxckdms.hxccore.utilities.HxCPlayerInfoHandler;
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
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import org.lwjgl.opengl.GL11;

import java.io.File;

public class DiseaseHandler {

    public int pRotOff = 0;
    public int bedAngle = 0;
    public float pHeightOff = 0;
    public boolean pInBed = false;

    @SubscribeEvent
    public void LivingAttack(LivingAttackEvent event) {
        if(event.entityLiving instanceof EntityPlayer && event.source.getEntity() instanceof EntityZombie){
            applyDisease((EntityPlayer)event.entityLiving, "Zombie Flu");
        }
    }



    @SubscribeEvent
    public void playersleepinbed(PlayerSleepInBedEvent event) {
        if(event.entityPlayer.worldObj.isDaytime()) {
            event.entityPlayer.setPosition(event.x, event.y, event.z);
            pRotOff = 90;
            pHeightOff = -1;
            pInBed = true;
            switch(event.entityPlayer.worldObj.getBlock(event.x,event.y,event.z).getBedDirection(event.entityPlayer.worldObj,event.x,event.y,event.z)) {
                case 0:
                    bedAngle = 90;
                    break;
                case 1:
                    bedAngle = 180;
                    break;
                case 2:
                    bedAngle = 270;
                    break;
                case 3:
                    bedAngle = 0;
                    break;
            }
        }
    }

    @SubscribeEvent
    public void OnLivingUpdate(LivingEvent.LivingUpdateEvent event) {

        if (event.entityLiving instanceof EntityPlayerMP) {

            EntityPlayer player = (EntityPlayer) event.entityLiving;

            String UUID = player.getUniqueID().toString();
            File CustomPlayerData = new File(GlobalVariables.modWorldDir, "HxC-" + UUID + ".dat");
            NBTTagCompound diseases = HxCPlayerInfoHandler.getTagCompound(player, "Diseases", new NBTTagCompound());
            HxCDiseases.diseases.forEach((diseasename, diseaseobj)-> {
                if(diseases.hasKey(diseasename)&&diseases.getBoolean(diseasename)) {
                    diseaseobj.tick(player);
                }
            });
        }
    }


    @SubscribeEvent
    public void renderPre(RenderPlayerEvent.Pre event){

        GL11.glPushMatrix();
        GL11.glTranslatef(0,pHeightOff,0);
        GL11.glRotatef((bedAngle), 0f, 1f, 0f);
        GL11.glRotatef((pRotOff), 0f, 0f, 1f);
        GL11.glPushMatrix();
    }

    @SubscribeEvent
    public void renderPost(RenderPlayerEvent.Post event){
        if(event.entityPlayer.getCommandSenderName().equals("wiggle1000")) {
            ((AbstractClientPlayer) event.entityPlayer).func_152121_a(MinecraftProfileTexture.Type.CAPE, new ResourceLocation(HxCDiseases.MODID, "textures/player/cape/wiggle1000.png"));
        }
        GL11.glPopMatrix();
        GL11.glTranslatef(0,-pHeightOff,0);
        GL11.glRotatef((-bedAngle), 0f, 1f, 0f);
        GL11.glRotatef((-pRotOff), 0f, 0f, 1f);
        GL11.glPopMatrix();
        if(pInBed){
            event.entityPlayer.renderYawOffset = 270;
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
            File CustomPlayerData = new File(GlobalVariables.modWorldDir, "HxC-" + UUID + ".dat");
            NBTTagCompound diseases = HxCPlayerInfoHandler.getTagCompound(player, "Diseases");
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
