package net.earlystage.compat;

import net.dehydration.api.DehydrationAPI;
import net.dehydration.api.FluidBehavior;
import net.earlystage.EarlyStageMain;
import net.earlystage.init.ItemInit;
import net.earlystage.item.BrickBucketItem;
import net.earlystage.item.WoodenBucketItem;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class EarlyStageDehydrationIntegration implements DehydrationAPI {

    public static Item PURIFIED_WATER_WOODEN_BUCKET;
    public static Item PURIFIED_WATER_BRICK_BUCKET;

    @Override
    public int calculateDrinkThirst(ItemStack itemStack, PlayerEntity playerEntity) {
        return 0;
    }

    @Override
    public void registerFluidBehaviors() {
        ResourceManagerHelper.registerBuiltinResourcePack(EarlyStageMain.identifierOf("dehydration_earlystage_compat"), FabricLoader.getInstance().getModContainer("earlystage").orElseThrow(),
                ResourcePackActivationType.DEFAULT_ENABLED);
        PURIFIED_WATER_WOODEN_BUCKET = ItemInit.register("purified_water_wooden_bucket", new WoodenBucketItem(Registries.FLUID.get(Identifier.of("dehydration:purified_water")), new Item.Settings().maxCount(1)));
        PURIFIED_WATER_BRICK_BUCKET = ItemInit.register("purified_water_brick_bucket", new BrickBucketItem(Registries.FLUID.get(Identifier.of("dehydration:purified_water")), new Item.Settings().maxCount(1)));

        FluidBehavior.registerWaterBucketForCauldron(ItemInit.WOODEN_BUCKET, ItemInit.WATER_WOODEN_BUCKET);
        FluidBehavior.registerWaterBucketForCauldron(ItemInit.BRICK_BUCKET, ItemInit.WATER_BRICK_BUCKET);
        FluidBehavior.registerWaterBucketForCopperCauldron(ItemInit.BRICK_BUCKET, ItemInit.WATER_BRICK_BUCKET);
        FluidBehavior.registerWaterBucketForCopperCauldron(ItemInit.WATER_BRICK_BUCKET, ItemInit.BRICK_BUCKET);
        FluidBehavior.registerWaterBucketForCopperCauldron(ItemInit.WOODEN_BUCKET, ItemInit.WATER_WOODEN_BUCKET);
        FluidBehavior.registerWaterBucketForRainwaterCollector(ItemInit.BRICK_BUCKET, ItemInit.WATER_BRICK_BUCKET);
        FluidBehavior.registerWaterBucketForRainwaterCollector(ItemInit.WOODEN_BUCKET, ItemInit.WATER_WOODEN_BUCKET);
        FluidBehavior.registerPurifiedWaterBucket(ItemInit.WOODEN_BUCKET, PURIFIED_WATER_WOODEN_BUCKET);
        FluidBehavior.registerPurifiedWaterBucket(ItemInit.BRICK_BUCKET, PURIFIED_WATER_BRICK_BUCKET);
    }
}
