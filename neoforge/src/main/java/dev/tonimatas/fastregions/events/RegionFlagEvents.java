package dev.tonimatas.fastregions.events;

import dev.tonimatas.fastregions.FastRegionsCommon;
import dev.tonimatas.fastregions.region.RegionEvents;
import dev.tonimatas.fastregions.region.RegionFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = FastRegionsCommon.MOD_ID)
public class RegionFlagEvents {
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        event.setCanceled(RegionEvents.cancelBlockEvent(event.getPlayer(), event.getPlayer().level(), event.getPos(), RegionFlag.BREAK_BLOCKS));
    }
}
