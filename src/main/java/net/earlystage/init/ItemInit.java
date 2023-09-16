package net.earlystage.init;

//import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;

import net.earlystage.item.*;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
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

    public static final Item FLINT_SWORD = register("flint_sword", new SwordItem(new FlintToolMaterial(), 1, -2.4f, new Item.Settings()));
    public static final Item FLINT_SHOVEL = register("flint_shovel", new ShovelItem(new FlintToolMaterial(), 0f, -3.0f, new Item.Settings()));
    public static final Item FLINT_PICKAXE = register("flint_pickaxe", new PickaxeItem(new FlintToolMaterial(), 0, -2.8f, new Item.Settings()));
    public static final Item FLINT_AXE = register("flint_axe", new AxeItem(new FlintToolMaterial(), 1f, -3.2f, new Item.Settings()));
    public static final Item FLINT_HOE = register("flint_hoe", new FlintHoeItem(new FlintToolMaterial(), 0, -3.0f, new Item.Settings()));

    // public static final Item WOODEN_SHIELD = register("wooden_shield",
    // new FabricShieldItem(new FabricItemSettings().maxDamage(59), 100, 0, Items.OAK_PLANKS, Items.DARK_OAK_PLANKS, Items.SPRUCE_PLANKS, Items.JUNGLE_PLANKS));

    // ONLY FOR NOW
    public static final Item WOODEN_SHIELD = register("wooden_shield", new Item(new Item.Settings().maxDamage(59)));

    public static final Item WOODEN_BUCKET = register("wooden_bucket", new WoodenBucketItem(Fluids.EMPTY, new Item.Settings().maxCount(16)));
    public static final Item WATER_WOODEN_BUCKET = register("water_wooden_bucket", new WoodenBucketItem(Fluids.WATER, new Item.Settings().recipeRemainder(WOODEN_BUCKET).maxCount(1)));

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
