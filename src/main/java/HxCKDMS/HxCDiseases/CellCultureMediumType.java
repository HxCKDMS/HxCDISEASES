package HxCKDMS.HxCDiseases;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;


public enum CellCultureMediumType{
    None,
    Bloodcells,
    Sucrose,
    DampPaper;
    public static HashMap<CellCultureMediumType,ResourceLocation> textureLocations = new HashMap<>();
    static {
        textureLocations.put(CellCultureMediumType.Bloodcells, new ResourceLocation(HxCDiseases.MODID + ":textures/models/medium_blood.png"));
        textureLocations.put(CellCultureMediumType.Sucrose, new ResourceLocation(HxCDiseases.MODID + ":textures/models/medium_sucrose.png"));
        textureLocations.put(CellCultureMediumType.DampPaper, new ResourceLocation(HxCDiseases.MODID + ":textures/models/medium_damp_paper.png"));
    }
}