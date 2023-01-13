package io.github.frostmourneee.enhanced_minecart_furnace.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.vehicle.MinecartFurnace;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Objects;

import static io.github.frostmourneee.enhanced_minecart_furnace.EnhancedMinecartFurnace.customPrint;
import static net.minecraft.world.level.block.HopperBlock.FACING;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {

    @Inject(method = "pushItemsTick", at = @At("HEAD"))
    private static void onPushItemsTick(Level level, BlockPos pos, BlockState blockState, HopperBlockEntity hopperBE, CallbackInfo ci) {
        AABB verticalAABB = hopperBE.getRenderBoundingBox().move(0.D, -0.5D, 0.0D).inflate(-0.25D, 0.4D, -0.25D);
        AABB xAlignedAABB = hopperBE.getRenderBoundingBox().inflate(0.25D, -0.1D, -0.25D);
        AABB zAlignedAABB = hopperBE.getRenderBoundingBox().inflate(-0.25D, -0.1D, 0.25D);

        ArrayList<MinecartFurnace> carts = new ArrayList<>(level.getEntitiesOfClass(MinecartFurnace.class, verticalAABB));
        carts.addAll(level.getEntitiesOfClass(MinecartFurnace.class, xAlignedAABB));
        carts.addAll(level.getEntitiesOfClass(MinecartFurnace.class, zAlignedAABB));
        carts.removeIf(cart -> !level.getBlockState(cart.blockPosition()).is(BlockTags.RAILS));

        if (carts.isEmpty()) return;

        level.setBlock(hopperBE.getBlockPos(), hopperBE.getBlockState().setValue
                      (FACING, Objects.requireNonNull(Direction.fromNormal(carts.get(0).blockPosition().subtract(hopperBE.getBlockPos())))), 2);

        /*
        for (int num = 0; num < 5; num++) {
            Optional<HopperBlockEntity> hopperBEContainer = cart.level.getBlockEntity(neighbourPosWODiagonal(cart.blockPosition(), num), BlockEntityType.HOPPER);
            if (hopperBEContainer.isEmpty()) continue;

            HopperBlockEntity hopperBE = hopperBEContainer.get();
            cart.level.setBlock(hopperBE.getBlockPos(), hopperBE.getBlockState().setValue(FACING, Direction.NORTH), 2);
        }*/
    }
}
