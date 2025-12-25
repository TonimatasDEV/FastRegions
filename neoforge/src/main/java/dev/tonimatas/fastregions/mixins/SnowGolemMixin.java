package dev.tonimatas.fastregions.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import dev.tonimatas.fastregions.region.RegionEvents;
import dev.tonimatas.fastregions.region.RegionFlag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.SnowGolem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowGolem.class)
public class SnowGolemMixin {
    @Inject(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"), cancellable = true)
    private void fastregions$snowgolemTrailsFlag(CallbackInfo ci, @Local(name = "blockpos") BlockPos blockpos) {
        SnowGolem snowGolem = (SnowGolem) (Object) this;
        
        if (RegionEvents.cancelGenericEvent(snowGolem.level(), blockpos, RegionFlag.SNOWGOLEM_TRAILS)) {
            ci.cancel();
        }
    }
}
