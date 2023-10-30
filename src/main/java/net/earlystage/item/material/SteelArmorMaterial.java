package net.earlystage.item.material;

import net.minecraft.item.ArmorItem.Type;
import net.earlystage.init.ItemInit;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class SteelArmorMaterial implements ArmorMaterial {

    private static final int[] BASE_DURABILITY = new int[] { 13, 15, 16, 11 };
    private static final int[] PROTECTION_AMOUNTS = new int[] { 3, 7, 5, 2 };

    @Override
    public int getDurability(Type type) {
        return BASE_DURABILITY[type.ordinal()] * 20;
    }

    @Override
    public int getProtection(Type type) {
        return PROTECTION_AMOUNTS[type.ordinal()];
    }

    @Override
    public int getEnchantability() {
        return 9;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ItemInit.STEEL_INGOT);
    }

    @Override
    public String getName() {
        return "steel";
    }

    @Override
    public float getToughness() {
        return 0.0F;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.0F;
    }

}
