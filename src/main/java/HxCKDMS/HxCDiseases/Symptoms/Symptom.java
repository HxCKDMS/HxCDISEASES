package HxCKDMS.HxCDiseases.Symptoms;

import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;

public interface Symptom {

    public static HashMap<String, Symptom> symptoms = new HashMap<>();


    void tick(EntityPlayer player);
    void remove(EntityPlayer player);
    void apply(EntityPlayer player);


}
