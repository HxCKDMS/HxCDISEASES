package com.wiggle1000.HxC.Diseases.items;

import HxCKDMS.HxCCore.Handlers.NBTFileIO;
import HxCKDMS.HxCCore.HxCCore;
import com.wiggle1000.HxC.Diseases.HxCDiseases;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.io.File;

public class ItemVial extends ItemFood{
	

	public String diseasename = "Default Disease";
	
	public ItemVial(String _diseasename, int _duration){
		super(0,false);
		this.diseasename = _diseasename;
		this.setCreativeTab(HxCDiseases.tabDiseases);
		this.setAlwaysEdible();
		this.setUnlocalizedName("vial_"+diseasename.replace(" ", "").toLowerCase());
		this.setTextureName(diseasename.replace(" ", "").toLowerCase()+"_vial");
	}

	@Override
	public EnumAction getItemUseAction(ItemStack item){
		return EnumAction.eat;
	}
	
	@Override
	public ItemStack onEaten(ItemStack item, World world, EntityPlayer player){
		applyDisease(player);
		return new ItemStack(item.getItem(),item.stackSize-1);
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack is, EntityPlayer myPlayer, Entity other){
		applyDisease(other);
		return true;
	}

	public void applyDisease(Entity player){
		if(!player.worldObj.isRemote){
			if(player instanceof EntityPlayerMP){
				String UUID = player.getUniqueID().toString();
				File CustomPlayerData = new File(HxCCore.HxCCoreDir, "HxC-" + UUID + ".dat");
				((EntityPlayer)player).addChatMessage(new ChatComponentText("You now have '"+this.diseasename+"'!"));
				NBTTagCompound Diseases = NBTFileIO.getNbtTagCompound(CustomPlayerData, "Diseases");
				try {
					Diseases.setBoolean(this.diseasename, true);
					NBTFileIO.setNbtTagCompound(CustomPlayerData, "Diseases", Diseases);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//NBTTagCompound Disease = Diseases.getCompoundTag( this.diseasename);
			}
		}
	}
}
