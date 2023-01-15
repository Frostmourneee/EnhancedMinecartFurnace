package io.github.frostmourneee.enhanced_minecart_furnace.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.vehicle.MinecartFurnace;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

import static net.minecraft.world.level.block.HopperBlock.FACING;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {
    private static final int FUEL_ADD_BY_HOPPER = 3600;

    @Inject(method = "pushItemsTick", at = @At("HEAD"))
    private static void onPushItemsTick(Level level, BlockPos pos, BlockState blockState, HopperBlockEntity hopperBE, CallbackInfo ci) {
        AABB verticalAABB = hopperBE.getRenderBoundingBox().move(0.D, -0.5D, 0.0D).inflate(-0.25D, 0.4D, -0.25D);
        AABB xAlignedAABB = hopperBE.getRenderBoundingBox().inflate(0.25D, -0.1D, -0.25D);
        AABB zAlignedAABB = hopperBE.getRenderBoundingBox().inflate(-0.25D, -0.1D, 0.25D);

        ArrayList<MinecartFurnace> carts = new ArrayList<>(level.getEntitiesOfClass(MinecartFurnace.class, verticalAABB));
        carts.addAll(level.getEntitiesOfClass(MinecartFurnace.class, xAlignedAABB));
        carts.addAll(level.getEntitiesOfClass(MinecartFurnace.class, zAlignedAABB));
        carts.removeIf(cart -> !level.getBlockState(cart.blockPosition()).is(BlockTags.RAILS));
        carts.removeIf(cart -> cart.fuel + FUEL_ADD_BY_HOPPER >= 32000);

        if (carts.isEmpty() || hopperBE.cooldownTime > 0) return;
        boolean hasFuelItem = false;
        int cartsFuelReceived = 0;
        int oldCartsSize = carts.size();
        for (int slot = 0; slot < hopperBE.getContainerSize() && cartsFuelReceived < oldCartsSize; slot++) {
            for (ItemStack itemStack : MinecartFurnace.INGREDIENT.getItems()) {
                if (!itemStack.is(hopperBE.getItem(slot).getItem())) continue;

                hasFuelItem = true;
                ArrayList<MinecartFurnace> notFueledCarts = new ArrayList<>(carts);
                for (MinecartFurnace cart : notFueledCarts) {
                    if (hopperBE.getItem(slot).getCount() == 0) break;

                    cart.fuel += FUEL_ADD_BY_HOPPER;
                    cartsFuelReceived++;
                    if (carts.size() > 1) carts.remove(cart);
                    hopperBE.getItem(slot).shrink(1);
                    switch (Direction.getNearest(cart.getDeltaMovement().x, cart.getDeltaMovement().y, cart.getDeltaMovement().z)) {
                        case NORTH -> {cart.xPush = 0.0D; cart.zPush = -1.0D;}
                        case EAST -> {cart.xPush = 1.0D; cart.zPush = 0.0D;}
                        case SOUTH -> {cart.xPush = 0.0D; cart.zPush = 1.0D;}
                        case WEST -> {cart.xPush = -1.0D; cart.zPush = 0.0D;}
                    }
                }
            }
        }
        if (!hasFuelItem) return;
        hopperBE.setCooldown(10);

        Direction hopperRotateDir = Direction.getNearest
                (carts.get(0).getX()-hopperBE.getLevelX(), carts.get(0).getY()-hopperBE.getLevelY(), carts.get(0).getZ()-hopperBE.getLevelZ());
        if (hopperRotateDir.equals(hopperBE.getBlockState().getValue(FACING))) return;
        level.setBlock(hopperBE.getBlockPos(), hopperBE.getBlockState().setValue
                      (FACING, hopperRotateDir), 2);
    }
}
