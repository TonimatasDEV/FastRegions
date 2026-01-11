package dev.tonimatas.fastregions.events;

import dev.tonimatas.fastregions.FastRegions;
import dev.tonimatas.fastregions.region.CallFlag;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.PlayerRideable;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.BlockGrowFeatureEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.neoforge.event.level.block.CropGrowEvent;

@EventBusSubscriber(modid = FastRegions.MOD_ID)
public class RegionFlagEvents {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        event.setCanceled(CallFlag.blockBreak(event.getPlayer(), event.getPos()));
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        event.setCanceled(CallFlag.blockPlace(event.getEntity(), event.getPos()));
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onMultiPlaceBlock(BlockEvent.EntityMultiPlaceEvent event) {
        event.setCanceled(CallFlag.blockPlace(event.getEntity(), event.getPos()));
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockInteract(PlayerInteractEvent.RightClickBlock event) {
        boolean cancel = CallFlag.blockInteract(event.getEntity(), event.getPos());
        
        if (cancel) {
            event.setUseBlock(TriState.FALSE);
            event.setCancellationResult(InteractionResult.FAIL);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockInteract(PlayerInteractEvent.EntityInteract event) {
        switch (event.getTarget()) {
            case VehicleEntity ignored -> 
                    event.setCanceled(CallFlag.vehicleInteract(event.getEntity(), event.getPos()));
            case PlayerRideable ignored ->
                event.setCanceled(CallFlag.rideEntity(event.getEntity(), event.getPos()));
            case ItemFrame ignored ->
                    event.setCanceled(CallFlag.rotateItemFrame(event.getEntity(), event.getPos()));
            default -> {
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getSource().is(DamageTypes.WITHER) || event.getSource().is(DamageTypes.WITHER_SKULL)) {
            event.setCanceled(CallFlag.witherDamage(event.getEntity()));
        }
        
        if (!event.isCanceled() && event.getSource().is(DamageTypes.FIREWORKS)) {
            event.setCanceled(CallFlag.fireworkDamage(event.getEntity()));
        }
        
        if (event.getEntity() instanceof Player player) {
            if (!event.isCanceled() && event.getSource().getEntity() instanceof Player attacker) {
                event.setCanceled(CallFlag.pvp(attacker, player));
            }

            if (!event.isCanceled() && event.getSource().is(DamageTypes.FALL)) {
                event.setCanceled(CallFlag.fallDamage(player));
            }

            if (!event.isCanceled()) {
                event.setCanceled(CallFlag.invinciblePlayers(player));
            }
        }

        if (!event.isCanceled() && event.getSource().getEntity() != null) {
            event.setCanceled(CallFlag.entityDamage(event.getSource().getEntity(), event.getEntity()));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
        event.getAffectedBlocks().removeIf(toBlow -> CallFlag.explosion(event.getLevel(), toBlow));
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onCropGrowth(CropGrowEvent.Pre event) {
        if (CallFlag.cropGrowth(event.getLevel(), event.getPos())) {
            event.setResult(CropGrowEvent.Pre.Result.DO_NOT_GROW);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPickup(ItemEntityPickupEvent.Pre event) {
        if (CallFlag.itemPickup(event.getItemEntity())) {
            event.setCanPickup(TriState.FALSE);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onFeatureGrow(BlockGrowFeatureEvent event) {
        if (event.getFeature() == null) return;
        
        if (event.getFeature().is(TreeFeatures.HUGE_BROWN_MUSHROOM) || event.getFeature().is(TreeFeatures.HUGE_RED_MUSHROOM)) {
            event.setCanceled(CallFlag.mushroomGrowth(event.getLevel(), event.getPos()));
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onUseItem(LivingEntityUseItemEvent.Start event) {
        event.setCanceled(CallFlag.useItems(event.getEntity()));
    }
}
