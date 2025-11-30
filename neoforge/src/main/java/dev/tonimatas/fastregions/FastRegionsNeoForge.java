package dev.tonimatas.fastregions;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(FastRegions.MOD_ID)
public class FastRegionsNeoForge {
    @SuppressWarnings("unused")
    public FastRegionsNeoForge(IEventBus eventBus) {
        FastRegions.init();
    }
}
