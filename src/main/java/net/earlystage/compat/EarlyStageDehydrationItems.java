package net.earlystage.compat;

import net.earlystage.EarlyStageMain;
import net.earlystage.init.ItemInit;
import net.earlystage.item.BrickBucketItem;
import net.earlystage.item.WoodenBucketItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;

public class EarlyStageDehydrationItems {

    public static Item PURIFIED_WATER_WOODEN_BUCKET = ItemInit.register("purified_water_wooden_bucket", new WoodenBucketItem(Registries.FLUID.get(EarlyStageMain.identifierOf("purified_water")), new Item.Settings().maxCount(1)));
    public static Item PURIFIED_WATER_BRICK_BUCKET = ItemInit.register("purified_water_brick_bucket", new BrickBucketItem(Registries.FLUID.get(EarlyStageMain.identifierOf("purified_water")), new Item.Settings().maxCount(1)));

    public static void init(){
    }

}
