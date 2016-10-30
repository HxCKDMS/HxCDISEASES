package HxCKDMS.HxCDiseases;

import HxCKDMS.HxCDiseases.Symptoms.Symptom;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Arrays;

public class Disease {

    private Symptom[] symptoms;
    public String feeling = "different";
    public int sicknessTicksRemaining = 12000;

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
        if(player.isPlayerSleeping()){
            sicknessTicksRemaining-=5;
        }else{
            sicknessTicksRemaining--;
        }
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
