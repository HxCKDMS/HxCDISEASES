package HxCKDMS.HxCDiseases.Symptoms;


import HxCKDMS.HxCDiseases.Disease;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Fatigue implements Symptom{
    @Override
    public void tick(EntityPlayer player, Disease disease){
        player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,80,2));
        player.addPotionEffect(new PotionEffect(Potion.weakness.id,80,2));
        player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id,80,3));
    }
    @Override
    public void remove(EntityPlayer player) {

    }
    @Override
    public void apply(EntityPlayer player) {

    }
}
