package dev.tonimatas.fastregions.mixins;

import dev.tonimatas.fastregions.region.CallFlag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoneycombItem.class)
public class HoneycombItemMixin {
    @Inject(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/HoneycombItem;getWaxed(Lnet/minecraft/world/level/block/state/BlockState;)Ljava/util/Optional;"), cancellable = true)
    private static void fastregions$getWaxed(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if (CallFlag.copperModification(context.getLevel(), context.getClickedPos())) {
            cir.setReturnValue(InteractionResult.PASS);
        }
    }
}
