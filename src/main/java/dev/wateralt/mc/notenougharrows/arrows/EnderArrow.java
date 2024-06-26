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
    return "Swaps you and the arrow's target";
  }

  @Override
  public int color() {
    return 0xC061CB;
  }

  @Override
  public void onEntityHit(ArrowEntity me, LivingEntity target) {
    Entity owner = me.getOwner();
    if(owner == null) return;
    if(owner.getEntityWorld() != target.getWorld()) return;
    if(!(me.getWorld() instanceof ServerWorld serverWorld)) return;
    
    Vec3d ownerPos = owner.getPos();
    Vec3d ownerVel = owner.getVelocity();
    float ownerYaw = owner.getYaw();
    float ownerPitch = owner.getPitch();
    Vec3d targetPos = target.getPos();
    Vec3d targetVel = target.getVelocity();
    float targetYaw = target.getYaw();
    float targetPitch = target.getPitch();
    owner.teleportTo(new TeleportTarget(
      serverWorld,
      targetPos, targetVel,
      targetYaw, targetPitch,
      TeleportTarget.NO_OP
    ));
    target.teleportTo(new TeleportTarget(
      serverWorld,
      ownerPos, ownerVel,
      ownerYaw, ownerPitch,
      TeleportTarget.NO_OP
    ));
  }

  @Override
  public void onBlockHit(ArrowEntity me) {
    
  }

  @Override
  public FletchingRecipe fletchingRecipe() {
    return new FletchingRecipe(new ItemStack(Items.ENDER_PEARL, 2), 1);
  }
}
