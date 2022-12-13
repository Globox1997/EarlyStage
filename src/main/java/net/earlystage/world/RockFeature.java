package net.earlystage.world;

import java.util.List;

import net.earlystage.block.RockBlock;
import net.earlystage.block.enums.RockVariants;
import net.earlystage.init.BlockInit;
import net.earlystage.init.TagInit;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.BlockFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

public class RockFeature {

    public static ConfiguredFeature<?, ?> ROCK_FEATURE = new ConfiguredFeature<>(Feature.SIMPLE_BLOCK,
            new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(
                    DataPool.<BlockState>builder().add(BlockInit.ROCK.getDefaultState().with(RockBlock.FACING, Direction.NORTH).with(RockBlock.ROCK_TYPE, RockVariants.EXTRA_LARGE), 1)
                            .add(BlockInit.ROCK.getDefaultState().with(RockBlock.FACING, Direction.EAST).with(RockBlock.ROCK_TYPE, RockVariants.EXTRA_LARGE), 1)
                            .add(BlockInit.ROCK.getDefaultState().with(RockBlock.FACING, Direction.SOUTH).with(RockBlock.ROCK_TYPE, RockVariants.EXTRA_LARGE), 1)
                            .add(BlockInit.ROCK.getDefaultState().with(RockBlock.FACING, Direction.WEST).with(RockBlock.ROCK_TYPE, RockVariants.EXTRA_LARGE), 1)
                            .add(BlockInit.ROCK.getDefaultState().with(RockBlock.FACING, Direction.NORTH).with(RockBlock.ROCK_TYPE, RockVariants.LARGE), 3)
                            .add(BlockInit.ROCK.getDefaultState().with(RockBlock.FACING, Direction.EAST).with(RockBlock.ROCK_TYPE, RockVariants.LARGE), 3)
                            .add(BlockInit.ROCK.getDefaultState().with(RockBlock.FACING, Direction.SOUTH).with(RockBlock.ROCK_TYPE, RockVariants.LARGE), 3)
                            .add(BlockInit.ROCK.getDefaultState().with(RockBlock.FACING, Direction.WEST).with(RockBlock.ROCK_TYPE, RockVariants.LARGE), 3)
                            .add(BlockInit.ROCK.getDefaultState().with(RockBlock.FACING, Direction.NORTH).with(RockBlock.ROCK_TYPE, RockVariants.MEDIUM), 5)
                            .add(BlockInit.ROCK.getDefaultState().with(RockBlock.FACING, Direction.EAST).with(RockBlock.ROCK_TYPE, RockVariants.MEDIUM), 5)
                            .add(BlockInit.ROCK.getDefaultState().with(RockBlock.FACING, Direction.SOUTH).with(RockBlock.ROCK_TYPE, RockVariants.MEDIUM), 5)
                            .add(BlockInit.ROCK.getDefaultState().with(RockBlock.FACING, Direction.WEST).with(RockBlock.ROCK_TYPE, RockVariants.MEDIUM), 5)
                            .add(BlockInit.ROCK.getDefaultState().with(RockBlock.FACING, Direction.NORTH).with(RockBlock.ROCK_TYPE, RockVariants.SMALL), 8)
                            .add(BlockInit.ROCK.getDefaultState().with(RockBlock.FACING, Direction.EAST).with(RockBlock.ROCK_TYPE, RockVariants.SMALL), 8)
                            .add(BlockInit.ROCK.getDefaultState().with(RockBlock.FACING, Direction.SOUTH).with(RockBlock.ROCK_TYPE, RockVariants.SMALL), 8)
                            .add(BlockInit.ROCK.getDefaultState().with(RockBlock.FACING, Direction.WEST).with(RockBlock.ROCK_TYPE, RockVariants.SMALL), 8).build())));

    public static PlacedFeature ROCK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(ROCK_FEATURE),
            List.of(CountPlacementModifier.of(3), RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(),
                    BlockFilterPlacementModifier.of(BlockPredicate.bothOf(BlockPredicate.IS_AIR, BlockPredicate.matchingBlockTag(new Vec3i(0, -1, 0), TagInit.ROCK_FEATURE_BLOCKS)))));

    public static void init() {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier("earlystage", "rock"), ROCK_FEATURE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier("earlystage", "rock"), ROCK_PLACED_FEATURE);
    }

}
