package HxCKDMS.HxCDiseases.Symptoms;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ImparedVision implements Symptom{
    @Override
    public void tick(EntityPlayer player) {
        player.addPotionEffect(new PotionEffect(Potion.blindness.id,80,2));
    }
    @Override
    public void remove(EntityPlayer player) {
        
    }
    @Override
    public void apply(EntityPlayer player) {

    }
}
