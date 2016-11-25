package HxCKDMS.HxCDiseases;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;

public class ClientShaderManager {
    @SideOnly(Side.CLIENT)
    public static void setShader(ResourceLocation sha){
        Minecraft mc = Minecraft.getMinecraft();
        try {
            mc.entityRenderer.theShaderGroup = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), sha);
        } catch (Exception e) {
            System.out.println(HxCDiseases.MODID + ": Failed to set a shader! More info: ");
            e.printStackTrace();
        }
        mc.entityRenderer.theShaderGroup.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
    }
}
