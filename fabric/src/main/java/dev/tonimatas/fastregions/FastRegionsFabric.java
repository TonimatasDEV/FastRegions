package dev.tonimatas.fastregions;

import net.fabricmc.api.ModInitializer;

public class FastRegionsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        FastRegions.init();
    }
}
