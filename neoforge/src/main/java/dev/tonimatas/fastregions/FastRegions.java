package dev.tonimatas.fastregions;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(FastRegionsCommon.MOD_ID)
public class FastRegions {
    @SuppressWarnings("unused")
    public FastRegions(IEventBus eventBus) {
        FastRegionsCommon.init();
    }
}
