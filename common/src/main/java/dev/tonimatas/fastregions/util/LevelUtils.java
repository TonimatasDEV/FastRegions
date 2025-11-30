package dev.tonimatas.fastregions.util;

import net.minecraft.world.level.Level;

public class LevelUtils {
    public static String getName(Level level) {
        return level.dimension().location().toString();
    }
}
