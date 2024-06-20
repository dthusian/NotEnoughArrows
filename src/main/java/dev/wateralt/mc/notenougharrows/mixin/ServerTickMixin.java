package dev.wateralt.mc.notenougharrows.mixin;

import dev.wateralt.mc.notenougharrows.NotEnoughArrows;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class ServerTickMixin {
  @Inject(at = @At("HEAD"), method = "tick")
  public void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
    NotEnoughArrows.trackerList.forEach(v -> v.tick((MinecraftServer) (Object) this));
  }
}
