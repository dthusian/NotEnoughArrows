package dev.wateralt.mc.notenougharrows.arrows;

import dev.wateralt.mc.notenougharrows.Arrow;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class LevitationArrow extends Arrow {

    @Override
    public String name() {
        return "Arrow of Levitation";
    }

    @Override
    public String lore() {
        return "{\"text\":\"Levitation (00:10)\",\"color\":\"blue\",\"italic\":false}";
    }

    @Override
    public int color() {
        return 0xededed;
    }

    @Override
    public void onEntityHit(ArrowEntity me, LivingEntity entity) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 10*20));
    }

    @Override
    public void onBlockHit(ArrowEntity me) { }

    @Override
    public FletchingRecipe fletchingRecipe() {
        return new FletchingRecipe(new ItemStack(Items.SHULKER_SHELL), 1);
    }
}
