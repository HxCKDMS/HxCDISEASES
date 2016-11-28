package HxCKDMS.HxCDiseases.blocks;


import HxCKDMS.HxCDiseases.HxCDiseases;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderTileIncubator extends TileEntitySpecialRenderer {

    float rotationacc = 0;

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTick) {
        if(((TileEntityIncubator) tileEntity).mixing){
            rotationacc+=partialTick;
        }
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        Tessellator wr = Tessellator.instance;
        wr.startDrawingQuads();
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(HxCDiseases.MODID+":textures/blocks/incubator_stir.png"));
        wr.setNormal(0, 1, 0);
        wr.addVertexWithUV(0.8, 1.02, 0.8, 0.0, 1.0);
        wr.addVertexWithUV(0.8, 1.02, 0.0, 1.0, 1.0);
        wr.addVertexWithUV(0.0, 1.02, 0.0, 1.0, 0.0);
        wr.addVertexWithUV(0.0, 1.02, 0.8, 0.0, 0.0);
        GL11.glTranslatef(0.5f,0,0.5f);
        GL11.glRotatef(rotationacc,0,1,0);
        GL11.glTranslatef(-0.4f,0,-0.4f);
        Tessellator.instance.draw();
        GL11.glPopMatrix();
        renderLabel((TileEntityIncubator) tileEntity,((TileEntityIncubator) tileEntity).status ,x+0.5f,y+2,z+0.5f,25);
        renderProgress((TileEntityIncubator) tileEntity,((TileEntityIncubator) tileEntity).progress ,x+0.5f,y+1.85d,z+0.5f,25,20);
    }
    protected void renderProgress(TileEntityIncubator entity, float prog, double d, double d1, double d2, int i, int width) {
        double f = entity.getDistanceFrom(this.field_147501_a.staticPlayerX, this.field_147501_a.staticPlayerY, this.field_147501_a.staticPlayerZ);
        if (f > i) {
            return;
        }
        FontRenderer fontrenderer = this.field_147501_a.getFontRenderer();
        float entrot = Minecraft.getMinecraft().thePlayer.getRotationYawHead();
        float entrotp = Minecraft.getMinecraft().thePlayer.rotationPitch;
        float f1 = 1.6F;
        float f2 = 0.01666667F * f1;

        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-1*entrot, 0, 1, 0);
        GL11.glRotatef(entrotp, 1, 0, 0);
        GL11.glScalef(0.3f, 0.3f, 0.3f);
        //GL11.glRotatef(-this.tileEntityRenderer.playerYaw, 0.0F, 1.0F, 0.0F);
        //GL11.glRotatef(this.tileEntityRenderer.playerPitch, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-f2, -f2, f2);
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        Tessellator tessellator = Tessellator.instance;
        byte byte0 = 0;
        GL11.glDisable(3553);
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(0.1F, 0.0F, 0.0F, 0.5F);
        tessellator.addVertex(-width - 1, -1 + byte0, 0.0D);
        tessellator.addVertex(-width - 1, 8 + byte0, 0.0D);
        tessellator.addVertex(width + 1, 8 + byte0, 0.0D);
        tessellator.addVertex(width + 1, -1 + byte0, 0.0D);
        tessellator.setColorRGBA_F(0.0F, 0.1F, 0.3F, 0.5F);
        tessellator.addVertex(-width - 1, -1 + byte0, 0.0D);
        tessellator.addVertex(-width - 1, 8 + byte0, 0.0D);
        tessellator.addVertex(-width + (prog * (width * 2)) + 1, 8 + byte0, 0.0D);
        tessellator.addVertex(-width + (prog * (width * 2)) + 1, -1 + byte0, 0.0D);
        tessellator.draw();
        //tessellator.func_78381_a();
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glEnable(2896);
        GL11.glDisable(3042);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }
    protected void renderLabel(TileEntityIncubator entity, String s, double d, double d1, double d2, int i) {
        double f = entity.getDistanceFrom(this.field_147501_a.staticPlayerX, this.field_147501_a.staticPlayerY, this.field_147501_a.staticPlayerZ);
        if (f > i) {
            return;
        }
        FontRenderer fontrenderer = this.field_147501_a.getFontRenderer();
        float entrot = Minecraft.getMinecraft().thePlayer.rotationYaw;
        float entrotp = Minecraft.getMinecraft().thePlayer.rotationPitch;
        float f1 = 1.6F;
        float f2 = 0.01666667F * f1;

        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-1*entrot, 0, 1, 0);
        GL11.glRotatef(entrotp, 1, 0, 0);
        GL11.glScalef(0.3f, 0.3f, 0.3f);
        //GL11.glRotatef(-this.tileEntityRenderer.playerYaw, 0.0F, 1.0F, 0.0F);
        //GL11.glRotatef(this.tileEntityRenderer.playerPitch, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-f2, -f2, f2);
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        Tessellator tessellator = Tessellator.instance;
        byte byte0 = 0;
        GL11.glDisable(3553);
        tessellator.disableColor();
        tessellator.startDrawingQuads();
        int j = fontrenderer.getStringWidth(s) / 2;
        tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.35F);
        tessellator.addVertex(-j - 1, -1 + byte0, 0.0D);
        tessellator.addVertex(-j - 1, 8 + byte0, 0.0D);
        tessellator.addVertex(j + 1, 8 + byte0, 0.0D);
        tessellator.addVertex(j + 1, -1 + byte0, 0.0D);
        tessellator.draw();
        //tessellator.func_78381_a();
        GL11.glEnable(3553);
        fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, 553648127);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, -1);
        GL11.glEnable(2896);
        GL11.glDisable(3042);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }
}
