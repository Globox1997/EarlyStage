package net.earlystage;

import net.earlystage.data.SieveDropTemplate;
import net.earlystage.init.*;
import net.earlystage.network.EarlyServerPacket;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EarlyStageMain implements ModInitializer {

    public static final List<SieveDropTemplate> SIEVE_DROP_TEMPLATES = new ArrayList<>();
    public static final Map<Item, List<Item>> CRAFTING_ROCK_RECIPE_ITEMS = new HashMap<>();
    public static volatile List<ResourceLocation> CRAFTING_ROCK_RECIPES = List.of();

    @Override
    public void onInitialize() {
        ConfigInit.init();
        BlockInit.init();
        ItemInit.init();
        CompatInit.init();
        TagInit.init();
        EventInit.init();
        LoaderInit.init();
        WorldInit.init();
        RecipeInit.init();
        EarlyServerPacket.init();
    }

    public static ResourceLocation identifierOf(String name) {
        return ResourceLocation.fromNamespaceAndPath("earlystage", name);
    }

}
