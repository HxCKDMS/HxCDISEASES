package HxCKDMS.HxCDiseases.Symptoms;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class DizzySpells implements Symptom{
    @Override
    public void tick(EntityPlayer player){
        if(player.worldObj.rand.nextInt(100)==1) {
            player.addPotionEffect(new PotionEffect(Potion.confusion.id, 80, 8));
            player.addPotionEffect(new PotionEffect(Potion.blindness.id, 20, 1));
        }
    }
    @Override
    public void remove(EntityPlayer player) {

    }
    @Override
    public void apply(EntityPlayer player) {

    }

}
