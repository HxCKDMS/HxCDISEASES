package HxCKDMS.HxCDiseases;

import HxCKDMS.HxCCore.HxCCore;
import HxCKDMS.HxCCore.api.Handlers.NBTFileIO;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;

import java.io.File;

public class Utilities {


    public static boolean applyDisease(Entity player, String disease){
        boolean retval = false;
        if(!player.worldObj.isRemote){
            if(player instanceof EntityPlayerMP){
                String UUID = player.getUniqueID().toString();
                File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");
                NBTTagCompound Diseases = NBTFileIO.getNbtTagCompound(CustomPlayerData, "Diseases");
                try {
                    if (!Diseases.getBoolean(disease)){
                        ((EntityPlayer) player).addChatMessage(new ChatComponentText("You now have '" + disease + "'!"));
                        HxCDiseases.diseases.forEach((diseasename, diseaseobj)-> {
                            if(Diseases.hasKey(diseasename)&&Diseases.getBoolean(diseasename)) {
                                diseaseobj.apply((EntityPlayer)player); ;
                            }
                        });
                        retval = true;
                        //player.worldObj.playSoundToNearExcept(null,"hxcdiseases:notify",3, 0.8f);
                        //player.playSound("hxcdiseases:notify",3, 0.8f);
                        Utilities.playSoundAtPlayer((EntityPlayer) player, "hxcdiseases:notify", 3, 0.1f + ((player.worldObj.rand.nextFloat() - 0.5f) / 5));
                    }
                    Diseases.setBoolean(disease, true);
                    NBTFileIO.setNbtTagCompound(CustomPlayerData, "Diseases", Diseases);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //NBTTagCompound Disease = Diseases.getCompoundTag( this.diseasename);
            }
        }
        return retval;
    }
    public static boolean removeDisease(Entity player, String disease){
        boolean retval = false;
        if(!player.worldObj.isRemote){
            if(player instanceof EntityPlayerMP){
                String UUID = player.getUniqueID().toString();
                File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");
                NBTTagCompound Diseases = NBTFileIO.getNbtTagCompound(CustomPlayerData, "Diseases");
                try {
                    if (Diseases.getBoolean(disease)){
                        ((EntityPlayer) player).addChatMessage(new ChatComponentText("You no longer have '" + disease + "'!"));
                        HxCDiseases.diseases.forEach((diseasename, diseaseobj)-> {
                            if(Diseases.hasKey(diseasename)&&Diseases.getBoolean(diseasename)) {
                                diseaseobj.remove((EntityPlayer) player);
                            }
                        });
                        retval = true;
                        //player.worldObj.playSoundToNearExcept(null,"hxcdiseases:notify",3, 0.8f);
                        //player.playSound("hxcdiseases:notify",3, 0.8f);
                        Utilities.playSoundAtPlayer((EntityPlayer) player, "hxcdiseases:notify", 3, 0.1f + ((player.worldObj.rand.nextFloat() - 0.5f) / 5));
                    }
                    Diseases.setBoolean(disease, false);
                    NBTFileIO.setNbtTagCompound(CustomPlayerData, "Diseases", Diseases);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //NBTTagCompound Disease = Diseases.getCompoundTag( this.diseasename);
            }
        }
        return retval;
    }

    public static ItemStack getDiseaseItem(String disease){
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("disease",disease);
        ItemStack itemStack = new ItemStack(HxCDiseases.vial);
        itemStack.setTagCompound(nbt);
        return itemStack;
    }

    public static void playSoundAtPlayer(EntityPlayer myPlayer, String sound, float volume, float pitch) {
        if (!myPlayer.worldObj.isRemote) {
            myPlayer.worldObj.playSoundAtEntity(myPlayer, sound, volume, pitch);
        }

    }

}
