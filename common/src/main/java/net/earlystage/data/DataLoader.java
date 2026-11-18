package net.earlystage.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.earlystage.EarlyStageMain;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplayContext;
import net.minecraft.util.context.ContextMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.earlystage.misc.ExtraBlastingRecipe;
import net.minecraft.world.level.block.Blocks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class DataLoader implements ResourceManagerReloadListener {

    private static final Logger LOGGER = LogManager.getLogger("EarlyStage");

    private static final Set<Identifier> CRAFTING_ROCK_RECIPE_IDS = new LinkedHashSet<>();

    @Override
    public void onResourceManagerReload(ResourceManager manager) {
        EarlyStageMain.SIEVE_DROP_TEMPLATES.clear();
        CRAFTING_ROCK_RECIPE_IDS.clear();

        boolean craftingRockRecipesReplaced = false;
        for (Map.Entry<Identifier, Resource> entry : manager.listResources("crafting_rock_recipes", id -> id.getPath().endsWith(".json")).entrySet()) {
            if (craftingRockRecipesReplaced) {
                break;
            }
            try (InputStream stream = entry.getValue().open()) {
                JsonObject data = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();

                if (GsonHelper.getAsBoolean(data, "replace", false)) {
                    craftingRockRecipesReplaced = true;
                    CRAFTING_ROCK_RECIPE_IDS.clear();
                }

                if (!data.has("values")) {
                    continue;
                }

                for (JsonElement element : data.getAsJsonArray("values")) {
                    String recipeId = element.isJsonObject() ? GsonHelper.getAsString(element.getAsJsonObject(), "id") : element.getAsString();
                    Identifier identifier = Identifier.tryParse(recipeId);
                    if (identifier == null) {
                        LOGGER.warn("Invalid recipe id {} in {}", recipeId, entry.getKey());
                        continue;
                    }
                    CRAFTING_ROCK_RECIPE_IDS.add(identifier);
                }
            } catch (Exception e) {
                LOGGER.error("Error occurred while loading resource {}. {}", entry.getKey().toString(), e.toString());
            }
        }

        Map<Item, SieveDropTemplate> mergedSieveDrops = new LinkedHashMap<>();
        Map<Item, SieveDropTemplate> replacedSieveDrops = new LinkedHashMap<>();
        manager.listResources("sieve_drops", id -> id.getPath().endsWith(".json")).forEach((id, resourceRef) -> {
            JsonObject data;
            try (InputStream stream = resourceRef.open()) {
                data = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
            } catch (Exception e) {
                LOGGER.error("Error occurred while loading resource {}. {}", id.toString(), e.toString());
                return;
            }
            if (!data.has("drops") || !data.get("drops").isJsonArray()) {
                LOGGER.warn("Sieve drop file {} has no drops array", id);
                return;
            }

            for (JsonElement dropElement : data.getAsJsonArray("drops")) {
                try {
                    JsonObject data2 = dropElement.getAsJsonObject();
                    String blockIdString = GsonHelper.getAsString(data2, "block_id");
                    Identifier blockId = Identifier.tryParse(blockIdString);
                    if (blockId == null || !BuiltInRegistries.BLOCK.containsKey(blockId) || BuiltInRegistries.BLOCK.getValue(blockId) == Blocks.AIR) {
                        LOGGER.warn("Block Id: {} in {} is not a valid block id", blockIdString, id);
                        continue;
                    }

                    Item blockItem = BuiltInRegistries.BLOCK.getValue(blockId).asItem();
                    if (blockItem == Items.AIR) {
                        LOGGER.warn("Block Id: {} in {} has no item form", blockIdString, id);
                        continue;
                    }

                    Map<Item, SieveDropTemplate> sieveDrops = GsonHelper.getAsBoolean(data2, "replace", false) ? replacedSieveDrops : mergedSieveDrops;
                    SieveDropTemplate template = sieveDrops.computeIfAbsent(blockItem, item -> new SieveDropTemplate(item, List.of(), List.of(), List.of()));
                    List<Item> blockDrops = template.getBlockDrops();
                    List<Float> dropChances = template.getDropChances();
                    List<Integer> rollCount = template.getRollCount();

                    for (JsonElement blockDropElement : GsonHelper.getAsJsonArray(data2, "block_drops")) {
                        try {
                            JsonObject data3 = blockDropElement.getAsJsonObject();
                            String itemIdString = GsonHelper.getAsString(data3, "item_id");
                            Identifier itemId = Identifier.tryParse(itemIdString);
                            if (itemId == null || !BuiltInRegistries.ITEM.containsKey(itemId) || BuiltInRegistries.ITEM.getValue(itemId) == Items.AIR) {
                                LOGGER.warn("Item Id: {} for block {} in {} is not a valid item id", itemIdString, blockIdString, id);
                                continue;
                            }
                            float chance = GsonHelper.getAsFloat(data3, "chance");
                            int rolls = GsonHelper.getAsInt(data3, "rolls");
                            blockDrops.add(BuiltInRegistries.ITEM.getValue(itemId));
                            dropChances.add(chance);
                            rollCount.add(rolls);
                        } catch (Exception e) {
                            LOGGER.error("Invalid sieve drop for block {} in {}. {}", blockIdString, id, e.toString());
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("Invalid sieve drop entry in {}. {}", id, e.toString());
                }
            }
        });
        mergedSieveDrops.forEach((blockItem, template) -> {
            if (!replacedSieveDrops.containsKey(blockItem)) {
                EarlyStageMain.SIEVE_DROP_TEMPLATES.add(template);
            }
        });
        EarlyStageMain.SIEVE_DROP_TEMPLATES.addAll(replacedSieveDrops.values());
    }

    public static void updateCraftingRockRecipeItems(MinecraftServer server) {
        Map<Item, List<Item>> recipeItems = new HashMap<>();
        List<CraftingRockRecipe> recipes = new ArrayList<>();
        ContextMap context = ContextMap.builder().set(SlotDisplayContext.REGISTRIES, server.registryAccess()).buildAndValidate(SlotDisplayContext.CONTEXT);
        for (Identifier recipeId : CRAFTING_ROCK_RECIPE_IDS) {
            Optional<RecipeHolder<?>> optional = server.getRecipeManager().byKey(ResourceKey.create(Registries.RECIPE, recipeId));
            if (optional.isEmpty()) {
                LOGGER.warn("Crafting rock recipe {} does not exist", recipeId);
                continue;
            }
            if (!(optional.get().value() instanceof CraftingRecipe recipe)) {
                LOGGER.warn("Crafting rock recipe {} is not a crafting recipe", recipeId);
                continue;
            }
            if (recipe.display().isEmpty()) {
                LOGGER.warn("Crafting rock recipe {} has no display", recipeId);
                continue;
            }
            RecipeDisplay display = recipe.display().get(0);
            ItemStack result = display.result().resolveForFirstStack(context);
            if (result.isEmpty()) {
                LOGGER.warn("Crafting rock recipe {} has no result", recipeId);
                continue;
            }
            recipes.add(new CraftingRockRecipe(recipeId, display));
            List<Item> inputs = recipeItems.computeIfAbsent(result.getItem(), item -> new ArrayList<>());
            for (Ingredient ingredient : recipe.placementInfo().ingredients()) {
                ingredient.items().forEach(holder -> {
                    if (!inputs.contains(holder.value())) {
                        inputs.add(holder.value());
                    }
                });
            }
        }
        EarlyStageMain.CRAFTING_ROCK_RECIPE_ITEMS.clear();
        EarlyStageMain.CRAFTING_ROCK_RECIPE_ITEMS.putAll(recipeItems);
        EarlyStageMain.CRAFTING_ROCK_RECIPES = List.copyOf(recipes);
        List<RecipeHolder<ExtraBlastingRecipe>> extraBlastingRecipes = new ArrayList<>();
        for (RecipeHolder<?> recipe : server.getRecipeManager().getRecipes()) {
            if (recipe.value() instanceof ExtraBlastingRecipe extraBlastingRecipe) {
                extraBlastingRecipes.add(new RecipeHolder<>(recipe.id(), extraBlastingRecipe));
            }
        }
        EarlyStageMain.EXTRA_BLASTING_RECIPES = List.copyOf(extraBlastingRecipes);
    }

}
