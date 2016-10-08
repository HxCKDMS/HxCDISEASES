package HxCKDMS.HxCDiseases;

import HxCKDMS.HxCDiseases.Symptoms.Symptom;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Arrays;

public class Disease {

    private Symptom[] symptoms;
    public String feeling = "different";

    public Disease(Symptom[] symptoms){
        this.symptoms = symptoms;
    }
    public Disease(Symptom[] symptoms, String feeling) {
        this.symptoms = symptoms;
        this.feeling = feeling;
    }
    public void tick(EntityPlayer player)
    {
        Arrays.stream(symptoms).forEach(symptom-> {
            symptom.tick(player);
        });
    }
    public void remove(EntityPlayer player)
    {
        Arrays.stream(symptoms).forEach(symptom-> {
            symptom.remove(player);
        });
    }
    public void apply(EntityPlayer player)
    {
        Arrays.stream(symptoms).forEach(symptom-> {
            symptom.apply(player);
        });
    }

}
