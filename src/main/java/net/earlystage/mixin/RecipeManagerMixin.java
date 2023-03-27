package net.earlystage.mixin;

import com.google.gson.JsonElement;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

    private static final boolean isEnvironmentZLoaded = FabricLoader.getInstance().isModLoaded("environmentz");

    @Inject(method = "apply", at = @At(value = "INVOKE_ASSIGN", target = "Lcom/google/common/collect/ImmutableMap;builder()Lcom/google/common/collect/ImmutableMap$Builder;"))
    private void applyMixin(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo info) {
        if (!isEnvironmentZLoaded) {
            map.remove(new Identifier("earlystage", "heating_stones_from_rock"));
        }

    }

}
