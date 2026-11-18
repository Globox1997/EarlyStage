package net.earlystage.neoforge.world;

import java.util.List;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.earlystage.init.WorldInit;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

public record RockBiomeModifier(HolderSet<PlacedFeature> features) implements BiomeModifier {

    public static final MapCodec<RockBiomeModifier> CODEC = RecordCodecBuilder
            .mapCodec(instance -> instance.group(PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(RockBiomeModifier::features)).apply(instance, RockBiomeModifier::new));

    @Override
    public void modify(RegistryAccess registryAccess, Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD && WorldInit.isRockBiome(biome::is)) {
            List<ResourceKey<PlacedFeature>> enabledFeatures = WorldInit.getEnabledFeatures();
            for (Holder<PlacedFeature> feature : this.features) {
                if (feature.unwrapKey().filter(enabledFeatures::contains).isPresent()) {
                    builder.getGenerationSettings().addFeature(WorldInit.STEP, feature);
                }
            }
        }
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return CODEC;
    }

}
