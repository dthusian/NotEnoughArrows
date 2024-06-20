package dev.wateralt.mc.notenougharrows.arrows;

import dev.wateralt.mc.notenougharrows.Arrow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;

public class EnderArrow extends Arrow {
  @Override
  public String name() {
    return "Ender Arrow";
  }

  @Override
  public String lore() {
    return "Teleports you to the arrow's location";
  }

  @Override
  public int color() {
    return 0xC061CB;
  }

  @Override
  public void onEntityHit(ArrowEntity me, LivingEntity entity) {
    onBlockHit(me);
  }

  @Override
  public void onBlockHit(ArrowEntity me) {
    Entity owner = me.getOwner();
    if(owner == null) return;
    if(owner.getEntityWorld() != me.getWorld()) return;
    
    for(int i = 0; i < 32; ++i) {
      owner.getEntityWorld().addParticle(
        ParticleTypes.PORTAL,
        me.getX(),
        me.getY() + me.getWorld().random.nextDouble() * 2.0,
        me.getZ(),
        me.getWorld().random.nextGaussian(),
        0.0,
        me.getWorld().random.nextGaussian()
      );
    }
    
    if(!(me.getWorld() instanceof ServerWorld serverWorld)) return;
    owner.teleportTo(new TeleportTarget(
      serverWorld,
      me.getPos(),
      Vec3d.ZERO,
      me.getOwner().getYaw(),
      me.getOwner().getPitch(),
      TeleportTarget.NO_OP
    ));
  }

  @Override
  public FletchingRecipe fletchingRecipe() {
    return new FletchingRecipe(new ItemStack(Items.ENDER_PEARL), 1);
  }
}
