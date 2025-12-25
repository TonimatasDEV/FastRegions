package dev.tonimatas.fastregions.mixins;

import dev.tonimatas.fastregions.region.RegionEvents;
import dev.tonimatas.fastregions.region.RegionFlag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FrostedIceBlock.class)
public class FrostedIceBlockMixin {
    @Inject(method = "slightlyMelt", at = @At("HEAD"), cancellable = true)
    private void fastregions$frostedIceMeltFlag(BlockState state, Level level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (RegionEvents.cancelGenericEvent(level, pos, RegionFlag.FROSTED_ICE_MELT)) {
            cir.setReturnValue(true);
        }
    }
}
