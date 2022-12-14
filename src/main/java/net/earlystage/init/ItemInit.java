package net.earlystage.init;

import net.earlystage.item.*;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemInit {

    public static final Item FLINT_SWORD = register("flint_sword", new SwordItem(new FlintToolMaterial(), 1, -2.4f, new Item.Settings().group(ItemGroup.COMBAT)));
    public static final Item FLINT_SHOVEL = register("flint_shovel", new ShovelItem(new FlintToolMaterial(), 0f, -3.0f, new Item.Settings().group(ItemGroup.TOOLS)));
    public static final Item FLINT_PICKAXE = register("flint_pickaxe", new PickaxeItem(new FlintToolMaterial(), 0, -2.8f, new Item.Settings().group(ItemGroup.TOOLS)));
    public static final Item FLINT_AXE = register("flint_axe", new AxeItem(new FlintToolMaterial(), 1f, -3.2f, new Item.Settings().group(ItemGroup.TOOLS)));
    public static final Item FLINT_HOE = register("flint_hoe", new FlintHoeItem(new FlintToolMaterial(), 0, -3.0f, new Item.Settings().group(ItemGroup.TOOLS)));

    private static Item register(String id, Item item) {
        return register(new Identifier("earlystage", id), item);
    }

    private static Item register(Identifier id, Item item) {
        return Registry.register(Registry.ITEM, id, item);
    }

    public static void init() {
    }
}
