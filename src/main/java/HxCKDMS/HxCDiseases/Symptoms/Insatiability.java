package HxCKDMS.HxCDiseases.Symptoms;


import HxCKDMS.HxCDiseases.Disease;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Insatiability implements Symptom{
    @Override
    public void tick(EntityPlayer player, Disease disease){
        player.addPotionEffect(new PotionEffect(Potion.hunger.id,80,2));
    }
    @Override
    public void remove(EntityPlayer player) {

    }
    @Override
    public void apply(EntityPlayer player) {

    }

}
