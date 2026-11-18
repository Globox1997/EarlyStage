package net.earlystage.compat;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.earlystage.EarlyStageMain;
import net.earlystage.data.CraftingRockRecipe;
import net.earlystage.data.SieveDropTemplate;
import net.earlystage.init.BlockInit;
import net.earlystage.misc.ExtraBlastingRecipe;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;

@JeiPlugin
public class EarlyStageJeiPlugin implements IModPlugin {

    public static final IRecipeType<CraftingRockRecipe> CRAFTING_ROCK = IRecipeType.create(EarlyStageMain.identifierOf("crafting_rock"), CraftingRockRecipe.class);
    public static final IRecipeType<SieveDropTemplate> SIEVE = IRecipeType.create(EarlyStageMain.identifierOf("sieve"), SieveDropTemplate.class);
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static final IRecipeType<RecipeHolder<ExtraBlastingRecipe>> EXTRA_BLASTING = IRecipeType.create(EarlyStageMain.identifierOf("blasting_extra"), (Class) RecipeHolder.class);

    @Override
    public Identifier getPluginUid() {
        return EarlyStageMain.identifierOf("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new CraftingRockJeiCategory(guiHelper), new SieveJeiCategory(guiHelper), new ExtraBlastingJeiCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(CRAFTING_ROCK, List.copyOf(EarlyStageMain.CRAFTING_ROCK_RECIPES));
        registration.addRecipes(SIEVE, new ArrayList<>(EarlyStageMain.SIEVE_DROP_TEMPLATES));
        registration.addRecipes(EXTRA_BLASTING, List.copyOf(EarlyStageMain.EXTRA_BLASTING_RECIPES));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(CRAFTING_ROCK, BlockInit.CRAFTING_ROCK);
        registration.addCraftingStation(SIEVE, BlockInit.SIEVE);
        registration.addCraftingStation(EXTRA_BLASTING, Items.BLAST_FURNACE);
    }

}
