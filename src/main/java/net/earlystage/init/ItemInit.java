package net.earlystage.init;

import net.earlystage.item.*;
import net.earlystage.item.material.*;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemInit {

    // Item Group
    public static final RegistryKey<ItemGroup> EARLYSTAGE_ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier("earlystage", "item_group"));

    public static final Item STEEL_INGOT = register("steel_ingot", new Item(new Item.Settings()));
    public static final Item STEEL_NUGGET = register("steel_nugget", new Item(new Item.Settings()));
    public static final Item STEEL_HELMET = register("steel_helmet", new ArmorItem(new SteelArmorMaterial(), ArmorItem.Type.HELMET, new Item.Settings()));
    public static final Item STEEL_CHESTPLATE = register("steel_chestplate", new ArmorItem(new SteelArmorMaterial(), ArmorItem.Type.CHESTPLATE, new Item.Settings()));
    public static final Item STEEL_LEGGINGS = register("steel_leggings", new ArmorItem(new SteelArmorMaterial(), ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item STEEL_BOOTS = register("steel_boots", new ArmorItem(new SteelArmorMaterial(), ArmorItem.Type.BOOTS, new Item.Settings()));
    public static final Item STEEL_SWORD = register("steel_sword", new SwordItem(new SteelToolMaterial(), 3, -2.4f, new Item.Settings()));
    public static final Item STEEL_SHOVEL = register("steel_shovel", new ShovelItem(new SteelToolMaterial(), 1.5f, -3.0f, new Item.Settings()));
    public static final Item STEEL_PICKAXE = register("steel_pickaxe", new PickaxeItem(new SteelToolMaterial(), 1, -2.8f, new Item.Settings()));
    public static final Item STEEL_AXE = register("steel_axe", new AxeItem(new SteelToolMaterial(), 6.0f, -3.1f, new Item.Settings()));
    public static final Item STEEL_HOE = register("steel_hoe", new EarlyStageHoeItem(new SteelToolMaterial(), -2, -1.0f, new Item.Settings()));
    public static final Item STEEL_HORSE_ARMOR = register("steel_horse_armor", new HorseArmorItem(6, "steel", new Item.Settings().maxCount(1)));

    public static final Item FLINT_SWORD = register("flint_sword", new SwordItem(new FlintToolMaterial(), 1, -2.4f, new Item.Settings()));
    public static final Item FLINT_SHOVEL = register("flint_shovel", new ShovelItem(new FlintToolMaterial(), 0f, -3.0f, new Item.Settings()));
    public static final Item FLINT_PICKAXE = register("flint_pickaxe", new PickaxeItem(new FlintToolMaterial(), 0, -2.8f, new Item.Settings()));
    public static final Item FLINT_AXE = register("flint_axe", new AxeItem(new FlintToolMaterial(), 1f, -3.2f, new Item.Settings()));
    public static final Item FLINT_HOE = register("flint_hoe", new EarlyStageHoeItem(new FlintToolMaterial(), 0, -3.0f, new Item.Settings()));

    public static final Item WOODEN_SHIELD = register("wooden_shield", new ShieldItem(new Item.Settings().maxDamage(69)));

    public static final Item STONE_SHEARS = register("stone_shears", new ShearsItem(new Item.Settings().maxDamage(24)));

    public static final Item WOODEN_BUCKET = register("wooden_bucket", new WoodenBucketItem(Fluids.EMPTY, new Item.Settings().maxCount(16)));
    public static final Item WATER_WOODEN_BUCKET = register("water_wooden_bucket", new WoodenBucketItem(Fluids.WATER, new Item.Settings().recipeRemainder(WOODEN_BUCKET).maxCount(1)));
    public static final Item CLAY_BUCKET = register("clay_bucket", new ClayBucketItem(new Item.Settings().maxCount(16)));
    public static final Item BRICK_BUCKET = register("brick_bucket", new BrickBucketItem(Fluids.EMPTY, new Item.Settings().maxCount(16)));
    public static final Item WATER_BRICK_BUCKET = register("water_brick_bucket", new BrickBucketItem(Fluids.WATER, new Item.Settings().recipeRemainder(BRICK_BUCKET).maxCount(1)));
    public static final Item LAVA_BRICK_BUCKET = register("lava_brick_bucket", new BrickBucketItem(Fluids.LAVA, new Item.Settings().recipeRemainder(BRICK_BUCKET).maxCount(1)));

    public static final Item OAK_BARK = register("oak_bark", new BarkItem(new Item.Settings(), Blocks.OAK_LOG, Blocks.OAK_WOOD, 150));
    public static final Item DARK_OAK_BARK = register("dark_oak_bark", new BarkItem(new Item.Settings(), Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_WOOD, 150));
    public static final Item ACACIA_BARK = register("acacia_bark", new BarkItem(new Item.Settings(), Blocks.ACACIA_LOG, Blocks.ACACIA_WOOD, 150));
    public static final Item CHERRY_BARK = register("cherry_bark", new BarkItem(new Item.Settings(), Blocks.CHERRY_LOG, Blocks.CHERRY_WOOD, 150));
    public static final Item BIRCH_BARK = register("birch_bark", new BarkItem(new Item.Settings(), Blocks.BIRCH_LOG, Blocks.BIRCH_WOOD, 150));
    public static final Item JUNGLE_BARK = register("jungle_bark", new BarkItem(new Item.Settings(), Blocks.JUNGLE_LOG, Blocks.JUNGLE_WOOD, 150));
    public static final Item SPRUCE_BARK = register("spruce_bark", new BarkItem(new Item.Settings(), Blocks.SPRUCE_LOG, Blocks.SPRUCE_WOOD, 150));
    public static final Item WARPED_BARK = register("warped_bark", new BarkItem(new Item.Settings(), Blocks.WARPED_STEM, Blocks.WARPED_HYPHAE, 150));
    public static final Item CRIMSON_BARK = register("crimson_bark", new BarkItem(new Item.Settings(), Blocks.CRIMSON_STEM, Blocks.CRIMSON_HYPHAE, 200));
    public static final Item MANGROVE_BARK = register("mangrove_bark", new BarkItem(new Item.Settings(), Blocks.MANGROVE_LOG, Blocks.MANGROVE_WOOD, 200));
    public static final Item BAMBOO_BARK = register("bamboo_bark", new BarkItem(new Item.Settings(), Blocks.BAMBOO_BLOCK, null, 100));

    private static Item register(String id, Item item) {
        return register(new Identifier("earlystage", id), item);
    }

    private static Item register(Identifier id, Item item) {
        ItemGroupEvents.modifyEntriesEvent(EARLYSTAGE_ITEM_GROUP).register(entries -> entries.add(item));
        return Registry.register(Registries.ITEM, id, item);
    }

    public static void init() {
        Registry.register(Registries.ITEM_GROUP, EARLYSTAGE_ITEM_GROUP,
                FabricItemGroup.builder().icon(() -> new ItemStack(WATER_WOODEN_BUCKET)).displayName(Text.translatable("item.earlystage.item_group")).build());
    }
}
