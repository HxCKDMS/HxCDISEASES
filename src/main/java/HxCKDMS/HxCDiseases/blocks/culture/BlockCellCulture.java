package HxCKDMS.HxCDiseases.blocks.culture;


import HxCKDMS.HxCDiseases.CellCultureMediumType;
import HxCKDMS.HxCDiseases.HxCDiseases;
import HxCKDMS.HxCDiseases.Utilities;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockCellCulture extends Block {

    public IIcon[] blockIcons = new IIcon[3];

    public BlockCellCulture() {
        super(Material.iron);
        this.setCreativeTab(HxCDiseases.tabDiseases);
        this.textureName = HxCDiseases.MODID+":cellculture";
        this.setLightLevel(1);
    }

    @Override
    public String getUnlocalizedName() {
        return HxCDiseases.MODID+".blockcellculture";
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getLightOpacity() {
        return 14;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(x+0.18, y+0.0, z+0.18, x+0.82, y+0.11, z+0.82);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(x+0.3, y+0.0, z+0.3, x+0.7, y+0.1, z+0.7);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float sx, float sy, float sz) {
        if(player.getHeldItem() != null) {
            if (Utilities.isSameDiseaseItem(player.getHeldItem(), Utilities.getSyringeItem("Player"))) {
                assert (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileEntityCellCulture && ((TileEntityCellCulture) world.getTileEntity(x, y, z)).mediumType == CellCultureMediumType.None);
                if (player.getHeldItem().stackSize > 1) {
                    player.getHeldItem().stackSize--;
                } else {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                }
                ((TileEntityCellCulture) world.getTileEntity(x, y, z)).setMedium(CellCultureMediumType.Bloodcells);
                world.markBlockForUpdate(x,y,z);
            }
        }
        return true;
    }

    @Override
    public IIcon getIcon(int side, int metadata){
        switch(side){
            case 1://top
                return blockIcons[0];
            case 0://bottom
                return blockIcons[2];
        }
        return blockIcons[1];
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        this.blockIcons[0] = reg.registerIcon(this.textureName + "_top");
        this.blockIcons[1] = reg.registerIcon(this.textureName + "_sides");
        this.blockIcons[2] = reg.registerIcon(this.textureName + "_bottom");
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityCellCulture();
    }


}
