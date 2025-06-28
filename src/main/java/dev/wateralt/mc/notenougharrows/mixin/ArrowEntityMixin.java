package dev.wateralt.mc.notenougharrows.mixin;

import dev.wateralt.mc.notenougharrows.Arrow;
import dev.wateralt.mc.notenougharrows.NotEnoughArrows;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArrowEntity.class)
public class ArrowEntityMixin {
  @Inject(at = @At("HEAD"), method = "tick")
  public void tick(CallbackInfo ci) {
    ArrowEntity that = (ArrowEntity) (Object) this;
    PersistentProjectileEntityAccessor that2 = (PersistentProjectileEntityAccessor) that;
    if (that2.isInGround() && that2.getInGroundTime() == 1) {
      if (that.getColor() >> 24 == 0x55) {
        Arrow arrow = NotEnoughArrows.arrowsByColor.get(that.getColor() & 0xffffff);
        if (arrow == null) {
          return;
        }
        arrow.onBlockHit(that);
      }
    }
  }

  @Inject(at = @At("HEAD"), method = "onHit")
  public void onHit(LivingEntity target, CallbackInfo ci) {
    ArrowEntity that = (ArrowEntity) (Object) this;
    if (that.getColor() >> 24 == 0x55) {
      Arrow arrow = NotEnoughArrows.arrowsByColor.get(that.getColor() & 0xffffff);
      if (arrow == null) {
        NotEnoughArrows.logger.warn("Strange arrow detected (color = {})", that.getColor());
        return;
      }
      arrow.onEntityHit(that, target);
    }
  }
}
