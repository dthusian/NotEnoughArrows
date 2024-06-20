package dev.wateralt.mc.notenougharrows.arrows;

import dev.wateralt.mc.notenougharrows.Arrow;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class WindArrow extends Arrow {

  @Override
  public String name() {
    return "Wind Arrow";
  }

  @Override
  public String lore() {
    return "Launches entities in the direction of the arrow";
  }

  @Override
  public int color() {
    return 0x00802b;
  }

  @Override
  public void onEntityHit(ArrowEntity me, LivingEntity entity) {
    entity.addVelocity(me.getVelocity().multiply(1.5));
    entity.velocityModified = true;
  }

  @Override
  public void onBlockHit(ArrowEntity me) { }

  @Override
  public FletchingRecipe fletchingRecipe() {
    return new FletchingRecipe(new ItemStack(Items.WIND_CHARGE), 1);
  }
}
