package dev.tonimatas.fastregions;

import dev.tonimatas.fastregions.region.RegionFlag;
import dev.tonimatas.fastregions.region.RegionManager;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = FastRegionsCommon.MOD_ID)
public class FastRegionsEvents {
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        event.setCanceled(RegionManager.cancelEvent(event.getPlayer(), event.getLevel(), event.getPos(), RegionFlag.CAN_BREAK_BLOCK));
    }
}
