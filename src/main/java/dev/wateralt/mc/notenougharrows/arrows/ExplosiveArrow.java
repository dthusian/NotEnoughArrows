package dev.wateralt.mc.notenougharrows.arrows;

import dev.wateralt.mc.notenougharrows.Arrow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class ExplosiveArrow extends Arrow {
    @Override
    public String name() {
        return "Explosive Arrow";
    }

    @Override
    public String lore() { return "{\"text\":\"Explodes on impact\",\"color\":\"gold\",\"italic\":false}"; }

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
        me.getWorld().createExplosion(null, me.getX(), me.getY(), me.getZ(), 4.0f, World.ExplosionSourceType.TNT);
        me.kill();
    }

    @Override
    public FletchingRecipe fletchingRecipe() {
        return new FletchingRecipe(new ItemStack(Items.TNT, 8), 1);
    }
}
