package HxCKDMS.HxCDiseases;

import HxCKDMS.HxCDiseases.Symptoms.Symptom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hxckdms.hxccore.libraries.GlobalVariables;
import hxckdms.hxccore.utilities.HxCPlayerInfoHandler;
import net.minecraft.client.Minecraft;
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
            if(player instanceof EntityPlayerMP) {
                if (HxCDiseases.diseases.get(disease) != null) {
                    String UUID = player.getUniqueID().toString();
                    File CustomPlayerData = new File(GlobalVariables.modWorldDir, "HxC-" + UUID + ".dat");
                    NBTTagCompound diseases = HxCPlayerInfoHandler.getTagCompound((EntityPlayer) player, "Diseases", new NBTTagCompound());
                    try {
                        if (!diseases.getBoolean(disease)) {
                            diseases.setBoolean(disease, true);
                            ((EntityPlayer) player).addChatMessage(new ChatComponentText("You're feeling " + HxCDiseases.diseases.get(disease).getfeeling));
                            HxCDiseases.diseases.forEach((diseasename, diseaseobj) -> {
                                if (diseases.hasKey(diseasename) && diseases.getBoolean(diseasename)) {
                                    diseaseobj.apply((EntityPlayer) player);
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
        }
        return retval;
    }
    public static boolean removeDisease(Entity player, String disease){
        boolean retval = false;
        if(!player.worldObj.isRemote){
            if(player instanceof EntityPlayerMP) {
                if (HxCDiseases.diseases.get(disease) != null) {
                    String UUID = player.getUniqueID().toString();

                    NBTTagCompound diseases = HxCPlayerInfoHandler.getTagCompound((EntityPlayer) player, "Diseases");
                    try {
                        if (diseases.getBoolean(disease)) {
                            ((EntityPlayer) player).addChatMessage(new ChatComponentText("You're feeling " + HxCDiseases.diseases.get(disease).curefeeling));
                            HxCDiseases.diseases.get(disease).remove((EntityPlayer) player);
                            diseases.setBoolean(disease, false);
                            retval = true;
                            //player.worldObj.playSoundToNearExcept(null,"hxcdiseases:notify",3, 0.8f);
                            //player.playSound("hxcdiseases:notify",3, 0.8f);
                            Utilities.playSoundAtPlayer((EntityPlayer) player, "hxcdiseases:notify", 3, 2f + ((player.worldObj.rand.nextFloat() - 0.5f) / 5));
                        } else {
                            diseases.setBoolean(disease, false); //silently disable anyways, just in case
                        }
                        HxCPlayerInfoHandler.setTagCompound((EntityPlayer) player, "Diseases", diseases);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //NBTTagCompound Disease = Diseases.getCompoundTag( this.diseasename);
                }
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

    public static ItemStack getSyringeItem(String mob){
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("disease", "Full Syringe");
        nbt.setString("mob", mob);
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
    @SideOnly(Side.CLIENT)
    public void vomit(EntityPlayer player, String disease) {
        if(player.worldObj.isRemote) {
            player.playSound("hxcdiseases:vomit", 1, 1 + ((player.worldObj.rand.nextFloat() - 0.5f) / 5));
            float baseYaw = player.getRotationYawHead() + 90;
            float basePitch = -(player.rotationPitch);
            for (int i = 0; i < (player.worldObj.rand.nextInt(800) + 200) / (Minecraft.getMinecraft().gameSettings.particleSetting + 1) * DiseaseConfig.uberVomit; i++) {
                float pitch = basePitch + player.worldObj.rand.nextInt(20) - 10;
                float yaw = baseYaw + player.worldObj.rand.nextInt(20) - 10;
                player.getEntityWorld().spawnEntityInWorld(new HxCKDMS.HxCDiseases.entity.EntityVomitFX(player.worldObj, player.posX, player.posY + player.getEyeHeight(), player.posZ, (Math.cos(Math.toRadians(yaw)) * 50), (Math.tan(Math.toRadians(pitch)) * 50), (Math.sin(Math.toRadians(yaw)) * 50), disease, player));
            }
        }
    }

    public static boolean isSameDiseaseItem(ItemStack first, ItemStack second)
    {
        NBTTagCompound nbt1 = first.getTagCompound();
        NBTTagCompound nbt2 = second.getTagCompound();
        if(nbt1.hasKey("disease") && nbt2.hasKey("disease") && nbt1.getString("disease") == nbt2.getString("disease")){
            if(nbt1.getString("disease") != "Full Syringe"){
                return true;
            }else{
                if(nbt1.hasKey("mob") && nbt2.hasKey("mob") && nbt1.getString("mob") == nbt2.getString("mob")){
                    return true;
                }
            }
        }
        return false;
    }

}
