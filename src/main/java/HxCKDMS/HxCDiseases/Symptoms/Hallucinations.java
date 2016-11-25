package HxCKDMS.HxCDiseases.Symptoms;


import HxCKDMS.HxCDiseases.HxCDiseases;
import HxCKDMS.HxCDiseases.PacketShader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class Hallucinations implements Symptom{

    String effect = "phosphor";

    public Hallucinations(){}

    public Hallucinations(String effect){
        this.effect = effect;
    }
    @Override
    public void tick(EntityPlayer player) {

    }
    @Override
    public void remove(EntityPlayer player) {
        HxCDiseases.networkWrapper.sendTo(new PacketShader(false, String.format("minecraft:shaders/post/%s.json", effect)), (EntityPlayerMP) player);
    }
    @Override
    public void apply(EntityPlayer player) {
        HxCDiseases.networkWrapper.sendTo(new PacketShader(true, String.format("minecraft:shaders/post/%s.json", effect)), (EntityPlayerMP) player);
    }
}
