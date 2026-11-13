package net.earlystage.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.earlystage.EarlyStageMain;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

        manager.findResources("sieve_drops", id -> id.getPath().endsWith(".json")).forEach((id, resourceRef) -> {
            try {
                InputStream stream = resourceRef.getInputStream();
                JsonObject data = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();

                for (int u = 0; u < data.getAsJsonArray("drops").size(); u++) {
                    JsonObject data2 = (JsonObject) data.getAsJsonArray("drops").get(u);
                    if (Registries.BLOCK.get(Identifier.of(data2.get("block_id").getAsString())) == Blocks.AIR) {
                        LOGGER.warn("Block Id: " + data2.get("block_id").getAsString() + " is not a valid block id");
                        continue;
                    }

                    Item blockItem = Registries.BLOCK.get(Identifier.of(data2.get("block_id").getAsString())).asItem();

                    List<Item> blockDrops = new ArrayList<>();
                    List<Float> dropChances = new ArrayList<>();
                    List<Integer> rollCount = new ArrayList<>();

                    if (JsonHelper.getBoolean(data2, "replace", false)) {
                        for (int i = 0; i < EarlyStageMain.SIEVE_DROP_TEMPLATES.size(); i++)
                            if (EarlyStageMain.SIEVE_DROP_TEMPLATES.get(i).getBlockItem() == blockItem) {
                                EarlyStageMain.SIEVE_DROP_TEMPLATES.remove(i);
                                break;
                            }
                    } else {
                        // checks if it already exists
                        for (int i = 0; i < EarlyStageMain.SIEVE_DROP_TEMPLATES.size(); i++)
                            if (EarlyStageMain.SIEVE_DROP_TEMPLATES.get(i).getBlockItem() == blockItem) {
                                blockDrops.addAll(EarlyStageMain.SIEVE_DROP_TEMPLATES.get(i).getBlockDrops());
                                dropChances.addAll(EarlyStageMain.SIEVE_DROP_TEMPLATES.get(i).getDropChances());
                                rollCount.addAll(EarlyStageMain.SIEVE_DROP_TEMPLATES.get(i).getRollCount());
                                EarlyStageMain.SIEVE_DROP_TEMPLATES.remove(i);
                                break;
                            }
                    }

                    for (int i = 0; i < data2.getAsJsonArray("block_drops").size(); i++) {
                        JsonObject data3 = (JsonObject) data2.getAsJsonArray("block_drops").get(i);
                        blockDrops.add(Registries.ITEM.get(Identifier.of(data3.get("item_id").getAsString())));
                        dropChances.add(data3.get("chance").getAsFloat());
                        rollCount.add(data3.get("rolls").getAsInt());
                    }
                    EarlyStageMain.SIEVE_DROP_TEMPLATES.add(new SieveDropTemplate(blockItem, blockDrops, dropChances, rollCount));
                }
            } catch (Exception e) {
                LOGGER.error("Error occurred while loading resource {}. {}", id.toString(), e.toString());
            }
        });
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
