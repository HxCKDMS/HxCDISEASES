package HxCKDMS.HxCDiseases;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class Keybinds {
    public static KeyBinding fart;
    @SideOnly(Side.CLIENT)
    public static void register()
    {
        fart = new KeyBinding("key.fart", Keyboard.KEY_B, "key.categories.HxCCore");
        ClientRegistry.registerKeyBinding(fart);
    }
}
