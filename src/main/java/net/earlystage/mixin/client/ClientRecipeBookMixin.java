package net.earlystage.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.earlystage.init.RecipeInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.recipe.RecipeEntry;

@Environment(EnvType.CLIENT)
@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin {

    @Inject(method = "getGroupForRecipe", at = @At(value = "FIELD", target = "Lnet/minecraft/recipe/RecipeType;SMITHING:Lnet/minecraft/recipe/RecipeType;", ordinal = 0), cancellable = true)
    private static void getGroupForRecipeMixin(RecipeEntry<?> recipe, CallbackInfoReturnable<RecipeBookGroup> info) {
        if (recipe.value().getType().equals(RecipeInit.EXTRA_BLASTING)) {
            info.setReturnValue(RecipeBookGroup.BLAST_FURNACE_MISC);
        }
    }
}
