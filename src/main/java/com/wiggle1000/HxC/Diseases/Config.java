package com.wiggle1000.HxC.Diseases;

import net.minecraftforge.common.config.Configuration;

public class Config {
    public static boolean lookatme;

    public Config(Configuration config) {
        config.load();
        lookatme = config.getBoolean("LookEbola", "features", false, "Look at me. You have Ebola now.");//because if you look at mandrake's mom you get ebola!
        if (config.hasChanged()) {
            config.save();
        }
    }
}