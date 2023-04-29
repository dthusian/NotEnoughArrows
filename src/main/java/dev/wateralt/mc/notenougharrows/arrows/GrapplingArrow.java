package dev.wateralt.mc.notenougharrows.arrows;

import dev.wateralt.mc.notenougharrows.Arrow;
import dev.wateralt.mc.notenougharrows.EntityTracker;
import dev.wateralt.mc.notenougharrows.NotEnoughArrows;
import dev.wateralt.mc.notenougharrows.mixin.PersistentProjectileEntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;

public class GrapplingArrow extends Arrow {
    private static final double GRAPPLE_RANGE = 75.0;
    private static final double GRAPPLE_STRENGTH = 0.25;
    private static final EntityTracker<ArrowEntity> tracker = new EntityTracker<>(GrapplingArrow::entityTick);

    private static void entityTick(Entity entity) {
        ArrowEntity hook = tracker.getTracking(entity);
        if(!hook.isAlive()) {
            tracker.untrack(entity);
            return;
        }
        Vec3d diff = hook.getPos().subtract(entity.getPos());
        if(diff.length() > GRAPPLE_RANGE || diff.length() < 2 || ((PersistentProjectileEntityAccessor) hook).getInGroundTime() > 20*5) {
            tracker.untrack(entity);
            return;
        }
        entity.addVelocity(diff.normalize().multiply(GRAPPLE_STRENGTH));
        entity.velocityModified = true;
    }

    public GrapplingArrow() {
        NotEnoughArrows.trackerList.add(tracker);
    }

    @Override
    public String name() {
        return "Grappling Arrow";
    }

    @Override
    public String lore() {
        return "Launches you in the direction of the arrow";
    }

    @Override
    public int color() {
        return 0x202020;
    }

    @Override
    public void onEntityHit(ArrowEntity me, LivingEntity entity) {
        entity.addVelocity(me.getOwner().getPos().subtract(entity.getPos()).normalize().multiply(GRAPPLE_STRENGTH * 5));
        entity.velocityModified = true;
    }

    @Override
    public void onBlockHit(ArrowEntity me) {
        Entity owner = me.getOwner();
        if(owner == null) return;
        Vec3d diff = me.getPos().subtract(owner.getPos());
        if(diff.length() > GRAPPLE_RANGE) return;
        tracker.track(me.getOwner(), me);
    }

    @Override
    public FletchingRecipe fletchingRecipe() {
        return new FletchingRecipe(new ItemStack(Items.STRING, 16), 1);
    }
}
