package com.wiggle1000.HxC.Diseases;

import com.wiggle1000.HxC.Diseases.items.ItemVial;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = HxCDiseases.MODID, version = HxCDiseases.VERSION, dependencies = "required-after:HxCCore")
public class HxCDiseases
{
	public static Config Config;
    public static final String MODID = "hxcdiseases";
    public static final String VERSION = "1.0";

	public ItemVial SwineFlu;
	public ItemVial Ebola;
    
    public static CreativeTabs tabDiseases = new CreativeTabs("tabDiseases") {
	    @Override
	    @SideOnly(Side.CLIENT)
	    public Item getTabIconItem() {
	        return Items.poisonous_potato;
	    }
	};
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Config = new Config(new Configuration(event.getSuggestedConfigurationFile()));
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
    	
    	//DECLARE--------------------------------
    	//FLU
		SwineFlu = new ItemVial("Swine Flu", 10000);
		//STDs
		Ebola = new ItemVial("Ebola", 10000);
    	

    	//REGISTER-------------------------------
		GameRegistry.registerItem(SwineFlu, "vialswineflu");
		GameRegistry.registerItem(Ebola, "vialebola");
		MinecraftForge.EVENT_BUS.register(new myEventHandler());


    }
}
