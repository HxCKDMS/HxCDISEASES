package HxCKDMS.HxCDiseases.Symptoms;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Nausea implements Symptom{
    @Override
    public void call(EntityPlayer player){
        player.addPotionEffect(new PotionEffect(Potion.confusion.id,80,8));
    }

}
