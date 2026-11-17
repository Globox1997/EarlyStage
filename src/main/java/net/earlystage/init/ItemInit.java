package net.earlystage.init;

import net.earlystage.EarlyStageMain;
import net.earlystage.item.BarkItem;
import net.earlystage.item.BrickBucketItem;
import net.earlystage.item.ClayBucketItem;
import net.earlystage.item.WoodenBucketItem;
import net.earlystage.item.material.EarlyStageArmorMaterials;
import net.earlystage.item.material.EarlyStageToolMaterials;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;

public class ItemInit {

    // Item Group
    public static final ResourceKey<CreativeModeTab> EARLYSTAGE_ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, EarlyStageMain.identifierOf("item_group"));

    public static final Item STEEL_INGOT = register("steel_ingot", new Item(new Item.Properties()));
    public static final Item STEEL_NUGGET = register("steel_nugget", new Item(new Item.Properties()));
    public static final Item DAMASCUS_STEEL_INGOT = register("damascus_steel_ingot", new Item(new Item.Properties()));
    public static final Item DAMASCUS_STEEL_NUGGET = register("damascus_steel_nugget", new Item(new Item.Properties()));

    public static final Item STEEL_HELMET = register("steel_helmet",
            new ArmorItem(EarlyStageArmorMaterials.STEEL, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(20))));
    public static final Item STEEL_CHESTPLATE = register("steel_chestplate",
            new ArmorItem(EarlyStageArmorMaterials.STEEL, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(20))));
    public static final Item STEEL_LEGGINGS = register("steel_leggings",
            new ArmorItem(EarlyStageArmorMaterials.STEEL, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(20))));
    public static final Item STEEL_BOOTS = register("steel_boots",
            new ArmorItem(EarlyStageArmorMaterials.STEEL, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(20))));

    public static final Item STEEL_SWORD = register("steel_sword",
            new SwordItem(EarlyStageToolMaterials.STEEL, new Item.Properties().attributes(SwordItem.createAttributes(EarlyStageToolMaterials.STEEL, 3, -2.4f))));
    public static final Item STEEL_SHOVEL = register("steel_shovel",
            new ShovelItem(EarlyStageToolMaterials.STEEL, new Item.Properties().attributes(ShovelItem.createAttributes(EarlyStageToolMaterials.STEEL, 1.5f, -3.0f))));
    public static final Item STEEL_PICKAXE = register("steel_pickaxe",
            new PickaxeItem(EarlyStageToolMaterials.STEEL, new Item.Properties().attributes(PickaxeItem.createAttributes(EarlyStageToolMaterials.STEEL, 1.0f, -2.8f))));
    public static final Item STEEL_AXE = register("steel_axe",
            new AxeItem(EarlyStageToolMaterials.STEEL, new Item.Properties().attributes(AxeItem.createAttributes(EarlyStageToolMaterials.STEEL, 6.0f, -3.1f))));
    public static final Item STEEL_HOE = register("steel_hoe",
            new HoeItem(EarlyStageToolMaterials.STEEL, new Item.Properties().attributes(HoeItem.createAttributes(EarlyStageToolMaterials.STEEL, -2.0f, -1.0f))));
    public static final Item STEEL_HORSE_ARMOR = register("steel_horse_armor",
            new AnimalArmorItem(EarlyStageArmorMaterials.STEEL, AnimalArmorItem.BodyType.EQUESTRIAN, false, new Item.Properties().stacksTo(1)));


    public static final Item DAMASCUS_STEEL_HELMET = register("damascus_steel_helmet",
            new ArmorItem(EarlyStageArmorMaterials.DAMASCUS_STEEL, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(20))));
    public static final Item DAMASCUS_STEEL_CHESTPLATE = register("damascus_steel_chestplate",
            new ArmorItem(EarlyStageArmorMaterials.DAMASCUS_STEEL, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(20))));
    public static final Item DAMASCUS_STEEL_LEGGINGS = register("damascus_steel_leggings",
            new ArmorItem(EarlyStageArmorMaterials.DAMASCUS_STEEL, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(20))));
    public static final Item DAMASCUS_STEEL_BOOTS = register("damascus_steel_boots",
            new ArmorItem(EarlyStageArmorMaterials.DAMASCUS_STEEL, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(20))));

    public static final Item DAMASCUS_STEEL_SWORD = register("damascus_steel_sword",
            new SwordItem(EarlyStageToolMaterials.DAMASCUS_STEEL, new Item.Properties().attributes(SwordItem.createAttributes(EarlyStageToolMaterials.DAMASCUS_STEEL, 3, -2.4f))));
    public static final Item DAMASCUS_STEEL_SHOVEL = register("damascus_steel_shovel",
            new ShovelItem(EarlyStageToolMaterials.DAMASCUS_STEEL, new Item.Properties().attributes(ShovelItem.createAttributes(EarlyStageToolMaterials.DAMASCUS_STEEL, 1.5f, -3.0f))));
    public static final Item DAMASCUS_STEEL_PICKAXE = register("damascus_steel_pickaxe",
            new PickaxeItem(EarlyStageToolMaterials.DAMASCUS_STEEL, new Item.Properties().attributes(PickaxeItem.createAttributes(EarlyStageToolMaterials.DAMASCUS_STEEL, 1.0f, -2.8f))));
    public static final Item DAMASCUS_STEEL_AXE = register("damascus_steel_axe",
            new AxeItem(EarlyStageToolMaterials.DAMASCUS_STEEL, new Item.Properties().attributes(AxeItem.createAttributes(EarlyStageToolMaterials.DAMASCUS_STEEL, 6.0f, -3.1f))));
    public static final Item DAMASCUS_STEEL_HOE = register("damascus_steel_hoe",
            new HoeItem(EarlyStageToolMaterials.DAMASCUS_STEEL, new Item.Properties().attributes(HoeItem.createAttributes(EarlyStageToolMaterials.DAMASCUS_STEEL, -2.0f, -1.0f))));
    public static final Item DAMASCUS_STEEL_HORSE_ARMOR = register("damascus_steel_horse_armor",
            new AnimalArmorItem(EarlyStageArmorMaterials.DAMASCUS_STEEL, AnimalArmorItem.BodyType.EQUESTRIAN, false, new Item.Properties().stacksTo(1)));

    public static final Item FLINT_SWORD = register("flint_sword",
            new SwordItem(EarlyStageToolMaterials.FLINT, new Item.Properties().attributes(SwordItem.createAttributes(EarlyStageToolMaterials.FLINT, 1, -2.4f))));
    public static final Item FLINT_SHOVEL = register("flint_shovel",
            new ShovelItem(EarlyStageToolMaterials.FLINT, new Item.Properties().attributes(ShovelItem.createAttributes(EarlyStageToolMaterials.FLINT, 0f, -3.0f))));
    public static final Item FLINT_PICKAXE = register("flint_pickaxe",
            new PickaxeItem(EarlyStageToolMaterials.FLINT, new Item.Properties().attributes(PickaxeItem.createAttributes(EarlyStageToolMaterials.FLINT, 0f, -2.8f))));
    public static final Item FLINT_AXE = register("flint_axe",
            new AxeItem(EarlyStageToolMaterials.FLINT, new Item.Properties().attributes(AxeItem.createAttributes(EarlyStageToolMaterials.FLINT, 1.0f, -3.2f))));
    public static final Item FLINT_HOE = register("flint_hoe",
            new HoeItem(EarlyStageToolMaterials.FLINT, new Item.Properties().attributes(HoeItem.createAttributes(EarlyStageToolMaterials.FLINT, 0f, -3.0f))));

    public static final Item WOODEN_SHIELD = register("wooden_shield", new ShieldItem(new Item.Properties().durability(69)));

    public static final Item STONE_SHEARS = register("stone_shears", new ShearsItem(new Item.Properties().durability(24).component(DataComponents.TOOL, ShearsItem.createToolProperties())));

    public static final Item WOODEN_BUCKET = register("wooden_bucket", new WoodenBucketItem(Fluids.EMPTY, new Item.Properties().stacksTo(16)));
    public static final Item WATER_WOODEN_BUCKET = register("water_wooden_bucket", new WoodenBucketItem(Fluids.WATER, new Item.Properties().craftRemainder(WOODEN_BUCKET).stacksTo(1)));
    public static final Item CLAY_BUCKET = register("clay_bucket", new ClayBucketItem(new Item.Properties().stacksTo(16)));
    public static final Item BRICK_BUCKET = register("brick_bucket", new BrickBucketItem(Fluids.EMPTY, new Item.Properties().stacksTo(16)));
    public static final Item WATER_BRICK_BUCKET = register("water_brick_bucket", new BrickBucketItem(Fluids.WATER, new Item.Properties().craftRemainder(BRICK_BUCKET).stacksTo(1)));
    public static final Item LAVA_BRICK_BUCKET = register("lava_brick_bucket", new BrickBucketItem(Fluids.LAVA, new Item.Properties().craftRemainder(BRICK_BUCKET).stacksTo(1)));

    public static final Item OAK_BARK = register("oak_bark", new BarkItem(new Item.Properties(), Blocks.OAK_LOG, Blocks.OAK_WOOD, 150));
    public static final Item DARK_OAK_BARK = register("dark_oak_bark", new BarkItem(new Item.Properties(), Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_WOOD, 150));
    public static final Item ACACIA_BARK = register("acacia_bark", new BarkItem(new Item.Properties(), Blocks.ACACIA_LOG, Blocks.ACACIA_WOOD, 150));
    public static final Item CHERRY_BARK = register("cherry_bark", new BarkItem(new Item.Properties(), Blocks.CHERRY_LOG, Blocks.CHERRY_WOOD, 150));
    public static final Item BIRCH_BARK = register("birch_bark", new BarkItem(new Item.Properties(), Blocks.BIRCH_LOG, Blocks.BIRCH_WOOD, 150));
    public static final Item JUNGLE_BARK = register("jungle_bark", new BarkItem(new Item.Properties(), Blocks.JUNGLE_LOG, Blocks.JUNGLE_WOOD, 150));
    public static final Item SPRUCE_BARK = register("spruce_bark", new BarkItem(new Item.Properties(), Blocks.SPRUCE_LOG, Blocks.SPRUCE_WOOD, 150));
    public static final Item WARPED_BARK = register("warped_bark", new BarkItem(new Item.Properties(), Blocks.WARPED_STEM, Blocks.WARPED_HYPHAE, 150));
    public static final Item CRIMSON_BARK = register("crimson_bark", new BarkItem(new Item.Properties(), Blocks.CRIMSON_STEM, Blocks.CRIMSON_HYPHAE, 200));
    public static final Item MANGROVE_BARK = register("mangrove_bark", new BarkItem(new Item.Properties(), Blocks.MANGROVE_LOG, Blocks.MANGROVE_WOOD, 200));
    public static final Item BAMBOO_BARK = register("bamboo_bark", new BarkItem(new Item.Properties(), Blocks.BAMBOO_BLOCK, null, 100));

    public static Item register(String id, Item item) {
        return register(EarlyStageMain.identifierOf(id), item);
    }

    private static Item register(ResourceLocation id, Item item) {
        ItemGroupEvents.modifyEntriesEvent(EARLYSTAGE_ITEM_GROUP).register(entries -> entries.accept(item));
        return Registry.register(BuiltInRegistries.ITEM, id, item);
    }

    public static void init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, EARLYSTAGE_ITEM_GROUP,
                FabricItemGroup.builder().icon(() -> new ItemStack(WATER_WOODEN_BUCKET)).title(Component.translatable("item.earlystage.item_group")).build());
    }
}
