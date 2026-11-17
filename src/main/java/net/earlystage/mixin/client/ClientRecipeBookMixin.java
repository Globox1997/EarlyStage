package net.earlystage.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.earlystage.init.RecipeInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeHolder;

@Environment(EnvType.CLIENT)
@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin {

    @Inject(method = "getCategory", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/crafting/RecipeType;SMITHING:Lnet/minecraft/world/item/crafting/RecipeType;", ordinal = 0), cancellable = true)
    private static void getGroupForRecipeMixin(RecipeHolder<?> recipe, CallbackInfoReturnable<RecipeBookCategories> info) {
        if (recipe.value().getType().equals(RecipeInit.EXTRA_BLASTING)) {
            info.setReturnValue(RecipeBookCategories.BLAST_FURNACE_MISC);
        }
    }
}
