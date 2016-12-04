package HxCKDMS.HxCDiseases.entity;

import HxCKDMS.HxCDiseases.DiseaseConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderVomit extends Render {
    final float size = 0.6f;
    @Override
    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
        float entrot = Minecraft.getMinecraft().thePlayer.getRotationYawHead();
        float entrotp = Minecraft.getMinecraft().thePlayer.rotationPitch;
        float f1 = 1.6F;
        float f2 = 0.01666667F * f1;
        EntityVomitFX ent = (EntityVomitFX)entity;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        if(entity.onGround){
            GL11.glRotatef(90, 1, 0, 0);
            GL11.glTranslatef(0,-0.5f,0);
        }else{
            GL11.glRotatef(-1*entrot, 0, 1, 0);
            GL11.glRotatef(entrotp, 1, 0, 0);
        }
        GL11.glScalef(0.3f, 0.3f, 0.3f);
        GL11.glScalef(ent.ticksExisted/(float)DiseaseConfig.vomitParticleLife, ent.ticksExisted/(float)DiseaseConfig.vomitParticleLife, ent.ticksExisted/(float)DiseaseConfig.vomitParticleLife);
        //GL11.glRotatef(-this.tileEntityRenderer.playerYaw, 0.0F, 1.0F, 0.0F);
        //GL11.glRotatef(this.tileEntityRenderer.playerPitch, 1.0F, 0.0F, 0.0F);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        Minecraft.getMinecraft().renderEngine.bindTexture(getEntityTexture(entity));
        //tessellator.setColorRGBA_F(ent.red,ent.green,ent.blue,1f);
        tessellator.addVertexWithUV(-size, -size, 0.0D, 0f, 0f);
        tessellator.addVertexWithUV(-size, size, 0.0D, 0f, 1f);
        tessellator.addVertexWithUV(size, size, 0.0D, 1f, 1f);
        tessellator.addVertexWithUV(size, -size, 0.0D, 1f, 0f);
        tessellator.draw();
        //tessellator.func_78381_a();
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return new ResourceLocation(((EntityVomitFX)entity).textureName);
    }
}
