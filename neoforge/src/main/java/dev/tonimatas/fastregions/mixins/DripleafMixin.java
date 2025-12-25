package dev.tonimatas.fastregions.mixins;

import dev.tonimatas.fastregions.region.RegionEvents;
import dev.tonimatas.fastregions.region.RegionFlag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.BigDripleafBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BigDripleafBlock.class)
public class DripleafMixin {
    @Inject(method = "canEntityTilt", at = @At("HEAD"), cancellable = true)
    private static void fastregions$dripleafFlag(BlockPos pos, Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (RegionEvents.cancelGenericEvent(entity, entity.level(), pos, RegionFlag.TOUCH_DRIPLEAF)) {
            cir.setReturnValue(false);
        }
    }
}
