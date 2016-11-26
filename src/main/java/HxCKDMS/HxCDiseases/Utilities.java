package HxCKDMS.HxCDiseases;

import HxCKDMS.HxCDiseases.Symptoms.Symptom;
import hxckdms.hxccore.libraries.GlobalVariables;
import hxckdms.hxccore.utilities.HxCPlayerInfoHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;

import java.io.File;
import java.util.HashMap;

public class Utilities {


    public static HashMap<String, Disease> getPlayerDiseases(EntityPlayer player) {
        HashMap<String, Disease> cDiseases = new HashMap<>();
        if(!player.worldObj.isRemote) {
            NBTTagCompound diseases = HxCPlayerInfoHandler.getTagCompound(player, "Diseases");
            HxCDiseases.diseases.forEach((diseasename, diseaseobj) -> {
                if (diseases != null) {
                    if (diseases.hasKey(diseasename) && diseases.getBoolean(diseasename)) {
                        cDiseases.put(diseasename, diseaseobj);
                    }
                }
            });
        }
        return cDiseases;
    }
    public static boolean applyDisease(Entity player, String disease){
        boolean retval = false;
        if(!player.worldObj.isRemote){
            if(player instanceof EntityPlayerMP){
                String UUID = player.getUniqueID().toString();
                File CustomPlayerData = new File(GlobalVariables.modWorldDir, "HxC-" + UUID + ".dat");
                NBTTagCompound diseases = HxCPlayerInfoHandler.getTagCompound((EntityPlayer)player, "Diseases", new NBTTagCompound());
                try {
                    if (!diseases.getBoolean(disease)){
                        diseases.setBoolean(disease, true);
                        ((EntityPlayer) player).addChatMessage(new ChatComponentText("You're feeling "+HxCDiseases.diseases.get(disease).getfeeling));
                        HxCDiseases.diseases.forEach((diseasename, diseaseobj)-> {
                            if(diseases.hasKey(diseasename)&&diseases.getBoolean(diseasename)) {
                                diseaseobj.apply((EntityPlayer)player);
                            }
                        });
                        retval = true;
                        //player.worldObj.playSoundToNearExcept(null,"hxcdiseases:notify",3, 0.8f);
                        //player.playSound("hxcdiseases:notify",3, 0.8f);
                        Utilities.playSoundAtPlayer((EntityPlayer) player, "hxcdiseases:notify", 3, 0.1f + ((player.worldObj.rand.nextFloat() - 0.5f) / 5));
                    }
                    HxCPlayerInfoHandler.setTagCompound((EntityPlayer) player, "Diseases", diseases);
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

                NBTTagCompound diseases = HxCPlayerInfoHandler.getTagCompound((EntityPlayer)player, "Diseases");
                try {
                    if (diseases.getBoolean(disease)){
                        ((EntityPlayer) player).addChatMessage(new ChatComponentText("You're feeling "+HxCDiseases.diseases.get(disease).curefeeling));
                        HxCDiseases.diseases.get(disease).remove((EntityPlayer)player);
                        diseases.setBoolean(disease, false);
                        retval = true;
                        //player.worldObj.playSoundToNearExcept(null,"hxcdiseases:notify",3, 0.8f);
                        //player.playSound("hxcdiseases:notify",3, 0.8f);
                        Utilities.playSoundAtPlayer((EntityPlayer) player, "hxcdiseases:notify", 3, 2f + ((player.worldObj.rand.nextFloat() - 0.5f) / 5));
                    }else{
                        diseases.setBoolean(disease, false); //silently disable anyways, just in case
                    }
                    HxCPlayerInfoHandler.setTagCompound((EntityPlayer)player, "Diseases", diseases);

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

    public static Symptom resolveSymptom(String name){
        return Symptom.symptoms.get(name);
    }

    public static Symptom[] GetSymptomsByNames(String[] names){
        Symptom[] ret = new Symptom[names.length];
        for (int i = 0; i < names.length; i++){
            ret[i] = resolveSymptom(names[i]);
        }
        return ret;
    }

    public static void playSoundAtPlayer(EntityPlayer myPlayer, String sound, float volume, float pitch) {
        if (!myPlayer.worldObj.isRemote) {
            myPlayer.worldObj.playSoundAtEntity(myPlayer, sound, volume, pitch);
        }

    }

}
