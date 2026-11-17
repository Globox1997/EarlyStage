package net.earlystage.init;

import net.earlystage.EarlyStageMain;
import net.earlystage.block.*;
import net.earlystage.block.entity.CraftingRockBlockEntity;
import net.earlystage.block.entity.SieveBlockEntity;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class BlockInit {

    public static final Block ROCK = register("rock", new RockBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instabreak()));
    public static final Block FLINT = register("flint", new FlintBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instabreak()));

    public static final Block SIEVE = register("sieve", new SieveBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final Block REDSTONE_SIEVE = register("redstone_sieve", new RedstoneSieveBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE)));

    public static final Block CRAFTING_ROCK = register("crafting_rock", new CraftingRockBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(1.5f, 6.0f)));

    public static final Block STEEL_BLOCK = register("steel_block", new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final Block DAMASCUS_STEEL_BLOCK = register("damascus_steel_block", new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

    public static BlockEntityType<SieveBlockEntity> SIEVE_ENTITY;
    public static BlockEntityType<CraftingRockBlockEntity> CRAFTING_ROCK_ENTITY;

    private static Block register(String id, Block block) {
        return register(EarlyStageMain.identifierOf(id), block);
    }

    private static Block register(ResourceLocation id, Block block) {
        Item item = Registry.register(BuiltInRegistries.ITEM, id, new BlockItem(block, new Item.Properties()));
        ItemGroupEvents.modifyEntriesEvent(ItemInit.EARLYSTAGE_ITEM_GROUP).register(entries -> entries.accept(item));
        return Registry.register(BuiltInRegistries.BLOCK, id, block);
    }

    public static void init() {
        SIEVE_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, EarlyStageMain.identifierOf("sieve_entity"), BlockEntityType.Builder.of(SieveBlockEntity::new, SIEVE, REDSTONE_SIEVE).build(null));
        CRAFTING_ROCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, EarlyStageMain.identifierOf("crafting_rock_entity"),
                BlockEntityType.Builder.of(CraftingRockBlockEntity::new, CRAFTING_ROCK).build(null));
    }
}
