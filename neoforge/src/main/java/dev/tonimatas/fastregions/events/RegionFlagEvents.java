package dev.tonimatas.fastregions.events;

import dev.tonimatas.fastregions.FastRegions;
import dev.tonimatas.fastregions.region.RegionEvents;
import dev.tonimatas.fastregions.region.RegionFlag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;

@EventBusSubscriber(modid = FastRegions.MOD_ID)
public class RegionFlagEvents {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        event.setCanceled(RegionEvents.cancelBlockEvent(event.getPlayer(), event.getPlayer().level(), event.getPos(), RegionFlag.BLOCK_BREAK));
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        event.setCanceled(RegionEvents.cancelBlockEvent(event.getEntity(), (Level) event.getLevel(), event.getPos(), RegionFlag.BLOCK_PLACE));
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onMultiPlaceBlock(BlockEvent.EntityMultiPlaceEvent event) {
        event.setCanceled(RegionEvents.cancelBlockEvent(event.getEntity(), (Level) event.getLevel(), event.getPos(), RegionFlag.BLOCK_PLACE));
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockInteract(PlayerInteractEvent.RightClickBlock event) {
        boolean cancel = RegionEvents.cancelBlockEvent(event.getEntity(), event.getLevel(), event.getPos(), RegionFlag.BLOCK_INTERACT);
        
        if (cancel) {
            event.setUseBlock(TriState.FALSE);
            event.setCancellationResult(InteractionResult.FAIL);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (event.getSource().getEntity() instanceof Player attacker) {
                event.setCanceled(RegionEvents.cancelEntityToEntityEvent(attacker, player, RegionFlag.PVP));
            }

            if (!event.isCanceled()) {
                event.setCanceled(RegionEvents.cancelEntityEvent(player, player.level(), player.getOnPos(), RegionFlag.INVINCIBLE_PLAYERS));
            }
        }

        if (!event.isCanceled() && event.getSource().getEntity() != null) {
            event.setCanceled(RegionEvents.cancelEntityToEntityEvent(event.getSource().getEntity(), event.getEntity(), RegionFlag.ENTITY_DAMAGE));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
        event.getAffectedBlocks().removeIf(toBlow -> RegionEvents.cancelBlockEvent(null, event.getLevel(), toBlow, RegionFlag.EXPLOSION));
    }
}
