package net.earlystage.misc;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import org.jetbrains.annotations.Nullable;

import net.earlystage.init.RecipeInit;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

public class ExtraBlastingRecipe implements Recipe<ExtraBlastingRecipeInput> {
    private final CookingRecipeCategory category;
    private final String group;
    private final Ingredient input;
    private final int inputCount;
    private final Ingredient extraInput;
    private final int extraInputCount;
    private final ItemStack output;
    private final float experience;
    private final int cookTime;

    public ExtraBlastingRecipe(String group, CookingRecipeCategory category, Ingredient input, int inputCount, Ingredient extraInput, int extraInputCount, ItemStack output, float experience,
            int cookTime) {
        this.category = category;
        this.group = group;
        this.input = input;
        this.inputCount = Math.max(1, inputCount);
        this.extraInput = extraInput;
        this.extraInputCount = Math.max(1, extraInputCount);
        this.output = output;
        this.experience = experience;
        this.cookTime = cookTime;
    }

    private ExtraBlastingRecipe(String group, CookingRecipeCategory category, CountedIngredient input, CountedIngredient extraInput, ItemStack output, float experience, int cookTime) {
        this(group, category, input.ingredient(), input.count(), extraInput.ingredient(), extraInput.count(), output, experience, cookTime);
    }

    public record CountedIngredient(Ingredient ingredient, int count) {

        public static final Codec<CountedIngredient> CODEC = Codec.withAlternative(
                RecordCodecBuilder.<CountedIngredient>mapCodec(instance -> instance.group(MapCodec.assumeMapUnsafe(Ingredient.DISALLOW_EMPTY_CODEC).forGetter(CountedIngredient::ingredient),
                        Codecs.POSITIVE_INT.optionalFieldOf("count", 1).forGetter(CountedIngredient::count)).apply(instance, CountedIngredient::new)).codec(),
                Ingredient.DISALLOW_EMPTY_CODEC, ingredient -> new CountedIngredient(ingredient, 1));
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Blocks.BLAST_FURNACE);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeInit.EXTRA_BLASTING_SERIALIZER;
    }

    private static boolean test(@Nullable ItemStack stack, Ingredient ingredient, int count) {
        return stack != null && !stack.isEmpty() && ingredient.test(stack) && stack.getCount() >= count;
    }

    public int getInputCount() {
        return this.inputCount;
    }

    public int getExtraInputCount() {
        return this.extraInputCount;
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

    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public ItemStack getResult(WrapperLookup wrapperLookup) {
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
    public RecipeType<?> getType() {
        return RecipeInit.EXTRA_BLASTING;
    }

    public CookingRecipeCategory getCategory() {
        return this.category;
    }

    public static class Serializer implements RecipeSerializer<ExtraBlastingRecipe> {

        public static final MapCodec<ExtraBlastingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                CookingRecipeCategory.CODEC.fieldOf("category").orElse(CookingRecipeCategory.MISC).forGetter(recipe -> recipe.category),
                CountedIngredient.CODEC.fieldOf("ingredient").forGetter(recipe -> new CountedIngredient(recipe.input, recipe.inputCount)),
                CountedIngredient.CODEC.fieldOf("extraingredient").forGetter(recipe -> new CountedIngredient(recipe.extraInput, recipe.extraInputCount)),
                ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
                Codec.FLOAT.fieldOf("experience").orElse(Float.valueOf(0.0f)).forGetter(recipe -> Float.valueOf(recipe.experience)),
                Codec.INT.fieldOf("cookingtime").orElse(100).forGetter(recipe -> recipe.cookTime)).apply(instance, ExtraBlastingRecipe::new));

        public static final PacketCodec<RegistryByteBuf, ExtraBlastingRecipe> PACKET_CODEC = PacketCodec.ofStatic(Serializer::write, Serializer::read);

        @Override
        public MapCodec<ExtraBlastingRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, ExtraBlastingRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static ExtraBlastingRecipe read(RegistryByteBuf buf) {
            String group = buf.readString();
            CookingRecipeCategory cookingRecipeCategory = buf.readEnumConstant(CookingRecipeCategory.class);
            Ingredient input = Ingredient.PACKET_CODEC.decode(buf);
            int inputCount = buf.readVarInt();
            Ingredient extraInput = Ingredient.PACKET_CODEC.decode(buf);
            int extraInputCount = buf.readVarInt();
            ItemStack itemStack = (ItemStack) ItemStack.PACKET_CODEC.decode(buf);
            float experience = buf.readFloat();
            int cookTime = buf.readVarInt();
            return new ExtraBlastingRecipe(group, cookingRecipeCategory, input, inputCount, extraInput, extraInputCount, itemStack, experience, cookTime);
        }

        private static void write(RegistryByteBuf buf, ExtraBlastingRecipe extraBlastingRecipe) {
            buf.writeString(extraBlastingRecipe.group);
            buf.writeEnumConstant(extraBlastingRecipe.getCategory());
            Ingredient.PACKET_CODEC.encode(buf, extraBlastingRecipe.input);
            buf.writeVarInt(extraBlastingRecipe.inputCount);
            Ingredient.PACKET_CODEC.encode(buf, extraBlastingRecipe.extraInput);
            buf.writeVarInt(extraBlastingRecipe.extraInputCount);
            ItemStack.PACKET_CODEC.encode(buf, extraBlastingRecipe.output);
            buf.writeFloat(extraBlastingRecipe.experience);
            buf.writeVarInt(extraBlastingRecipe.cookTime);
        }

    }

    @Override
    public boolean matches(ExtraBlastingRecipeInput extraBlastingRecipeInput, World world) {
        return test(extraBlastingRecipeInput.input(), this.input, this.inputCount) && test(extraBlastingRecipeInput.extraInput(), this.extraInput, this.extraInputCount);
    }

    @Override
    public ItemStack craft(ExtraBlastingRecipeInput extraBlastingRecipeInput, WrapperLookup wrapperLookup) {
        return this.output.copy();
    }

}
