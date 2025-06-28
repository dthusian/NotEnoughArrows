package dev.wateralt.mc.notenougharrows.mixin;

import net.minecraft.entity.projectile.PersistentProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PersistentProjectileEntity.class)
public interface PersistentProjectileEntityAccessor {
  @Accessor
  int getInGroundTime();

  @Invoker("isInGround")
  boolean isInGround();
  
  @Accessor("pickupType")
  void setPickupType(PersistentProjectileEntity.PickupPermission pickupType);
}
