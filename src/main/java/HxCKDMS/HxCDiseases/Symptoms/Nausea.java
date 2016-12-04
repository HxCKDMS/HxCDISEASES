package HxCKDMS.HxCDiseases.Symptoms;


import HxCKDMS.HxCDiseases.Disease;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Nausea implements Symptom{
    @Override
    public void tick(EntityPlayer player, Disease disease){
        player.addPotionEffect(new PotionEffect(Potion.confusion.id,80,8));
    }
    @Override
    public void remove(EntityPlayer player) {

    }
    @Override
    public void apply(EntityPlayer player) {

    }
}
