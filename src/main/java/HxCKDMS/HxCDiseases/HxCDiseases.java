package HxCKDMS.HxCDiseases;

import HxCKDMS.HxCDiseases.Symptoms.*;
import HxCKDMS.HxCDiseases.Villager.ComponentDoctor;
import HxCKDMS.HxCDiseases.Villager.TradeHandlerDoctor;
import HxCKDMS.HxCDiseases.Villager.VillagerDoctorHandler;
import HxCKDMS.HxCDiseases.items.ItemVial;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hxckdms.hxcconfig.HxCConfig;
import hxckdms.hxccore.libraries.GlobalVariables;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.HashMap;
import java.util.UUID;

@Mod(modid = HxCDiseases.MODID, version = HxCDiseases.VERSION, dependencies = "required-after:hxccore")
public class HxCDiseases
{
	//public static DiseaseConfig DiseaseConfig;
    public static final String MODID = "hxcdiseases";
    public static final String VERSION = "1.0";
	public static UUID feverHealthUUID = UUID.fromString("f2b5a521-bca6-43d1-a07e-f2ca9f84f541");

	public static int villagerID = 18978;

	public TradeHandlerDoctor tradeHandlerDoctor;
	public HxCConfig hxCConfig;
	public static SimpleNetworkWrapper networkWrapper = new SimpleNetworkWrapper(MODID);

	public static DamageSource fever;

	public static HashMap<String, Disease> diseases = new HashMap<>();
	public static HashMap<Class, String> mobs = new HashMap<>();

	static {
		diseases.put("Vial", null);
		diseases.put("Diagnosis", null);
		diseases.put("Mysterious Gem", null);
		diseases.put("Syringe", null);
		diseases.put("EyeDropper", null);
		diseases.put("Grand Panacea", null);
		diseases.put("Inner Ear Infection", new Disease(new Symptom[]{new Nausea(), new Instability(), new Fatigue()}, "dizzy."));
		diseases.put("Swine Flu", new Disease(new Symptom[]{new DizzySpells(),new Sneezes(), new Fatigue(), new Fever(104)},"pretty sick."));
		diseases.put("Bronchitis", new Disease(new Symptom[]{ new Coughing(), new Coughing(), new Coughing(), new Fever(102)},"congested and weak."));
		diseases.put("Ebola", new Disease(new Symptom[]{new Nausea(), new Instability(), new Coughing(), new Coughing(), new ImparedVision(), new Fatigue(), new Fever(107)},"very ill! See a doctor!"));
		diseases.put("Common Cold", new Disease(new Symptom[]{new Sneezes(), new Coughing(), new Fatigue(), new Fever(100)}, "a little under the weather."));
		diseases.put("Zombie Flu", new Disease(new Symptom[]{new ImparedVision(), new Instability(), new Nausea(), new Coughing(), new Coughing(), new Insatiability(), new Fever(108), new Fatigue()},"an insatiable hunger for flesh."));
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
		hxCConfig = new HxCConfig(DiseaseConfig.class, "HxCDiseases", GlobalVariables.modConfigDir, "cfg", MODID);
		hxCConfig.initConfiguration();
		networkWrapper.registerMessage(PacketKey.handler.class, PacketKey.class, 1, Side.SERVER);
		Keybinds.register();
		fever = new DamageSource("sickness.fever").setDamageBypassesArmor();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		EntityList.stringToClassMapping.forEach((name, ent)->{
			if(EntityLivingBase.class.isAssignableFrom((Class)ent) && (Class)ent != EntityLiving.class){
				mobs.put((Class)ent, (String)name);
			}
		});
    	//DECLARE--------------------------------
    	//FLU
		//SwineFlu = new ItemVial("Swine Flu", 10000);
		//STDs
		//Ebola = new ItemVial("Ebola", 10000);
		//COMMON SICKNESSES
		//CommonCold = new ItemVial("Common Cold", 10000);
		vial = new ItemVial();
		tradeHandlerDoctor = new TradeHandlerDoctor();
    	//REGISTER-------------------------------
		//GameRegistry.registerItem(SwineFlu, "vialswineflu");
		//GameRegistry.registerItem(Ebola, "vialebola");
		//GameRegistry.registerItem(CommonCold, "vialcold");
		GameRegistry.registerItem(vial,"itemvial");

		VillagerRegistry.instance().registerVillagerId(villagerID);
		VillagerRegistry.instance().registerVillageTradeHandler(villagerID, tradeHandlerDoctor);
		VillagerRegistry.instance().registerVillagerSkin(villagerID, new ResourceLocation(MODID,"textures/entity/villager/doctor.png"));

		addVillagePiece(ComponentDoctor.class, "Doctor");
		addVillageCreationHandler(new VillagerDoctorHandler());

		MinecraftForge.EVENT_BUS.register(new DiseaseHandler());
		FMLCommonHandler.instance().bus().register(new DiseaseHandler());
		AddRecipes();

    }

	void AddRecipes(){
		GameRegistry.addRecipe(new ShapedOreRecipe(Utilities.getDiseaseItem("Vial"),
				"A",
				"A",
				'A', "paneGlass"
		));
		GameRegistry.addRecipe(new ShapedOreRecipe(Utilities.getDiseaseItem("EyeDropper"),
				" B ",
				"A A",
				"AAA",
				'A', "paneGlass",
				'B', "cobblestone"
		));
		GameRegistry.addRecipe(new ShapedOreRecipe(Utilities.getDiseaseItem("Grand Panacea"),
				"AAA",
				"ACA",
				"ABA",
				'A', "blockGold",
				'B', Utilities.getDiseaseItem("EyeDropper"),
				'C', Utilities.getDiseaseItem("Mysterious Gem")
		));

	}

	public static void addVillagePiece(Class c, String s){
		try{
			MapGenStructureIO.func_143031_a(c, s);
		}catch(Exception e){
			System.out.println("Failed To Spawn The Extra Village Pieces With The ID: " + s);
		}
	}

	public static void addVillageCreationHandler(VillagerRegistry.IVillageCreationHandler v){
		VillagerRegistry.instance().registerVillageCreationHandler(v);
	}
}
