package net.earlystage.init;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import net.earlystage.EarlyStageMain;
import net.earlystage.item.BarkItem;
import net.earlystage.item.BrickBucketItem;
import net.earlystage.item.ClayBucketItem;
import net.earlystage.item.WoodenBucketItem;
import net.earlystage.item.material.EarlyStageArmorMaterials;
import net.earlystage.item.material.EarlyStageToolMaterials;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
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

    public static final Item STEEL_INGOT = register("steel_ingot", Item::new, new Item.Properties());
    public static final Item STEEL_NUGGET = register("steel_nugget", Item::new, new Item.Properties());
    public static final Item DAMASCUS_STEEL_INGOT = register("damascus_steel_ingot", Item::new, new Item.Properties());
    public static final Item DAMASCUS_STEEL_NUGGET = register("damascus_steel_nugget", Item::new, new Item.Properties());

    public static final Item STEEL_HELMET = register("steel_helmet", Item::new, new Item.Properties().humanoidArmor(EarlyStageArmorMaterials.STEEL, ArmorType.HELMET));
    public static final Item STEEL_CHESTPLATE = register("steel_chestplate", Item::new, new Item.Properties().humanoidArmor(EarlyStageArmorMaterials.STEEL, ArmorType.CHESTPLATE));
    public static final Item STEEL_LEGGINGS = register("steel_leggings", Item::new, new Item.Properties().humanoidArmor(EarlyStageArmorMaterials.STEEL, ArmorType.LEGGINGS));
    public static final Item STEEL_BOOTS = register("steel_boots", Item::new, new Item.Properties().humanoidArmor(EarlyStageArmorMaterials.STEEL, ArmorType.BOOTS));

    public static final Item STEEL_SWORD = register("steel_sword", Item::new, new Item.Properties().sword(EarlyStageToolMaterials.STEEL, 3.0f, -2.4f));
    public static final Item STEEL_SHOVEL = register("steel_shovel", Item::new, new Item.Properties().shovel(EarlyStageToolMaterials.STEEL, 1.5f, -3.0f));
    public static final Item STEEL_PICKAXE = register("steel_pickaxe", Item::new, new Item.Properties().pickaxe(EarlyStageToolMaterials.STEEL, 1.0f, -2.8f));
    public static final Item STEEL_AXE = register("steel_axe", Item::new, new Item.Properties().axe(EarlyStageToolMaterials.STEEL, 6.0f, -3.1f));
    public static final Item STEEL_HOE = register("steel_hoe", Item::new, new Item.Properties().hoe(EarlyStageToolMaterials.STEEL, -2.0f, -1.0f));
    public static final Item STEEL_HORSE_ARMOR = register("steel_horse_armor", Item::new, new Item.Properties().horseArmor(EarlyStageArmorMaterials.STEEL).stacksTo(1));

    public static final Item DAMASCUS_STEEL_HELMET = register("damascus_steel_helmet", Item::new, new Item.Properties().humanoidArmor(EarlyStageArmorMaterials.DAMASCUS_STEEL, ArmorType.HELMET));
    public static final Item DAMASCUS_STEEL_CHESTPLATE = register("damascus_steel_chestplate", Item::new,
            new Item.Properties().humanoidArmor(EarlyStageArmorMaterials.DAMASCUS_STEEL, ArmorType.CHESTPLATE));
    public static final Item DAMASCUS_STEEL_LEGGINGS = register("damascus_steel_leggings", Item::new,
            new Item.Properties().humanoidArmor(EarlyStageArmorMaterials.DAMASCUS_STEEL, ArmorType.LEGGINGS));
    public static final Item DAMASCUS_STEEL_BOOTS = register("damascus_steel_boots", Item::new, new Item.Properties().humanoidArmor(EarlyStageArmorMaterials.DAMASCUS_STEEL, ArmorType.BOOTS));

    public static final Item DAMASCUS_STEEL_SWORD = register("damascus_steel_sword", Item::new, new Item.Properties().sword(EarlyStageToolMaterials.DAMASCUS_STEEL, 3.0f, -2.4f));
    public static final Item DAMASCUS_STEEL_SHOVEL = register("damascus_steel_shovel", Item::new, new Item.Properties().shovel(EarlyStageToolMaterials.DAMASCUS_STEEL, 1.5f, -3.0f));
    public static final Item DAMASCUS_STEEL_PICKAXE = register("damascus_steel_pickaxe", Item::new, new Item.Properties().pickaxe(EarlyStageToolMaterials.DAMASCUS_STEEL, 1.0f, -2.8f));
    public static final Item DAMASCUS_STEEL_AXE = register("damascus_steel_axe", Item::new, new Item.Properties().axe(EarlyStageToolMaterials.DAMASCUS_STEEL, 6.0f, -3.1f));
    public static final Item DAMASCUS_STEEL_HOE = register("damascus_steel_hoe", Item::new, new Item.Properties().hoe(EarlyStageToolMaterials.DAMASCUS_STEEL, -2.0f, -1.0f));
    public static final Item DAMASCUS_STEEL_HORSE_ARMOR = register("damascus_steel_horse_armor", Item::new,
            new Item.Properties().horseArmor(EarlyStageArmorMaterials.DAMASCUS_STEEL).stacksTo(1));

    public static final Item FLINT_SWORD = register("flint_sword", Item::new, notEnchantable(new Item.Properties().sword(EarlyStageToolMaterials.FLINT, 1.0f, -2.4f)));
    public static final Item FLINT_SHOVEL = register("flint_shovel", Item::new, notEnchantable(new Item.Properties().shovel(EarlyStageToolMaterials.FLINT, 0.0f, -3.0f)));
    public static final Item FLINT_PICKAXE = register("flint_pickaxe", Item::new, notEnchantable(new Item.Properties().pickaxe(EarlyStageToolMaterials.FLINT, 0.0f, -2.8f)));
    public static final Item FLINT_AXE = register("flint_axe", Item::new, notEnchantable(new Item.Properties().axe(EarlyStageToolMaterials.FLINT, 1.0f, -3.2f)));
    public static final Item FLINT_HOE = register("flint_hoe", Item::new, notEnchantable(new Item.Properties().hoe(EarlyStageToolMaterials.FLINT, 0.0f, -3.0f)));

    public static final Item WOODEN_SHIELD = register("wooden_shield", ShieldItem::new,
            new Item.Properties().durability(69).repairable(ItemTags.WOODEN_TOOL_MATERIALS).equippableUnswappable(EquipmentSlot.OFFHAND)
                    .delayedComponent(DataComponents.BLOCKS_ATTACKS,
                            context -> new BlocksAttacks(0.25f, 1.0f, List.of(new BlocksAttacks.DamageReduction(90.0f, Optional.empty(), 0.0f, 1.0f)),
                                    new BlocksAttacks.ItemDamageFunction(3.0f, 1.0f, 1.0f), Optional.of(context.getOrThrow(DamageTypeTags.BYPASSES_SHIELD)),
                                    Optional.of(SoundEvents.SHIELD_BLOCK), Optional.of(SoundEvents.SHIELD_BREAK)))
                    .component(DataComponents.BREAK_SOUND, SoundEvents.SHIELD_BREAK));

    public static final Item STONE_SHEARS = register("stone_shears", ShearsItem::new,
            new Item.Properties().durability(24).component(DataComponents.TOOL, ShearsItem.createToolProperties()));

    public static final Item WOODEN_BUCKET = register("wooden_bucket", properties -> new WoodenBucketItem(Fluids.EMPTY, properties), new Item.Properties().stacksTo(16));
    public static final Item WATER_WOODEN_BUCKET = register("water_wooden_bucket", properties -> new WoodenBucketItem(Fluids.WATER, properties),
            new Item.Properties().craftRemainder(WOODEN_BUCKET).stacksTo(1));
    public static final Item CLAY_BUCKET = register("clay_bucket", ClayBucketItem::new, new Item.Properties().stacksTo(16));
    public static final Item BRICK_BUCKET = register("brick_bucket", properties -> new BrickBucketItem(Fluids.EMPTY, properties), new Item.Properties().stacksTo(16));
    public static final Item WATER_BRICK_BUCKET = register("water_brick_bucket", properties -> new BrickBucketItem(Fluids.WATER, properties),
            new Item.Properties().craftRemainder(BRICK_BUCKET).stacksTo(1));
    public static final Item LAVA_BRICK_BUCKET = register("lava_brick_bucket", properties -> new BrickBucketItem(Fluids.LAVA, properties),
            new Item.Properties().craftRemainder(BRICK_BUCKET).stacksTo(1));

    public static final Item OAK_BARK = registerBark("oak_bark", Blocks.OAK_LOG, Blocks.OAK_WOOD, 150);
    public static final Item DARK_OAK_BARK = registerBark("dark_oak_bark", Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_WOOD, 150);
    public static final Item ACACIA_BARK = registerBark("acacia_bark", Blocks.ACACIA_LOG, Blocks.ACACIA_WOOD, 150);
    public static final Item CHERRY_BARK = registerBark("cherry_bark", Blocks.CHERRY_LOG, Blocks.CHERRY_WOOD, 150);
    public static final Item BIRCH_BARK = registerBark("birch_bark", Blocks.BIRCH_LOG, Blocks.BIRCH_WOOD, 150);
    public static final Item JUNGLE_BARK = registerBark("jungle_bark", Blocks.JUNGLE_LOG, Blocks.JUNGLE_WOOD, 150);
    public static final Item SPRUCE_BARK = registerBark("spruce_bark", Blocks.SPRUCE_LOG, Blocks.SPRUCE_WOOD, 150);
    public static final Item WARPED_BARK = registerBark("warped_bark", Blocks.WARPED_STEM, Blocks.WARPED_HYPHAE, 150);
    public static final Item CRIMSON_BARK = registerBark("crimson_bark", Blocks.CRIMSON_STEM, Blocks.CRIMSON_HYPHAE, 200);
    public static final Item MANGROVE_BARK = registerBark("mangrove_bark", Blocks.MANGROVE_LOG, Blocks.MANGROVE_WOOD, 200);
    public static final Item BAMBOO_BARK = registerBark("bamboo_bark", Blocks.BAMBOO_BLOCK, null, 100);

    public static Item register(String id, Function<Item.Properties, Item> factory, Item.Properties properties) {
        Identifier identifier = EarlyStageMain.identifierOf(id);
        Item item = Registry.register(BuiltInRegistries.ITEM, identifier, factory.apply(properties.setId(ResourceKey.create(Registries.ITEM, identifier))));
        CreativeModeTabEvents.modifyOutputEvent(EARLYSTAGE_ITEM_GROUP).register(entries -> entries.accept(item));
        return item;
    }

    private static Item.Properties notEnchantable(Item.Properties properties) {
        return properties.delayedComponent(DataComponents.ENCHANTABLE, context -> null);
    }

    public static Item registerBark(String id, @Nullable Block logBlock, @Nullable Block woodBlock, int burnTime) {
        return register(id, properties -> new BarkItem(properties, logBlock, woodBlock), new Item.Properties().cookingFuel(switch (burnTime) {
            case 100 -> ContextIntProviders.COOKING_TIME_WOOD_ITEMS_EXTRA_SMALL;
            case 200 -> ContextIntProviders.COOKING_TIME_WOOD_ITEMS_LARGE;
            default -> ContextIntProviders.COOKING_TIME_WOOD_SLABS;
        }));
    }

    public static void init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, EARLYSTAGE_ITEM_GROUP,
                FabricCreativeModeTab.builder().icon(() -> new ItemStack(WATER_WOODEN_BUCKET)).title(Component.translatable("item.earlystage.item_group")).build());

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
}
