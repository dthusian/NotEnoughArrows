package dev.wateralt.mc.notenougharrows;

import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.TypeFilter;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

public class EntityTracker<T> {
  Consumer<Entity> entityTick;
  HashMap<UUID, T> trackedEntities;

  public EntityTracker(Consumer<Entity> tickFunction) {
    trackedEntities = new HashMap<>();
    entityTick = tickFunction;
  }

  public void track(Entity entity, T data) {
    trackedEntities.put(entity.getUuid(), data);
  }

  public T getTracking(Entity entity) {
    return trackedEntities.get(entity.getUuid());
  }

  public void untrack(Entity entity) {
    trackedEntities.remove(entity.getUuid());
  }

  public void tick(MinecraftServer server) {
    server.getWorlds().forEach(world -> {
      world.getEntitiesByType(TypeFilter.instanceOf(Entity.class), entity -> trackedEntities.containsKey(entity.getUuid()))
        .forEach(entityTick::accept);
    });
  }
}
