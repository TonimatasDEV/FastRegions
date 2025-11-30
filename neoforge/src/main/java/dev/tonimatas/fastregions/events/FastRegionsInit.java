package dev.tonimatas.fastregions.events;

import dev.tonimatas.fastregions.FastRegionsCommon;
import dev.tonimatas.fastregions.region.RegionManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

@EventBusSubscriber(modid = FastRegionsCommon.MOD_ID)
public class FastRegionsInit {
    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        RegionManager.loadRegions(event.getServer());
    }

}
