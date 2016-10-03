package HxCKDMS.HxCDiseases.Symptoms;


import net.minecraft.entity.player.EntityPlayer;

public class Sneezes implements Symptom{
    @Override
    public void tick(EntityPlayer player){
        if(player.worldObj.rand.nextInt(900)==1) {
            player.playSound("hxcdiseases:sneeze", 1, 1+((player.worldObj.rand.nextFloat()-0.5f)/5));
            player.addVelocity(0,0.2f,0);
        }
    }
    @Override
    public void remove(EntityPlayer player) {

    }
    @Override
    public void apply(EntityPlayer player) {

    }
}
