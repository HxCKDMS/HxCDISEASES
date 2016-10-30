package HxCKDMS.HxCDiseases.Villager;

import HxCKDMS.HxCDiseases.Utilities;
import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

import java.util.Random;

public class TradeHandlerDoctor implements VillagerRegistry.IVillageTradeHandler {
    @Override
    public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random) {
        recipeList.clear();
        recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Items.emerald,1),Utilities.getDiseaseItem("Diagnosis")));
        recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Items.emerald,64), new ItemStack(Items.emerald,64), Utilities.getDiseaseItem("Mysterious Gem")));
    }
}
