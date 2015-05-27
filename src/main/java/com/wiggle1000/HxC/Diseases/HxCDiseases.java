package com.wiggle1000.HxC.Diseases;

import com.wiggle1000.HxC.Diseases.items.ItemVial;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;

@Mod(modid = HxCDiseases.MODID, version = HxCDiseases.VERSION, dependencies = "required-after:HxCCore")
public class HxCDiseases
{
	
    public static final String MODID = "hxcdiseases";
    public static final String VERSION = "1.0";
    
    public ItemVial SwineFlu;
    
    public static CreativeTabs tabDiseases = new CreativeTabs("tabDiseases") {
	    @Override
	    @SideOnly(Side.CLIENT)
	    public Item getTabIconItem() {
	        return Items.poisonous_potato;
	    }
	};
	
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	
    	//DECLARE--------------------------------
    	//FLU
    	SwineFlu = new ItemVial("Swine Flu",new Potion[]{Potion.digSlowdown,Potion.confusion,Potion.poison,Potion.weakness,Potion.hunger,Potion.moveSlowdown}, 10000);
    	
    	
    	//REGISTER-------------------------------
    	GameRegistry.registerItem(SwineFlu, "vialswineflu");
    }
}
