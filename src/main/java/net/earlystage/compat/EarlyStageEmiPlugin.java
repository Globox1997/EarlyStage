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
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;

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
        for (RecipeEntry<ExtraBlastingRecipe> recipe : manager.listAllOfType(RecipeInit.EXTRA_BLASTING)) {
            emiRegistry.addRecipe(new ExtraBlastingEmiRecipe(recipe.value()));
        }
        emiRegistry.addCategory(CRAFTING_ROCK_CATEGORY);
        emiRegistry.addWorkstation(CRAFTING_ROCK_CATEGORY, CRAFTING_ROCK);

        DynamicRegistryManager registryManager = MinecraftClient.getInstance().world != null ? MinecraftClient.getInstance().world.getRegistryManager() : null;
        if (registryManager != null) {
            for (Identifier recipeId : EarlyStageMain.CRAFTING_ROCK_RECIPES) {
                manager.get(recipeId).ifPresent(recipe -> {
                    if (recipe.value() instanceof CraftingRecipe craftingRecipe) {
                        ItemStack output = craftingRecipe.getResult(registryManager);
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
