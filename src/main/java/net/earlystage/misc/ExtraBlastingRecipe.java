package net.earlystage.misc;

import java.util.stream.Stream;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.Nullable;

import net.earlystage.init.RecipeInit;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class ExtraBlastingRecipe implements Recipe<Inventory> {
    private final Identifier id;
    private final CookingRecipeCategory category;
    private final String group;
    private final Ingredient input;
    private final Ingredient extraInput;
    private final ItemStack output;
    private final float experience;
    private final int cookTime;

    public ExtraBlastingRecipe(Identifier id, String group, CookingRecipeCategory category, Ingredient input, Ingredient extraInput, ItemStack output, float experience, int cookTime) {
        this.category = category;
        this.id = id;
        this.group = group;
        this.input = input;
        this.extraInput = extraInput;
        this.output = output;
        this.experience = experience;
        this.cookTime = cookTime;
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Blocks.BLAST_FURNACE);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeInit.EXTRA_BLASTING_SERIALIZER;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return this.input.test(inventory.getStack(0)) && test(inventory.getStack(3), this.extraInput);
    }

    private boolean test(@Nullable ItemStack input, Ingredient ingredient) {
        if (input == null) {
            return false;
        }
        if (this.isEmpty()) {
            return input.isEmpty();
        }
        for (ItemStack itemStack2 : ingredient.getMatchingStacks()) {
            if (!itemStack2.isOf(input.getItem())) {
                continue;
            }
            if (input.getCount() < itemStack2.getCount()) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        return this.output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.add(this.input);
        defaultedList.add(this.extraInput);
        return defaultedList;
    }

    public float getExperience() {
        return this.experience;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return this.output;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    public int getCookTime() {
        return this.cookTime;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeInit.EXTRA_BLASTING;
    }

    public CookingRecipeCategory getCategory() {
        return this.category;
    }

    @SuppressWarnings("deprecation")
    public static class Serializer implements RecipeSerializer<ExtraBlastingRecipe> {

        @Override
        public ExtraBlastingRecipe read(Identifier identifier, JsonObject jsonObject) {
            String group = JsonHelper.getString(jsonObject, "group", "");
            CookingRecipeCategory cookingRecipeCategory = CookingRecipeCategory.CODEC.byId(JsonHelper.getString(jsonObject, "category", null), CookingRecipeCategory.MISC);
            JsonElement jsonElement = JsonHelper.hasArray(jsonObject, "ingredient") ? JsonHelper.getArray(jsonObject, "ingredient") : JsonHelper.getObject(jsonObject, "ingredient");
            Ingredient ingredient = Ingredient.fromJson(jsonElement, false);
            if (jsonElement.isJsonObject()) {
                ingredient = getCountedIngredientFromIngredient((JsonObject) jsonElement, ingredient);
            }

            JsonElement jsonElement2 = JsonHelper.hasArray(jsonObject, "extraingredient") ? JsonHelper.getArray(jsonObject, "extraingredient") : JsonHelper.getObject(jsonObject, "extraingredient");
            Ingredient extraIngredient = Ingredient.fromJson(jsonElement2, false);
            if (jsonElement2.isJsonObject()) {
                extraIngredient = getCountedIngredientFromIngredient((JsonObject) jsonElement2, extraIngredient);
            }

            ItemStack itemStack;
            if (JsonHelper.hasJsonObject(jsonObject, "result")) {
                JsonObject jsonObject2 = JsonHelper.getObject(jsonObject, "result");
                String result = jsonObject2.get("item").getAsString();
                itemStack = new ItemStack(Registries.ITEM.getOrEmpty(new Identifier(result)).orElseThrow(() -> new IllegalStateException("Item: " + result + " does not exist")),
                        jsonObject2.has("count") ? jsonObject2.get("count").getAsInt() : 1);
            } else {
                String result = JsonHelper.getString(jsonObject, "result");
                itemStack = new ItemStack(Registries.ITEM.getOrEmpty(new Identifier(result)).orElseThrow(() -> new IllegalStateException("Item: " + result + " does not exist")));
            }
            float f = JsonHelper.getFloat(jsonObject, "experience", 0.0f);
            int i = JsonHelper.getInt(jsonObject, "cookingtime", 100);
            return new ExtraBlastingRecipe(identifier, group, cookingRecipeCategory, ingredient, extraIngredient, itemStack, f, i);
        }

        @Override
        public ExtraBlastingRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            String string = packetByteBuf.readString();
            CookingRecipeCategory cookingRecipeCategory = packetByteBuf.readEnumConstant(CookingRecipeCategory.class);
            Ingredient ingredient = Ingredient.fromPacket(packetByteBuf);
            Ingredient extraIngredient = Ingredient.fromPacket(packetByteBuf);
            ItemStack itemStack = packetByteBuf.readItemStack();
            float f = packetByteBuf.readFloat();
            int i = packetByteBuf.readVarInt();
            return new ExtraBlastingRecipe(identifier, string, cookingRecipeCategory, ingredient, extraIngredient, itemStack, f, i);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, ExtraBlastingRecipe extraBlastingRecipe) {
            packetByteBuf.writeString(extraBlastingRecipe.group);
            packetByteBuf.writeEnumConstant(extraBlastingRecipe.getCategory());
            extraBlastingRecipe.input.write(packetByteBuf);
            extraBlastingRecipe.extraInput.write(packetByteBuf);
            packetByteBuf.writeItemStack(extraBlastingRecipe.output);
            packetByteBuf.writeFloat(extraBlastingRecipe.experience);
            packetByteBuf.writeVarInt(extraBlastingRecipe.cookTime);
        }

        private static Ingredient getCountedIngredientFromIngredient(JsonObject jsonObject, Ingredient extraIngredient) {
            if (jsonObject.has("count")) {
                int count = jsonObject.get("count").getAsInt();
                extraIngredient = Ingredient.ofStacks(Stream.of(extraIngredient.getMatchingStacks()).map((stack) -> {
                    stack.setCount(count);
                    return stack;
                }));

            }
            return extraIngredient;
        }

    }
}
