package net.earlystage.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class TagInit {

    // Item
    public static final TagKey<Item> UNUSABLE_CRAFTING_ROCK_ITEMS = TagKey.of(Registry.ITEM_KEY, new Identifier("earlystage", "unusable_crafting_rock_items"));
    // Block
    public static final TagKey<Block> ROCK_FEATURE_BLOCKS = TagKey.of(Registry.BLOCK_KEY, new Identifier("earlystage", "rock_feature_blocks"));
    // Biome
    public static final TagKey<Biome> ROCK_FEATURE_BIOMES = TagKey.of(Registry.BIOME_KEY, new Identifier("earlystage", "rock_feature_biomes"));

    public static void init() {
    }

}
