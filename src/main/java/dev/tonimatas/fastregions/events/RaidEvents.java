package dev.tonimatas.fastregions.events;

import dev.tonimatas.fastregions.flag.Flag;
import dev.tonimatas.fastregions.util.EventUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.raid.RaidTriggerEvent;

public class RaidEvents implements Listener {
    @EventHandler
    private void onRaidTriggered(RaidTriggerEvent event) {
        if (EventUtils.isRegionNotContainFlag(event.getPlayer(), Flag.raid, event.getWorld(), event.getRaid().getLocation())) event.setCancelled(true);
    }
}
