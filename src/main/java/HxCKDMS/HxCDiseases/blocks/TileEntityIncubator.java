package HxCKDMS.HxCDiseases.blocks;

import HxCKDMS.HxCDiseases.HxCDiseases;
import HxCKDMS.HxCDiseases.IncubatorRecipe;
import HxCKDMS.HxCDiseases.Utilities;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import java.util.Random;

public class TileEntityIncubator extends TileEntity
{
    public String status = "";
    public boolean mixing = false;
    public boolean processing = false;
    public int progressCounter = 0;
    public int target = 0;
    public float progress = 0;
    public IncubatorRecipe making;
    private Random rand = new Random();
    @Override
    public void updateEntity() {
        mixing = processing;
        worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord,yCoord+1,zCoord,xCoord+1,yCoord+2,zCoord+1)).forEach((Object oItem)->{
            EntityItem item = (EntityItem)oItem;
            NBTTagCompound nbt = item.getEntityItem().getTagCompound();
            if(!processing) {
                HxCDiseases.incubatorRecipes.forEach((IncubatorRecipe recipe) -> {
                    if (Utilities.isSameDiseaseItem(recipe.input, item.getEntityItem())) {
                        if(!processing) {
                            if (item.getEntityItem().stackSize > 1) {
                                ItemStack newstack = item.getEntityItem();
                                newstack.stackSize--;
                                item.setEntityItemStack(newstack);
                            } else {
                                item.setDead();
                            }
                        }

                        status = "Attempting to grow \""+recipe.output.getDisplayName()+"\".";
                        making = recipe;
                        progress = 0;
                        progressCounter = 0;
                        processing = true;
                        target = recipe.time;
                        worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hxcdiseases:incuwork", 1.5f, 1);
                    }

                });
            }
        });
        if(processing){
            if(progressCounter<target){
                progressCounter++;
                if(progressCounter%60==0 && progressCounter < target-60){
                    worldObj.playSoundEffect(xCoord,yCoord,zCoord,"hxcdiseases:incuwork",1.5f,1);
                }
            }else{
                if(!worldObj.isRemote) {
                    if (rand.nextInt(100) <= making.successChance) {
                        outputItem();
                    } else {
                        fail();
                    }
                }
                processing = false;
            }
        }
        progress = (float)progressCounter/(float)target;
    }

    private void outputItem(){
    status = "Culture grown successfully!";
        EntityItem item = new EntityItem(worldObj, xCoord, yCoord + 2, zCoord, making.output.copy());
        worldObj.spawnEntityInWorld(item);
    }

    private void fail(){
        worldObj.playSoundEffect(xCoord,yCoord,zCoord,"hxcdiseases:fail",1,1);
        status = "Failed to grow culture.";
    }
}
