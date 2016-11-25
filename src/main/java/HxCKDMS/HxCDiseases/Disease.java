package HxCKDMS.HxCDiseases;

import HxCKDMS.HxCDiseases.Symptoms.Symptom;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Arrays;

public class Disease {

    private Symptom[] symptoms;
    public String getfeeling = "sick.";
    public String curefeeling = "better.";
    public int sicknessTicksRemaining = 20;
    public boolean curesOverTime = true;

    public Disease(int timeCoeff, Symptom[] symptoms){
        this.symptoms = symptoms;
    }

    public Disease(int timeCoeff, Symptom[] symptoms, String getfeeling, String curefeeling) {
        this.symptoms = symptoms;
        this.getfeeling = getfeeling;
        this.curefeeling = curefeeling;
        sicknessTicksRemaining *= timeCoeff;
        if(timeCoeff < 0){
            curesOverTime = false;
        }
    }
    public Disease(int timeCoeff, String[] symptomNames, String getfeeling, String curefeeling) {
        this.symptoms = Utilities.GetSymptomsByNames(symptomNames);
        this.getfeeling = getfeeling;
        this.curefeeling = curefeeling;
        sicknessTicksRemaining *= timeCoeff;
        if(timeCoeff < 0){
            curesOverTime = false;
        }
    }
    public void tick(EntityPlayer player)
    {
        Arrays.stream(symptoms).forEach(symptom-> {
            symptom.tick(player);
        });
        if(curesOverTime) {
            if (player.isPlayerSleeping()) {
                sicknessTicksRemaining -= 10;
            } else {
                sicknessTicksRemaining--;
            }
            if(sicknessTicksRemaining<=0){
                this.remove(player);
            }
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
