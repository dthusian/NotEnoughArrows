package dev.wateralt.mc.notenougharrows.arrows;

import dev.wateralt.mc.notenougharrows.Arrow;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

public class ExplosiveArrow extends Arrow {
  @Override
  public String name() {
    return "Explosive Arrow";
  }

  @Override
  public String lore() {
    return "Explodes on impact";
  }

  @Override
  public int color() {
    return 0x4f1d00;
  }

  @Override
  public void onEntityHit(ArrowEntity me, LivingEntity entity) {
    onBlockHit(me);
  }

  @Override
  public void onBlockHit(ArrowEntity me) {
    LivingEntity owner = null;
    if(me.getOwner() instanceof LivingEntity meOwner) {
      owner = meOwner;
    }
    TntEntity tnt = new TntEntity(me.getWorld(), me.getX(), me.getY(), me.getZ(), owner);
    tnt.setFuse(40);
    tnt.setNoGravity(true);
    tnt.setVelocity(0, 0, 0);
    me.getWorld().spawnEntity(tnt);
    me.kill();
  }

  @Override
  public FletchingRecipe fletchingRecipe() {
    return new FletchingRecipe(new ItemStack(Items.TNT, 8), 1);
  }
}
