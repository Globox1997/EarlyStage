package net.earlystage.init;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.earlystage.EarlyStageMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class WorldInit {

    public static final ResourceKey<PlacedFeature> ROCK = ResourceKey.create(Registries.PLACED_FEATURE, EarlyStageMain.identifierOf("rock"));
    public static final ResourceKey<PlacedFeature> FLINT = ResourceKey.create(Registries.PLACED_FEATURE, EarlyStageMain.identifierOf("flint"));
    public static final GenerationStep.Decoration STEP = GenerationStep.Decoration.TOP_LAYER_MODIFICATION;

    public static boolean isRockBiome(Predicate<TagKey<Biome>> hasTag) {
        return hasTag.test(BiomeTags.IS_FOREST) || hasTag.test(BiomeTags.IS_HILL) || hasTag.test(BiomeTags.IS_MOUNTAIN) || hasTag.test(BiomeTags.IS_RIVER)
                || hasTag.test(TagInit.ROCK_FEATURE_BIOMES);
    }

    public static List<ResourceKey<PlacedFeature>> getEnabledFeatures() {
        List<ResourceKey<PlacedFeature>> features = new ArrayList<>();
        if (ConfigInit.CONFIG.generateRocks) {
            features.add(ROCK);
        }
        if (ConfigInit.CONFIG.generateFlint) {
            features.add(FLINT);
        }
        return features;
    }
}
