package net.earlystage.item;

import net.earlystage.compat.EarlyStageDehydrationItems;
import net.earlystage.init.ConfigInit;
import net.earlystage.init.ItemInit;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class BrickBucketItem extends Item implements DispensibleContainerItem {
    private final Fluid fluid;

    public BrickBucketItem(Fluid fluid, Item.Properties settings) {
        super(settings);
        this.fluid = fluid;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        BlockHitResult blockHitResult = BrickBucketItem.getPlayerPOVHitResult(world, user, this.fluid == Fluids.EMPTY ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemStack);
        }
        if (blockHitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getDirection();
            BlockPos blockPos2 = blockPos.relative(direction);
            if (!world.mayInteract(user, blockPos) || !user.mayUseItemAt(blockPos2, direction, itemStack)) {
                return InteractionResultHolder.fail(itemStack);
            }
            if (this.fluid == Fluids.EMPTY) {
                BlockState blockState = world.getBlockState(blockPos);
                if (blockState.getBlock() instanceof BucketPickup drainable && !blockState.getFluidState().isEmpty() && blockState.getFluidState().isSource()
                        && (blockState.getFluidState().is(FluidTags.WATER) || blockState.getFluidState().is(FluidTags.LAVA))) {
                    user.awardStat(Stats.ITEM_USED.get(this));
                    ItemStack itemStack2;
                    if (blockState.getFluidState().is(FluidTags.LAVA)) {
                        world.playSound(user, blockPos, SoundEvents.BUCKET_FILL_LAVA, SoundSource.BLOCKS, 1.0f, 1.0f);
                        itemStack2 = new ItemStack(ItemInit.LAVA_BRICK_BUCKET);
                    } else if (EarlyStageDehydrationItems.isPurifiedWater(blockState.getFluidState())) {
                        world.playSound(user, blockPos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
                        itemStack2 = new ItemStack(EarlyStageDehydrationItems.PURIFIED_WATER_BRICK_BUCKET);
                    } else {
                        world.playSound(user, blockPos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
                        itemStack2 = new ItemStack(ItemInit.WATER_BRICK_BUCKET);
                    }
                    world.gameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
                    ItemStack itemStack3 = ItemUtils.createFilledResult(itemStack, user, itemStack2);
                    if (!world.isClientSide()) {
                        drainable.pickupBlock(user, world, blockPos, blockState);
                        CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) user, itemStack2);
                    }
                    return InteractionResultHolder.sidedSuccess(itemStack3, world.isClientSide());
                }
                return InteractionResultHolder.fail(itemStack);
            }
            BlockState blockState = world.getBlockState(blockPos);
            BlockPos blockPos3 = blockState.getBlock() instanceof LiquidBlockContainer fluidFillable && this.fluid == Fluids.WATER ? blockPos : blockPos2;
            if (this.emptyContents(user, world, blockPos3, blockHitResult)) {
                this.checkExtraContent(user, world, itemStack, blockPos3);
                if (user instanceof ServerPlayer) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) user, blockPos3, itemStack);
                }
                user.awardStat(Stats.ITEM_USED.get(this));
                return InteractionResultHolder.sidedSuccess(BrickBucketItem.getEmptiedStack(itemStack, user, this.fluid), world.isClientSide());
            }
            return InteractionResultHolder.fail(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
    }

    public static ItemStack getEmptiedStack(ItemStack stack, Player player, Fluid fluid) {
        if (!player.getAbilities().instabuild) {
            if (fluid == Fluids.LAVA) {
                return new ItemStack(Items.AIR);
            }
            return new ItemStack(ItemInit.BRICK_BUCKET);
        }
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        if (this.fluid == Fluids.LAVA && ConfigInit.CONFIG.infoTooltips) {
            tooltip.add(Component.translatable("item.earlystage.lava_brick_bucket.tooltip"));
        }
        super.appendHoverText(stack, context, tooltip, type);
    }

    @Override
    public void checkExtraContent(@Nullable Player player, Level world, ItemStack stack, BlockPos pos) {
    }

    @Override
    public boolean emptyContents(@Nullable Player player, Level world, BlockPos pos, @Nullable BlockHitResult hitResult) {
        if (!(this.fluid instanceof FlowingFluid)) {
            return false;
        }
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        boolean bl = blockState.canBeReplaced(this.fluid);
        boolean bl2 = blockState.isAir() || bl || block instanceof LiquidBlockContainer fluidFillable && fluidFillable.canPlaceLiquid(player, world, pos, blockState, this.fluid);
        if (!bl2) {
            return hitResult != null && this.emptyContents(player, world, hitResult.getBlockPos().relative(hitResult.getDirection()), null);
        }
        if (world.dimensionType().ultraWarm() && this.fluid.is(FluidTags.WATER)) {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            world.playSound(player, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5f, 2.6f + (world.random.nextFloat() - world.random.nextFloat()) * 0.8f);
            for (int l = 0; l < 8; ++l) {
                world.addParticle(ParticleTypes.LARGE_SMOKE, (double) i + Math.random(), (double) j + Math.random(), (double) k + Math.random(), 0.0, 0.0, 0.0);
            }
            return true;
        }
        if (block instanceof LiquidBlockContainer && this.fluid == Fluids.WATER) {
            ((LiquidBlockContainer) block).placeLiquid(world, pos, blockState, ((FlowingFluid) this.fluid).getSource(false));
            this.playEmptyingSound(player, world, pos);
            return true;
        }
        if (!world.isClientSide() && bl && !blockState.liquid()) {
            world.destroyBlock(pos, true);
        }
        if (world.setBlock(pos, this.fluid.defaultFluidState().createLegacyBlock(), Block.UPDATE_ALL | Block.UPDATE_IMMEDIATE) || blockState.getFluidState().isSource()) {
            this.playEmptyingSound(player, world, pos);
            return true;
        }
        return false;
    }

    private void playEmptyingSound(@Nullable Player player, LevelAccessor world, BlockPos pos) {
        SoundEvent soundEvent = this.fluid.is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY;
        world.playSound(player, pos, soundEvent, SoundSource.BLOCKS, 1.0f, 1.0f);
        if (this.fluid.is(FluidTags.LAVA) && player != null && !player.isCreative()) {
            world.playSound(player, pos, SoundEvents.GENERIC_BURN, SoundSource.PLAYERS, 0.5f, 1.0f);
        }
        world.gameEvent((Entity) player, GameEvent.FLUID_PLACE, pos);
    }
}
