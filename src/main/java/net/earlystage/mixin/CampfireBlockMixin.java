package net.earlystage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.earlystage.init.TagInit;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

@Mixin(CampfireBlock.class)
public abstract class CampfireBlockMixin extends BaseEntityBlock {

    public CampfireBlockMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    private void onUseWithItemMixin(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<ItemInteractionResult> info) {
        if (CampfireBlock.canLight(state) && player.getItemInHand(hand).is(TagInit.BARK_ITEMS)) {
            if (!world.isClientSide()) {
                world.setBlockAndUpdate(pos, state.setValue(CampfireBlock.LIT, true));
                if (!player.isCreative()) {
                    player.getItemInHand(hand).shrink(1);
                }
                player.awardStat(Stats.INTERACT_WITH_CAMPFIRE);
                info.setReturnValue(ItemInteractionResult.SUCCESS);
            } else {
                info.setReturnValue(ItemInteractionResult.CONSUME);
            }
        }
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, placer, itemStack);
        world.setBlockAndUpdate(pos, state.setValue(CampfireBlock.LIT, false));
    }

}
