package com.wiggle1000.HxC.Diseases;

import net.minecraftforge.common.config.Configuration;

public class Config {
    public static boolean lookatme;
    public static int uberVomit;
    public static int vomitChance;

    public Config(Configuration config) {
        config.load();
        lookatme = config.getBoolean("LookEbola", "features", false, "Look at me. You have Ebola now.");//because if you look at mandrake's mom you get ebola!
        uberVomit = config.getInt("VomitParticleAmount", "features", 1, 0, 20, "I heard you liked vomit! SO NOW YOU CAN VOMIT WHILE YOU VOMIT.");//if you enable this, lag is gonna happen!
        vomitChance = config.getInt("VomitChance", "features", 1000, 0, 2000, "I heard you liked vomit! SO NOW YOU CAN VOMIT AS MUCH AS YOU WANT.");//if you enable this, lag is gonna happen!
        if (config.hasChanged()) {
            config.save();
        }
    }
}