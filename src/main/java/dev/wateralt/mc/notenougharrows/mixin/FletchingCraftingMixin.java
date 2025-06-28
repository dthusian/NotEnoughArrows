package dev.wateralt.mc.notenougharrows.mixin;

import dev.wateralt.mc.notenougharrows.Arrow;
import dev.wateralt.mc.notenougharrows.NotEnoughArrows;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.*;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Mixin(ItemEntity.class)
public abstract class FletchingCraftingMixin {
  @Shadow
  public abstract ItemStack getStack();

  @Shadow
  private int itemAge;

  @Inject(at = @At("HEAD"), method = "tick")
  public void tick(CallbackInfo ci) {
    if (this.getStack().isOf(Items.ARROW)) {
      Entity entityThis = ((Entity) (Object) this);
      BlockPos myPos = entityThis.getBlockPos();
      BlockState underMe = entityThis.getWorld().getBlockState(myPos.down());
      if (this.itemAge % 5 == 0 && underMe.isOf(Blocks.FLETCHING_TABLE)) {
        World world = entityThis.getWorld();
        Optional<ItemEntity> foundIngredient = world.getEntitiesByClass(ItemEntity.class, new Box(myPos), v -> true)
          .stream()
          .map(v -> {
            Arrow arrow = NotEnoughArrows.arrowsByIngredient.get(v.getStack().getItem());
            if (arrow == null) return null;
            if (this.getStack().getCount() >= arrow.fletchingRecipe().numArrows() && v.getStack().getCount() >= arrow.fletchingRecipe().item().getCount()) {
              return v;
            } else {
              return null;
            }
          })
          .filter(Objects::nonNull)
          .findFirst();
        if (foundIngredient.isPresent()) {
          Arrow arrowType = NotEnoughArrows.arrowsByIngredient.get(foundIngredient.get().getStack().getItem());
          this.getStack().decrement(arrowType.fletchingRecipe().numArrows());
          foundIngredient.get().getStack().decrement(arrowType.fletchingRecipe().item().getCount());

          ItemStack createdArrows = new ItemStack(Items.TIPPED_ARROW, arrowType.fletchingRecipe().numArrows());
          ComponentMap map = ComponentMap.builder()
            .add(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(
              Optional.empty(),
              Optional.of(0x55000000 | arrowType.color()),
              List.of(),
              Optional.empty()
            ))
            .add(DataComponentTypes.ITEM_NAME, Text.literal(arrowType.name()))
            .add(DataComponentTypes.LORE, new LoreComponent(
              List.of(Text.literal(arrowType.lore()))
            ))
            .build();
          createdArrows.applyComponentsFrom(map);

          ItemEntity createdArrowsEntity = new ItemEntity(world, entityThis.getX(), entityThis.getY(), entityThis.getZ(), createdArrows);
          world.spawnEntity(createdArrowsEntity);
        }
      }
    }
  }
}
