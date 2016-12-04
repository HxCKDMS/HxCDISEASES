package HxCKDMS.HxCDiseases.entity;


import HxCKDMS.HxCDiseases.DiseaseConfig;
import HxCKDMS.HxCDiseases.HxCDiseases;
import HxCKDMS.HxCDiseases.Utilities;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityVomitFX extends EntityFX{

    public String disease = "";
    public EntityPlayer Owner;
    public float red = 0;
    public float green = 0;
    public float blue = 0;
    public String textureName = HxCDiseases.MODID + ":textures/entity/particles/vomit.png";

    public EntityVomitFX(World world, double X, double Y, double Z, double mX, double mY, double mZ, String disease, EntityPlayer owner, float r, float g, float b, int type) {
        super(world, X, Y, Z, mX, mY, mZ);
        this.particleGravity=0.8f;
        this.setRotation(0, world.rand.nextInt(360));
        this.disease = disease;
        this.Owner = owner;
        red = r;
        green = g;
        blue = b;
        this.setParticleTextureIndex(0);
        if(type==1){
            textureName = HxCDiseases.MODID + ":textures/entity/particles/poo.png";
        }
    }

    @Override
    public void onUpdate(){
        super.onUpdate();
        if(this.onGround){
            this.setVelocity(0, 0, 0);
            this.setSize(0.4f, .01f);
        }
        if(this.ticksExisted > DiseaseConfig.vomitParticleLife){
            this.setDead();
        }
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player){
        if(player!=Owner) {
            if(disease.contains(",")){
                for (String s : disease.split(",")) {
                    applyDisease(player, s);
                }
            }else {
                applyDisease(player, disease);
            }
        }
    }


    public void applyDisease(Entity player, String disease){
        Utilities.applyDisease(player,disease);
    }
}
