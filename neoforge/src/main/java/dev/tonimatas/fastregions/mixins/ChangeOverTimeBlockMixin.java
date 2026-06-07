package dev.tonimatas.fastregions.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import dev.tonimatas.fastregions.region.CallFlag;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(ChangeOverTimeBlock.class)
public interface ChangeOverTimeBlockMixin {
    @Redirect(method = "getNextState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/ChangeOverTimeBlock;getNext(Lnet/minecraft/world/level/block/state/BlockState;)Ljava/util/Optional;"))
    private static Optional<BlockState> fastregions$getNext(ChangeOverTimeBlock<?> instance, BlockState blockState, @Local(argsOnly = true) ServerLevel level, @Local(argsOnly = true) BlockPos blockPos) {
        if (instance instanceof WeatheringCopper && CallFlag.copperOxidation(level, blockPos)) {
            return Optional.empty();
        }
        return instance.getNext(blockState);
    }
}
