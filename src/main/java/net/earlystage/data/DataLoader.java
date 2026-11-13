package net.earlystage.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.earlystage.EarlyStageMain;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.Registries;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class DataLoader implements SimpleSynchronousResourceReloadListener {

    private static final Logger LOGGER = LogManager.getLogger("EarlyStage");

    private static final Set<Identifier> CRAFTING_ROCK_RECIPE_IDS = new LinkedHashSet<>();

    @Override
    public Identifier getFabricId() {
        return EarlyStageMain.identifierOf("loader");
    }

    @Override
    public void reload(ResourceManager manager) {
        EarlyStageMain.SIEVE_DROP_TEMPLATES.clear();
        CRAFTING_ROCK_RECIPE_IDS.clear();

        boolean craftingRockRecipesReplaced = false;
        for (Map.Entry<Identifier, Resource> entry : manager.findResources("crafting_rock_recipes", id -> id.getPath().endsWith(".json")).entrySet()) {
            if (craftingRockRecipesReplaced) {
                break;
            }
            try (InputStream stream = entry.getValue().getInputStream()) {
                JsonObject data = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();

                if (JsonHelper.getBoolean(data, "replace", false)) {
                    craftingRockRecipesReplaced = true;
                    CRAFTING_ROCK_RECIPE_IDS.clear();
                }

                if (!data.has("values")) {
                    continue;
                }

                for (JsonElement element : data.getAsJsonArray("values")) {
                    String recipeId = element.isJsonObject() ? JsonHelper.getString(element.getAsJsonObject(), "id") : element.getAsString();
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
        manager.findResources("sieve_drops", id -> id.getPath().endsWith(".json")).forEach((id, resourceRef) -> {
            JsonObject data;
            try (InputStream stream = resourceRef.getInputStream()) {
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
                    String blockIdString = JsonHelper.getString(data2, "block_id");
                    Identifier blockId = Identifier.tryParse(blockIdString);
                    if (blockId == null || !Registries.BLOCK.containsId(blockId) || Registries.BLOCK.get(blockId) == Blocks.AIR) {
                        LOGGER.warn("Block Id: {} in {} is not a valid block id", blockIdString, id);
                        continue;
                    }

                    Item blockItem = Registries.BLOCK.get(blockId).asItem();
                    if (blockItem == Items.AIR) {
                        LOGGER.warn("Block Id: {} in {} has no item form", blockIdString, id);
                        continue;
                    }

                    Map<Item, SieveDropTemplate> sieveDrops = JsonHelper.getBoolean(data2, "replace", false) ? replacedSieveDrops : mergedSieveDrops;
                    SieveDropTemplate template = sieveDrops.computeIfAbsent(blockItem, item -> new SieveDropTemplate(item, List.of(), List.of(), List.of()));
                    List<Item> blockDrops = template.getBlockDrops();
                    List<Float> dropChances = template.getDropChances();
                    List<Integer> rollCount = template.getRollCount();

                    for (JsonElement blockDropElement : JsonHelper.getArray(data2, "block_drops")) {
                        try {
                            JsonObject data3 = blockDropElement.getAsJsonObject();
                            String itemIdString = JsonHelper.getString(data3, "item_id");
                            Identifier itemId = Identifier.tryParse(itemIdString);
                            if (itemId == null || !Registries.ITEM.containsId(itemId) || Registries.ITEM.get(itemId) == Items.AIR) {
                                LOGGER.warn("Item Id: {} for block {} in {} is not a valid item id", itemIdString, blockIdString, id);
                                continue;
                            }
                            float chance = JsonHelper.getFloat(data3, "chance");
                            int rolls = JsonHelper.getInt(data3, "rolls");
                            blockDrops.add(Registries.ITEM.get(itemId));
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
        for (Identifier recipeId : CRAFTING_ROCK_RECIPE_IDS) {
            Optional<RecipeEntry<?>> optional = server.getRecipeManager().get(recipeId);
            if (optional.isEmpty()) {
                LOGGER.warn("Crafting rock recipe {} does not exist", recipeId);
                continue;
            }
            if (!(optional.get().value() instanceof CraftingRecipe recipe)) {
                LOGGER.warn("Crafting rock recipe {} is not a crafting recipe", recipeId);
                continue;
            }
            ItemStack result = recipe.getResult(server.getRegistryManager());
            if (result.isEmpty()) {
                LOGGER.warn("Crafting rock recipe {} has no result", recipeId);
                continue;
            }
            List<Item> inputs = recipeItems.computeIfAbsent(result.getItem(), item -> new ArrayList<>());
            for (Ingredient ingredient : recipe.getIngredients()) {
                for (ItemStack stack : ingredient.getMatchingStacks()) {
                    if (!stack.isEmpty() && !inputs.contains(stack.getItem())) {
                        inputs.add(stack.getItem());
                    }
                }
            }
        }
        EarlyStageMain.CRAFTING_ROCK_RECIPE_ITEMS.clear();
        EarlyStageMain.CRAFTING_ROCK_RECIPE_ITEMS.putAll(recipeItems);
    }

}
