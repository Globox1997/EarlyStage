package net.earlystage;

import net.earlystage.data.CraftingRockRecipe;
import net.earlystage.data.SieveDropTemplate;
import net.earlystage.misc.ExtraBlastingRecipe;
import net.earlystage.init.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeHolder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EarlyStageMain {

    public static final String MOD_ID = "earlystage";

    public static final List<SieveDropTemplate> SIEVE_DROP_TEMPLATES = new ArrayList<>();
    public static final Map<Item, List<Item>> CRAFTING_ROCK_RECIPE_ITEMS = new HashMap<>();
    public static volatile List<CraftingRockRecipe> CRAFTING_ROCK_RECIPES = List.of();
    public static volatile List<RecipeHolder<ExtraBlastingRecipe>> EXTRA_BLASTING_RECIPES = List.of();

    public static void init() {
        ConfigInit.init();
    }

    public static Identifier identifierOf(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }

}
