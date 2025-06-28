package dev.wateralt.mc.notenougharrows.arrows;

import dev.wateralt.mc.notenougharrows.Arrow;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import java.util.List;

public class FlashArrow extends Arrow {
  @Override
  public String name() {
    return "Flash Arrow";
  }

  @Override
  public String lore() {
    return "Blinds players in a radius";
  }

  @Override
  public int color() {
    return 0xffffff;
  }

  @Override
  public void onEntityHit(ArrowEntity me, LivingEntity entity) {
    onBlockHit(me);
  }

  @Override
  public void onBlockHit(ArrowEntity me) {
    List<PlayerEntity> entityList = me.getWorld().getEntitiesByClass(
      PlayerEntity.class,
      new Box(me.getPos().subtract(20, 20, 20), me.getPos().add(20, 20, 20)),
      e -> true
    );
    entityList.forEach(player -> {
      var raycast = player.getWorld().raycast(new RaycastContext(player.getPos(), me.getPos(), RaycastContext.ShapeType.VISUAL, RaycastContext.FluidHandling.NONE, player));
      Vec3d vectorToArrow = me.getPos().subtract(player.getPos()).normalize();
      Vec3d vectorLooking = player.getRotationVector().normalize();
      double flashAmt = vectorToArrow.dotProduct(vectorLooking);
      if(raycast.getType() == HitResult.Type.MISS || flashAmt < 0.2) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 5 * 20, 2, false, false));
      }
    });
    me.kill((ServerWorld) me.getWorld());
  }

  @Override
  public FletchingRecipe fletchingRecipe() {
    return new FletchingRecipe(new ItemStack(Items.GLOWSTONE, 4), 1);
  }
}
