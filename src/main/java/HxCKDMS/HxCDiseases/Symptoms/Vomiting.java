package HxCKDMS.HxCDiseases.Symptoms;


import HxCKDMS.HxCDiseases.Disease;
import HxCKDMS.HxCDiseases.HxCDiseases;
import HxCKDMS.HxCDiseases.PacketParticles;
import HxCKDMS.HxCDiseases.Utilities;
import net.minecraft.entity.player.EntityPlayer;

public class Vomiting implements Symptom{
    @Override
    public void tick(EntityPlayer player, Disease disease){
        if(player.worldObj.rand.nextInt(800)==1) {
            Utilities.playSoundAtPlayer(player, "hxcdiseases:vomit", 3, 1 + ((player.worldObj.rand.nextFloat() - 0.5f) / 5));
            HxCDiseases.networkWrapper.sendToDimension(new PacketParticles(1, player.getCommandSenderName(), disease.name), player.dimension);
        }
    }

    @Override
    public void remove(EntityPlayer player) {

    }
    @Override
    public void apply(EntityPlayer player) {

    }

}
