package net.earlystage.init;

import net.earlystage.block.*;
import net.earlystage.block.entity.CraftingRockBlockEntity;
import net.earlystage.block.entity.SieveBlockEntity;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BlockInit {

    public static final Block ROCK = register("rock", new RockBlock(FabricBlockSettings.create().mapColor(MapColor.STONE_GRAY).breakInstantly()));
    public static final Block FLINT = register("flint", new FlintBlock(FabricBlockSettings.create().mapColor(MapColor.STONE_GRAY).breakInstantly()));

    public static final Block SIEVE = register("sieve", new SieveBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS)));
    public static final Block REDSTONE_SIEVE = register("redstone_sieve", new RedstoneSieveBlock(FabricBlockSettings.copy(Blocks.COBBLESTONE)));

    public static final Block CRAFTING_ROCK = register("crafting_rock", new CraftingRockBlock(FabricBlockSettings.create().mapColor(MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6.0f)));

    public static final Block STEEL_BLOCK = register("steel_block", new Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)));

    public static BlockEntityType<SieveBlockEntity> SIEVE_ENTITY;
    public static BlockEntityType<CraftingRockBlockEntity> CRAFTING_ROCK_ENTITY;

    private static Block register(String id, Block block) {
        return register(new Identifier("earlystage", id), block);
    }

    private static Block register(Identifier id, Block block) {
        Item item = Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Settings()));
        ItemGroupEvents.modifyEntriesEvent(ItemInit.EARLYSTAGE_ITEM_GROUP).register(entries -> entries.add(item));
        return Registry.register(Registries.BLOCK, id, block);
    }

    public static void init() {
        SIEVE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "earlystage:sieve_entity", FabricBlockEntityTypeBuilder.create(SieveBlockEntity::new, SIEVE, REDSTONE_SIEVE).build(null));
        CRAFTING_ROCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "earlystage:crafting_rock_entity",
                FabricBlockEntityTypeBuilder.create(CraftingRockBlockEntity::new, CRAFTING_ROCK).build(null));
    }
}
