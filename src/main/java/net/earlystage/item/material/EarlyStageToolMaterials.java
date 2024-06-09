package net.earlystage.item.material;

import net.earlystage.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

public class EarlyStageToolMaterials implements ToolMaterial {

    public static final ToolMaterial FLINT = new EarlyStageToolMaterials(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 29, 1.5f, 0.0f, 0, Ingredient.ofItems(Items.FLINT), "flint");

    public static final ToolMaterial STEEL = new EarlyStageToolMaterials(BlockTags.INCORRECT_FOR_IRON_TOOL, 500, 7.0f, 2.0f, 14, Ingredient.ofItems(ItemInit.STEEL_INGOT), "steel");

    private final TagKey<Block> inverseTag;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Ingredient repairIngredient;
    private final String name;

    private EarlyStageToolMaterials(TagKey<Block> inverseTag, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Ingredient repairIngredient, String name) {
        this.inverseTag = inverseTag;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
        this.name = name;
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return this.inverseTag;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
