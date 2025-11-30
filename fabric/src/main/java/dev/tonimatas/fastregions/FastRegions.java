package dev.tonimatas.fastregions;

import net.fabricmc.api.ModInitializer;

public class FastRegions implements ModInitializer {

    @Override
    public void onInitialize() {
        FastRegionsCommon.init();
    }
}
