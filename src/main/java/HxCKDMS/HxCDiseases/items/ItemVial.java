package HxCKDMS.HxCDiseases.items;

import HxCKDMS.HxCDiseases.HxCDiseases;
import HxCKDMS.HxCDiseases.Utilities;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemVial extends ItemFood{
	@SideOnly(Side.CLIENT)
	public HashMap<String,IIcon> icons = new HashMap<>();
	
	public ItemVial(){
		super(0,false);
		this.setCreativeTab(HxCDiseases.tabDiseases);
		this.setAlwaysEdible();
		this.setUnlocalizedName("vial");
		//this.setTextureName(HxCDiseases.MODID+":"+"vial_"+diseasename.replace(" ", "").toLowerCase());
		this.setHasSubtypes(true);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack item){return EnumAction.drink;}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {return 32;}

	@Override
	@SuppressWarnings("unchecked")
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		List<ItemStack> dgs = new ArrayList<>();
		HxCDiseases.diseases.keySet().forEach(disease -> {
			ItemStack stack = new ItemStack(item, 1, 0);
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("disease", disease);
			stack.setTagCompound(tag);
			dgs.add(stack);
		});
		list.addAll(dgs);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass) {
		return icons.get(stack.getTagCompound().getString("disease"));
	}



	//icons.get(stack.getTagCompound().getString("disease"));

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconReg) {
		HxCDiseases.diseases.keySet().forEach(diseasename-> {
					icons.put(diseasename, iconReg.registerIcon(HxCDiseases.MODID + ":" + "disease_" +diseasename.replace(" ", "").toLowerCase()));
				});
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		if(player.capabilities.isCreativeMode) {
			this.onEaten(stack, world, player);
			return stack;
		}else{
			player.setItemInUse(stack, getMaxItemUseDuration(stack));
			return stack;
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return  "item.vial_" +itemStack.getTagCompound().getString("disease").replace(" ", "").toLowerCase();
	}



	@Override
	public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer player){
		String disease = itemStack.getTagCompound().getString("disease");
		if(applyDisease(player, disease)) {
			return player.inventory.decrStackSize(player.inventory.currentItem,itemStack.stackSize-1);
		}
		return itemStack;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack itemStack, EntityPlayer myPlayer, Entity other){
		String disease = itemStack.getTagCompound().getString("disease");
		if(applyDisease(other, disease)) {
			if (!myPlayer.capabilities.isCreativeMode) {
				myPlayer.inventory.decrStackSize(myPlayer.inventory.currentItem, 1);
			}
			Utilities.playSoundAtPlayer(myPlayer, "hxcdiseases:notify", 3, 1 + ((itemRand.nextFloat() - 0.5f) / 5));
		}
		return true;
	}

	public boolean applyDisease(Entity player, String disease){
		return Utilities.applyDisease(player,disease);
	}
}
