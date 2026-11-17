package net.earlystage.misc;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import org.jetbrains.annotations.Nullable;

import net.earlystage.init.RecipeInit;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class ExtraBlastingRecipe implements Recipe<ExtraBlastingRecipeInput> {
    private final CookingBookCategory category;
    private final String group;
    private final Ingredient input;
    private final int inputCount;
    private final Ingredient extraInput;
    private final int extraInputCount;
    private final ItemStack output;
    private final float experience;
    private final int cookTime;

    public ExtraBlastingRecipe(String group, CookingBookCategory category, Ingredient input, int inputCount, Ingredient extraInput, int extraInputCount, ItemStack output, float experience,
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

    private ExtraBlastingRecipe(String group, CookingBookCategory category, CountedIngredient input, CountedIngredient extraInput, ItemStack output, float experience, int cookTime) {
        this(group, category, input.ingredient(), input.count(), extraInput.ingredient(), extraInput.count(), output, experience, cookTime);
    }

    public record CountedIngredient(Ingredient ingredient, int count) {

        public static final Codec<CountedIngredient> CODEC = Codec.withAlternative(
                RecordCodecBuilder.<CountedIngredient>mapCodec(instance -> instance.group(MapCodec.assumeMapUnsafe(Ingredient.CODEC_NONEMPTY).forGetter(CountedIngredient::ingredient),
                        ExtraCodecs.POSITIVE_INT.optionalFieldOf("count", 1).forGetter(CountedIngredient::count)).apply(instance, CountedIngredient::new)).codec(),
                Ingredient.CODEC_NONEMPTY, ingredient -> new CountedIngredient(ingredient, 1));
    }

    @Override
    public ItemStack getToastSymbol() {
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
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> defaultedList = NonNullList.create();
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
    public ItemStack getResultItem(Provider wrapperLookup) {
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

    public CookingBookCategory getCategory() {
        return this.category;
    }

    public static class Serializer implements RecipeSerializer<ExtraBlastingRecipe> {

        public static final MapCodec<ExtraBlastingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                CookingBookCategory.CODEC.fieldOf("category").orElse(CookingBookCategory.MISC).forGetter(recipe -> recipe.category),
                CountedIngredient.CODEC.fieldOf("ingredient").forGetter(recipe -> new CountedIngredient(recipe.input, recipe.inputCount)),
                CountedIngredient.CODEC.fieldOf("extraingredient").forGetter(recipe -> new CountedIngredient(recipe.extraInput, recipe.extraInputCount)),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
                Codec.FLOAT.fieldOf("experience").orElse(Float.valueOf(0.0f)).forGetter(recipe -> Float.valueOf(recipe.experience)),
                Codec.INT.fieldOf("cookingtime").orElse(100).forGetter(recipe -> recipe.cookTime)).apply(instance, ExtraBlastingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ExtraBlastingRecipe> PACKET_CODEC = StreamCodec.of(Serializer::write, Serializer::read);

        @Override
        public MapCodec<ExtraBlastingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ExtraBlastingRecipe> streamCodec() {
            return PACKET_CODEC;
        }

        private static ExtraBlastingRecipe read(RegistryFriendlyByteBuf buf) {
            String group = buf.readUtf();
            CookingBookCategory cookingRecipeCategory = buf.readEnum(CookingBookCategory.class);
            Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            int inputCount = buf.readVarInt();
            Ingredient extraInput = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            int extraInputCount = buf.readVarInt();
            ItemStack itemStack = (ItemStack) ItemStack.STREAM_CODEC.decode(buf);
            float experience = buf.readFloat();
            int cookTime = buf.readVarInt();
            return new ExtraBlastingRecipe(group, cookingRecipeCategory, input, inputCount, extraInput, extraInputCount, itemStack, experience, cookTime);
        }

        private static void write(RegistryFriendlyByteBuf buf, ExtraBlastingRecipe extraBlastingRecipe) {
            buf.writeUtf(extraBlastingRecipe.group);
            buf.writeEnum(extraBlastingRecipe.getCategory());
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, extraBlastingRecipe.input);
            buf.writeVarInt(extraBlastingRecipe.inputCount);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, extraBlastingRecipe.extraInput);
            buf.writeVarInt(extraBlastingRecipe.extraInputCount);
            ItemStack.STREAM_CODEC.encode(buf, extraBlastingRecipe.output);
            buf.writeFloat(extraBlastingRecipe.experience);
            buf.writeVarInt(extraBlastingRecipe.cookTime);
        }

    }

    @Override
    public boolean matches(ExtraBlastingRecipeInput extraBlastingRecipeInput, Level world) {
        return test(extraBlastingRecipeInput.input(), this.input, this.inputCount) && test(extraBlastingRecipeInput.extraInput(), this.extraInput, this.extraInputCount);
    }

    @Override
    public ItemStack assemble(ExtraBlastingRecipeInput extraBlastingRecipeInput, Provider wrapperLookup) {
        return this.output.copy();
    }

}
