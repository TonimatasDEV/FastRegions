package dev.tonimatas.fastregions.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import dev.tonimatas.fastregions.region.CallFlag;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecartItem.class)
public class MinecartItemMixin {
    @Inject(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"), cancellable = true)
    private void onUseOn(UseOnContext context, CallbackInfoReturnable<Boolean> cir, @Local(name = "abstractminecart") AbstractMinecart abstractminecart) {
        if (CallFlag.vehiclePlace(context.getPlayer(), abstractminecart)) {
            cir.setReturnValue(false);

            if (context.getPlayer() != null) {
                context.getPlayer().inventoryMenu.sendAllDataToRemote();
            }
        }
    }
}
