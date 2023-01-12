package io.github.frostmourneee.enhanced_minecart_furnace.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin( AbstractMinecart.class )
public abstract class AbstractMinecartMixin {

    @Redirect(method = "getMaxSpeedWithRail", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/BaseRailBlock;getRailMaxSpeed(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/vehicle/AbstractMinecart;)F"))
    private float onGetMaxSpeedWithRail(BaseRailBlock instance, BlockState blockState, Level level, BlockPos blockPos, AbstractMinecart abstractMinecart) {
        return abstractMinecart.isInWater() ? 0.2f : 0.4f;
    }
}


















