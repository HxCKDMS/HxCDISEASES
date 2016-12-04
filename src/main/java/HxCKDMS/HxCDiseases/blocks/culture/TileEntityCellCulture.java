package HxCKDMS.HxCDiseases.blocks.culture;

import HxCKDMS.HxCDiseases.CellCultureMediumType;
import HxCKDMS.HxCDiseases.CellCultureRecipe;
import HxCKDMS.HxCDiseases.HxCDiseases;
import HxCKDMS.HxCDiseases.Utilities;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import java.util.Random;

public class TileEntityCellCulture extends TileEntity
{
    public String status = "";
    public boolean processing = false;
    public int progressCounter = 0;
    public CellCultureMediumType mediumType = CellCultureMediumType.None;
    public int target = 0;
    public float progress = 0;
    public CellCultureRecipe making;
    private Random rand = new Random();

    @Override
    public void updateEntity() {
        worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord+0.2f,yCoord,zCoord+0.2f,xCoord+0.8f,yCoord+0.5f,zCoord+0.8f)).forEach((Object oItem)->{
            EntityItem item = (EntityItem)oItem;
            NBTTagCompound nbt = item.getEntityItem().getTagCompound();
            markDirty();
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            if(!processing) {
                HxCDiseases.cellCultureRecipes.forEach((CellCultureRecipe recipe) -> {
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
                    }

                });
            }
        });
        if(processing){
            if(progressCounter<target){
                progressCounter++;
                if(progressCounter%60==0 && progressCounter < target-60){
                    //worldObj.playSoundEffect(xCoord,yCoord,zCoord,"hxcdiseases:incuwork",1.5f,1);
                    markDirty();
                }
            }else{
                if(!worldObj.isRemote) {
                    if (rand.nextInt(100) <= making.successChance) {
                        outputItem();
                    } else {
                        fail();
                    }
                    setMedium(CellCultureMediumType.None);
                }
                processing = false;
            }
        }
        progress = (float)progressCounter/(float)target;
    }

    private void outputItem(){
        status = "Culture grown successfully!";
        EntityItem item = new EntityItem(worldObj, xCoord, yCoord + 1, zCoord, making.output.copy());
        worldObj.spawnEntityInWorld(item);
        markDirty();
    }

    public void setMedium(CellCultureMediumType type){
        this.mediumType = type;
        markDirty();
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        status = tag.getString("status");
        processing = tag.getBoolean("processing");
        progressCounter = tag.getInteger("progressCounter");
        setMedium(CellCultureMediumType.values()[tag.getInteger("mediumtype")]);
        target = tag.getInteger("target");
        if(tag.hasKey("recipeIndex")) {
            making = HxCDiseases.cellCultureRecipes.get(tag.getInteger("recipeIndex"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setString("status", status);
        tag.setBoolean("processing", processing);
        tag.setInteger("progressCounter", progressCounter);
        tag.setInteger("target", target);
        tag.setInteger("mediumtype", mediumType.ordinal());
        if(making!=null) {
            tag.setInteger("recipeIndex", HxCDiseases.cellCultureRecipes.indexOf(making));
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        readFromNBT(packet.func_148857_g());
    }

    private void fail(){
        worldObj.playSoundEffect(xCoord,yCoord,zCoord,"hxcdiseases:fail",1,1);
        status = "Failed to grow culture.";
        markDirty();
    }
}
