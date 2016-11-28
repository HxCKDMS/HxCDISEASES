package HxCKDMS.HxCDiseases;


import net.minecraft.item.ItemStack;

public class IncubatorRecipe {
    public ItemStack input;
    public ItemStack output;
    public int time = 100;
    public int successChance = 100;

    public IncubatorRecipe(ItemStack input, ItemStack output, int time, int successChance){
        this.input = input;
        this.output = output;
        this.time = time;
        this.successChance = successChance;
    }

}
