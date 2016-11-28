package HxCKDMS.HxCDiseases;

import HxCKDMS.HxCDiseases.Proxies.CommonProxy;
import HxCKDMS.HxCDiseases.Symptoms.*;
import HxCKDMS.HxCDiseases.Villager.ComponentDoctor;
import HxCKDMS.HxCDiseases.Villager.TradeHandlerDoctor;
import HxCKDMS.HxCDiseases.Villager.VillagerDoctorHandler;
import HxCKDMS.HxCDiseases.blocks.BlockIncubator;
import HxCKDMS.HxCDiseases.blocks.TileEntityIncubator;
import HxCKDMS.HxCDiseases.items.ItemVial;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
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
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static HxCKDMS.HxCDiseases.Symptoms.Symptom.symptoms;

@Mod(modid = HxCDiseases.MODID, version = HxCDiseases.VERSION, dependencies = "required-after:hxccore")
public class HxCDiseases
{

	public static final String MODID = "hxcdiseases";
	public static final String VERSION = "1.0";
	@Mod.Instance(MODID)
	public static HxCDiseases instance;

	@SidedProxy(clientSide = "HxCKDMS.HxCDiseases.Proxies.ClientProxy", serverSide = "HxCKDMS.HxCDiseases.Proxies.CommonProxy")
	public static CommonProxy proxy;

	public static final UUID feverHealthUUID = UUID.fromString("f2b5a521-bca6-43d1-a07e-f2ca9f84f541");

	public static final int villagerID = 18978;

	public TradeHandlerDoctor tradeHandlerDoctor;
	public HxCConfig hxCConfig;
	public static SimpleNetworkWrapper networkWrapper = new SimpleNetworkWrapper(MODID);

	public static final String[] shaderNames = {"antialias","art","bits","blobs","blobs2","blur","bumpy","color_convolve","deconverge","desaturate","flip","fxaa","green","invert","notch","ntsc","outline","pencil","phosphor","scan_pincushion","sobel","wobble"};

	public static DamageSource fever;
	public static DamageSource bloodLoss;

	public static BlockIncubator blockIncubator;

	public static HashMap<String, Disease> diseases = new HashMap<>();
	public static HashMap<Class, String> mobs = new HashMap<>();
	public static ArrayList<IncubatorRecipe> incubatorRecipes = new ArrayList<IncubatorRecipe>();

	static {
		symptoms.put("Coughing", new Coughing());
		symptoms.put("DizzySpells", new DizzySpells());
		symptoms.put("Fatigue", new Fatigue());
		symptoms.put("Fever", new Fever(99));
		symptoms.put("FeverI", new Fever(99));
		symptoms.put("FeverII", new Fever(100));
		symptoms.put("FeverIII", new Fever(102));
		symptoms.put("FeverIV", new Fever(105));
		for (String shader: shaderNames) {
			symptoms.put("Hallucination_"+shader, new Hallucinations(shader));
		}
		symptoms.put("ImpairedVision", new ImparedVision(1));
		symptoms.put("ImpairedVisionI", new ImparedVision(1));
		symptoms.put("ImpairedVisionII", new ImparedVision(2));
		symptoms.put("Insatiability", new Insatiability());
		symptoms.put("Instability", new Instability());
		symptoms.put("Nausea", new Nausea());
		symptoms.put("Sneezes", new Sneezes());

		diseases.put("Vial", null);
		diseases.put("Diagnosis", null);
		diseases.put("Mysterious Gem", null);
		diseases.put("Syringe", null);
		diseases.put("EyeDropper", null);
		diseases.put("Grand Panacea", null);
		diseases.put("Inner Ear Infection", new Disease(500, true, new Symptom[]{new Nausea(), new Instability(), new Fatigue(), new Hallucinations("phosphor")}, "dizzy.", "like you've regained orientation."));
		diseases.put("Swine Flu", new Disease(1000, true, new Symptom[]{new DizzySpells(),new Sneezes(), new Fatigue(), new Fever(104)},"cold and irritable.", "recovered and warm."));
		diseases.put("Bronchitis", new Disease(600, true, new Symptom[]{ new Coughing(), new Coughing(), new Coughing(), new Fever(102)},"congested and weak.", "like you can breathe again."));
		diseases.put("Ebola", new Disease(-1, false, new Symptom[]{new Nausea(), new Instability(), new Coughing(), new Coughing(), new ImparedVision(2), new Fatigue(), new Hallucinations("sobel"), new Fever(107)},"like you're dieing! See a doctor!", "miraculously cured."));
		diseases.put("Common Cold", new Disease(400, true, new Symptom[]{new Sneezes(), new Coughing(), new Fatigue(), new Fever(100)}, "a little under the weather.", "much better."));
		diseases.put("Zombie Flu", new Disease(-1, false, new Symptom[]{new ImparedVision(1), new Instability(), new Nausea(), new Coughing(), new Coughing(), new Insatiability(), new Fever(108), new Fatigue(), new Hallucinations("wobble")},"an insatiable hunger for flesh.", "like brains are suddenly unappetizing."));
	}

	public static void registerIncubatorRecipes(){
		incubatorRecipes.add(new IncubatorRecipe(Utilities.getSyringeItem("Pig"), Utilities.getDiseaseItem("Swine Flu"), 500, 3));
		incubatorRecipes.add(new IncubatorRecipe(Utilities.getSyringeItem("Zombie"), Utilities.getDiseaseItem("Zombie Flu"), 600, 10));
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
		networkWrapper.registerMessage(PacketKey.handler.class, PacketKey.class, 0, Side.SERVER);
		HxCDiseases.networkWrapper.registerMessage(PacketGui.handler.class, PacketGui.class, 1, Side.CLIENT);
		networkWrapper.registerMessage(PacketShader.handler.class, PacketShader.class, 2, Side.CLIENT);
		fever = new DamageSource("sickness.fever").setDamageBypassesArmor();
		bloodLoss = new DamageSource("sickness.bloodloss");
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		EntityList.stringToClassMapping.forEach((name, ent)->{
			if(EntityLivingBase.class.isAssignableFrom((Class)ent) && (Class)ent != EntityLiving.class){
				mobs.put((Class)ent, (String)name);
			}
		});
		vial = new ItemVial();
		blockIncubator = new BlockIncubator();
		tradeHandlerDoctor = new TradeHandlerDoctor();
		GameRegistry.registerItem(vial,"itemvial");

		VillagerRegistry.instance().registerVillagerId(villagerID);
		VillagerRegistry.instance().registerVillageTradeHandler(villagerID, tradeHandlerDoctor);

		addVillagePiece(ComponentDoctor.class, "Doctor");
		addVillageCreationHandler(new VillagerDoctorHandler());

		GameRegistry.registerBlock(blockIncubator, "blockincubator");
		GameRegistry.registerTileEntity(TileEntityIncubator.class, "tileincubator");

		MinecraftForge.EVENT_BUS.register(new DiseaseHandler());
		FMLCommonHandler.instance().bus().register(new DiseaseHandler());
		AddRecipes();
		registerIncubatorRecipes();
		proxy.init(event);
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
				"DDD",
				"DCD",
				"ABA",
				'A', "blockGold",
				'D', "blockDiamond",
				'B', Utilities.getDiseaseItem("EyeDropper"),
				'C', Utilities.getDiseaseItem("Mysterious Gem")
		));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockIncubator),
				"BJB",
				"JIJ",
				"KDK",
				'I', Items.cauldron,
				'D', "dustRedstone",
				'B', Utilities.getDiseaseItem("Vial"),
				'J', "ingotIron",
				'K', "blockIron"
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
