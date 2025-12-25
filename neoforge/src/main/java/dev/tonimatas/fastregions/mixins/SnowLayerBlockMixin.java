package dev.tonimatas.fastregions.mixins;

import dev.tonimatas.fastregions.region.RegionEvents;
import dev.tonimatas.fastregions.region.RegionFlag;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowLayerBlock.class)
public class SnowLayerBlockMixin {
    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void fastregions$snowMeltFlag(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (RegionEvents.cancelGenericEvent(level, pos, RegionFlag.SNOW_MELT)) {
            ci.cancel();
        }
    }
}
