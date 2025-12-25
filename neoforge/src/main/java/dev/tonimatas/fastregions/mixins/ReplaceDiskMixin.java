package dev.tonimatas.fastregions.mixins;

import dev.tonimatas.fastregions.region.RegionEvents;
import dev.tonimatas.fastregions.region.RegionFlag;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.enchantment.effects.ReplaceDisk;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ReplaceDisk.class)
public class ReplaceDiskMixin {
    @Redirect(method = "apply", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private boolean fastregions$formFrostedIceFlag(ServerLevel instance, BlockPos blockPos, BlockState blockState) {
        if (blockState.getBlock() instanceof FrostedIceBlock) {
            if (RegionEvents.cancelGenericEvent(instance, blockPos, RegionFlag.FROSTED_ICE_FORM)) {
                return false;
            }
        }
        
        return instance.setBlockAndUpdate(blockPos, blockState);
    }
}
