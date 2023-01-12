package io.github.frostmourneee.enhanced_minecart_furnace.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.MinecartFurnace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.frostmourneee.enhanced_minecart_furnace.EnhancedMinecartFurnace.customPrint;

@Mixin( MinecartFurnace.class )
public abstract class MinecartFurnaceMixin {

    @Inject(method = "interact", at = @At(value = "TAIL"))
    private void onInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        MinecartFurnace cart = (MinecartFurnace) (Object) this;

        if (cart.fuel > 0 && player.isPassenger()) {
            cart.xPush *= -1;
            cart.zPush *= -1;
        }
    }
}
