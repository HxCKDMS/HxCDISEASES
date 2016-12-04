package HxCKDMS.HxCDiseases.Symptoms;


import HxCKDMS.HxCDiseases.Disease;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ImparedVision implements Symptom{
    int degree = 1;
    public ImparedVision(int degree){
        this.degree = degree;
    }
    @Override
    public void tick(EntityPlayer player, Disease disease) {
        player.addPotionEffect(new PotionEffect(Potion.blindness.id,80,degree));
    }
    @Override
    public void remove(EntityPlayer player) {
        
    }
    @Override
    public void apply(EntityPlayer player) {

    }
}
