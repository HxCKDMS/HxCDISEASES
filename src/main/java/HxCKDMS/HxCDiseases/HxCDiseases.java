package HxCKDMS.HxCDiseases;

import HxCKDMS.HxCCore.HxCCore;
import HxCKDMS.HxCCore.api.Configuration.Config;
import HxCKDMS.HxCCore.api.Configuration.HxCConfig;
import HxCKDMS.HxCDiseases.items.ItemVial;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = HxCDiseases.MODID, version = HxCDiseases.VERSION, dependencies = "required-after:HxCCore")
public class HxCDiseases
{
	//public static DiseaseConfig DiseaseConfig;
    public static final String MODID = "hxcdiseases";
    public static final String VERSION = "1.0";

	public HxCConfig hxCConfig;

	public static ItemVial SwineFlu;
	public static ItemVial Ebola;
	public static ItemVial CommonCold;
    
    public static CreativeTabs tabDiseases = new CreativeTabs("tabDiseases") {
	    @Override
	    @SideOnly(Side.CLIENT)
	    public Item getTabIconItem() {
	        return CommonCold;
	    }
	};
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//DiseaseConfig = new DiseaseConfig(new Configuration(event.getSuggestedConfigurationFile()));
		hxCConfig = new HxCConfig(DiseaseConfig.class, "HxCDiseases", HxCCore.HxCConfigDir, "cfg", MODID);
		hxCConfig.initConfiguration();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{

    	//DECLARE--------------------------------
    	//FLU
		SwineFlu = new ItemVial("Swine Flu", 10000);
		//STDs
		Ebola = new ItemVial("Ebola", 10000);
		//COMMON SICKNESSES
		CommonCold = new ItemVial("Common Cold", 10000);


    	//REGISTER-------------------------------
		GameRegistry.registerItem(SwineFlu, "vialswineflu");
		GameRegistry.registerItem(Ebola, "vialebola");
		GameRegistry.registerItem(CommonCold, "vialcold");



		MinecraftForge.EVENT_BUS.register(new DiseaseHandler());


    }
}
