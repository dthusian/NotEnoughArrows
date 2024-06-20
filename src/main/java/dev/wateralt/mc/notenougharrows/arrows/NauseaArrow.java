package dev.wateralt.mc.notenougharrows.arrows;

import dev.wateralt.mc.notenougharrows.Arrow;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class NauseaArrow extends Arrow {
  @Override
  public String name() {
    return "Arrow of Nausea";
  }

  @Override
  public String lore() {
    return "Applies Nausea for 10 seconds";
  }

  @Override
  public int color() {
    return 0x074000;
  }

  @Override
  public void onEntityHit(ArrowEntity me, LivingEntity entity) {
    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 22 * 20));
  }

  @Override
  public void onBlockHit(ArrowEntity me) {
  }

  @Override
  public FletchingRecipe fletchingRecipe() {
    return new FletchingRecipe(new ItemStack(Items.PUFFERFISH), 8);
  }
}
