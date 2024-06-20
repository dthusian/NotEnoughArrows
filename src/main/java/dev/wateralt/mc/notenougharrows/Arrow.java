package dev.wateralt.mc.notenougharrows;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;

public abstract class Arrow {
  public record FletchingRecipe(ItemStack item, int numArrows) {
  }

  public abstract String name();

  public abstract String lore();

  public abstract int color();

  public abstract void onEntityHit(ArrowEntity me, LivingEntity entity);

  public abstract void onBlockHit(ArrowEntity me);

  public abstract FletchingRecipe fletchingRecipe();
}
