package HxCKDMS.HxCDiseases.Symptoms;


import net.minecraft.entity.player.EntityPlayer;

public class Coughing implements Symptom{
    @Override
    public void call(EntityPlayer player){
        if(player.worldObj.rand.nextInt(900)==1) {
            player.playSound("hxcdiseases:cough", 1, 1+((player.worldObj.rand.nextFloat()-0.5f)/5));
        }
    }

}
