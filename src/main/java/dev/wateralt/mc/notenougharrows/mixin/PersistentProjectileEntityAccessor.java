package dev.wateralt.mc.notenougharrows.mixin;

import net.minecraft.entity.projectile.PersistentProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PersistentProjectileEntity.class)
public interface PersistentProjectileEntityAccessor {
  @Accessor
  int getInGroundTime();

  @Accessor
  boolean getInGround();
  
  @Accessor("pickupType")
  void setPickupType(PersistentProjectileEntity.PickupPermission pickupType);
}
