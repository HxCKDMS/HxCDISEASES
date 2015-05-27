package com.wiggle1000.HxC.Diseases;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class Utils {

    public static void playSound(String soundName, float xCoord, float yCoord, float zCoord, float volume, float pitch)
    {
        FMLClientHandler.instance().getClient().getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(soundName), volume, pitch, xCoord, yCoord, zCoord));
    }
}
