package dev.tonimatas.fastregions.mixins;

import dev.tonimatas.fastregions.region.RegionEvents;
import dev.tonimatas.fastregions.region.RegionFlag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VehicleEntity.class)
public class VehicleEntityMixin {
    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void fastregions$vehicleDestroyFlag(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        VehicleEntity vehicle = ((VehicleEntity) (Object) this);
        
        if (RegionEvents.cancelGenericEvent(source.getEntity(), vehicle.level(), vehicle.blockPosition(), RegionFlag.VEHICLE_DESTROY)) {
            cir.setReturnValue(false);
        }
    }
}
