package dev.tonimatas.fastregions.mixins;

import dev.tonimatas.fastregions.region.CallFlag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderpearlItem.class)
public class EnderpearlItemMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void fastregions$useEnderPearl(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (CallFlag.useEnderPearl(player)) {
            cir.setReturnValue(InteractionResultHolder.fail(player.getItemInHand(hand)));
        }
    }
}
