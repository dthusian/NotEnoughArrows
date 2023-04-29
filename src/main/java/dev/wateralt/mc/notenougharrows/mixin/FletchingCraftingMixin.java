package dev.wateralt.mc.notenougharrows.mixin;

import dev.wateralt.mc.notenougharrows.Arrow;
import dev.wateralt.mc.notenougharrows.NotEnoughArrows;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.Optional;

@Mixin(ItemEntity.class)
public abstract class FletchingCraftingMixin {
    @Shadow public abstract ItemStack getStack();

    @Shadow private int itemAge;

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) {
        if(this.getStack().isOf(Items.ARROW)) {
            Entity entityThis = ((Entity) (Object) this);
            BlockPos myPos = entityThis.getBlockPos();
            BlockState underMe = entityThis.getWorld().getBlockState(myPos.down());
            if(this.itemAge % 5 == 0 && underMe.isOf(Blocks.FLETCHING_TABLE)) {
                World world = entityThis.getWorld();
                Optional<ItemEntity> foundIngredient = world.getEntitiesByClass(ItemEntity.class, new Box(myPos), v -> true)
                        .stream()
                        .map(v -> {
                            Arrow arrow = NotEnoughArrows.arrowsByIngredient.get(v.getStack().getItem());
                            if(arrow == null) return null;
                            if(this.getStack().getCount() >= arrow.fletchingRecipe().numArrows() && v.getStack().getCount() >= arrow.fletchingRecipe().item().getCount()) {
                                return v;
                            } else {
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .findFirst();
                if(foundIngredient.isPresent()) {
                    Arrow recipe = NotEnoughArrows.arrowsByIngredient.get(foundIngredient.get().getStack().getItem());
                    this.getStack().decrement(recipe.fletchingRecipe().numArrows());
                    foundIngredient.get().getStack().decrement(recipe.fletchingRecipe().item().getCount());
                    ItemStack createdArrows = new ItemStack(Items.TIPPED_ARROW, recipe.fletchingRecipe().numArrows());
                    createdArrows.setNbt(new NbtCompound());
                    createdArrows.setSubNbt("CustomPotionColor", NbtInt.of(0x55000000 | recipe.color()));
                    createdArrows.setSubNbt("HideFlags", NbtInt.of(32));
                    {
                        NbtCompound nbt = new NbtCompound();
                        nbt.putString("Name", "{\"text\":\"%s\",\"color\":\"white\",\"italic\":false}".formatted(recipe.name()));
                        NbtList lore = new NbtList();
                        lore.add(NbtString.of(recipe.lore()));
                        nbt.put("Lore", lore);
                        createdArrows.setSubNbt("display", nbt);
                    }
                    ItemEntity createdArrowsEntity = new ItemEntity(world, entityThis.getX(), entityThis.getY(), entityThis.getZ(), createdArrows);
                    world.spawnEntity(createdArrowsEntity);
                }
            }
        }
    }
}
