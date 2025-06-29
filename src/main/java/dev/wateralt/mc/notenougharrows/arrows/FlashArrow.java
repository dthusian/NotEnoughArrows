package dev.wateralt.mc.notenougharrows.arrows;

import dev.wateralt.mc.notenougharrows.Arrow;
import dev.wateralt.mc.notenougharrows.EntityTracker;
import dev.wateralt.mc.notenougharrows.NotEnoughArrows;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import java.util.List;

public class FlashArrow extends Arrow {
  EntityTracker<Integer[]> tracker = new EntityTracker<>(this::flashPlayer);
  public FlashArrow() {
    NotEnoughArrows.trackerList.add(tracker);
  }
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
        //tracker.track(player, new Integer[] { 37 });
        
      }
    });
    me.kill((ServerWorld) me.getWorld());
  }

  @Override
  public FletchingRecipe fletchingRecipe() {
    return new FletchingRecipe(new ItemStack(Items.GLOWSTONE, 4), 1);
  }
  
  private void flashPlayer(Entity player) {
    if(player instanceof ServerPlayerEntity spe) {
      Integer[] flashTime = this.tracker.getTracking(player);
      flashTime[0]--;
      if(flashTime[0] <= 0) {
        this.tracker.untrack(player);
      }
      
      Vec3d vectorLooking = player.getRotationVector().normalize();
      Vec3d flashOrigin = player.getEyePos().add(vectorLooking.multiply(0.2));
      Vec3d perp1 = vectorLooking.crossProduct(new Vec3d(0, 1, 0)).normalize();
      Vec3d perp2 = vectorLooking.crossProduct(perp1).normalize();
      final double velFactor = 1;
      final double spreadFactor = 1;
      for(int i = 0; i < 4; i++) {
        for(int j = -i; j <= i; j++) {
          for(int k = -i; k <= i; k++) {
            Vec3d pos = flashOrigin
              .add(vectorLooking.multiply(i * velFactor))
              .add(perp1.multiply(j * spreadFactor))
              .add(perp2.multiply(k * spreadFactor));
            spe.getWorld().spawnParticles(
              spe, ParticleTypes.END_ROD,
              true, true,
              pos.getX(), pos.getY(), pos.getZ(),
              20,
              0.0, 0.0, 0.0,
              0.1);
          }
        }
      }
    }
  }
}
