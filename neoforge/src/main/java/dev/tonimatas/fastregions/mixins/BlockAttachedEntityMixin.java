package dev.tonimatas.fastregions.mixins;

import dev.tonimatas.fastregions.region.RegionEvents;
import dev.tonimatas.fastregions.region.RegionFlag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.decoration.BlockAttachedEntity;
import net.minecraft.world.entity.decoration.Painting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockAttachedEntity.class)
public class BlockAttachedEntityMixin {
    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void fastregions$destroyItemFrameFlag(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        BlockAttachedEntity blockAttachedEntity = ((BlockAttachedEntity) (Object) this);

        if (blockAttachedEntity instanceof Painting) {
            if (RegionEvents.cancelGenericEvent(source.getEntity(), blockAttachedEntity.level(), blockAttachedEntity.blockPosition(), RegionFlag.DESTROY_PAINTING)) {
                cir.setReturnValue(false);
            }
        }
    }
}
