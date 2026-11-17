package net.earlystage.init;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import net.earlystage.EarlyStageMain;
import net.earlystage.block.*;
import net.earlystage.block.entity.CraftingRockBlockEntity;
import net.earlystage.block.entity.SieveBlockEntity;
import net.earlystage.item.CraftingRockBlockItem;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class BlockInit {

    public static final Block ROCK = register("rock", RockBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instabreak());
    public static final Block FLINT = register("flint", FlintBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instabreak());

    public static final Block SIEVE = register("sieve", SieveBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final Block REDSTONE_SIEVE = register("redstone_sieve", RedstoneSieveBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE));

    public static final Block CRAFTING_ROCK = register("crafting_rock", CraftingRockBlock::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(1.5f, 6.0f), CraftingRockBlockItem::new);

    public static final Block STEEL_BLOCK = register("steel_block", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final Block DAMASCUS_STEEL_BLOCK = register("damascus_steel_block", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));

    public static BlockEntityType<SieveBlockEntity> SIEVE_ENTITY;
    public static BlockEntityType<CraftingRockBlockEntity> CRAFTING_ROCK_ENTITY;

    private static Block register(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
        return register(id, factory, properties, BlockItem::new);
    }

    private static Block register(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties, BiFunction<Block, Item.Properties, Item> itemFactory) {
        Identifier identifier = EarlyStageMain.identifierOf(id);
        Block block = Registry.register(BuiltInRegistries.BLOCK, identifier, factory.apply(properties.setId(ResourceKey.create(Registries.BLOCK, identifier))));
        Item item = Registry.register(BuiltInRegistries.ITEM, identifier,
                itemFactory.apply(block, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, identifier)).useBlockDescriptionPrefix()));
        CreativeModeTabEvents.modifyOutputEvent(ItemInit.EARLYSTAGE_ITEM_GROUP).register(entries -> entries.accept(item));
        return block;
    }

    public static void init() {
        SIEVE_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, EarlyStageMain.identifierOf("sieve_entity"), new BlockEntityType<>(SieveBlockEntity::new, Set.of(SIEVE, REDSTONE_SIEVE)));
        CRAFTING_ROCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, EarlyStageMain.identifierOf("crafting_rock_entity"),
                new BlockEntityType<>(CraftingRockBlockEntity::new, Set.of(CRAFTING_ROCK)));
    }
}
