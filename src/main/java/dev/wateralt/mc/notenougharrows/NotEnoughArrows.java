package dev.wateralt.mc.notenougharrows;

import dev.wateralt.mc.notenougharrows.arrows.ExplosiveArrow;
import dev.wateralt.mc.notenougharrows.arrows.GrapplingArrow;
import dev.wateralt.mc.notenougharrows.arrows.LevitationArrow;
import dev.wateralt.mc.notenougharrows.arrows.NauseaArrow;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotEnoughArrows implements ModInitializer {
    public static Map<Integer, Arrow> arrowsByColor;
    public static Map<Item, Arrow> arrowsByIngredient;
    public static List<EntityTracker<?>> trackerList;
    public static Logger logger = LoggerFactory.getLogger(NotEnoughArrows.class);
    public NotEnoughArrows() {
        arrowsByColor = new HashMap<>();
        arrowsByIngredient = new HashMap<>();
        trackerList = new ArrayList<>();
    }
    @Override
    public void onInitialize() {
        addArrow(new ExplosiveArrow());
        addArrow(new LevitationArrow());
        addArrow(new NauseaArrow());
        addArrow(new GrapplingArrow());
    }
    private void addArrow(Arrow arrow) {
        arrowsByColor.put(arrow.color(), arrow);
        arrowsByIngredient.put(arrow.fletchingRecipe().item().getItem(), arrow);
    }
}
