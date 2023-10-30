package net.earlystage.item;

import net.earlystage.init.ItemInit;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class ClayBucketItem extends Item {

    public ClayBucketItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = ClayBucketItem.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack);
        }
        if (blockHitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getSide();
            BlockPos blockPos2 = blockPos.offset(direction);
            if (!world.canPlayerModifyAt(user, blockPos) || !user.canPlaceOn(blockPos2, direction, itemStack)) {
                return TypedActionResult.fail(itemStack);
            }
            BlockState blockState = world.getBlockState(blockPos);
            if (!blockState.getFluidState().isEmpty() && blockState.getFluidState().isOf(Fluids.LAVA)) {
                user.incrementStat(Stats.USED.getOrCreateStat(this));

                world.playSound(user, blockPos, SoundEvents.ITEM_BUCKET_FILL_LAVA, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.playSound(user, blockPos, SoundEvents.ENTITY_GENERIC_BURN, SoundCategory.BLOCKS, 0.5f, 1.0f);

                world.emitGameEvent((Entity) user, GameEvent.FLUID_PICKUP, blockPos);
                ItemStack itemStack2 = new ItemStack(ItemInit.LAVA_BRICK_BUCKET);
                ItemStack itemStack3 = ItemUsage.exchangeStack(itemStack, user, itemStack2);
                if (!world.isClient()) {
                    world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
                    Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity) user, itemStack2);
                }
                return TypedActionResult.success(itemStack3, world.isClient());
            }
            return TypedActionResult.fail(itemStack);
        }
        return TypedActionResult.pass(itemStack);
    }

}
