package dev.tonimatas.fastregions.mixins;

import dev.tonimatas.fastregions.region.RegionEvents;
import dev.tonimatas.fastregions.region.RegionFlag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Ravager.class)
public class RavagerMixin {
    @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;destroyBlock(Lnet/minecraft/core/BlockPos;ZLnet/minecraft/world/entity/Entity;)Z"))
    private boolean fastregions$ravagerGriefFlag(Level instance, BlockPos blockPos, boolean b, Entity entity) {
        if (!RegionEvents.cancelGenericEvent(instance, blockPos, RegionFlag.RAVAGER_GRIEF)) {
            return instance.destroyBlock(blockPos, true, entity);
        }

        return false;
    }
}
