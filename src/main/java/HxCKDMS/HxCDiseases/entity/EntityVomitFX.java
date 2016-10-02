package HxCKDMS.HxCDiseases.entity;


import HxCKDMS.HxCDiseases.Utilities;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityVomitFX extends EntityFX{

    public String Disease = "";
    public EntityPlayer Owner;

    public EntityVomitFX(World world, double X, double Y, double Z, double mX, double mY, double mZ, String disease, EntityPlayer owner) {
        super(world, X, Y, Z, mX, mY, mZ);
        this.particleGravity=0.8f;
        this.setRotation(0, world.rand.nextInt(360));
        this.Disease = disease;
        this.Owner = owner;
    }

    @Override
    public void onUpdate(){
        super.onUpdate();
        if(this.ticksExisted>500){
            this.setVelocity(0, 0, 0);
            this.setSize(0.1f, 2f);
        }
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player){
        if(player!=Owner) {
            applyDisease(player, Disease);
        }
    }


    public void applyDisease(Entity player, String disease){
        Utilities.applyDisease(player,disease);
    }
}
