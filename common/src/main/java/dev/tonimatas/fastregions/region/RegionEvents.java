package dev.tonimatas.fastregions.region;

import dev.tonimatas.fastregions.util.PermissionUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class RegionEvents {
    public static boolean cancelBlockEvent(Player player, Level level, BlockPos pos, RegionFlag flag) {
        if (level.isClientSide()) return false;

        Region result = RegionManager.getRegion(level, pos);
        Block block = level.getBlockState(pos).getBlock();
        String blockId = BuiltInRegistries.BLOCK.getKey(block).toString();

        if (result != null) {
            if (result.hasFlagWithAllowedList(flag, blockId)) {
                if (player != null) {
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

        Region result = RegionManager.getRegion(level, receiver.getOnPos());
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
