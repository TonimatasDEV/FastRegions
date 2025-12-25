package dev.tonimatas.fastregions.mixins;

import dev.tonimatas.fastregions.region.RegionEvents;
import dev.tonimatas.fastregions.region.RegionFlag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    @Inject(method = "addEntity", at = @At("HEAD"), cancellable = true)
    private void fastregions$lightningFlag(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof LightningBolt lightningBolt) {
            if (RegionEvents.cancelGenericEvent((ServerLevel) (Object) this, lightningBolt.blockPosition(), RegionFlag.LIGHTNING_BOLTS)) {
                cir.setReturnValue(false);
            }
        }
    }
}
