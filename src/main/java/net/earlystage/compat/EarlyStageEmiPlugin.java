package net.earlystage.compat;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import net.earlystage.EarlyStageMain;
import net.earlystage.data.SieveDropTemplate;
import net.earlystage.init.BlockInit;
import net.earlystage.init.RecipeInit;
import net.earlystage.init.RenderInit;
import net.earlystage.misc.ExtraBlastingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

public class EarlyStageEmiPlugin implements EmiPlugin {

    public static final EmiStack CRAFTING_ROCK = EmiStack.of(BlockInit.CRAFTING_ROCK.asItem());
    public static final EmiRecipeCategory CRAFTING_ROCK_CATEGORY = new EmiRecipeCategory(EarlyStageMain.identifierOf("crafting_rock"), CRAFTING_ROCK,
            new EmiTexture(RenderInit.GUI_ICON_TEXTURES, 18, 0, 16, 16));

    public static final EmiStack SIEVE = EmiStack.of(BlockInit.SIEVE);
    public static final EmiRecipeCategory SIEVE_CATEGORY = new EmiRecipeCategory(EarlyStageMain.identifierOf("sieve"), SIEVE,
            new EmiTexture(RenderInit.SIEVE, 0, 0, 16, 16));

    @Override
    public void register(EmiRegistry emiRegistry) {
        RecipeManager manager = emiRegistry.getRecipeManager();
        for (RecipeHolder<ExtraBlastingRecipe> recipe : manager.getAllRecipesFor(RecipeInit.EXTRA_BLASTING)) {
            emiRegistry.addRecipe(new ExtraBlastingEmiRecipe(recipe.value()));
        }
        emiRegistry.addCategory(CRAFTING_ROCK_CATEGORY);
        emiRegistry.addWorkstation(CRAFTING_ROCK_CATEGORY, CRAFTING_ROCK);

        RegistryAccess registryManager = Minecraft.getInstance().level != null ? Minecraft.getInstance().level.registryAccess() : null;
        if (registryManager != null) {
            for (ResourceLocation recipeId : EarlyStageMain.CRAFTING_ROCK_RECIPES) {
                manager.byKey(recipeId).ifPresent(recipe -> {
                    if (recipe.value() instanceof CraftingRecipe craftingRecipe) {
                        ItemStack output = craftingRecipe.getResultItem(registryManager);
                        if (!output.isEmpty()) {
                            emiRegistry.addRecipe(new RockEmiRecipe(recipeId, craftingRecipe, output));
                        }
                    }
                });
            }
        }

        emiRegistry.addCategory(SIEVE_CATEGORY);
        emiRegistry.addWorkstation(SIEVE_CATEGORY, SIEVE);

        for (SieveDropTemplate template : EarlyStageMain.SIEVE_DROP_TEMPLATES) {
            emiRegistry.addRecipe(new SieveEmiRecipe(template));
        }
    }

}
