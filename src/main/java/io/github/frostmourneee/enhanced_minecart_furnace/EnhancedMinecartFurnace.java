package io.github.frostmourneee.enhanced_minecart_furnace;

import net.minecraftforge.fml.common.Mod;

@Mod(EnhancedMinecartFurnace.MODID)
public class EnhancedMinecartFurnace
{
    public static final String MODID = "enhanced_minecart_furnace";

    public static void customPrint(Object... str) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Object s : str) {
            stringBuilder.append(s);
            stringBuilder.append(" ");
        }
        System.out.println(stringBuilder);
    }
}
