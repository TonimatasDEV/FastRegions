package dev.tonimatas.fastregions.mixins;

import dev.tonimatas.fastregions.region.RegionEvents;
import dev.tonimatas.fastregions.region.RegionFlag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class BiomeMixin {
    @Inject(method = "shouldSnow", at = @At("HEAD"), cancellable = true)
    private void fastregions$snowFallFlag(LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (RegionEvents.cancelGenericEvent((Level) level, pos, RegionFlag.SNOW_FALL)) {
            cir.setReturnValue(false);
        }
    }
}
