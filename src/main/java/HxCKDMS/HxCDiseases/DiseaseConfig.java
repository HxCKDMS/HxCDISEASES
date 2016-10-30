package HxCKDMS.HxCDiseases;

import HxCKDMS.HxCCore.api.Configuration.Config;

@Config
public class DiseaseConfig {
    @Config.comment("Look at me! Look at me!! You have Ebola now. (Default = false)")
    public static boolean lookatme = false;
    @Config.comment("Play vomit sound when dropping an item? (Default = false)")
    public static boolean itemVomit = false;
    @Config.comment("Fart when crouching? (Default = true)")
    public static boolean farts = true;
    @Config.comment("Enabling this may cause lag - Enable at your own risk. (Default = 1)")
    public static int uberVomit = 1;
    @Config.comment("Vomit Chance (Default = 1000)")
    public static int vomitChance = 1000;

   /* public DiseaseConfig(Configuration config) {

        config.load();
        lookatme = config.getBoolean("LookEbola", "features", false, "Look at me. Look at me. You have Ebola now.");
        uberVomit = config.getInt("VomitParticleAmount", "features", 1, 0, 20, "Enabling this may cause lag - Enable at your own risk");
        vomitChance = config.getInt("VomitChance", "features", 1000, 0, 2000, "Vomit Chance");

        if(config.hasChanged()) config.save();
    }*/
}
