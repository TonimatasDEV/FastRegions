package dev.tonimatas.fastregions.mixins;

import dev.tonimatas.fastregions.region.RegionEvents;
import dev.tonimatas.fastregions.region.RegionFlag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.decoration.ItemFrame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFrame.class)
public class ItemFrameMixin {
    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void fastregions$destroyItemFrameFlag(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ItemFrame itemFrame = ((ItemFrame) (Object) this);

        if (RegionEvents.cancelGenericEvent(source.getEntity(), itemFrame.level(), itemFrame.blockPosition(), RegionFlag.DESTROY_ITEM_FRAME)) {
            cir.setReturnValue(false);
        }
    }
}
