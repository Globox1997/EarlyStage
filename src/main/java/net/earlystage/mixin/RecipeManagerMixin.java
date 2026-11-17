package net.earlystage.mixin;

import com.google.gson.JsonElement;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

    private static final boolean isEnvironmentZLoaded = FabricLoader.getInstance().isModLoaded("environmentz");

    @Inject(method = "apply", at = @At("HEAD"))
    private void applyMixin(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo info) {
        if (!isEnvironmentZLoaded) {
            map.remove(ResourceLocation.fromNamespaceAndPath("earlystage", "heating_stones_from_rock"));
        }

    }

}
