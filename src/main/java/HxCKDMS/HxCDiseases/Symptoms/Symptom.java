package HxCKDMS.HxCDiseases.Symptoms;

import HxCKDMS.HxCDiseases.Disease;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;

public interface Symptom {

    HashMap<String, Symptom> symptoms = new HashMap<>();

    void tick(EntityPlayer player, Disease disease);
    void remove(EntityPlayer player);
    void apply(EntityPlayer player);


}
