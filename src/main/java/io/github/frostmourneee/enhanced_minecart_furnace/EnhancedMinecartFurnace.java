package io.github.frostmourneee.enhanced_minecart_furnace;

import net.minecraft.core.BlockPos;
import net.minecraftforge.fml.common.Mod;

@Mod(EnhancedMinecartFurnace.MODID)
public class EnhancedMinecartFurnace
{
    public static final String MODID = "enhanced_minecart_furnace";

    public static BlockPos neighbourPosWODiagonal(BlockPos pos, int num) {
        return switch (num) {
            case 0 -> pos.above();
            case 1 -> pos.north();
            case 2 -> pos.east();
            case 3 -> pos.south();
            case 4 -> pos.west();
            case 5 -> pos.below();
            default -> pos;
        };
    }

    public static void customPrint(Object... str) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Object s : str) {
            stringBuilder.append(s);
            stringBuilder.append(" ");
        }
        System.out.println(stringBuilder);
    }
}
