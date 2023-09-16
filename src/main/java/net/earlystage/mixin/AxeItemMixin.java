package net.earlystage.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin extends MiningToolItem {

    public AxeItemMixin(float attackDamage, float attackSpeed, ToolMaterial material, TagKey<Block> effectiveBlocks, Settings settings) {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        if (state.isIn(BlockTags.LOGS)) {
            return true;
        }
        return super.isSuitableFor(state);
    }

}
