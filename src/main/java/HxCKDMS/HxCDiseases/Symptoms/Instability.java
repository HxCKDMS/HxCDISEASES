package HxCKDMS.HxCDiseases.Symptoms;


import net.minecraft.entity.player.EntityPlayer;

public class Instability implements Symptom{
    @Override
    public void tick(EntityPlayer player){
        if(player.worldObj.rand.nextInt(100)==1 && !(player.isSneaking() || player.isRiding() || player.isPlayerSleeping())) {
            player.addVelocity(player.worldObj.rand.nextFloat()-0.5f,0,player.worldObj.rand.nextFloat()-0.5f);
        }
    }
    @Override
    public void remove(EntityPlayer player) {

    }
    @Override
    public void apply(EntityPlayer player) {

    }
}
