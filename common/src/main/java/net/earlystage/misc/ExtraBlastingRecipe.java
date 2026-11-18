package net.earlystage.misc;

import java.util.List;
import java.util.Optional;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import org.jetbrains.annotations.Nullable;

import net.earlystage.EarlyStageMain;
import net.earlystage.init.RecipeInit;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

public class ExtraBlastingRecipe extends AbstractCookingRecipe {
    private final int inputCount;
    private final Ingredient extraInput;
    private final int extraInputCount;
    @Nullable
    private PlacementInfo extraPlacementInfo;

    public ExtraBlastingRecipe(Recipe.CommonInfo commonInfo, AbstractCookingRecipe.CookingBookInfo bookInfo, Ingredient input, int inputCount, Ingredient extraInput, int extraInputCount,
            ItemStackTemplate result, float experience, int cookTime) {
        super(commonInfo, bookInfo, input, result, experience, cookTime);
        this.inputCount = Math.max(1, inputCount);
        this.extraInput = extraInput;
        this.extraInputCount = Math.max(1, extraInputCount);
    }

    private ExtraBlastingRecipe(Recipe.CommonInfo commonInfo, AbstractCookingRecipe.CookingBookInfo bookInfo, CountedIngredient input, CountedIngredient extraInput, ItemStackTemplate result,
            float experience, int cookTime) {
        this(commonInfo, bookInfo, input.ingredient(), input.count(), extraInput.ingredient(), extraInput.count(), result, experience, cookTime);
    }

    public record CountedIngredient(Ingredient ingredient, int count) {

        private static final MapCodec<Ingredient> OBJECT_INGREDIENT_CODEC = Codec.mapPair(Ingredient.CODEC.optionalFieldOf("ingredient"), Ingredient.CODEC.optionalFieldOf("item")).flatXmap(
                pair -> pair.getFirst().or(pair::getSecond).map(DataResult::success).orElseGet(() -> DataResult.error(() -> "Missing ingredient")),
                ingredient -> DataResult.success(Pair.of(Optional.of(ingredient), Optional.empty())));

        public static final Codec<CountedIngredient> CODEC = Codec.withAlternative(
                RecordCodecBuilder.<CountedIngredient>mapCodec(instance -> instance.group(
                        OBJECT_INGREDIENT_CODEC.forGetter(CountedIngredient::ingredient),
                        ExtraCodecs.POSITIVE_INT.optionalFieldOf("count", 1).forGetter(CountedIngredient::count)).apply(instance, CountedIngredient::new)).codec(),
                Ingredient.CODEC, ingredient -> new CountedIngredient(ingredient, 1));

        public static final StreamCodec<RegistryFriendlyByteBuf, CountedIngredient> STREAM_CODEC = StreamCodec.composite(Ingredient.CONTENTS_STREAM_CODEC, CountedIngredient::ingredient,
                ByteBufCodecs.VAR_INT, CountedIngredient::count, CountedIngredient::new);
    }

    public static Optional<RecipeHolder<ExtraBlastingRecipe>> getRecipeFor(ServerLevel level, ItemStack input, ItemStack extraInput) {
        for (RecipeHolder<ExtraBlastingRecipe> recipe : EarlyStageMain.EXTRA_BLASTING_RECIPES) {
            if (recipe.value().matches(input, extraInput)) {
                return Optional.of(recipe);
            }
        }
        return Optional.empty();
    }

    private static boolean test(@Nullable ItemStack stack, Ingredient ingredient, int count) {
        return stack != null && !stack.isEmpty() && ingredient.test(stack) && stack.getCount() >= count;
    }

    public boolean matches(ItemStack input, ItemStack extraInput) {
        return test(input, this.input(), this.inputCount) && test(extraInput, this.extraInput, this.extraInputCount);
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return test(input.item(), this.input(), this.inputCount);
    }

    public int getInputCount() {
        return this.inputCount;
    }

    public Ingredient getExtraInput() {
        return this.extraInput;
    }

    public int getExtraInputCount() {
        return this.extraInputCount;
    }

    public ItemStackTemplate getResult() {
        return this.result();
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.extraPlacementInfo == null) {
            this.extraPlacementInfo = PlacementInfo.create(List.of(this.input(), this.extraInput));
        }
        return this.extraPlacementInfo;
    }

    @Override
    protected Item furnaceIcon() {
        return Items.BLAST_FURNACE;
    }

    @Override
    public RecipeSerializer<ExtraBlastingRecipe> getSerializer() {
        return RecipeInit.EXTRA_BLASTING_SERIALIZER;
    }

    @Override
    public RecipeType<ExtraBlastingRecipe> getType() {
        return RecipeInit.EXTRA_BLASTING;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.BLAST_FURNACE_MISC;
    }

    public static final MapCodec<ExtraBlastingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Recipe.CommonInfo.MAP_CODEC.forGetter(recipe -> recipe.commonInfo),
            AbstractCookingRecipe.CookingBookInfo.MAP_CODEC.forGetter(recipe -> recipe.bookInfo),
            CountedIngredient.CODEC.fieldOf("ingredient").forGetter(recipe -> new CountedIngredient(recipe.input(), recipe.inputCount)),
            CountedIngredient.CODEC.fieldOf("extraingredient").forGetter(recipe -> new CountedIngredient(recipe.extraInput, recipe.extraInputCount)),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(ExtraBlastingRecipe::result), Codec.FLOAT.optionalFieldOf("experience", 0.0f).forGetter(AbstractCookingRecipe::experience),
            Codec.INT.optionalFieldOf("cookingtime", 100).forGetter(AbstractCookingRecipe::cookingTime)).apply(instance, ExtraBlastingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ExtraBlastingRecipe> STREAM_CODEC = StreamCodec.composite(Recipe.CommonInfo.STREAM_CODEC, recipe -> recipe.commonInfo,
            AbstractCookingRecipe.CookingBookInfo.STREAM_CODEC, recipe -> recipe.bookInfo, CountedIngredient.STREAM_CODEC, recipe -> new CountedIngredient(recipe.input(), recipe.inputCount),
            CountedIngredient.STREAM_CODEC, recipe -> new CountedIngredient(recipe.extraInput, recipe.extraInputCount), ItemStackTemplate.STREAM_CODEC, ExtraBlastingRecipe::result,
            ByteBufCodecs.FLOAT, AbstractCookingRecipe::experience, ByteBufCodecs.VAR_INT, AbstractCookingRecipe::cookingTime, ExtraBlastingRecipe::new);

}
