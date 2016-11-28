package HxCKDMS.HxCDiseases.blocks;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityIncubator extends TileEntity
{
    public String status = "";
    public boolean mixing = false;

    @Override
    public void updateEntity() {
        mixing = false;
        worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord,yCoord+1,zCoord,xCoord+1,yCoord+2,zCoord+1)).forEach((Object oItem)->{
            EntityItem item = (EntityItem)oItem;
           // if(itemCompound.getString("disease") == "Filled Syringe" && itemCompound.getString("mob") == "Pig"){
           // }
            status = item.getEntityItem().getDisplayName();
            mixing = true;
        });
    }


}
