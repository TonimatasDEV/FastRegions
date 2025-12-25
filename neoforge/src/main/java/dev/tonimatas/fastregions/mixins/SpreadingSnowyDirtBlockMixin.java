package dev.tonimatas.fastregions.mixins;

import dev.tonimatas.fastregions.region.RegionEvents;
import dev.tonimatas.fastregions.region.RegionFlag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.MyceliumBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpreadingSnowyDirtBlock.class)
public class SpreadingSnowyDirtBlockMixin {
    @Inject(method = "canPropagate", at = @At(value = "HEAD"), cancellable = true)
    private static void fastregions$spreadFlags(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        Block block = state.getBlock();
        
        if (block instanceof GrassBlock) {
            if (RegionEvents.cancelGenericEvent((Level) level, pos, RegionFlag.GRASS_SPREAD)) {
                cir.setReturnValue(false);
            }
        } else if (block instanceof MyceliumBlock) {
            if (RegionEvents.cancelGenericEvent((Level) level, pos, RegionFlag.MYCELIUM_SPREAD)) {
                cir.setReturnValue(false);
            }
        }
    }
}
