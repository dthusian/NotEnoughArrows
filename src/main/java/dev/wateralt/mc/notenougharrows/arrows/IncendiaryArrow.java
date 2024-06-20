package dev.wateralt.mc.notenougharrows.arrows;

import dev.wateralt.mc.notenougharrows.Arrow;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.HashMap;

public class IncendiaryArrow extends Arrow {
    @Override
    public String name() {
        return "Incendiary Arrow";
    }

    @Override
    public String lore() {
        return "Spews hot fluids wherever it hits";
    }

    @Override
    public int color() {
        return 0xa83632;
    }

    @Override
    public void onEntityHit(ArrowEntity me, LivingEntity entity) {
        onBlockHit(me);
    }

    @Override
    public void onBlockHit(ArrowEntity me) {
        BlockState state = me.getBlockStateAtPos();
        if(state.isOf(Blocks.AIR) || state.isOf(Blocks.CAVE_AIR) || state.isOf(Blocks.VOID_AIR)) {
            me.getWorld().setBlockState(me.getBlockPos(), Blocks.LAVA.getDefaultState());
        }
    }

    @Override
    public FletchingRecipe fletchingRecipe() {
        return new FletchingRecipe(new ItemStack(Items.LAVA_BUCKET), 1);
    }
}
