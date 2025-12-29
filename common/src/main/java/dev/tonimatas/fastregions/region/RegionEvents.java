package dev.tonimatas.fastregions.region;

import dev.tonimatas.fastregions.util.PermissionUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;

public class RegionEvents {
    public static boolean cancelGenericEvent(BlockGetter level, BlockPos pos, RegionFlag flag) {
        if (level instanceof ServerLevel serverLevel) {
            return cancelGenericEvent(null, serverLevel, pos, flag);
        }

        return false;
    }

    public static boolean cancelGenericEvent(LevelReader level, BlockPos pos, RegionFlag flag) {
        if (level instanceof ServerLevel serverLevel) {
            return cancelGenericEvent(null, serverLevel, pos, flag);
        }
        
        return false;
    }

    public static boolean cancelGenericEvent(Entity entity, Level level, BlockPos pos, RegionFlag flag) {
        if (level.isClientSide()) return false;

        Region result = RegionManager.getRegion(level, pos);

        if (result != null) {
            if (result.hasFlagWithAllowedList(flag, "")) {
                if (entity instanceof Player player) {
                    return !PermissionUtils.hasRegionBypass(player, result.getName());
                } else {
                    return true;
                }
            }
        }

        return false;
    }
    
    public static boolean cancelBlockEvent(Entity entity, Level level, BlockPos pos, RegionFlag flag) {
        if (level.isClientSide()) return false;

        Region result = RegionManager.getRegion(level, pos);
        Block block = level.getBlockState(pos).getBlock();
        String blockId = BuiltInRegistries.BLOCK.getKey(block).toString();

        if (result != null) {
            if (result.hasFlagWithAllowedList(flag, blockId)) {
                if (entity instanceof Player player) {
                    return !PermissionUtils.hasRegionBypass(player, result.getName());
                } else {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean cancelEntityToEntityEvent(Entity action, Entity receiver, RegionFlag flag) {
        Level level = receiver.level();
        if (level.isClientSide()) return false;

        Region result = RegionManager.getRegion(level, receiver.blockPosition());
        String entityId = BuiltInRegistries.ENTITY_TYPE.getKey(receiver.getType()).toString();

        if (result != null) {
            if (result.hasFlagWithAllowedList(flag, entityId)) {
                if (action instanceof Player player) {
                    return !PermissionUtils.hasRegionBypass(player, result.getName());
                } else {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean cancelEntityEvent(Player player, Level level, BlockPos pos, RegionFlag flag) {
        if (level.isClientSide()) return false;

        Region result = RegionManager.getRegion(level, pos);

        if (result != null) {
            if (result.has(flag)) {
                return !PermissionUtils.hasRegionBypass(player, result.getName());
            }
        }

        return false;
    }
}
