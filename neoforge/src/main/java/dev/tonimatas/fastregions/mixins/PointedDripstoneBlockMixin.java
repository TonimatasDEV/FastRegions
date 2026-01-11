package dev.tonimatas.fastregions.mixins;

import dev.tonimatas.fastregions.region.CallFlag;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlockMixin {
    @Inject(method = "growStalactiteOrStalagmiteIfPossible", at = @At("HEAD"), cancellable = true)
    private static void fastregions$dripstoneGrowth(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (CallFlag.dripstoneGrowth(level, pos)) {
            ci.cancel();
        }
    }
}
