package dev.tonimatas.fastregions.events;

import dev.tonimatas.fastregions.FastRegions;
import dev.tonimatas.fastregions.region.RegionManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

@EventBusSubscriber(modid = FastRegions.MOD_ID)
public class FastRegionsInit {
    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        RegionManager.loadRegions(event.getServer());
    }

}
