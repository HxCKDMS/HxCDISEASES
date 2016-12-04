package HxCKDMS.HxCDiseases;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hxckdms.hxccore.libraries.GlobalVariables;
import hxckdms.hxccore.utilities.HxCPlayerInfoHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemFood;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

import java.io.File;

public class DiseaseHandler {

    public int pRotOff = 0;
    public int bedAngle = 0;
    public float pHeightOff = 0;
    public boolean pInBed = false;

    @SubscribeEvent
    public void LivingAttack(LivingAttackEvent event) {
        if(event.entityLiving instanceof EntityPlayer && event.source.getEntity() instanceof EntityZombie){
            if(((EntityPlayer) event.entityLiving).worldObj.rand.nextInt(100)<=2) {
                applyDisease((EntityPlayer) event.entityLiving, "Zombie Flu");
            }
        }
    }

    @SubscribeEvent
    public void onPlayerUseitem(PlayerUseItemEvent event) {
        if(event.item.getItem() instanceof ItemFood){
            if(LanguageRegistry.instance().getStringLocalization(event.item.getUnlocalizedName(),"en_US").toLowerCase().contains("raw") && event.entityPlayer.worldObj.rand.nextInt(100)<50){
                applyDisease(event.entityPlayer, "Salmonella");
            }
        }
    }

    @SubscribeEvent
    public void playersleepinbed(PlayerSleepInBedEvent event) {
        if(event.result == EntityPlayer.EnumStatus.OK){

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

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void renderPre(RenderPlayerEvent.Pre event){

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void renderPost(RenderPlayerEvent.Post event){

    }

    @SubscribeEvent
    public void ItemTossEvent(ItemTossEvent event) {
        Utilities.playSoundAtPlayer(event.player,"hxcdiseases:vomit", 3, 1 + ((event.player.worldObj.rand.nextFloat() - 0.5f) / 5));
    }


    @SideOnly(Side.CLIENT)
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
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            NBTTagCompound diseases = HxCPlayerInfoHandler.getTagCompound(player, "Diseases");
            HxCDiseases.diseases.forEach((diseasename, diseaseobj)-> {
                if(diseases != null && diseases.hasKey(diseasename) && diseases.getBoolean(diseasename)) {
                    disableDisease(player, diseasename);
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


}
