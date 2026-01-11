package dev.tonimatas.fastregions.region;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class CallFlag {
    public static boolean blockBreak(Entity entity, BlockPos pos) {
        return RegionEvents.cancelBlockEvent(entity, entity.level(), pos, RegionFlag.BLOCK_BREAK);
    }

    public static boolean blockPlace(Entity entity, BlockPos pos) {
        return RegionEvents.cancelBlockEvent(entity, entity.level(), pos, RegionFlag.BLOCK_PLACE);
    }

    public static boolean blockInteract(Entity entity, BlockPos pos) {
        return RegionEvents.cancelBlockEvent(entity, entity.level(), pos, RegionFlag.BLOCK_INTERACT);
    }

    public static boolean vehicleInteract(Entity entity, BlockPos pos) {
        return RegionEvents.cancelGenericEvent(entity, entity.level(), pos, RegionFlag.VEHICLE_INTERACT);
    }

    public static boolean rideEntity(Entity entity, BlockPos pos) {
        return RegionEvents.cancelGenericEvent(entity, entity.level(), pos, RegionFlag.RIDE_ENTITY);
    }

    public static boolean rotateItemFrame(Entity entity, BlockPos pos) {
        return RegionEvents.cancelGenericEvent(entity, entity.level(), pos, RegionFlag.ROTATE_ITEM_FRAME);
    }

    public static boolean witherDamage(Entity entity) {
        return RegionEvents.cancelEntityToEntityEvent(null, entity, RegionFlag.WITHER_DAMAGE);
    }

    public static boolean fireworkDamage(Entity entity) {
        return RegionEvents.cancelEntityToEntityEvent(null, entity, RegionFlag.FIREWORK_DAMAGE);
    }

    public static boolean pvp(Entity attacker, Entity receiver) {
        return RegionEvents.cancelEntityToEntityEvent(attacker, receiver, RegionFlag.PVP);
    }

    public static boolean invinciblePlayers(Player player) {
        return RegionEvents.cancelEntityEvent(player, player.level(), player.blockPosition(), RegionFlag.INVINCIBLE_PLAYERS);
    }

    public static boolean entityDamage(Entity attacker, Entity receiver) {
        return RegionEvents.cancelEntityToEntityEvent(attacker, receiver, RegionFlag.ENTITY_DAMAGE);
    }

    public static boolean explosion(Level level, BlockPos blockPos) {
        return RegionEvents.cancelBlockEvent(null, level, blockPos, RegionFlag.EXPLOSION);
    }

    public static boolean cropGrowth(LevelAccessor level, BlockPos blockPos) {
        return RegionEvents.cancelGenericEvent(level, blockPos, RegionFlag.CROP_GROWTH);
    }

    public static boolean itemPickup(ItemEntity itemEntity) {
        return RegionEvents.cancelGenericEvent(itemEntity.level(), itemEntity.blockPosition(), RegionFlag.CROP_GROWTH);
    }
    
    public static boolean mushroomGrowth(LevelAccessor level, BlockPos blockPos) {
        return RegionEvents.cancelGenericEvent(level, blockPos, RegionFlag.MUSHROOM_GROWTH);
    }

    public static boolean fallDamage(Player player) {
        return RegionEvents.cancelEntityEvent(player, player.level(), player.blockPosition(), RegionFlag.FALL_DAMAGE);
    }
    
    // TODO: Remaining flags
}
