package HxCKDMS.HxCDiseases.Proxies;

import HxCKDMS.HxCDiseases.Keybinds;
import HxCKDMS.HxCDiseases.blocks.RenderTileIncubator;
import HxCKDMS.HxCDiseases.blocks.TileEntityIncubator;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraft.util.ResourceLocation;

import static HxCKDMS.HxCDiseases.HxCDiseases.MODID;
import static HxCKDMS.HxCDiseases.HxCDiseases.villagerID;

public class ClientProxy extends CommonProxy{
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        Keybinds.register();
        VillagerRegistry.instance().registerVillagerSkin(villagerID, new ResourceLocation(MODID,"textures/entity/villager/doctor.png"));
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityIncubator.class, new RenderTileIncubator());
        super.init(event);
    }
}
