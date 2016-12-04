package HxCKDMS.HxCDiseases;


import net.minecraft.item.ItemStack;



public class CellCultureRecipe {
    public ItemStack input;
    public CellCultureMediumType medium = CellCultureMediumType.Bloodcells;
    public ItemStack output;
    public int time = 100;
    public int successChance = 100;

    public CellCultureRecipe(ItemStack input, CellCultureMediumType medium, ItemStack output, int time, int successChance){
        this.input = input;
        this.medium = medium;
        this.output = output;
        this.time = time;
        this.successChance = successChance;
    }

}
