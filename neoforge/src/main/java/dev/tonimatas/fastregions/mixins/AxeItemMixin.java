package dev.tonimatas.fastregions.mixins;

import dev.tonimatas.fastregions.region.CallFlag;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AxeItem.class)
public class AxeItemMixin {
    @Redirect(method = "evaluateNewBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getToolModifiedState(Lnet/minecraft/world/item/context/UseOnContext;Lnet/neoforged/neoforge/common/ItemAbility;Z)Lnet/minecraft/world/level/block/state/BlockState;", ordinal = 1))
    private BlockState fastregions$copperScrape(BlockState instance, UseOnContext useOnContext, ItemAbility itemAbility, boolean b) {
        return CallFlag.copperModification(useOnContext.getLevel(), useOnContext.getClickedPos()) ? instance : instance.getToolModifiedState(useOnContext, itemAbility, false);
    }

    @Redirect(method = "evaluateNewBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getToolModifiedState(Lnet/minecraft/world/item/context/UseOnContext;Lnet/neoforged/neoforge/common/ItemAbility;Z)Lnet/minecraft/world/level/block/state/BlockState;", ordinal = 2))
    private BlockState fastregions$copperWaxOff(BlockState instance, UseOnContext useOnContext, ItemAbility itemAbility, boolean b) {
        return CallFlag.copperModification(useOnContext.getLevel(), useOnContext.getClickedPos()) ? instance : instance.getToolModifiedState(useOnContext, itemAbility, false);
    }
}
