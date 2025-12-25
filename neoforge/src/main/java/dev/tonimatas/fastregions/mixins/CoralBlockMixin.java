package dev.tonimatas.fastregions.mixins;

import dev.tonimatas.fastregions.region.RegionEvents;
import dev.tonimatas.fastregions.region.RegionFlag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CoralBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CoralBlock.class)
public class CoralBlockMixin {
    @Inject(method = "scanForWater", at = @At("HEAD"), cancellable = true)
    private void fastregions$coralDieFlag(BlockGetter level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (RegionEvents.cancelGenericEvent(level, pos, RegionFlag.CORAL_DIE)) {
            cir.setReturnValue(true);
        }
    }
}
