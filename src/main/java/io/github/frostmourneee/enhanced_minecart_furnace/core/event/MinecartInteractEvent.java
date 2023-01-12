package io.github.frostmourneee.enhanced_minecart_furnace.core.event;

import io.github.frostmourneee.enhanced_minecart_furnace.EnhancedMinecartFurnace;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.MinecartFurnace;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EnhancedMinecartFurnace.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MinecartInteractEvent {

    @SubscribeEvent
    public static void onMinecartInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Entity cart = event.getTarget();

        if (!(cart instanceof MinecartFurnace)) return;
        
    }
}
