package io.github.frostmourneee.enhanced_minecart_furnace.mixin;

import net.minecraft.world.entity.vehicle.MinecartFurnace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.github.frostmourneee.enhanced_minecart_furnace.EnhancedMinecartFurnace.customPrint;

@Mixin( MinecartFurnace.class )
public abstract class MinecartFurnaceMixin {

    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void onTick(CallbackInfo info) {
        customPrint("haha");

    }
}
