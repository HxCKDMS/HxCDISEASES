package HxCKDMS.HxCDiseases;

import net.minecraftforge.common.config.Configuration;

public class Config {
    public static boolean lookatme;
    public static int uberVomit;
    public static int vomitChance;

    public Config(Configuration config) {
        config.load();
        lookatme = config.getBoolean("LookEbola", "features", false, "Look at me. Look at me. You have Ebola now.");
        uberVomit = config.getInt("VomitParticleAmount", "features", 1, 0, 20, "Enabling this may cause lag - Enable at your own risk");
        vomitChance = config.getInt("VomitChance", "features", 1000, 0, 2000, "Vomit Chance");
            config.save();
        }
    }
}
