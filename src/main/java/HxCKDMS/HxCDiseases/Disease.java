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
    public boolean curable = false;

    public Disease(int timeCoeff, boolean curable, Symptom[] symptoms){
        initialize(timeCoeff, curable, symptoms, getfeeling, curefeeling);
    }

    public Disease(int timeCoeff, boolean curable, Symptom[] symptoms, String getfeeling, String curefeeling) {
        initialize(timeCoeff, curable, symptoms, getfeeling, curefeeling);
    }
    public Disease(int timeCoeff, boolean curable,  String[] symptomNames, String getfeeling, String curefeeling) {
        initialize(timeCoeff, curable, Utilities.GetSymptomsByNames(symptomNames), getfeeling, curefeeling);
    }

    private void initialize(int timeCoeff, boolean curable, Symptom[] symptoms, String getfeeling, String curefeeling) {
        this.symptoms = symptoms;
        this.getfeeling = getfeeling;
        this.curefeeling = curefeeling;
        sicknessTicksRemaining *= timeCoeff;
        if(timeCoeff < 0 || !curable){
            curesOverTime = false;
        }
    }
    public void tick(EntityPlayer player)
    {
        Arrays.stream(symptoms).forEach(symptom-> {
            symptom.tick(player);
        });
        if(curesOverTime) {
            if (player.isPlayerSleeping() && player.isPlayerFullyAsleep()) {
                sicknessTicksRemaining -= 100;
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
