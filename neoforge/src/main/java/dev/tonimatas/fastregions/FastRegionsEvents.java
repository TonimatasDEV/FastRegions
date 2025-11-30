package dev.tonimatas.fastregions;

import dev.tonimatas.fastregions.commands.RegionCommand;
import dev.tonimatas.fastregions.region.RegionFlag;
import dev.tonimatas.fastregions.region.RegionManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

@EventBusSubscriber(modid = FastRegionsCommon.MOD_ID)
public class FastRegionsEvents {
    @SubscribeEvent
    public static void onCommandsRegister(ServerStartedEvent event) {
        RegionManager.loadRegions(event.getServer());
    }
    
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new RegionCommand(event.getDispatcher());
    }
    
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        event.setCanceled(RegionManager.cancelEvent(event.getPlayer(), event.getPlayer().level(), event.getPos(), RegionFlag.CAN_BREAK_BLOCK));
    }
}
