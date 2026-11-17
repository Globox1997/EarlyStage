package net.earlystage.init;

import net.earlystage.EarlyStageMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class TagInit {

    // Item
    public static final TagKey<Item> BARK_ITEMS = TagKey.create(Registries.ITEM, EarlyStageMain.identifierOf("bark_items"));
    public static final TagKey<Item> ROCK_ITEMS = TagKey.create(Registries.ITEM, EarlyStageMain.identifierOf("rock_items"));
    // Block
    public static final TagKey<Block> ROCK_FEATURE_BLOCKS = TagKey.create(Registries.BLOCK, EarlyStageMain.identifierOf("rock_feature_blocks"));
    // Biome
    public static final TagKey<Biome> ROCK_FEATURE_BIOMES = TagKey.create(Registries.BIOME, EarlyStageMain.identifierOf("rock_feature_biomes"));

    public static void init() {
    }

}
