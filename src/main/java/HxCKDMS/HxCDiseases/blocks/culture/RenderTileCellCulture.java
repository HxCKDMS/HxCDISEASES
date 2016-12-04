package HxCKDMS.HxCDiseases.blocks.culture;


import HxCKDMS.HxCDiseases.CellCultureMediumType;
import HxCKDMS.HxCDiseases.HxCDiseases;
import HxCKDMS.HxCDiseases.models.ModelCellCulture;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderTileCellCulture extends TileEntitySpecialRenderer {

    private final ResourceLocation textureDish = new ResourceLocation(HxCDiseases.MODID + ":textures/models/petridish.png");
    private final ResourceLocation textureGrowth = new ResourceLocation(HxCDiseases.MODID + ":textures/models/viralgrowth.png");

    private final ModelCellCulture modelCellCulture = new ModelCellCulture();
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTick) {
        TileEntityCellCulture tile = ((TileEntityCellCulture)tileEntity);
        GL11.glColor3f(1,1,1);
        GL11.glPushMatrix();
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        GL11.glTranslated(x + 0.5F, y + 0.001F, z + 0.5F);
        bindTexture(textureDish);
        modelCellCulture.renderPart("Dish");
        if(tile.mediumType != CellCultureMediumType.None) {
            bindTexture(CellCultureMediumType.textureLocations.get(tile.mediumType));
            modelCellCulture.renderPart("Medium");
            if(tile.processing) {
                bindTexture(textureGrowth);
                GL11.glColor4f(1f, 1f, 1f, 0.01f+tile.progress);
                modelCellCulture.renderPart("Growth");
            }
        }

        GL11.glPopMatrix();
    }
}
