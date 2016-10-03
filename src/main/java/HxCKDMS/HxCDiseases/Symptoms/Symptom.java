package HxCKDMS.HxCDiseases.Symptoms;

import net.minecraft.entity.player.EntityPlayer;

public interface Symptom {
    void tick(EntityPlayer player);
    void remove(EntityPlayer player);
    void apply(EntityPlayer player);
}
