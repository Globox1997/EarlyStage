package net.earlystage.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class TagInit {

    // Item
    public static final TagKey<Item> USABLE_CRAFTING_ROCK_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier("earlystage", "usable_crafting_rock_items"));
    public static final TagKey<Item> BARK_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier("earlystage", "bark_items"));
    // Block
    public static final TagKey<Block> ROCK_FEATURE_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier("earlystage", "rock_feature_blocks"));
    // Biome
    public static final TagKey<Biome> ROCK_FEATURE_BIOMES = TagKey.of(RegistryKeys.BIOME, new Identifier("earlystage", "rock_feature_biomes"));

    public static void init() {
    }

}
