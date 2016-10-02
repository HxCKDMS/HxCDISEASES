package HxCKDMS.HxCDiseases.Symptoms;


import HxCKDMS.HxCDiseases.HxCDiseases;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;

public class Fever implements Symptom{
    float temperature = 98.7f;
    int counter = 1;
    public Fever(float temp){
        temperature = temp;
    }

    @Override
    public void call(EntityPlayer player) {
        counter++;
        IAttributeInstance ph = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
        AttributeModifier HealthBuff = new AttributeModifier(HxCDiseases.feverHealthUUID, "Fever", -((temperature-100)/10F), 1);
        ph.removeModifier(HealthBuff);
        ph.applyModifier(HealthBuff);
        if(player.getHealth()<=1){
            player.onDeath(HxCDiseases.fever);
        }
    }

}
