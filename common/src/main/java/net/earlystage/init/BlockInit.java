package net.earlystage.init;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import net.earlystage.EarlyStageMain;
import net.earlystage.block.*;
import net.earlystage.block.entity.CraftingRockBlockEntity;
import net.earlystage.block.entity.SieveBlockEntity;
import net.earlystage.item.CraftingRockBlockItem;
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

    public static Block ROCK;
    public static Block FLINT;

    public static Block SIEVE;
    public static Block REDSTONE_SIEVE;

    public static Block CRAFTING_ROCK;

    public static Block STEEL_BLOCK;
    public static Block DAMASCUS_STEEL_BLOCK;

    public static BlockEntityType<SieveBlockEntity> SIEVE_ENTITY;
    public static BlockEntityType<CraftingRockBlockEntity> CRAFTING_ROCK_ENTITY;

    public static void registerBlocks(BiConsumer<Identifier, Block> registrar) {
        ROCK = register(registrar, "rock", RockBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instabreak());
        FLINT = register(registrar, "flint", FlintBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instabreak());

        SIEVE = register(registrar, "sieve", SieveBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
        REDSTONE_SIEVE = register(registrar, "redstone_sieve", RedstoneSieveBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE));

        CRAFTING_ROCK = register(registrar, "crafting_rock", CraftingRockBlock::new,
                BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(1.5f, 6.0f));

        STEEL_BLOCK = register(registrar, "steel_block", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
        DAMASCUS_STEEL_BLOCK = register(registrar, "damascus_steel_block", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    }

    public static void registerItems(BiConsumer<Identifier, Item> registrar) {
        registerBlockItem(registrar, "rock", ROCK, BlockItem::new);
        registerBlockItem(registrar, "flint", FLINT, BlockItem::new);
        registerBlockItem(registrar, "sieve", SIEVE, BlockItem::new);
        registerBlockItem(registrar, "redstone_sieve", REDSTONE_SIEVE, BlockItem::new);
        registerBlockItem(registrar, "crafting_rock", CRAFTING_ROCK, CraftingRockBlockItem::new);
        registerBlockItem(registrar, "steel_block", STEEL_BLOCK, BlockItem::new);
        registerBlockItem(registrar, "damascus_steel_block", DAMASCUS_STEEL_BLOCK, BlockItem::new);
    }

    public static void registerBlockEntities(BiConsumer<Identifier, BlockEntityType<?>> registrar) {
        SIEVE_ENTITY = new BlockEntityType<>(SieveBlockEntity::new, Set.of(SIEVE, REDSTONE_SIEVE));
        registrar.accept(EarlyStageMain.identifierOf("sieve_entity"), SIEVE_ENTITY);
        CRAFTING_ROCK_ENTITY = new BlockEntityType<>(CraftingRockBlockEntity::new, Set.of(CRAFTING_ROCK));
        registrar.accept(EarlyStageMain.identifierOf("crafting_rock_entity"), CRAFTING_ROCK_ENTITY);
    }

    private static Block register(BiConsumer<Identifier, Block> registrar, String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
        Identifier identifier = EarlyStageMain.identifierOf(id);
        Block block = factory.apply(properties.setId(ResourceKey.create(Registries.BLOCK, identifier)));
        registrar.accept(identifier, block);
        return block;
    }

    private static void registerBlockItem(BiConsumer<Identifier, Item> registrar, String id, Block block, BiFunction<Block, Item.Properties, Item> itemFactory) {
        Identifier identifier = EarlyStageMain.identifierOf(id);
        Item item = itemFactory.apply(block, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, identifier)).useBlockDescriptionPrefix());
        registrar.accept(identifier, item);
        ItemInit.TAB_ITEMS.add(item);
    }
}
