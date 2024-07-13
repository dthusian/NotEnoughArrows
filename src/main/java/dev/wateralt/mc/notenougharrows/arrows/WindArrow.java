package dev.wateralt.mc.notenougharrows.arrows;

import dev.wateralt.mc.notenougharrows.Arrow;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;

public class WindArrow extends Arrow {
  private static final double WIND_STRENGTH = 1.5;
  private static final double VELOCITY_MAX = 6.0;

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
    Vec3d vel = entity.getVelocity();
    vel = vel.add(me.getVelocity().multiply(WIND_STRENGTH));
    if(vel.length() > VELOCITY_MAX) {
      vel = vel.normalize().multiply(VELOCITY_MAX);
    }
    entity.setVelocity(vel);
    entity.velocityModified = true;
  }

  @Override
  public void onBlockHit(ArrowEntity me) { }

  @Override
  public FletchingRecipe fletchingRecipe() {
    return new FletchingRecipe(new ItemStack(Items.WIND_CHARGE), 1);
  }
}
