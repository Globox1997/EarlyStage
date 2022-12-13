package net.earlystage.init;

import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TagInit {

    public static final TagKey<Block> ROCK_FEATURE_BLOCKS = TagKey.of(Registry.BLOCK_KEY, new Identifier("earlystage", "rock_feature_blocks"));

    public static void init() {
    }

}
