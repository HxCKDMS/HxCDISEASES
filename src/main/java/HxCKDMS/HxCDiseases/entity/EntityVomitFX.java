package HxCKDMS.HxCDiseases.entity;

import HxCKDMS.HxCCore.Handlers.NBTFileIO;
import HxCKDMS.HxCCore.HxCCore;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.io.File;

public class EntityVomitFX extends EntityFX{

    public String Disease = "";
    public EntityPlayer Owner;

    public EntityVomitFX(World world, double X, double Y, double Z, double mX, double mY, double mZ, String disease, EntityPlayer owner) {
        super(world, X, Y, Z, mX, mY, mZ);
        this.particleGravity=0.8f;
        this.setRotation(0, world.rand.nextInt(360));
        this.Disease = disease;
        this.Owner = owner;
    }

    @Override
    public void onUpdate(){
        super.onUpdate();
        if(this.ticksExisted>500){
            this.setVelocity(0, 0, 0);
            this.setSize(0.1f, 2f);
        }
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player){
        if(player!=Owner) {
            applyDisease(player, Disease);
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
