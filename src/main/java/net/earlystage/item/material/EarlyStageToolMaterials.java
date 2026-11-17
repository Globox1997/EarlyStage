package net.earlystage.item.material;

import net.earlystage.init.ItemInit;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import java.util.function.Supplier;

public class EarlyStageToolMaterials implements Tier {

    public static final Tier FLINT = new EarlyStageToolMaterials(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 29, 1.5f, 0.0f, 0, () -> Items.FLINT, "flint");

    public static final Tier STEEL = new EarlyStageToolMaterials(BlockTags.INCORRECT_FOR_IRON_TOOL, 500, 7.0f, 2.0f, 14, () -> ItemInit.STEEL_INGOT, "steel");

    public static final Tier DAMASCUS_STEEL = new EarlyStageToolMaterials(BlockTags.INCORRECT_FOR_IRON_TOOL, 720, 7.0f, 2.5f, 16, () -> ItemInit.DAMASCUS_STEEL_INGOT, "damascus_steel");

    private final TagKey<Block> inverseTag;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Item> repairItemSupplier;
    private final String name;

    private EarlyStageToolMaterials(TagKey<Block> inverseTag, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Item> repairItemSupplier, String name) {
        this.inverseTag = inverseTag;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairItemSupplier = repairItemSupplier;
        this.name = name;
    }

    @Override
    public int getUses() {
        return this.itemDurability;
    }

    @Override
    public float getSpeed() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return this.inverseTag;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(this.repairItemSupplier.get());
    }

    @Override
    public String toString() {
        return this.name;
    }
}
