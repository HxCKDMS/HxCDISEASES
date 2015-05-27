package com.wiggle1000.HxC.Diseases.items;

import java.util.List;

import com.wiggle1000.HxC.Diseases.HxCDiseases;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionAttackDamage;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemVial extends ItemFood{
	

	public String diseasename = "Default Disease";
	public Potion[] effects;
	public int duration = 120;
	
	public ItemVial(String _diseasename, Potion[] _effects, int _duration){
		super(0,false);
		this.diseasename = _diseasename;
		this.effects = _effects;
		this.duration = _duration;
		this.setCreativeTab(HxCDiseases.tabDiseases);
		this.setHasSubtypes(true);
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
	//DrKeldon is a donkey
	public void applyDisease(Entity player){
		if(!player.worldObj.isRemote){
			if(player instanceof EntityPlayer){
				((EntityPlayer)player).addChatMessage(new ChatComponentText("You now have '"+this.diseasename+"'!"));
			}
			if(player instanceof EntityLiving){
				for(Potion p: effects){
					((EntityLiving)player).addPotionEffect(new PotionEffect(p.id, duration, 1));
				}
			}else if(player instanceof EntityPlayer){
				for(Potion p: effects){
					((EntityPlayer)player).addPotionEffect(new PotionEffect(p.id, duration, 1));
				}
			}
		}
	}
	@Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list){
        for(int i = 0; i < 9; i++){
            list.add(new ItemStack(item, 1, i));
        }
    }
}
