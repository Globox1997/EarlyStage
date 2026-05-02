package net.earlystage.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.earlystage.EarlyStageMain;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class DataLoader implements SimpleSynchronousResourceReloadListener {

    private static final Logger LOGGER = LogManager.getLogger("EarlyStage");

    private static boolean CRAFTING_ROCK_RECIPES_REPLACED = false;

    @Override
    public Identifier getFabricId() {
        return EarlyStageMain.identifierOf("loader");
    }

    @Override
    public void reload(ResourceManager manager) {
        EarlyStageMain.SIEVE_DROP_TEMPLATES.clear();
        EarlyStageMain.CRAFTING_ROCK_RECIPE_ITEMS.clear();

        manager.findResources("crafting_rock_recipes", id -> id.getPath().endsWith(".json")).forEach((id, resourceRef) -> {
            try {
                InputStream stream = resourceRef.getInputStream();
                JsonObject data = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();

                if (CRAFTING_ROCK_RECIPES_REPLACED) {
                    return;
                }
                CRAFTING_ROCK_RECIPES_REPLACED = data.has("replace") && data.get("replace").getAsBoolean();
                if (CRAFTING_ROCK_RECIPES_REPLACED) {
                    EarlyStageMain.CRAFTING_ROCK_RECIPE_ITEMS.clear();
                }

                if (!data.has("values")) {
                    return;
                }

                JsonArray values = data.getAsJsonArray("values");
                for (JsonElement element : values) {
                    Identifier recipeId = Identifier.of(element.getAsString());

                    Identifier fileId = Identifier.of(recipeId.getNamespace(), "recipe/" + recipeId.getPath() + ".json");

                    try {
                        Collection<Resource> resources = manager.getAllResources(fileId);

                        for (Resource resource : resources) {
                            try (InputStream recipeStream = resource.getInputStream()) {
                                JsonObject recipeJson = JsonParser.parseReader(new InputStreamReader(recipeStream)).getAsJsonObject();

                                extractRecipeItems(recipeJson, manager).ifPresent(recipeItems -> {
                                    Item outputItem = Registries.ITEM.get(recipeItems.output());
                                    List<Item> inputItems = recipeItems.inputs().stream().map(Registries.ITEM::get).toList();

                                    EarlyStageMain.CRAFTING_ROCK_RECIPE_ITEMS.merge(outputItem, new ArrayList<>(inputItems), (existing, incoming) -> {
                                        existing.addAll(incoming);
                                        return existing;
                                    });
                                });
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.warn("Could not load recipe {} referenced in {}: {}", recipeId, id, e.toString());
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Error occurred while loading resource {}. {}", id.toString(), e.toString());
            }
        });

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

    private record RecipeItems(Identifier output, List<Identifier> inputs) {
    }

    private Optional<RecipeItems> extractRecipeItems(JsonObject recipe, ResourceManager resourceManager) {
        if (!recipe.has("type") || !recipe.has("result")) {
            return Optional.empty();
        }

        String type = recipe.get("type").getAsString();
        List<Identifier> inputs = new ArrayList<>();

        switch (type) {
            case "minecraft:crafting_shaped" -> {
                if (recipe.has("key")) {
                    for (Map.Entry<String, JsonElement> entry : recipe.getAsJsonObject("key").entrySet()) {
                        inputs.addAll(resolveIngredient(entry.getValue(), resourceManager));
                    }
                }
            }
            case "minecraft:crafting_shapeless" -> {
                if (recipe.has("ingredients")) {
                    for (JsonElement ingredient : recipe.getAsJsonArray("ingredients")) {
                        inputs.addAll(resolveIngredient(ingredient, resourceManager));
                    }
                }
            }
            default -> {
                LOGGER.warn("Unsupported recipe type: {}", type);
                return Optional.empty();
            }
        }

        JsonObject result = recipe.getAsJsonObject("result");
        String outputKey = result.has("id") ? "id" : "item";
        if (!result.has(outputKey)) {
            return Optional.empty();
        }

        Identifier outputId = Identifier.of(result.get(outputKey).getAsString());
        if (!Registries.ITEM.containsId(outputId)) {
            LOGGER.warn("Unknown output item in recipe: {}", outputId);
            return Optional.empty();
        }

        return Optional.of(new RecipeItems(outputId, inputs));
    }

    private List<Identifier> resolveIngredient(JsonElement ingredient, ResourceManager resourceManager) {
        if (ingredient.isJsonArray()) {
            List<Identifier> items = new ArrayList<>();
            for (JsonElement element : ingredient.getAsJsonArray()) {
                items.addAll(resolveIngredient(element, resourceManager));
            }
            return items;
        }

        JsonObject obj = ingredient.getAsJsonObject();

        if (obj.has("item")) {
            Identifier itemId = Identifier.of(obj.get("item").getAsString());
            if (Registries.ITEM.containsId(itemId)) {
                return List.of(itemId);
            } else {
                LOGGER.warn("Unknown item in recipe ingredient: {}", itemId);
            }
        } else if (obj.has("tag")) {
            Identifier tagId = Identifier.of(obj.get("tag").getAsString());
            return resolveItemTag(tagId, resourceManager);
        }

        return List.of();
    }

    private List<Identifier> resolveItemTag(Identifier tagId, ResourceManager resourceManager) {
        return resolveItemTag(tagId, resourceManager, new HashSet<>());
    }

    private List<Identifier> resolveItemTag(Identifier tagId, ResourceManager resourceManager, Set<Identifier> visited) {
        if (!visited.add(tagId)) {
            LOGGER.warn("Circular tag reference detected: {}", tagId);
            return List.of();
        }

        Identifier fileId = Identifier.of(tagId.getNamespace(), "tags/item/" + tagId.getPath() + ".json");

        List<Identifier> result = new ArrayList<>();
        Collection<Resource> resources;

        try {
            resources = resourceManager.getAllResources(fileId);
        } catch (Exception e) {
            LOGGER.warn("Could not find tag file for {}: {}", tagId, e.toString());
            return List.of();
        }

        for (Resource resource : resources) {
            try (InputStream stream = resource.getInputStream()) {
                JsonObject json = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();

                if (json.has("replace") && json.get("replace").getAsBoolean()) {
                    result.clear();
                }

                if (!json.has("values")) {
                    continue;
                }
                JsonArray values = json.getAsJsonArray("values");
                for (JsonElement element : values) {
                    String entry = element.getAsString();

                    if (entry.startsWith("#")) {
                        Identifier nestedTag = Identifier.of(entry.substring(1));
                        result.addAll(resolveItemTag(nestedTag, resourceManager, visited));
                    } else {
                        Identifier itemId = Identifier.of(entry);
                        if (Registries.ITEM.containsId(itemId)) {
                            result.add(itemId);
                        } else {
                            LOGGER.warn("Tag {} references unknown item {}, skipping.", tagId, itemId);
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Error reading tag file {} from {}: {}", fileId, resource, e.toString());
            }
        }
        return result;
    }

}
