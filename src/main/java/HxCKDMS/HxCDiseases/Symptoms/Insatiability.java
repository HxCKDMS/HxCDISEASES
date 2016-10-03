package HxCKDMS.HxCDiseases.Symptoms;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Insatiability implements Symptom{
    @Override
    public void tick(EntityPlayer player){
        player.addPotionEffect(new PotionEffect(Potion.hunger.id,80,5));
    }
    @Override
    public void remove(EntityPlayer player) {

    }
    @Override
    public void apply(EntityPlayer player) {

    }

}
