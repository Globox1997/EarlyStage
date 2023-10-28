package net.earlystage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.earlystage.item.BarkItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin extends MiningToolItem {

    public AxeItemMixin(float attackDamage, float attackSpeed, ToolMaterial material, TagKey<Block> effectiveBlocks, Settings settings) {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
    }

    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V", ordinal = 0), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void useOnBlockMixin(ItemUsageContext context, CallbackInfoReturnable<ActionResult> info, World world, BlockPos blockPos, PlayerEntity playerEntity, BlockState blockState) {
        if (!world.isClient()) {
            ItemEntity itemEntity = new ItemEntity(world, context.getHitPos().getX(), context.getHitPos().getY(), context.getHitPos().getZ(),
                    new ItemStack(BarkItem.BARK_ITEMS.get(blockState.getBlock())));
            itemEntity.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity);
        }
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        if (state.isIn(BlockTags.LOGS)) {
            return true;
        }
        return super.isSuitableFor(state);
    }

}
