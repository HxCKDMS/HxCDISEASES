package HxCKDMS.HxCDiseases;

import HxCKDMS.HxCCore.HxCCore;
import HxCKDMS.HxCCore.api.Configuration.HxCConfig;
import HxCKDMS.HxCDiseases.Symptoms.*;
import HxCKDMS.HxCDiseases.items.ItemVial;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;
import java.util.UUID;

@Mod(modid = HxCDiseases.MODID, version = HxCDiseases.VERSION, dependencies = "required-after:HxCCore")
public class HxCDiseases
{
	//public static DiseaseConfig DiseaseConfig;
    public static final String MODID = "hxcdiseases";
    public static final String VERSION = "1.0";
	public static UUID feverHealthUUID = UUID.fromString("f2b5a521-bca6-43d1-a07e-f2ca9f84f541");

	public HxCConfig hxCConfig;
	public static SimpleNetworkWrapper networkWrapper = new SimpleNetworkWrapper(MODID);

	public static DamageSource fever;

	public static HashMap<String, Disease> diseases = new HashMap<>();

	static {
		diseases.put("Inner Ear Infection", new Disease(new Symptom[]{new Nausea(), new Instability(), new Fatigue()}));
		diseases.put("Swine Flu", new Disease(new Symptom[]{new DizzySpells(),new Sneezes(), new Fatigue(), new Fever(104)}));
		diseases.put("Bronchitis", new Disease(new Symptom[]{ new Coughing(), new Coughing(), new Coughing(), new Fever(102)}));
		diseases.put("Ebola", new Disease(new Symptom[]{new Nausea(), new Instability(), new Coughing(), new Coughing(), new ImparedVision(), new Fatigue(), new Fever(107)}));
		diseases.put("Common Cold", new Disease(new Symptom[]{new Sneezes(), new Coughing(), new Fatigue(), new Fever(100)}));
		diseases.put("Zombie Flu", new Disease(new Symptom[]{new ImparedVision(), new Instability(), new Nausea(), new Coughing(), new Coughing(), new Insatiability(), new Fever(108), new Fatigue()}));
	}

	public static ItemVial vial;

    public static CreativeTabs tabDiseases = new CreativeTabs("tabDiseases") {
		@SideOnly(Side.CLIENT)
		@Override
	    public ItemStack getIconItemStack() {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("disease","Swine Flu");
	        ItemStack itemStack = new ItemStack(vial);
			itemStack.setTagCompound(nbt);
			return itemStack;
	    }

		@SideOnly(Side.CLIENT)
		@Override
		public Item getTabIconItem() {
			return null;
		}
	};
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//DiseaseConfig = new DiseaseConfig(new Configuration(event.getSuggestedConfigurationFile()));
		hxCConfig = new HxCConfig(DiseaseConfig.class, "HxCDiseases", HxCCore.HxCConfigDir, "cfg", MODID);
		hxCConfig.initConfiguration();
		networkWrapper.registerMessage(PacketKey.handler.class, PacketKey.class, 1, Side.SERVER);
		Keybinds.register();
		fever = new DamageSource("sickness.fever").setDamageBypassesArmor();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{

    	//DECLARE--------------------------------
    	//FLU
		//SwineFlu = new ItemVial("Swine Flu", 10000);
		//STDs
		//Ebola = new ItemVial("Ebola", 10000);
		//COMMON SICKNESSES
		//CommonCold = new ItemVial("Common Cold", 10000);
		vial = new ItemVial();

    	//REGISTER-------------------------------
		//GameRegistry.registerItem(SwineFlu, "vialswineflu");
		//GameRegistry.registerItem(Ebola, "vialebola");
		//GameRegistry.registerItem(CommonCold, "vialcold");
		GameRegistry.registerItem(vial,"itemvial");


		MinecraftForge.EVENT_BUS.register(new DiseaseHandler());
		FMLCommonHandler.instance().bus().register(new DiseaseHandler());


    }
}
