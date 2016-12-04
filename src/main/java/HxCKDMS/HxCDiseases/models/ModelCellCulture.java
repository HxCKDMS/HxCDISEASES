package HxCKDMS.HxCDiseases.models;

import HxCKDMS.HxCDiseases.HxCDiseases;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

@SideOnly(Side.CLIENT)
public class ModelCellCulture {
    private IModelCustom modelCellCulture;

    public ModelCellCulture() {
        modelCellCulture = AdvancedModelLoader.loadModel(new ResourceLocation(HxCDiseases.MODID + ":textures/models/CellCulture.obj"));
    }

    public void render() {
        modelCellCulture.renderAll();
    }

    public void renderPart(String part) { //note to self: "Dish", "Growth", and "Medium"
        modelCellCulture.renderPart(part);
    }
}
