package HxCKDMS.HxCDiseases.Symptoms;


import HxCKDMS.HxCDiseases.HxCDiseases;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;

public class Fever implements Symptom{
    float temperature = 98.7f;
    int counter = 1;
    IAttributeInstance ph;
    AttributeModifier HealthBuff;
    public Fever(float temp){
        temperature = temp;
    }
    @Override
    public void tick(EntityPlayer player) {
        counter++;

    }
    @Override
    public void remove(EntityPlayer player) {
        try {
            ph.removeModifier(HealthBuff);
        }catch (Exception e){}
    }

    @Override
    public void apply(EntityPlayer player) {
        ph = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
        HealthBuff = new AttributeModifier(HxCDiseases.feverHealthUUID, "Fever", -((temperature-100)/10F), 1);
        ph.removeModifier(HealthBuff);
        ph.applyModifier(HealthBuff);
    }
}
