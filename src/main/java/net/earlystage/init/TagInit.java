package net.earlystage.init;

import net.earlystage.EarlyStageMain;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

public class TagInit {

    // Item
    public static final TagKey<Item> BARK_ITEMS = TagKey.of(RegistryKeys.ITEM, EarlyStageMain.identifierOf("bark_items"));
    public static final TagKey<Item> ROCK_ITEMS = TagKey.of(RegistryKeys.ITEM, EarlyStageMain.identifierOf("rock_items"));
    // Block
    public static final TagKey<Block> ROCK_FEATURE_BLOCKS = TagKey.of(RegistryKeys.BLOCK, EarlyStageMain.identifierOf("rock_feature_blocks"));
    // Biome
    public static final TagKey<Biome> ROCK_FEATURE_BIOMES = TagKey.of(RegistryKeys.BIOME, EarlyStageMain.identifierOf("rock_feature_biomes"));

    public static void init() {
    }

}
