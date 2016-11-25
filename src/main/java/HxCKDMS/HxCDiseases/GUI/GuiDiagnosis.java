package HxCKDMS.HxCDiseases.GUI;

import HxCKDMS.HxCDiseases.HxCDiseases;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiDiagnosis extends GuiScreen{

    private int xSize = 256, ySize = 256;
    private int xStart = 0, yStart = 0;
    private String text = "";

    public GuiDiagnosis(String text)
    {
        this.text = text;
    }

    @Override
    public void initGui() {
        xStart = (width - xSize) / 2;
        yStart = (height - ySize) / 2;
        buttonList.clear();
        buttonList.add(new GuiButton(0, xStart + (xSize/2) - 30, yStart + ySize-30, 60, 20, "OK."));
        super.initGui();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.getTextureManager().bindTexture(new ResourceLocation(HxCDiseases.MODID + ":gui/DiagnosisBG.png"));
        drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
        fontRendererObj.drawString("Diagnosis", xStart + (xSize / 2) - (fontRendererObj.getStringWidth("Diagnosis") / 2), yStart + 20, Color.black.getRGB());
        String[] lines = text.split("\n");
        GL11.glPushMatrix();
        GL11.glScalef(0.8f, 0.8f, 0.8f);
        for(int y = 0; y < lines.length; y++) {
            fontRendererObj.drawString(lines[y], (int)((xStart + 10) * 1.25f), (int)((yStart + 40 + (y * 10)) * 1.25f), Color.black.getRGB());
        }
        GL11.glPopMatrix();
        super.drawScreen(i, j, f);
    }
    @Override
    public void actionPerformed(GuiButton button) {
        mc.thePlayer.closeScreenNoPacket();
        super.actionPerformed(button);
    }
}
