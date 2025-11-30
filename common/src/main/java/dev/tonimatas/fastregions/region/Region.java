package dev.tonimatas.fastregions.region;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.List;

public record Region(BoundingBox box, List<RegionFlag> flags) {
    public boolean contains(BlockPos pos) {
        return box.isInside(pos);
    }
    
    public boolean has(RegionFlag flag) {
        return flags.contains(flag);
    }
}
