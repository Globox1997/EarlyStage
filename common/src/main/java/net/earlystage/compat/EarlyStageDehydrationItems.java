package net.earlystage.compat;

import net.earlystage.init.ItemInit;
import net.earlystage.item.BrickBucketItem;
import net.earlystage.item.WoodenBucketItem;
import java.util.function.BiConsumer;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public class EarlyStageDehydrationItems {

    public static final Identifier PURIFIED_WATER_ID = Identifier.fromNamespaceAndPath("dehydration", "purified_water");

    public static Item PURIFIED_WATER_WOODEN_BUCKET = null;
    public static Item PURIFIED_WATER_BRICK_BUCKET = null;

    public static void register(BiConsumer<Identifier, Item> registrar) {
        Fluid purifiedWater = BuiltInRegistries.FLUID.getValue(PURIFIED_WATER_ID);
        PURIFIED_WATER_WOODEN_BUCKET = ItemInit.register(registrar, "purified_water_wooden_bucket", properties -> new WoodenBucketItem(purifiedWater, properties), new Item.Properties().stacksTo(1));
        PURIFIED_WATER_BRICK_BUCKET = ItemInit.register(registrar, "purified_water_brick_bucket", properties -> new BrickBucketItem(purifiedWater, properties), new Item.Properties().stacksTo(1));
    }

    public static boolean isPurifiedWater(FluidState fluidState) {
        return PURIFIED_WATER_WOODEN_BUCKET != null && fluidState.is(BuiltInRegistries.FLUID.getValue(PURIFIED_WATER_ID));
    }

}
