package dev.tonimatas.fastregions.events;

import dev.tonimatas.fastregions.FastRegionsCommon;
import dev.tonimatas.fastregions.commands.RegionCommand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = FastRegionsCommon.MOD_ID)
public class FastRegionsCommands {
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new RegionCommand(event.getDispatcher());
    }
}
