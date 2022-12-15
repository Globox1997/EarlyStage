package net.earlystage.init;

import net.earlystage.block.*;
import net.earlystage.block.entity.SieveBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockInit {

    public static final Block ROCK = register("rock", new RockBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).breakInstantly()), ItemGroup.DECORATIONS);

    public static final Block SIEVE = register("sieve", new SieveBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS)), ItemGroup.DECORATIONS);
    public static final Block REDSTONE_SIEVE = register("redstone_sieve", new RedstoneSieveBlock(FabricBlockSettings.copy(Blocks.COBBLESTONE)), ItemGroup.DECORATIONS);
    public static BlockEntityType<SieveBlockEntity> SIEVE_ENTITY;

    private static Block register(String id, Block block, ItemGroup itemGroup) {
        return register(new Identifier("earlystage", id), block, itemGroup);
    }

    private static Block register(Identifier id, Block block, ItemGroup itemGroup) {
        // register block item
        Registry.register(Registry.ITEM, id, new BlockItem(block, new Item.Settings().group(itemGroup)));
        return Registry.register(Registry.BLOCK, id, block);
    }

    public static void init() {
        SIEVE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "earlystage:sieve_entity", FabricBlockEntityTypeBuilder.create(SieveBlockEntity::new, SIEVE, REDSTONE_SIEVE).build(null));
    }
}
