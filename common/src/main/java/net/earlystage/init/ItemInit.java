package net.earlystage.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import net.earlystage.EarlyStageMain;
import net.earlystage.item.BarkItem;
import net.earlystage.item.BrickBucketItem;
import net.earlystage.item.ClayBucketItem;
import net.earlystage.item.WoodenBucketItem;
import net.earlystage.item.material.EarlyStageArmorMaterials;
import net.earlystage.item.material.EarlyStageToolMaterials;
import net.earlystage.platform.Services;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import org.jetbrains.annotations.Nullable;

public class ItemInit {

    public static final ResourceKey<CreativeModeTab> EARLYSTAGE_ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, EarlyStageMain.identifierOf("item_group"));
    public static final List<Item> TAB_ITEMS = new ArrayList<>();

    public static Item STEEL_INGOT;
    public static Item STEEL_NUGGET;
    public static Item DAMASCUS_STEEL_INGOT;
    public static Item DAMASCUS_STEEL_NUGGET;

    public static Item STEEL_HELMET;
    public static Item STEEL_CHESTPLATE;
    public static Item STEEL_LEGGINGS;
    public static Item STEEL_BOOTS;

    public static Item STEEL_SWORD;
    public static Item STEEL_SHOVEL;
    public static Item STEEL_PICKAXE;
    public static Item STEEL_AXE;
    public static Item STEEL_HOE;
    public static Item STEEL_HORSE_ARMOR;

    public static Item DAMASCUS_STEEL_HELMET;
    public static Item DAMASCUS_STEEL_CHESTPLATE;
    public static Item DAMASCUS_STEEL_LEGGINGS;
    public static Item DAMASCUS_STEEL_BOOTS;

    public static Item DAMASCUS_STEEL_SWORD;
    public static Item DAMASCUS_STEEL_SHOVEL;
    public static Item DAMASCUS_STEEL_PICKAXE;
    public static Item DAMASCUS_STEEL_AXE;
    public static Item DAMASCUS_STEEL_HOE;
    public static Item DAMASCUS_STEEL_HORSE_ARMOR;

    public static Item FLINT_SWORD;
    public static Item FLINT_SHOVEL;
    public static Item FLINT_PICKAXE;
    public static Item FLINT_AXE;
    public static Item FLINT_HOE;

    public static Item WOODEN_SHIELD;

    public static Item STONE_SHEARS;

    public static Item WOODEN_BUCKET;
    public static Item WATER_WOODEN_BUCKET;
    public static Item CLAY_BUCKET;
    public static Item BRICK_BUCKET;
    public static Item WATER_BRICK_BUCKET;
    public static Item LAVA_BRICK_BUCKET;

    public static Item OAK_BARK;
    public static Item DARK_OAK_BARK;
    public static Item ACACIA_BARK;
    public static Item CHERRY_BARK;
    public static Item BIRCH_BARK;
    public static Item JUNGLE_BARK;
    public static Item SPRUCE_BARK;
    public static Item WARPED_BARK;
    public static Item CRIMSON_BARK;
    public static Item MANGROVE_BARK;
    public static Item BAMBOO_BARK;

    public static void register(BiConsumer<Identifier, Item> registrar) {
        STEEL_INGOT = register(registrar, "steel_ingot", Item::new, new Item.Properties());
        STEEL_NUGGET = register(registrar, "steel_nugget", Item::new, new Item.Properties());
        DAMASCUS_STEEL_INGOT = register(registrar, "damascus_steel_ingot", Item::new, new Item.Properties());
        DAMASCUS_STEEL_NUGGET = register(registrar, "damascus_steel_nugget", Item::new, new Item.Properties());

        STEEL_HELMET = register(registrar, "steel_helmet", Item::new, new Item.Properties().humanoidArmor(EarlyStageArmorMaterials.STEEL, ArmorType.HELMET));
        STEEL_CHESTPLATE = register(registrar, "steel_chestplate", Item::new, new Item.Properties().humanoidArmor(EarlyStageArmorMaterials.STEEL, ArmorType.CHESTPLATE));
        STEEL_LEGGINGS = register(registrar, "steel_leggings", Item::new, new Item.Properties().humanoidArmor(EarlyStageArmorMaterials.STEEL, ArmorType.LEGGINGS));
        STEEL_BOOTS = register(registrar, "steel_boots", Item::new, new Item.Properties().humanoidArmor(EarlyStageArmorMaterials.STEEL, ArmorType.BOOTS));

        STEEL_SWORD = register(registrar, "steel_sword", Item::new, new Item.Properties().sword(EarlyStageToolMaterials.STEEL, 3.0f, -2.4f));
        STEEL_SHOVEL = register(registrar, "steel_shovel", Item::new, new Item.Properties().shovel(EarlyStageToolMaterials.STEEL, 1.5f, -3.0f));
        STEEL_PICKAXE = register(registrar, "steel_pickaxe", Item::new, new Item.Properties().pickaxe(EarlyStageToolMaterials.STEEL, 1.0f, -2.8f));
        STEEL_AXE = register(registrar, "steel_axe", Item::new, new Item.Properties().axe(EarlyStageToolMaterials.STEEL, 6.0f, -3.1f));
        STEEL_HOE = register(registrar, "steel_hoe", Item::new, new Item.Properties().hoe(EarlyStageToolMaterials.STEEL, -2.0f, -1.0f));
        STEEL_HORSE_ARMOR = register(registrar, "steel_horse_armor", Item::new, new Item.Properties().horseArmor(EarlyStageArmorMaterials.STEEL).stacksTo(1));

        DAMASCUS_STEEL_HELMET = register(registrar, "damascus_steel_helmet", Item::new, new Item.Properties().humanoidArmor(EarlyStageArmorMaterials.DAMASCUS_STEEL, ArmorType.HELMET));
        DAMASCUS_STEEL_CHESTPLATE = register(registrar, "damascus_steel_chestplate", Item::new,
                new Item.Properties().humanoidArmor(EarlyStageArmorMaterials.DAMASCUS_STEEL, ArmorType.CHESTPLATE));
        DAMASCUS_STEEL_LEGGINGS = register(registrar, "damascus_steel_leggings", Item::new,
                new Item.Properties().humanoidArmor(EarlyStageArmorMaterials.DAMASCUS_STEEL, ArmorType.LEGGINGS));
        DAMASCUS_STEEL_BOOTS = register(registrar, "damascus_steel_boots", Item::new, new Item.Properties().humanoidArmor(EarlyStageArmorMaterials.DAMASCUS_STEEL, ArmorType.BOOTS));

        DAMASCUS_STEEL_SWORD = register(registrar, "damascus_steel_sword", Item::new, new Item.Properties().sword(EarlyStageToolMaterials.DAMASCUS_STEEL, 3.0f, -2.4f));
        DAMASCUS_STEEL_SHOVEL = register(registrar, "damascus_steel_shovel", Item::new, new Item.Properties().shovel(EarlyStageToolMaterials.DAMASCUS_STEEL, 1.5f, -3.0f));
        DAMASCUS_STEEL_PICKAXE = register(registrar, "damascus_steel_pickaxe", Item::new, new Item.Properties().pickaxe(EarlyStageToolMaterials.DAMASCUS_STEEL, 1.0f, -2.8f));
        DAMASCUS_STEEL_AXE = register(registrar, "damascus_steel_axe", Item::new, new Item.Properties().axe(EarlyStageToolMaterials.DAMASCUS_STEEL, 6.0f, -3.1f));
        DAMASCUS_STEEL_HOE = register(registrar, "damascus_steel_hoe", Item::new, new Item.Properties().hoe(EarlyStageToolMaterials.DAMASCUS_STEEL, -2.0f, -1.0f));
        DAMASCUS_STEEL_HORSE_ARMOR = register(registrar, "damascus_steel_horse_armor", Item::new,
                new Item.Properties().horseArmor(EarlyStageArmorMaterials.DAMASCUS_STEEL).stacksTo(1));

        FLINT_SWORD = register(registrar, "flint_sword", Item::new, notEnchantable(new Item.Properties().sword(EarlyStageToolMaterials.FLINT, 1.0f, -2.4f)));
        FLINT_SHOVEL = register(registrar, "flint_shovel", Item::new, notEnchantable(new Item.Properties().shovel(EarlyStageToolMaterials.FLINT, 0.0f, -3.0f)));
        FLINT_PICKAXE = register(registrar, "flint_pickaxe", Item::new, notEnchantable(new Item.Properties().pickaxe(EarlyStageToolMaterials.FLINT, 0.0f, -2.8f)));
        FLINT_AXE = register(registrar, "flint_axe", Item::new, notEnchantable(new Item.Properties().axe(EarlyStageToolMaterials.FLINT, 1.0f, -3.2f)));
        FLINT_HOE = register(registrar, "flint_hoe", Item::new, notEnchantable(new Item.Properties().hoe(EarlyStageToolMaterials.FLINT, 0.0f, -3.0f)));

        WOODEN_SHIELD = register(registrar, "wooden_shield", ShieldItem::new,
                new Item.Properties().durability(69).repairable(ItemTags.WOODEN_TOOL_MATERIALS).equippableUnswappable(EquipmentSlot.OFFHAND)
                        .delayedComponent(DataComponents.BLOCKS_ATTACKS,
                                context -> new BlocksAttacks(0.25f, 1.0f, List.of(new BlocksAttacks.DamageReduction(90.0f, Optional.empty(), 0.0f, 1.0f)),
                                        new BlocksAttacks.ItemDamageFunction(3.0f, 1.0f, 1.0f), Optional.of(context.getOrThrow(DamageTypeTags.BYPASSES_SHIELD)),
                                        Optional.of(SoundEvents.SHIELD_BLOCK), Optional.of(SoundEvents.SHIELD_BREAK)))
                        .component(DataComponents.BREAK_SOUND, SoundEvents.SHIELD_BREAK));

        STONE_SHEARS = register(registrar, "stone_shears", ShearsItem::new, new Item.Properties().durability(24).component(DataComponents.TOOL, ShearsItem.createToolProperties()));

        WOODEN_BUCKET = register(registrar, "wooden_bucket", properties -> new WoodenBucketItem(Fluids.EMPTY, properties), new Item.Properties().stacksTo(16));
        WATER_WOODEN_BUCKET = register(registrar, "water_wooden_bucket", properties -> new WoodenBucketItem(Fluids.WATER, properties),
                new Item.Properties().craftRemainder(WOODEN_BUCKET).stacksTo(1));
        CLAY_BUCKET = register(registrar, "clay_bucket", ClayBucketItem::new, new Item.Properties().stacksTo(16));
        BRICK_BUCKET = register(registrar, "brick_bucket", properties -> new BrickBucketItem(Fluids.EMPTY, properties), new Item.Properties().stacksTo(16));
        WATER_BRICK_BUCKET = register(registrar, "water_brick_bucket", properties -> new BrickBucketItem(Fluids.WATER, properties),
                new Item.Properties().craftRemainder(BRICK_BUCKET).stacksTo(1));
        LAVA_BRICK_BUCKET = register(registrar, "lava_brick_bucket", properties -> new BrickBucketItem(Fluids.LAVA, properties),
                new Item.Properties().craftRemainder(BRICK_BUCKET).stacksTo(1));

        OAK_BARK = registerBark(registrar, "oak_bark", Blocks.OAK_LOG, Blocks.OAK_WOOD, 150);
        DARK_OAK_BARK = registerBark(registrar, "dark_oak_bark", Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_WOOD, 150);
        ACACIA_BARK = registerBark(registrar, "acacia_bark", Blocks.ACACIA_LOG, Blocks.ACACIA_WOOD, 150);
        CHERRY_BARK = registerBark(registrar, "cherry_bark", Blocks.CHERRY_LOG, Blocks.CHERRY_WOOD, 150);
        BIRCH_BARK = registerBark(registrar, "birch_bark", Blocks.BIRCH_LOG, Blocks.BIRCH_WOOD, 150);
        JUNGLE_BARK = registerBark(registrar, "jungle_bark", Blocks.JUNGLE_LOG, Blocks.JUNGLE_WOOD, 150);
        SPRUCE_BARK = registerBark(registrar, "spruce_bark", Blocks.SPRUCE_LOG, Blocks.SPRUCE_WOOD, 150);
        WARPED_BARK = registerBark(registrar, "warped_bark", Blocks.WARPED_STEM, Blocks.WARPED_HYPHAE, 150);
        CRIMSON_BARK = registerBark(registrar, "crimson_bark", Blocks.CRIMSON_STEM, Blocks.CRIMSON_HYPHAE, 200);
        MANGROVE_BARK = registerBark(registrar, "mangrove_bark", Blocks.MANGROVE_LOG, Blocks.MANGROVE_WOOD, 200);
        BAMBOO_BARK = registerBark(registrar, "bamboo_bark", Blocks.BAMBOO_BLOCK, null, 100);

        BarkItem.addStrippedBlock(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
        BarkItem.addStrippedBlock(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD);
        BarkItem.addStrippedBlock(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
        BarkItem.addStrippedBlock(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD);
        BarkItem.addStrippedBlock(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
        BarkItem.addStrippedBlock(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD);
        BarkItem.addStrippedBlock(Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG);
        BarkItem.addStrippedBlock(Blocks.CHERRY_WOOD, Blocks.STRIPPED_CHERRY_WOOD);
        BarkItem.addStrippedBlock(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
        BarkItem.addStrippedBlock(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD);
        BarkItem.addStrippedBlock(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
        BarkItem.addStrippedBlock(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD);
        BarkItem.addStrippedBlock(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
        BarkItem.addStrippedBlock(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD);
        BarkItem.addStrippedBlock(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM);
        BarkItem.addStrippedBlock(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE);
        BarkItem.addStrippedBlock(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM);
        BarkItem.addStrippedBlock(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE);
        BarkItem.addStrippedBlock(Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG);
        BarkItem.addStrippedBlock(Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD);
        BarkItem.addStrippedBlock(Blocks.BAMBOO_BLOCK, Blocks.STRIPPED_BAMBOO_BLOCK);
    }

    public static void registerCreativeModeTab(BiConsumer<Identifier, CreativeModeTab> registrar) {
        registrar.accept(EARLYSTAGE_ITEM_GROUP.identifier(),
                Services.PLATFORM.createCreativeModeTab(Component.translatable("item.earlystage.item_group"), () -> new ItemStack(WATER_WOODEN_BUCKET), () -> TAB_ITEMS));
    }

    public static Item register(BiConsumer<Identifier, Item> registrar, String id, Function<Item.Properties, Item> factory, Item.Properties properties) {
        Identifier identifier = EarlyStageMain.identifierOf(id);
        Item item = factory.apply(properties.setId(ResourceKey.create(Registries.ITEM, identifier)));
        registrar.accept(identifier, item);
        TAB_ITEMS.add(item);
        return item;
    }

    private static Item.Properties notEnchantable(Item.Properties properties) {
        return properties.delayedComponent(DataComponents.ENCHANTABLE, context -> null);
    }

    public static Item registerBark(BiConsumer<Identifier, Item> registrar, String id, @Nullable Block logBlock, @Nullable Block woodBlock, int burnTime) {
        return register(registrar, id, properties -> new BarkItem(properties, logBlock, woodBlock), new Item.Properties().cookingFuel(switch (burnTime) {
            case 100 -> ContextIntProviders.COOKING_TIME_WOOD_ITEMS_EXTRA_SMALL;
            case 200 -> ContextIntProviders.COOKING_TIME_WOOD_ITEMS_LARGE;
            default -> ContextIntProviders.COOKING_TIME_WOOD_SLABS;
        }));
    }
}
