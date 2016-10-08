package HxCKDMS.HxCDiseases.Villager;

import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraft.world.gen.structure.StructureVillagePieces;

import java.util.List;
import java.util.Random;

public class VillagerDoctorHandler implements VillagerRegistry.IVillageCreationHandler {

    @Override
    public StructureVillagePieces.PieceWeight getVillagePieceWeight (Random random, int i) {
        return new StructureVillagePieces.PieceWeight(ComponentDoctor.class, 30, i + random.nextInt(4));
    }

    @Override
    public Class<?> getComponentClass () {
        return ComponentDoctor.class;
    }

    @Override
    public Object buildComponent (StructureVillagePieces.PieceWeight villagePiece, StructureVillagePieces.Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5) {
        return ComponentDoctor.buildComponent(startPiece, pieces, random, p1, p2, p3, p4, p5);
    }
}
