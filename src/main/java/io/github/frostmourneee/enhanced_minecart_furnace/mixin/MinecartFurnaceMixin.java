package io.github.frostmourneee.enhanced_minecart_furnace.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.MinecartFurnace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.frostmourneee.enhanced_minecart_furnace.EnhancedMinecartFurnace.customPrint;


@Mixin( MinecartFurnace.class )
public abstract class MinecartFurnaceMixin {

    private final MinecartFurnace cart = (MinecartFurnace) (Object) this;

    @Inject(method = "interact", at = @At("TAIL"))
    private void onInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        customPrint(cart.fuel);
        if (cart.fuel > 0 && player.isPassenger()) {
            cart.xPush *= -1;
            cart.zPush *= -1;
        }
    }

    @ModifyConstant(method = "getMaxCartSpeedOnRail", constant = @Constant(floatValue = 0.2f))
    private float onGetMaxCartSpeedOnRail(float constant) {
        return 0.4f;
    }
}
