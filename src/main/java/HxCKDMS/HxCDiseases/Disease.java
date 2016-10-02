package HxCKDMS.HxCDiseases;

import HxCKDMS.HxCDiseases.Symptoms.Symptom;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Arrays;

public class Disease {

    private Symptom[] symptoms;

    public Disease(Symptom[] symptoms){
        this.symptoms = symptoms;
    }

    public void tick(EntityPlayer player)
    {
        Arrays.stream(symptoms).forEach(symptom-> {
            symptom.call(player);
        });
    }

}
