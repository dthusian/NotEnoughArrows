package dev.wateralt.mc.notenougharrows.arrows;

import dev.wateralt.mc.notenougharrows.Arrow;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Box;

import java.util.List;

public class FlashArrow extends Arrow {
  @Override
  public String name() {
    return "Flash Arrow";
  }

  @Override
  public String lore() {
    return "Reveals players in a radius";
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
      new Box(me.getPos().subtract(5, 5, 5), me.getPos().add(5, 5, 5)),
      e -> true
    );
    entityList.forEach(player -> {
      player.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 5 * 20, 1, false, false));
    });
    me.kill();
  }

  @Override
  public FletchingRecipe fletchingRecipe() {
    return new FletchingRecipe(new ItemStack(Items.GLOWSTONE, 4), 1);
  }
}
